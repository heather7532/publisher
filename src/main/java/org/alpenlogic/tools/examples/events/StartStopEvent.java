package org.alpenlogic.tools.examples.events;

import org.springframework.context.ApplicationEvent;

public class StartStopEvent extends ApplicationEvent {

    private final String msg;
    private final int max;
    private final boolean stop;

    public StartStopEvent(Object source, String msg, int max, boolean stop) {
        super(source);
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