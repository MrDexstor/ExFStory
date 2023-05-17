package com.vuzz.forgestory.api.plotter.js;

import com.vuzz.forgestory.annotations.Documentate;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.registries.ForgeRegistries;

public class ApiJS implements JSResource {

    @Documentate(desc = "Default MC blocks.")
    public Blocks MC_BLOCKS = new Blocks();

    @Documentate(desc = "Default MC items.")
    public Items MC_ITEMS = new Items();

    @Documentate(desc = "Logs an error to the console and the chat.")
    public void printError(String err) { print(err,PrintType.ERROR); }
    
    @Documentate(desc = "Logs the information to the console and the chat.")
    public void printInfo(String x) { print(x,PrintType.INFO); }

    public void print(String x, int type) {
        String msg = PrintType.MSGS[type]+": "+x;
        System.out.println(msg);
    }

    @Documentate(desc = "Executes command")
    public int executeCommand(PlayerEntity player, String command) {
        MinecraftServer server = player.level.getServer();
            if(server == null) return 0;
        CommandSource source = server.createCommandSourceStack()
            .withEntity(player)
            .withPermission(4);
        return server.getCommands().performCommand(source, command);
    }

    @Documentate(desc = "Executes command") 
    public int executeCommand(PlayerJS player, String command) { 
        return executeCommand((PlayerEntity) player.getNative(), command); }

    @Documentate(desc = "Creates BlockPos Class")
    public static BlockPos createBlockPos(double[] xyz) { return new BlockPos(xyz[0],xyz[1],xyz[2]);}

    @Documentate(desc = "Creates BlockPos Class")
    public BlockPos createBlockPos(double x, double y, double z) { return new BlockPos(x,y,z); }

    public double[] bpToArr(BlockPos pos) {return new double[] {pos.getX(),pos.getY(),pos.getZ()};}

    @Documentate(desc = "Gets item registry by id")
    public Item getItem(String itemId) {
        String id = itemId;
        String modId = "minecraft";
        String name = "";
        if(id.indexOf(":") != -1) {
            modId = id.substring(0,id.indexOf(":"));
        }
        name = id.substring(id.indexOf(":")+1);
        Item itemReg = ForgeRegistries.ITEMS.getValue(new ResourceLocation(modId, name));
        return itemReg;
    }

    @Documentate(desc = "Gets block registry by id")
    public static Block getBlock(String blockId) {
        String id = blockId;
        String modId = "minecraft";
        String name = "";
        if(id.indexOf(":") != -1) {
            modId = id.substring(0,id.indexOf(":"));
        }
        name = id.substring(id.indexOf(":")+1);
        Block itemReg = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(modId, name));
        return itemReg;
    }

    @Documentate(desc = "Creates an ItemStack class")
    public ItemStack createStack(Item item, int count) {
        ItemStack stack = new ItemStack(item);
        stack.setCount(count);
        return stack;
    }

    @Documentate(desc = "Creates a BlockState class")
    public static BlockState createBlockState(Block block) {
        BlockState blockState = block.defaultBlockState();
        return blockState;
    }

    @Documentate(desc = "Creates a BlockState class")
    public static BlockState createBlockState(String blockId) {
        return createBlockState(ApiJS.getBlock(blockId));
    }




    @Override public Object getNative() { return this; }
    @Override public String getResourceId() { return "api"; }
    @Override public boolean isClient() { return false; }

    public static class AnimState {
        @Documentate(desc = "Play anim Only Once")
        public static final int PLAY_ONCE = 0;

        @Documentate(desc = "Play anim in a Loop")
        public static final int LOOP = 1;
    }

    public static class PrintType {
        @Documentate(desc = "Information print type.")
        public static final int INFO = 0;

        @Documentate(desc = "Error print type.")
        public static final int ERROR = 1;

        public static final String[] MSGS = new String[] { "INFO","ERROR" };
    }

    public static class CameraMode {

        public double posX = 0;
        public double posY = 0;
        public double posZ = 0;

        public double rotX = 0;
        public double rotY = 0;

        public String type = "undef";

        public CameraMode(double px, double py, double pz, double rx, double ry, String type) {
            posX = px;
            posY = py;
            posZ = pz;
            rotX = rx;
            rotY = ry;
            this.type = type;
        }

        @Documentate(desc = "Full locked camera (Position & Rotation).")
        public static CameraMode FULL(double px, double py, double pz, double rx, double ry) {
            return new CameraMode(px,py,pz,rx,ry,"full"); }

        @Documentate(desc = "Position locked camera.")
        public static CameraMode POS_ONLY(double px, double py, double pz) {
            return new CameraMode(px,py,pz,0,0,"pos_only"); }
        
        @Documentate(desc = "Rotation locked camera.")
        public static CameraMode ROT_ONLY(double rx, double ry) {
            return new CameraMode(0,0,0,rx,ry,"rot_only"); }

        public static CameraMode NIL() {
            return new CameraMode(0, 0, 0, 0, 0, "undef");}
    }

    @Documentate(desc = "Creates new NpcBuilder class.")
    public NpcBuilder newNpcData() {
        return new NpcBuilder();
    }

    public static class NpcBuilder {
        public String id = "dummy";
        public String name = "NPC";
        public String texturePath = "forgestory:textures/entity/npc";
        public String modelPath = "forgestory:geo/steve.geo";
        public String animationPath = "forgestory:animations/npc.animation";
        public double[] scale = new double[] {1d,1d,1d};

        @Documentate(desc = "Npc with default steve model and animations. Custom: id, name, texturePath")
        public NpcBuilder asSteve(String id, String name, String texturePath) {
            this.id = id;
            this.name = name;
            this.texturePath = texturePath;
            modelPath = "forgestory:geo/steve.geo";
            return this;
        }

        @Documentate(desc = "Npc with default alex model and animations. Custom: id, name, texturePath")
        public NpcBuilder asAlex(String id, String name, String texturePath) {
            this.id = id;
            this.name = name;
            this.texturePath = texturePath;
            modelPath = "forgestory:geo/alex.geo";
            return this;
        }

        @Documentate(desc = "Sets npc id.")
        public NpcBuilder withId(String id) { this.id = id; return this; }

        @Documentate(desc = "Sets npc name.")
        public NpcBuilder withName(String name) { this.name = name; return this; }

        @Documentate(desc = "Sets npc texture path.")
        public NpcBuilder withTexture(String texture) { this.texturePath = texture; return this; }

        @Documentate(desc = "Sets npc model path.")
        public NpcBuilder withModel(String model) { this.modelPath = model; return this; }

        @Documentate(desc = "Sets npc animation path.")
        public NpcBuilder withAnimation(String animation) { this.animationPath = animation; return this; }

        @Documentate(desc = "Sets npc scale. NOT IMPLEMENTED")
        public NpcBuilder withScale(double[] scale) { this.scale = scale; return this; }

        @Documentate(desc = "Sets npc scale. NOT IMPLEMENTED")
        public NpcBuilder withScale(double x, double y, double z) { return withScale(new double[] {x,y,z}); }

    }

}
