=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Homework 10 README
PennKey: thuyle
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
This is a remake of the Classic Snake game with keyboard control.

===============
Implementation:
===============

Classes:

- Game: The Game Main class that specifies the frame and widgets of the GUI.

- GameCourt: This GameCourt class holds the primary game logic for how different objects interact 
             with one another. The timer interacts with the different 
             methods and repaints the GUI on every tick().

- Direction: This file holds an enumeration called Direction, which is used in GameCourt.java to 
             indicate the direction an object should move.

- Snake: A Snake game object displayed as a series of square, starting in the upper left corner of 
         the game court in the single person game play.

- Cherry: The Cherry game object displayed using an image.

Special features:

- Multipleplayer: Two-player mode where two players use two sets of keys to play against each other.

- File I/O: High Score feature that persists every launch.

=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
HAPPY SNAKING!
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=