package com.vuzz.forgestory.api.plotter.util;

import java.io.File;
import java.io.FileFilter;

public class Filters {
    public static FileFilter onlyDir = File::isDirectory;
    public static FileFilter onlyStory = (file) -> {return file.getName().endsWith(".fs");};
    public static FileFilter onlyJs = (file) -> {return file.getName().endsWith(".js");};
    public static FileFilter onlyJson = (file) -> {return file.getName().endsWith(".json");};
}
