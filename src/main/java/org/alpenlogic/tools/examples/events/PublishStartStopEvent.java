package org.alpenlogic.tools.examples.events;

public class PublishStartStopEvent {
    private final String msg;
    private final int max;
    private final boolean stop;

    public PublishStartStopEvent(String msg, int max, boolean stop) {
        this.msg = msg;
        this.max = max;
        this.stop = stop;
    }

    public String getMsg() {
        return msg;
    }

    public int getMax() {
        return max;
    }

    public boolean isStop() {
        return stop;
    }
}