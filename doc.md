## ApiJS  
### Methods  
* [void][void] **printError([String][String] arg0)** - Logs an error to the console and the chat.        
* [void][void] **printInfo([String][String] arg0)** - Logs the information to the console and the chat.  
* **MrAlxart_** - Author.  
### Fields
## PlayerJS
### Methods
* [String][String] **getPlayerName()** - Returns player name.
* [double[]][double] **getPosition()** - Gets Player Position.
* [double][double] **getX()** - Gets Player X Position.
* [double][double] **getY()** - Gets Player Y Position.
* [double][double] **getZ()** - Gets Player Z Position.
* [PlayerJS][PlayerJS] **sendMessage([String][String] arg0)** - Sends message to the player.
* [void][void] **setHeadRotation([float][float] arg0, [float][float] arg1)** - Sets rotation of player head on X&Y / Pitch&Yaw.
* [void][void] **setPosition([double[]][double] arg0)** - Sets Player Position.
* [void][void] **setPosition([double][double] arg0, [double][double] arg1, [double][double] arg2)** - Sets Player Position.
* [void][void] **setX([double][double] arg0)** - Sets Player X Position.
* [void][void] **setY([double][double] arg0)** - Sets Player Y Position.
* [void][void] **setZ([double][double] arg0)** - Sets Player Z Position.
### Fields
## WorldJS
### Methods
### Fields
## NpcJS
### Methods
* [void][void] **animLoop([String][String] arg0)** - Play anim in a Loop
* [void][void] **animPlayOnce([String][String] arg0)** - Play anim Only Once
* [void][void] **despawnSelf()** - Despawns/Removes npc from world.
* [double[]][double] **getPosition()** - Gets NPC's position.
* [double][double] **getX()** - Gets NPC's X position.
* [double][double] **getY()** - Gets NPC's Y position.
* [double][double] **getZ()** - Gets NPC's Z position.
* [void][void] **moveToPosition([double][double] arg0, [double][double] arg1, [double][double] arg2)** - Moves NPC to position with default speed. 

* [void][void] **moveToPosition([double][double] arg0, [double][double] arg1, [double][double] arg2, [double][double] arg3)** - Moves NPC to position with preset speed.
* [void][void] **moveToPosition([double[]][double] arg0)** - Moves NPC to position with default speed.
* [void][void] **moveToPosition([double[]][double] arg0, [double][double] arg1)** - Moves NPC to position with preset speed.
* [void][void] **setEntityFocused([Entity][Entity] arg0)** - Focuses npc on another entity.
* [void][void] **setEntityFocused([JSResource][JSResource] arg0)** - Focuses npc on another entity.
* [void][void] **setEntityFocused()** - Makes npc don't focus on anything.
* [void][void] **setHeadRotation([float][float] arg0)** - Rotates NPC's head by Y/yaw.
* [void][void] **setIdleAnimation([String][String] arg0)** - Changes default idle animation.
* [void][void] **setPosition([double[]][double] arg0)** - Sets NPC's position.
* [void][void] **setPosition([double][double] arg0, [double][double] arg1, [double][double] arg2)** - Sets NPC's position.
* [void][void] **setWalkAnimation([String][String] arg0)** - Changes default walking animation.
* [void][void] **setX([double][double] arg0)** - Sets NPC's X position.
* [void][void] **setY([double][double] arg0)** - Sets NPC's Y position.
* [void][void] **setZ([double][double] arg0)** - Sets NPC's Z position.
* [void][void] **stopLoop()** - Stops playing anim.
* [void][void] **stopPlayOnce()** - Stops playing anim.
### Fields
## SceneJS
### Methods
* [SceneJS][SceneJS] **addAction([Consumer][Consumer] arg0, [ActionEvent][ActionEvent] arg1)** - Adds action to the scene.
* [void][void] **createNpc([World][World] arg0, [NpcBuilder][NpcBuilder] arg1, [double[]][double] arg2)** - Creates an npc.
* [void][void] **endScene()** - Ends the scene
* [NpcJS][NpcJS] **getNpc([String][String] arg0)** - Gets npc by its id.
* [void][void] **showFadeScreen([int][int] arg0)** - Creates a smooth appearing black rectangle that lasts for n ticks.
* [void][void] **showFadeScreen([int][int] arg0, [String][String] arg1)** - Creates a smooth appearing colored rectangle that lasts for n ticks.   
### Fields
## ActionPacketData
### Methods
### Fields
* [String][String] **messageSent** - Returns sent message (if sent).
* [boolean][boolean] **playKeyPressed** - Is PlayAction keybinding pressed.
* [SceneInstance][SceneInstance] **scene** - Current scene.
## SceneInstance
### Methods
### Fields
## Story
### Methods
* [void][void] **queueScene([PlayerEntity][PlayerEntity] arg0, [String][String] arg1, [double][double] arg2)** - Sets next queued scene for player. NOTE: Timer is ticking only when active scene is ended!
* [void][void] **queueScene([JSResource][JSResource] arg0, [String][String] arg1, [double][double] arg2)** - Sets next queued scene for player.    
### Fields
## AnimState
### Methods
### Fields
* [int][int] **LOOP** - Play anim in a Loop
* [int][int] **PLAY_ONCE** - Play anim Only Once
## ActionEvent
### Methods
* [ActionEvent][ActionEvent] **DEF()** - Create default action event. Activates on PlayAction keybinding.
* [ActionEvent][ActionEvent] **DELAY([int][int] arg0)** - Creates on ticks passed (delay) action event. Activates when timer in ticks is finished. 

