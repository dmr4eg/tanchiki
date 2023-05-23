# <span dir="">•</span> **Motivation**

### **Project "Tanchiki"**<span dir="">:video_game:</span>

> **"Tanks" is a retro game that was developed and released in the 1980s. The game was originally created for the Atari 2600 console, but it was later ported to other systems, including the Commodore 64, Apple II, and others.**

#### Our remake will contain the same idea like in original game. We share interests with retro games, and from our opinion it is quite interesting project to re-create. Now, let's see what our project will be in the future:

#### The game involves two players that engage in a real-time battle to the death against bots. Also it can be played in a solo mode (versus the same enemies - bots). Game goal is to kill all enemies, stay alive and defend base, while they're trying to destroy players base and kill everyone. Players must be careful, because team damaging is allowed.

#### The game is played from a top-down perspective, and players control their tanks as they try to destroy their opponent. The game will be using only keyboard movement for both players. Read info about controls, software documentation, game models and more on other pages.

####

# <span dir="">•</span> **Creators**

> [**Damir Asylbekov**](https://github.com/dmr4eg)
>
> [**Alisher Nurakhmetov**](https://github.com/1Lukrecia1)

#### Our project is written entirely in Java(ver. 19) using MVC. We are using Java FX to draw the GUI. The view is responsible for the GUI along with the controller. The logic will be placed in Model.

#### Online play will be implemented using a ping-pong ball principle. The server will run locally.

#### Game client - The game client will constantly and cyclically send data to the server, specifying the port and IP address, and listen on a specific port.

#### Game server - The server will be implemented in parallel on another thread and also listen on a specific port. Incoming data will be sent to the game client of the other player.

#### Data storage and transmission - Data will be stored in JSON format, which will contain key-value data.

#### Data for all objects will be provided to the server, which will transmit them to the game client of the other player.

---

#### Below is information about each class and its methods, attributes:

![java](uploads/1ad73e9e56f40e278874da7b2bbb9c07/java.png)

---

# Here's description of each object in game:

#### <span dir="">•</span> **For example, as a static objects we define:**

**(each texture has 50x50 format)**

> #### block - obstacle made of durable materials, that means it is solid.
>
> ![solidbrick](uploads/db11638fafd51e9d8d194438389d5c0f/solidbrick.png)
>
> #### removable_brick - obstacle made of bricks, which players can break with their bullets by shooting at them.
>
> ![brickdef](uploads/938835c31f0763ab31205b26a091bb17/brickdef.png)
>
> #### base - object, that players must protect, because if someone breaks it, the game will end.
>
> ![base_block](uploads/4fb3983e0d6587b4def8e0e2c0584097/base_block.png)

---

#### <span dir="">•</span> **Here are some non-static objects, as:**

> #### enemy - enemy tanks, their mission is to kill players or destroy the base.
>
> ![enup](uploads/d95f12185fb668e4206300f22fffa47d/enup.png)
>
> #### player - this objects represents player's model and behaviour.
>
> ![p1up](uploads/5c32d7975aa72a593cd444d7ea545435/p1up.png)
>
> #### bullet - this objects displays a bullet that was shot by an enemy or a player.
>
> ![enemy_bullet](uploads/c783aa3d857f1cf863eda5001bcfe63e/enemy_bullet.png)

# How to play?!

## **<span dir="">•</span> _In the main menu, players must choose a game mode:_**

> ### Singleplayer - game mode for 1 player, i.e solo game versus bots
>
> ### Multiplayer - game mode for 2 players, playing 1v1 or playing together versus bots
>
> ### Level Editor SP - game level creating and editing for singleplayer, you can build your own level to play on
>
> ### Level Editor MP - game level creating and editing for multiplayer, you can build your own level to play on
>
> **The game "Tanks" has a simple level editor that allows players to create their own levels. In the construction, players can choose various blocks to create their map, such as walls (breakable or unbreakable), bonuses, or enemy tank spawners.**
>
> ![Screenshot_2023-05-22_at_11.59.10_AM](uploads/bb6364869111a95c4fafa4f672aa01a7/Screenshot_2023-05-22_at_11.59.10_AM.png)

## **<span dir="">•</span> _In the actual game, you will see something like this:_**

> ### ![NES_01](uploads/1b64701158faba76dd2894610ab0b0fa/NES_01.gif)
>
> ### On the right side of a page, you can see amount of enemies on a level and each players remaining life count.

## **<span dir="">•</span> _Player abilities:_**

> **Arrow UP or W - Move up \
> Arrow LEFT or A - Move to the left\
> Arrow DOWN or S - Move down\
> Arrow RIGHT or D - Move to the right**
>
> **Each collected item will be activated immediately after getting it.**

### _Each player must shoot at the enemies:_

> **E - shoot a bullet in direction, where player's model is oriented at the moment.**
