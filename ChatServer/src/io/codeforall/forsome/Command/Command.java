package io.codeforall.forsome.Command;

import io.codeforall.forsome.Worker;

public interface Command {

    void init(Worker worker, String message);
}
