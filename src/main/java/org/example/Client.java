package org.example;

import java.io.*;
import java.net.Socket;
import java.lang.Thread;

public class Client{

    private final String serverName;
    private final int serverPort;
    private String username;

    public Client(String serverName, int serverPort) {
        this.serverName = serverName;
        this.serverPort = serverPort;
    }

    public void start() {
        try {
            System.out.println("Enter username:");
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
            username = console.readLine();

            Socket socket = new Socket(serverName, serverPort);
            System.out.println("You have been connected to the chat server.");

            ServerListener serverListener = new ServerListener(socket);
            Thread thread = new Thread(serverListener);
            thread.start();

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            String input;
            while ((input = console.readLine()) != null) {
                out.println(username + ": " + input);
            }
            out.close();
            console.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static class ServerListener implements Runnable {
        private final Socket socket;

        public ServerListener(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String input;
                while ((input = in.readLine()) != null) {
                    System.out.println(input);
                }
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}