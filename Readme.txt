Problem Statement:
To move the robot towards a particular goal in a n*m grid structure which contains obstacles to restrict the movement of the bot.

Compilation Instruction:
Can be compiled using- 
javac rover.java

And then can be run using-
java rover "input.txt"

The program can only take one input at a time.

The output is created as output.txt and its file can be changed from within the program.



Program Details:
The maximum moves that the rover can make is 9990 and if executed more than that would give an array index out of bound error but the program would come out of it saying that the Rover can't find the path.
If path would be more than that the array RoverHistory[size][], the size should be increased.


The direction is referenced in degrees and are as corresponding:
Professors Direction	Program Direction
0			0
1			45
2			90
3			135
4			180
5			225
6			270
7			315
The output are printed in programs direction


Rover GUI :
The Yellow boxes are used to depict the path taken by the rover.
The Blue box show the current location of rover.
The Green box shows the goal of the rover.


Input Format:
Dimensionx DimensionY
RoverStartx RoverStartY RoverDirection
GoalX GoalY
ObstacleX1 ObstacleY1
ObstacleX2 ObstacleY2
.....



Output Format:
LocationX LocationY Direction
....
(In increasing order the most recently visited location is at bottom)