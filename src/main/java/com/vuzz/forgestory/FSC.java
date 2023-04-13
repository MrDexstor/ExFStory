package com.vuzz.forgestory;

public class FSC {
    public static String[] forgeStoryAscii = new String[] {
        "┏━━━┓╋╋╋╋╋╋╋╋╋╋┏━━━┓┏┓",
        "┃┏━━┛╋╋╋╋╋╋╋╋╋╋┃┏━┓┣┛┗┓",
        "┃┗━━┳━━┳━┳━━┳━━┫┗━━╋┓┏╋━━┳━┳┓╋┏┓",
        "┃┏━━┫┏┓┃┏┫┏┓┃┃━╋━━┓┃┃┃┃┏┓┃┏┫┃╋┃┃",
        "┃┃╋╋┃┗┛┃┃┃┗┛┃┃━┫┗━┛┃┃┗┫┗┛┃┃┃┗━┛┃",
        "┗┛╋╋┗━━┻┛┗━┓┣━━┻━━━┛┗━┻━━┻┛┗━┓┏┛",
        "╋╋╋╋╋╋╋╋╋┏━┛┃╋╋╋╋╋╋╋╋╋╋╋╋╋╋┏━┛┃",
        "╋╋╋╋╋╋╋╋╋┗━━┛╋╋╋╋╋╋╋╋╋╋╋╋╋╋┗━━┛"
    };

    public static String fsVersion = "1.1";
    public static String envType = "Plotter";
    public static String envVersion = "2";
    public static String envDescription = "JS/JSON availibility";
    public static String docLink = "https://github.com/VuzZis/FS-Official/wiki";

    public static String udcTick = "✔";
    public static String udcCross = "✖";
    public static String udcRFaceArrow = "⋙";
    public static String udcStar = "✦";
    public static String udcRombus = "◆";
    
    public static void sendInformationMessage() {
        String logo = join(forgeStoryAscii, "\n");
        String fsInfo = "ForgeStory "+fsVersion+" | Made by VuzZ";
        String envInfo = udcRFaceArrow+"Env: "+envType+" "+envVersion+" | "+envDescription;
        String docInfo = udcRFaceArrow+"Documentation: "+docLink;
        String headfooter = "------------------------------------";
        
        System.out.println(
            "\n"+headfooter
            +"\n"+logo
            +"\n"+fsInfo
            +"\n"+envInfo
            +"\n"+docInfo
            +"\n"+headfooter
        );
    }

    public static String join(String[] array, String reg) {
        String joined = "";
        for(String el : array) {
            joined+=el+reg;
        }
        return joined;
    }

    public static String createMessage(String msg) {
        return msg+" | fsLogger";
    }

    public static void sout(String x) {
        System.out.println(createMessage(x));
    }

    public static  enum FSError {
        ROOT("RootError"),
        STORY("StoryError"),
        SCENE("SceneError"),
        JS("SceneError"),
        GENERAL("UnexpectedError"),
        NPC("NpcError"),
        JSON("JsonError"),
        PLOTTER("PlotterError")
        ;

        public final String errorName;
        FSError(String errorName) {
            this.errorName = errorName;
        }

        public String createMessage(FSError type, String msg) {
            return type.errorName+": "+msg+" | fsLogger";
        }
    }
}