* [ActionEvent][ActionEvent] **MSG_SENT([String[]][String] arg0)** - Creates on message sent action event. Activates when a message with defined keywords is sent.
### Fields
## NpcBuilder
### Methods
* [NpcBuilder][NpcBuilder] **asAlex([String][String] arg0, [String][String] arg1, [String][String] arg2)** - Npc with default alex model and animations. Custom: id, name, texturePath
* [NpcBuilder][NpcBuilder] **asSteve([String][String] arg0, [String][String] arg1, [String][String] arg2)** - Npc with default steve model and animations. Custom: id, name, texturePath
* [NpcBuilder][NpcBuilder] **withAnimation([String][String] arg0)** - Sets npc animation path.
* [NpcBuilder][NpcBuilder] **withId([String][String] arg0)** - Sets npc id.
* [NpcBuilder][NpcBuilder] **withModel([String][String] arg0)** - Sets npc model path.
* [NpcBuilder][NpcBuilder] **withName([String][String] arg0)** - Sets npc name.
* [NpcBuilder][NpcBuilder] **withScale([double[]][double] arg0)** - Sets npc scale. NOT IMPLEMENTED
* [NpcBuilder][NpcBuilder] **withScale([double][double] arg0, [double][double] arg1, [double][double] arg2)** - Sets npc scale. NOT IMPLEMENTED    
* [NpcBuilder][NpcBuilder] **withTexture([String][String] arg0)** - Sets npc texture path.
### Fields

[void]: https://docs.oracle.com/javase/8/docs/api/java/lang/Void.html
[int]: https://docs.oracle.com/javase/8/docs/api/java/lang/Integer.html
[String]: https://docs.oracle.com/javase/8/docs/api/java/lang/String.html
[Object]: https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/Object.html
[File]: https://docs.oracle.com/javase/8/docs/api/java/io/File.html
[ServerPlayerEntity]: https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.15.2/net/minecraft/entity/player/ServerPlayerEntity.html
[PlayerEntity]: https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.15.2/net/minecraft/entity/player/PlayerEntity.html
[double]: https://docs.oracle.com/javase/8/docs/api/java/lang/Double.html
[float]: https://docs.oracle.com/javase/8/docs/api/java/lang/Float.html
[boolean]: https://docs.oracle.com/javase/8/docs/api/java/lang/Boolean.html
[List]: https://docs.oracle.com/javase/8/docs/api/java/util/List.html
[BlockPos]: https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.16.5/net/minecraft/util/math/BlockPos.html
[Item]: https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.16.5/net/minecraft/item/Item.html
[Block]: https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.16.5/net/minecraft/block/Block.html
[ItemStack]: https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.16.5/net/minecraft/item/ItemStack.html
[BlockState]: https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.16.5/net/minecraft/block/BlockState.html
[World]: https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.16.5/net/minecraft/world/World.html
[ServerWorld]: https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.16.5/net/minecraft/world/server/ServerWorld.html
[Minecraft]: https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.16.5/net/minecraft/client/Minecraft.html
[MinecraftServer]: https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.16.5/net/minecraft/server/MinecraftServer.html
[Entity]: https://nekoyue.github.io/ForgeJavaDocs-NG/javadoc/1.16.5/net/minecraft/entity/Entity.html
[JSResource]: Classes#JSResource
[SceneJS]: Classes#SceneJS
[SceneInstance]: Classes#SceneInstance
[ActionEvent]: Classes#ActionEvent
[Story]: Classes#Story
[ApiJS]: Classes#ApiJS
[PlayerJS]: Classes#PlayerJS
[ApiJS]: Classes#ApiJS
[Consumer]: https://docs.oracle.com/javase/8/docs/api/java/util/function/Consumer.html
[NpcBuilder]: Classes#NpcBuilder