
/**
 * The main server class.
 */

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.FileHandler;

public class CPSS {

    private final int MaxUsers = 9;
    private ServerSocket ss;
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
                            // Message reg = (Message) in.readObject();
                            sendMessage("A new user has connected");
                            threads[numUsers] = new UserThread(newSocket, numUsers, in, numUsers >= 2, numUsers == 0);
                            threads[numUsers].start();
                            System.out.println("Connection " + numUsers + users[numUsers]);
                            numUsers++;
                            if (numUsers == 2) {
                                startMatch();
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
                System.out.println("Problem sending message");
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
        sendMessage("Client#" + id + " disconnected.");
    }

    public void startMatch() {
        sendMessage("init");
    }

    private class UserThread extends Thread {
        private Socket mySocket;
        private ObjectInputStream myInputStream;
        private ObjectOutputStream myOutputStream;
        private int myId;

        private boolean myTurn = false;
        private boolean isSpectator;
        private int cupsLeft;
        private int shots = 0;
        private int shotsMade = 0;

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
                                    }
                                    sendMessage("register", new String[] { "status:" + registered });
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
                                    }
                                    sendMessage("login", new String[] { "status:" + loggedin });
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
                                    sendMessage("reg", new String[] { "id:" + myId, "turn:" + myTurn });
                                    break;
                                case "throw": // user threw the ball
                                    int made = m.getInt("made");
                                    shots++;
                                    if (made != -1) {
                                        shotsMade++;
                                    }
                                    if (shots == 2) {
                                        if (shotsMade == 2) {
                                            broadcast("turn",
                                                    new String[] { "id:" + myId, "turn:true", "misc:ballsback" });
                                        } else {
                                            broadcast("turn", new String[] { "id:" + myId, "turn:false" });
                                        }
                                    }
                                    break;
                                case "disc": // user just disconnected
                                    alive = false;
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
                    System.out.println("Client#" + myId + " disconnected.");
                    alive = false;
                }
            }
            removeClient(myId);
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
    }
}
