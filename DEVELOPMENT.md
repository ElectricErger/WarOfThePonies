
=======================
Introduction
=======================

This development will work with MVC to the closest of our ability given that neither of the authors have 
experience with developing in an architecture style.

Recommended tutorial: https://www.youtube.com/watch?v=9dzhgsVaiSo


=======================
Breakdown
=======================
(THIS SECTION IS JUST FOR ME, PAY NO MIND TO IT)

As of now the breakdown is as such:

 JFrame --> JPanel
 JPanel --> Plot & Map & Controller
 Map --> WorldObject
 WorldObject --> TextWindow

 JFrame - Singleton that creates a JPanel
 JPanel - Main thread here - Controls the graphics, although the map takes care of 
 	the actual content to be blit to the screen. Remember any graphical changes must
 	be done within this thread.
 GSM - Maintains plot state, characters and map need this
 Map - It will swash around for what map is active
 	when it finds it, the main thread will blit the images
 Controller - A listener that triggers things asynchronously. When it gets this it
 	gets the world object (player, sign, etc) and performs the action. 
 WorldObject - The model object for what is to be blit
 TextWindow - 
 
 Object			Owned by	Needs
 ---------------------------------
 JFrame			Main		None
 JPanel			JFrame		None
 GSM			JPanel		Map
 Map			JPanel		GSM	
 Controller		JPanel		Map
 WorldObject	Map			GSM


=======================
BUILDING ENGINES
=======================

=======================
Views and Controls
=======================
Basically every Frame calls the JPanel's "paintComponent" function via redraw
While you can draw outsize this thread, it is not thread safe and can cause issues

When we draw the frame we pass it up to the GSM which maintains the chapter state (and subsequently passes it to that)
		At the time of writing this, I've considered that we can make a raw "inGame" GameState that simply deals with all the engines

Actions are taken care of by an "actionListener" implemented in the JPanel. This allows actions ot occur asynchronously with respect to the frames. Similar to draw it passes it up to the GSM (since actions are a function of what state we are in), and subsequently the GameState


IMPORTANT ISSUE: We need to resolve whether or not we want to bind keys in some alternative way now, so that we can program for that later. The leading idea at this point in time is linking buttons to numbers (e.g. 1 = left, 2 = up, 3 = right, 4 = down, 5 = A, 6 = B, 7 = Start)

=======================
Object Models
=======================
The general in game objects are:
	Character - Has an xy coordinate on a Map, dialog, and a picture
		Main Character - Is a special object directly referenced in GameState most controller functions will go to this
	Map - This is the general world we are in now. Referenced in GameState it has the responsibility to print the local screen and maintain the characters on that screen (including the main character)

These are updated by the controller (that passes through GameState)


=======================
Text Engine
=======================
The controller goes to the game state, checks if there is text to be displayed (if so do it), if not and not in battle, map.objectInfront(). If there is an object interact with it (which may return a Dialog). Toss the dialog to the engine 
	At the time of writing this I am considering having text in battle...maybe we should change this up later

=======================
Battle Engine
=======================


=======================
Map
=======================





=======================
CONTENT ORIENTED
=======================
All of these next sections are in limbo until we can determine how we will build the engines

=======================
Dialog Format
=======================
The dialog should be stored in plain text files and written with the following code (perhaps JSON?)

NAME{
	CHAPTER{
		DIALOG{
			LINE1
			LINE2
			LINE3
			LINE4
		}
	}
}

Further each line should not exceed <CHARACTER LIMIT TO BE DETERMINED LATER>
All dialog should be put into /res/dialog.res


=======================
Plot Format
=======================
If I can figure out how to do this correctly we will put it into a res file



=======================
Map Format
=======================
We have two types of map files:
World Map: a singleton that contains all pertainent information about that map
Maps: That have all the information about tiles and people's locations

===World Map====
MAP NUMBER,MAP NAME
MAP FILE
CONNECTION NUMBER,X1,Y1,WIDTH,HEIGHT

===Map===
TILE FILE
MAP DATA
.
.
.

CHARACTER NUMBER, X, Y
.
.
.

OBJECT, IMG, X, Y
.
.
.

=======================
Characters Format
=======================
Characters are objects that need certain information such as dialog, sprite, and location. 

CHARACTER NUMBER, MAP NUMBER
PLOT NUMBER: DIALOG (new line = \n)
.
.
.

<TRIGGER>
TRIGGER NUMBER: 