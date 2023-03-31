package org.example;

import java.io.*;
import java.net.*;
import java.util.*;

public class Server {
    private static final List<Socket> clients = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(500);
            System.out.println("Server started on port 500");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                clients.add(clientSocket);
                System.out.println("New client connected: " + clientSocket.getInetAddress().getHostAddress());

                ClientHandler clientHandler = new ClientHandler(clientSocket);
                Thread t = new Thread(clientHandler);
                t.start();
            }

        } catch (IOException e) {
            System.out.println("Error Message: " + e.getMessage());
        }
    }
    public static void broadcast(String message, Socket sender) throws IOException {

        for(int i = 0; i < clients.size(); i++) {
            Socket client = clients.get(i);

            if (client != sender) {
                PrintWriter output = new PrintWriter(client.getOutputStream(), true);
                output.println(message);
            }
        }
    }
}

