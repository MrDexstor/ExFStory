package com.vuzz.forgestory.api.plotter.js.event;

public class MessageEvent implements EventJS {

    public String message;

    public MessageEvent(String message) {
        this.message = message;
    }

    @Override
    public String getName() {return "message";}


}
