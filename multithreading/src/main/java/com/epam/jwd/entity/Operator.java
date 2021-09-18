package com.epam.jwd.entity;

public abstract class Operator {
    private volatile boolean busy;

    public boolean isBusy() {
        return busy;
    }

    public void setBusy(boolean busy) {
        this.busy = busy;
    }

    public abstract void service();
}
