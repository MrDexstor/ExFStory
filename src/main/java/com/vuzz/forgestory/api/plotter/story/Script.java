package com.vuzz.forgestory.api.plotter.story;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.ScriptableObject;

import com.vuzz.forgestory.api.Environment;

public class Script {

    public File scriptFile;
    public String scriptId;
    
    public Script(File script) {
        scriptFile = script;
        scriptId = script.getName();
    }

    public void runWithEnv(Environment env) {
        try 
        {

        Context ctx = Context.enter();
        ScriptableObject scope = ctx.initStandardObjects();
        PlotterEnvironment customEnv = (PlotterEnvironment) env;
        InputStreamReader reader = new InputStreamReader(new FileInputStream(scriptFile),StandardCharsets.UTF_8);
        
        applyEnvToScope(scope, customEnv);
        runLibraries(ctx,customEnv,scope);
        
        ctx.evaluateReader(scope, reader, scriptId,1,null);
            
        } catch (Exception e) { e.printStackTrace(); }
    }

    public void runLibraries(Context ctx, PlotterEnvironment env, ScriptableObject scope) throws IOException {
        for(File lib : env.story.libraries) {
            InputStreamReader reader = new InputStreamReader(new FileInputStream(lib),StandardCharsets.UTF_8);
            ctx.evaluateReader(scope, reader, "lib/"+lib.getName(),1,null);
        }
    }

    public void applyEnvToScope(ScriptableObject scope, Environment env) {
        Field[] envFields = env.getClass().getFields();
        for(Field envField:envFields) try 
        {
            String fieldId = envField.getName();
            Object fieldVal = envField.get(env);
            ScriptableObject.putConstProperty(scope, fieldId, fieldVal);
        } 
        catch (Exception e) { e.printStackTrace(); }
    }

}
