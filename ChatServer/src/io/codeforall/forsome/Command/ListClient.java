package io.codeforall.forsome.Command;

import io.codeforall.forsome.Server;
import io.codeforall.forsome.Worker;

public class ListClient implements Command{
    private Server server;

    public ListClient(Server server) {
        this.server = server;
    }

    @Override
    public void init(Worker worker, String message) {
        worker.send(server.listClients().trim());
    }
}
