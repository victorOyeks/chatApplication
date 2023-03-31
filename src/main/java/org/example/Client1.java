package org.example;

public class Client1 {
    public static void main(String[] args) {
        Client client1 = new Client("localhost", 500);
        client1.start();
    }
}