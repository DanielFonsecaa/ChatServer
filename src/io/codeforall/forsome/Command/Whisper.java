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

    public Whisper(Server server) {
        this.server = server;
    }

    @Override
    public void init(Worker worker, String message) {
        if (message.startsWith("/whisper")) {
            String[] parts = message.split(" ", 3); // Split into at most 3 parts
            if (parts.length >= 3) {
                String target = parts[1];
                String whisperMessage = parts[2];
                server.whisper(target, whisperMessage);
            } else {
                worker.send("Invalid whisper format. Use: /whisper <target> <message>");
            }
        }
    }
}
