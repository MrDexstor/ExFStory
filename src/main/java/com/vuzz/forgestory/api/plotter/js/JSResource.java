package com.vuzz.forgestory.api.plotter.js;

public interface JSResource {
    Object getNative();
    String getResourceId();
    boolean isClient();
}
