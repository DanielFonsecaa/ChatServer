package io.codeforall.forsome;

import io.codeforall.forsome.Command.Command;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public class Worker implements Runnable {

    private String name;
    private final Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private final Server server;
    private final HashMap<String, Command> hashMap;

    public Worker(String name, Socket clientSocket, Server server, HashMap<String, Command> stratMap) throws IOException {
        this.name = name;
        this.clientSocket = clientSocket;
        this.out = new PrintWriter(this.clientSocket.getOutputStream(), true);
        this.in = new BufferedReader(new InputStreamReader(this.clientSocket.getInputStream()));
        this.server = server;
        this.hashMap = stratMap;
    }

    public Socket getClientSocket() {
        return clientSocket;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void send(String message) {
        out.println(message);
    }


    @Override
    public void run() {
        try {
            out.println("your name: ");
            String name = in.readLine();  //First message client write will be his name
            setName(name);

            while (!clientSocket.isClosed()) {
                String message = in.readLine(); //read the message

                if (message == null) { // if press control C
                    break;
                }

                Command hashM = ((hashMap.get(message) == null) ? hashMap.get("") : hashMap.get(message)); // verify if it's a message or a command
                hashM.init(this, message);
                System.out.println(getName() + " " + message);

            }
            server.broadcast(this.name, " has left the chat");
            server.listOfClients.remove(this); //remove the worker from the list

        } catch (IOException e) {
            System.err.println(e.getMessage() + "error in changeName");
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
