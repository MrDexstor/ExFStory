package com.vuzz.forgestory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Comparator;

import com.vuzz.forgestory.annotations.Documentate;
import com.vuzz.forgestory.api.plotter.js.*;
import com.vuzz.forgestory.api.plotter.js.ApiJS.NpcBuilder;
import com.vuzz.forgestory.api.plotter.story.ActionEvent;
import com.vuzz.forgestory.api.plotter.story.Story;
import com.vuzz.forgestory.api.plotter.story.data.ActionPacketData;
import com.vuzz.forgestory.api.plotter.story.instances.SceneInstance;

public class Documentation {

    public static Class<?>[] toGenerate = new Class[] {
        ApiJS.class, PlayerJS.class, WorldJS.class,
        NpcJS.class, SceneJS.class, ActionPacketData.class,
        SceneInstance.class, Story.class, ApiJS.AnimState.class, ActionEvent.class,
        NpcBuilder.class
    };

    public static String docString = "";

    public static String[] docRefs = new String[] {
        "[void]: https://docs.oracle.com/javase/8/docs/api/java/lang/Void.html",
        "[int]: https://docs.oracle.com/javase/8/docs/api/java/lang/Integer.html",
        "[String]: https://docs.oracle.com/javase/8/docs/api/java/lang/String.html",
        "[Object]: https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Object.html",
        "[File]: https://docs.oracle.com/javase/8/docs/api/java/io/File.html",
        "[ServerPlayerEntity]: https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.15.2/net/minecraft/entity/player/ServerPlayerEntity.html",
        "[PlayerEntity]: https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.15.2/net/minecraft/entity/player/PlayerEntity.html",
        "[double]: https://docs.oracle.com/javase/8/docs/api/java/lang/Double.html",
        "[float]: https://docs.oracle.com/javase/8/docs/api/java/lang/Float.html",
        "[boolean]: https://docs.oracle.com/javase/8/docs/api/java/lang/Boolean.html",
        "[List]: https://docs.oracle.com/javase/8/docs/api/java/util/List.html",
        "[BlockPos]: https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.16.5/net/minecraft/util/math/BlockPos.html",
        "[Item]: https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.16.5/net/minecraft/item/Item.html",
        "[Block]: https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.16.5/net/minecraft/block/Block.html",
        "[ItemStack]: https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.16.5/net/minecraft/item/ItemStack.html",
        "[BlockState]: https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.16.5/net/minecraft/block/BlockState.html",
        "[World]: https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.16.5/net/minecraft/world/World.html",
        "[ServerWorld]: https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.16.5/net/minecraft/world/server/ServerWorld.html",
        "[Minecraft]: https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.16.5/net/minecraft/client/Minecraft.html",
        "[MinecraftServer]: https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.16.5/net/minecraft/server/MinecraftServer.html",
        "[Entity]: https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.16.5/net/minecraft/entity/Entity.html",
        "[JSResource]: Classes#JSResource",
        "[SceneJS]: Classes#SceneJS",
        "[SceneInstance]: Classes#SceneInstance",
        "[ActionEvent]: Classes#ActionEvent",
        "[Story]: Classes#Story",
        "[ApiJS]: Classes#ApiJS",
        "[PlayerJS]: Classes#PlayerJS",
        "[ApiJS]: Classes#ApiJS",
        "[Consumer]: https://docs.oracle.com/javase/8/docs/api/java/util/function/Consumer.html",
        "[NpcBuilder]: Classes#NpcBuilder"
    };

    public static void main(String[] args) {
        System.out.println("Generating Documentation...");
        for(Class<?> c : toGenerate) {
            doClass(c);
        }
        docString+="\n";
        for (String ref : docRefs) {
            docString+=ref+"\n";
        }
        System.out.println(docString);
    }

    public static void doClass(Class<?> cl) {
        docString+="## "+cl.getSimpleName()+"  \n";
        Method[] methods = cl.getDeclaredMethods();
        Field[] fields = cl.getFields();
        Arrays.sort( methods,
            (Comparator<? super Method>) (Method s1, Method s2) -> { return s1.getName().compareTo(s2.getName()); });
        Arrays.sort( fields,
            (Comparator<? super Field>) (Field a1, Field a2) -> { return a1.getName().compareTo(a2.getName()); });
        docString+="### Methods  \n";
        for(Method m : methods) {
            boolean isForDocs = m.isAnnotationPresent(Documentate.class);
            if(!isForDocs) continue;
            Documentate docAn = m.getAnnotation(Documentate.class);
            String type = m.getReturnType().getSimpleName();
            type = type.replace("[]", "");
                type = "["+m.getReturnType().getSimpleName()+"]"+"["+type+"]";
            docString+="* "+type+" **"+m.getName()+getArgs(m)+"** - "+docAn.desc()+"  \n";
        }
        docString+="### Fields  \n";
        for(Field f : fields) {
            boolean isForDocs = f.isAnnotationPresent(Documentate.class);
            if(!isForDocs) continue;
            Documentate docAn = f.getAnnotation(Documentate.class);
            String type = f.getType().getSimpleName();
            type = type.replace("[]", "");
                type = "["+f.getType().getSimpleName()+"]"+"["+type+"]";
            docString+="* "+type+" **"+f.getName()+"** - "+docAn.desc()+"  \n";
        }

    }

    public static String getArgs(Method m) {
        Parameter[] args = m.getParameters();
        String str = "(";
        int i = 0;
        for(Parameter arg : args) {
            if(i != 0) str+=", ";
            String type = arg.getType().getSimpleName();
                type = type.replace("[]", "");
                type = "["+arg.getType().getSimpleName()+"]["+type+"]";
            str+=type+" "+arg.getName();
            i++;
        }
        str+=")";
        return str;
    }
}
