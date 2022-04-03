import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CPSS {
    private ServerSocket ss;
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
                            // Message reg = (Message) in.readObject();
                            sendMessage("A new user has connected");
                            threads[numUsers] = new UserThread(newSocket, numUsers, in);
                            threads[numUsers].start();
                            System.out.println("Connection " + numUsers + users[numUsers]);
                            numUsers++;
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

    private class UserThread extends Thread {
        private Socket mySocket;
        private ObjectInputStream myInputStream;
        private ObjectOutputStream myOutputStream;
        private int myId;

        private UserThread(Socket newSocket, int id, ObjectInputStream in) throws IOException {
            mySocket = newSocket;
            myId = id;
            myInputStream = in;
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
                        CPSS.this.sendMessage("hi");
                        Message m = new Message(str);
                        switch (m.getType()) {
                            case "reg":
                                // CPSS.this.sendMessage("got reg");
                                System.out.println("got reg");
                                break;
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
    }
}
