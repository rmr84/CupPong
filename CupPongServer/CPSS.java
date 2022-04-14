
/**
 * The main server class.
 */

import java.io.*;
import java.net.*;

public class CPSS {

    private final int MaxUsers = 9;
    private Socket[] users;
    private UserThread[] threads;
    private int numUsers;

    public CPSS(int port) {
        users = new Socket[MaxUsers];
        threads = new UserThread[MaxUsers]; // Set things up and start
        numUsers = 0; // Server running
        try {
            runServer(port);
        } catch (Exception e) {
            System.out.println("Problem with server");
        }
    }

    private void runServer(int port) throws IOException {
        ServerSocket s = new ServerSocket(port);
        System.out.println("Started: " + s);

        try {
            while (true) {
                if (numUsers < MaxUsers) {
                    try {
                        Socket newSocket = s.accept();
                        synchronized (this) {
                            users[numUsers] = newSocket;
                            ObjectInputStream in = new ObjectInputStream(newSocket.getInputStream());
                            threads[numUsers] = new UserThread(newSocket, numUsers, in, numUsers >= 2, numUsers == 0);
                            threads[numUsers].start();
                            System.out.println("Connection " + numUsers + users[numUsers]);
                            numUsers++;
                            if (numUsers == 2) {
                                startMatch();
                                System.out.println("#################################");
                                System.out.println("New match started.");
                                System.out.println("#################################");
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Problem with connection...terminating");
                    }
                }
            }
        } finally {
            System.out.println("Server shutting down");
        }
    }

    public synchronized void sendMessage(String msg) {
        for (int i = 0; i < numUsers; i++) {
            ObjectOutputStream out = threads[i].getOutputStream();
            try {
                out.writeObject(msg);
                out.flush();
            } catch (IOException e) {
                // System.out.println("Problem sending message");
            }
        }
    }

    public synchronized void removeClient(int id) {
        try {
            users[id].close();
        } catch (IOException e) {
            System.out.println("Client#" + id + " is not connected.");
        }
        users[id] = null;
        threads[id] = null;
        // fill up "gap" in arrays
        for (int i = id; i < numUsers - 1; i++) {
            users[i] = users[i + 1];
            threads[i] = threads[i + 1];
            threads[i].setId(i);
        }
        numUsers--;
    }

    public void startMatch() {
        sendMessage("init");
    }

    private class UserThread extends Thread {
        private Socket mySocket;
        private ObjectInputStream myInputStream;
        private ObjectOutputStream myOutputStream;
        private int myId;
        private String name = "guest";

        private boolean myTurn = false;
        private boolean isSpectator;
        private int cupsLeft = 6;
        private int shots = 0;
        private int shotsMade = 0;

        private int STAT_MADE = 0;
        private int STAT_THROWS = 0;
        private int STAT_BACK = 0;

        private int turn = 1;

        private UserState state = UserState.NOTCONNECTED;

        private UserThread(Socket newSocket, int id, ObjectInputStream in, boolean spectator, boolean turn)
                throws IOException {
            mySocket = newSocket;
            myId = id;
            myInputStream = in;
            isSpectator = spectator;
            if (isSpectator) {
                myTurn = false;
            } else {
                myTurn = turn;
            }
            myOutputStream = new ObjectOutputStream(newSocket.getOutputStream());
        }

        public ObjectInputStream getInputStream() {
            return myInputStream;
        }

        public ObjectOutputStream getOutputStream() {
            return myOutputStream;
        }

        public synchronized void setId(int newId) {
            myId = newId;
        }

        @Override
        public void run() {
            boolean alive = true;
            while (alive) {
                String str = null;
                try {
                    str = (String) myInputStream.readObject();
                    synchronized (this) {
                        Message m = new Message(str);
                        if (!m.isEmpty()) {
                            switch (m.getType()) {
                                case "register": // user is trying to register
                                    if (state != UserState.NOTCONNECTED) {
                                        sendMessage("sys", new String[] { "msg:You are already signed in." });
                                        break;
                                    }

                                    String user = m.getString("user");
                                    String pass = m.getString("pass");

                                    System.out.println("Register: " + user + ", " + pass);

                                    boolean registered = FileManager.getInstance().register(m.getString("user"),
                                            m.getString("pass"));
                                    if (registered) {
                                        state = UserState.LOBBY;
                                        name = user;
                                    }
                                    sendMessage("register", new String[] { "status:" + registered, "name:" + name });
                                    break;
                                case "login": // user is trying to log in
                                    if (state != UserState.NOTCONNECTED) {
                                        sendMessage("sys", new String[] { "msg:You are already signed in." });
                                        break;
                                    }
                                    boolean loggedin = FileManager.getInstance().login(m.getString("user"),
                                            m.getString("pass"));
                                    if (loggedin) {
                                        state = UserState.LOBBY;
                                        name = m.getString("user");
                                    }
                                    sendMessage("login", new String[] { "status:" + loggedin, "name:" + name });
                                    break;
                                case "reg": // user just joined a match
                                    if (state == UserState.NOTCONNECTED) {
                                        // this shouldnt be possible
                                        sendMessage("leave", new String[0]);
                                        break;
                                    }
                                    if (state == UserState.GAME) {
                                        sendMessage("sys", new String[] { "msg:You are already in a game." });
                                    }
                                    state = UserState.GAME;
                                    cupsLeft = m.getInt("cups");
                                    sendMessage("reg", new String[] { "turn:" + myTurn });
                                    break;
                                case "throw": // user threw the ball [deprecated]
                                    break;
                                case "rball": // reset ball: either in cup or missed
                                    boolean counted = m.getBool("counted");
                                    if (!counted) {
                                        break;
                                    }
                                    shots++;
                                    STAT_THROWS++;
                                    int made = m.getInt("made");
                                    System.out.println("[Throw] from " + name + ". Shots this turn: " + shots);

                                    if (made != -1) {
                                        System.out.println("\t-Made: " + (made > -1 ? "True" : "False"));
                                        broadcast("cup", new String[] { "name:" + name, "id:" + getCup(made) });
                                        System.out.println("Broadcasted [cup] ID: " + made);
                                        shotsMade++;
                                        STAT_MADE++;
                                        cupsLeft--;
                                        System.out.println("\t-Cups left: " + cupsLeft);
                                        if (cupsLeft == 0) {
                                            broadcast("gameover", new String[] { "winner:" + name });
                                            System.out.println("#################################");
                                            System.out.println("Game is over. " + name + " wins.");
                                            System.out.println("#################################");
                                            shots = 0;
                                            shotsMade = 0;
                                        }
                                    }

                                    if (shots == 2) {

                                        if (shotsMade == 2) {
                                            STAT_BACK++;
                                            broadcast("turn",
                                                    new String[] { "name:" + name, "turn:true", "misc:ballsback" });
                                            System.out.println(name + " got balls back. It is their turn.");
                                        } else {
                                            broadcast("turn",
                                                    new String[] { "name:" + name, "turn:true", "misc:null" });
                                            System.out.println(name + " used 2 shots. It is not their turn.");
                                        }
                                        shots = 0;
                                        shotsMade = 0;
                                    } else {
                                        sendMessage("throw", new String[] { "made:" + (made > -1) });
                                    }

                                    break;
                                case "disc": // user just disconnected
                                    System.out.println(name + "[#" + myId + "] disconnected.");
                                    alive = false;
                                    break;
                                case "join":
                                    broadcast("join", new String[] { "name:" + name });
                                    break;
                                case "leave":
                                    broadcast("leave", new String[] { "name:" + name });
                                    break;
                                case "win":
                                    FileManager.getInstance().writestats(name, newStats(true));
                                    STAT_MADE = 0;
                                    STAT_THROWS = 0;
                                    STAT_BACK = 0;
                                    break;
                                case "lose":
                                    FileManager.getInstance().writestats(name, newStats(false));
                                    STAT_MADE = 0;
                                    STAT_THROWS = 0;
                                    STAT_BACK = 0;
                                    break;
                                case "stats":
                                    sendMessage("stats",
                                            new String[] { "s:" + FileManager.getInstance().getstats(name) });
                                    break;
                                default: // uncaught message type
                                    sendMessage("sys",
                                            new String[] { "msg:Type '" + m.getType() + "' is invalid." });
                                    break;
                            }
                        } else {
                            sendMessage("sys", new String[] { "msg:Message was empty." });
                        }
                    }
                } catch (ClassNotFoundException e) {
                    System.out.println("Error receiving message....shutting down");
                    alive = false;
                } catch (IOException e) {
                    System.out.println(name + "[#" + myId + "] disconnected.");
                    alive = false;
                }
            }
            System.out.println("removing");
            removeClient(myId);
        }

        public int getCup(int i) {
            return 11 - i;

            /*
             * 
             * switch (i) {
             * case 6:
             * return 5;
             * case 7:
             * return 4;
             * case 8:
             * return 3;
             * case 9:
             * return 2;
             * case 10:
             * return 1;
             * case 11:
             * return 0;
             * default:
             * return 0;
             * 
             * }
             */
        }

        public void broadcast(String type, String[] params) {
            String p = "";
            for (String s : params) {
                p += "," + s;
            }
            p = type + p;
            CPSS.this.sendMessage(p);
        }

        public void sendMessage(String type, String[] params) {

            String p = "";
            for (String s : params) {
                p += "," + s;
            }
            p = type + p;
            try {
                myOutputStream.writeObject(p);
                myOutputStream.flush();
            } catch (IOException e) {
                System.out.println("Could not send message to user.");
            }
        }

        public String newStats(boolean win) {
            String[] stats_str = FileManager.getInstance().getstats(name).split(",");
            int[] stats = new int[5];
            for (int i = 0; i < stats_str.length; i++) {
                stats[i] = Integer.parseInt(stats_str[i]);
            }
            stats[win ? 0 : 1]++;
            stats[2] += STAT_MADE;
            stats[3] += STAT_THROWS - STAT_MADE;
            stats[4] += STAT_BACK;
            return stats[0] + "," + stats[1] + "," + stats[2] + "," + stats[3] + "," + stats[4];
        }
    }
}
