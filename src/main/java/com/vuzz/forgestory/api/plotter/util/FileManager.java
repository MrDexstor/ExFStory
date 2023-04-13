package com.vuzz.forgestory.api.plotter.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.function.Consumer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;

public class FileManager {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void listInDirAndDo(File f, FileFilter filter, Consumer<File> cb) {
        f.mkdir();
        File[] files = f.listFiles(filter);
        for(File ff : files) cb.accept(ff);
    }

    public static void listInDirAndDo(File f, String dir, FileFilter filter, Consumer<File> cb) 
    { 
        f.mkdir();
        listInDirAndDo(new File(f,dir), filter, cb); 
    }


    public static InputStreamReader createInput(File f) throws FileNotFoundException 
        { return new InputStreamReader(new FileInputStream(f),StandardCharsets.UTF_8); }

    public static OutputStreamWriter createOutput(File f) throws FileNotFoundException 
        { return new OutputStreamWriter(new FileOutputStream(f),StandardCharsets.UTF_8); }

    public static <T> T jsonToJava(File f, Class<T> classOf) throws InstantiationException, IllegalAccessException {
        try {
            f.createNewFile();
            InputStreamReader reader = createInput(f);
            T data = gson.fromJson(reader,classOf);
                if(data == null) data = classOf.newInstance();
            javaToJson(f,data);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return classOf.newInstance();
        }
    } 

    public static void javaToJson(File f, Object data) {
        try {
            f.createNewFile();
            OutputStreamWriter writer = createOutput(f);
            gson.toJson(data,writer);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object getSafely(Object obj, Object val) {
        if(obj == null) return val;
        return obj;
    }

    @SuppressWarnings("all")
    public static Object getSafely(HashMap map, Object obj, Object val) {
        return getSafely(map.get(obj),val);
    }

    @SuppressWarnings("all")
    public static Object getSafely(LinkedTreeMap map, Object obj, Object val) {
        return getSafely(map.get(obj),val);
    }
    
}
