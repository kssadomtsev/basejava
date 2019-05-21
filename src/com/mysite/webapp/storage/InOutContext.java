package com.mysite.webapp.storage;

import com.mysite.webapp.model.Resume;

public class InOutContext<O, I> {
    private InOutStrategy strategy;

    public void setStrategy(InOutStrategy strategy) {
        this.strategy = strategy;
    }

    public void executeDoWrite(Resume r, O out) throws Exception {
        strategy.doWrite(r, out);
    }

    public Resume executeDoRead(I in) throws Exception {
        return strategy.doRead(in);
    }
}
