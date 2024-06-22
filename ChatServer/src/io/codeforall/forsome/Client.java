package io.codeforall.forsome;

import io.codeforall.forsome.Command.Command;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;

public class Client {
    private Worker worker;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private HashMap<String, Command> hashMap;
    public Client(String serverAddress, int serverPort) {

        System.out.println("Trying to establishing the connection, please wait...");
        try {
            clientSocket = new Socket(serverAddress, serverPort);

            in = new BufferedReader(new InputStreamReader(System.in));
            out = new PrintWriter(this.clientSocket.getOutputStream(),true);
            while(clientSocket.isConnected()){
                try {
                    String message = in.readLine();
                    out.println(message);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        new Client("localhost",8090);
    }
}
