package io.codeforall.forsome.Command;

import io.codeforall.forsome.Server;
import io.codeforall.forsome.Worker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;

public class Whisper implements Command {
    private Server server;
    private BufferedReader in;
    private PrintWriter out;
    private String recipient;

    public Whisper(Server server) {
        this.server = server;
    }

    @Override
    public void init(Worker worker, String message) {
        String[] array = message.split(" ");

        //server.whisper(array[1], message);
        System.out.println("Whispering to :" + message);
    }
}
