package io.codeforall.forsome.Command;

import io.codeforall.forsome.Command.Command;
import io.codeforall.forsome.Server;
import io.codeforall.forsome.Worker;

public class Message implements Command {
    private Server server;

    public Message(Server server) {
        this.server = server;
    }

    @Override
    public void init(Worker worker, String message) {

        server.broadcast(worker.getName(), message);
    }
}
