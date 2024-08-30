package io.codeforall.forsome;

import io.codeforall.forsome.Command.*;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private final int DEFAULTPORT = 8090;
    private ServerSocket serverSocket;
    private Worker worker;
    protected List<Worker> listOfClients;
    private ExecutorService cachedPool;
    private HashMap<String, Command> hashMap;
    private int numberOfClient = 1;

    public Server() {
        listOfClients = Collections.synchronizedList(new LinkedList<>());
        hashMap = buildMap();
        cachedPool = Executors.newCachedThreadPool();
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    public void start() {
        try {
            System.out.println("Binding");
            serverSocket = new ServerSocket(DEFAULTPORT);

            System.out.println("Server started and waiting for connection. Server: " + serverSocket);

            dispatch();

        } catch (IOException e) {
            System.err.println(e.getMessage() + " Error in Start method");
        }
    }

    public void dispatch() {

        while (true) {
            try {
                Socket clientSocket = serverSocket.accept();

                worker = new Worker("client " + numberOfClient, clientSocket, this, hashMap);
                numberOfClient++;

                listOfClients.add(worker); //add the worker to the list
                cachedPool.submit(worker); //add the worker to a thread

                System.out.println(worker.getName() + " has joined the chat");

            } catch (IOException e) {
                System.err.println(e.getMessage() + " Error in dispatch method");
            }
        }
    }

    public void broadcast(String name, String message) {
        for (Worker w : listOfClients) {
            if (!w.getName().equals(name)) {
                w.send("["+name+"]" + " " + message);
            }
        }
    }

    public String listClients() {

        StringBuilder sb = new StringBuilder();
        for (Worker w : listOfClients) {
            sb.append(" - ");
            sb.append(w.getName());
            sb.append("\n");
        }
        return sb.toString();
    }
    public void whisper(String targetName, String message) {
        for (Worker w : listOfClients) {
            if (w.getName().equals(targetName)) {
                System.out.println("I got here on server.whisper");
                return;
            }
        }
        System.out.println("User '" + targetName + "' not found.");
    }

    public HashMap<String, Command> buildMap() {

        HashMap<String, Command> hashMap = new HashMap<>();

        hashMap.put("/list", new ListClient(this));
        hashMap.put("/quit", new Quit(this));
        hashMap.put("/whisper", new Whisper(this));
        hashMap.put("", new Message(this));

        return hashMap;
    }
}
