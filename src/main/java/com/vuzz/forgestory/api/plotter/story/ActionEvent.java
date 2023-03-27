package com.vuzz.forgestory.api.plotter.story;

import com.vuzz.forgestory.annotations.Documentate;

public class ActionEvent {

    public int type = 0;

    @Documentate(desc = "Create default action event. Activates on PlayAction keybinding.")
    public static ActionEvent DEF() {
        return new ActionEvent();
    }

    @Documentate(desc = "Creates on message sent action event. Activates when a message with defined keywords is sent.")
    public static ActionEvent MSG_SENT(String[] keyWords) {
        return new MessageSentActionEvent(keyWords);
    }

    public static class MessageSentActionEvent extends ActionEvent {
        public final String[] keyWords;
        public int type = 1;
        public MessageSentActionEvent(String[] keyWords) { this.type = 1; super.type = 1; this.keyWords = keyWords; }
    }

}
