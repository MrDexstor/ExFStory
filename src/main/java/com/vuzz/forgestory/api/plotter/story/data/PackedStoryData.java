package com.vuzz.forgestory.api.plotter.story.data;

import java.util.ArrayList;

public class PackedStoryData {
    public String id = "";
    public String compiler = "plotter";
    public String version = "2";
    public ArrayList<SceneJSON> scenes = new ArrayList<SceneJSON>();
    public ArrayList<PackedScriptData> scripts = new ArrayList<PackedScriptData>();
    public ArrayList<PackedLibData> libs = new ArrayList<PackedLibData>();
}