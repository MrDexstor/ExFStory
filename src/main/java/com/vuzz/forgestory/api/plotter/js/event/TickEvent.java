package com.vuzz.forgestory.api.plotter.js.event;

import com.vuzz.forgestory.annotations.Documentate;

public class TickEvent implements EventJS {

    @Documentate(desc = "Ticks passed")
    public int ticks;
    public TickEvent(int ticks) {this.ticks = ticks;}

    @Override
    public String getName() {return "tick";}


}
