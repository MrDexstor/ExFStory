package com.vuzz.forgestory.api.plotter.story;

import java.util.function.Consumer;

import com.vuzz.forgestory.api.plotter.story.data.ActionPacketData;

public class Action {
    
    private Consumer<ActionPacketData> actionFunc;
    private ActionEvent event;

    public Action(Consumer<ActionPacketData> act, ActionEvent ev) {
        actionFunc = act;
        event = ev;
    }

    public Consumer<ActionPacketData> getActionFunc() {
        return actionFunc;
    }

    public ActionEvent getEvent() {
        return event;
    }

}
