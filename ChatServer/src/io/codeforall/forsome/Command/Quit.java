package io.codeforall.forsome.Command;

import io.codeforall.forsome.Server;
import io.codeforall.forsome.Worker;

import java.io.IOException;

public class Quit implements Command{
    private Server server;

    public Quit(Server server) {
        this.server = server;
    }

    @Override
    public void init(Worker worker, String message) {
        try{
            worker.getClientSocket().close();

        }catch (IOException e){
            System.err.println("error in Quit init");
        }
    }
}
