package com.vuzz.forgestory.api.plotter.story.data;

import com.vuzz.forgestory.annotations.Documentate;
import com.vuzz.forgestory.api.plotter.story.instances.SceneInstance;

public class ActionPacketData {
    @Documentate(desc = "Is PlayAction keybinding pressed.")
    public boolean playKeyPressed = false;

    @Documentate(desc = "Returns sent message (if sent).")
    public String messageSent = "";

    @Documentate(desc = "Current scene.")
    public SceneInstance scene;
}
