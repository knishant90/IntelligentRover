import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JFrame;



class RoverBoard {

	JFrame frame=new JFrame(); //creates frame
    JButton[][] grid; //names the grid of buttons

    public RoverBoard(rover r,Environment e){ //constructor
            int width = r.PerceivedEnvironment.length;
            int height = r.PerceivedEnvironment[0].length;
    		frame.setLayout(new GridLayout(width,height)); //set layout
    		
            grid=new JButton[width][height]; //allocate the size of grid
            for(int y=0; y<height; y++){
                    for(int x=0; x<width; x++){
                            grid[x][y]=new JButton(""); //creates new button
                            grid[x][y].setPreferredSize(new Dimension(20, 20));
                            if(e.EnvironmentData[x][y] == 'O')
                            {
                            	grid[x][y].setBackground(Color.black);
                            }
                            else if(x == r.RoverLocationX && y == r.RoverLocationY)
                            {
                            	grid[x][y].setBackground(Color.BLUE);
                            }
                            else if(x == r.GoalX && y == r.GoalY)
                            {
                            	grid[x][y].setBackground(Color.GREEN);
                            }
                            else
                            {
                            	for(int i=0;i<r.RoverHistory.length;i++)
                            	{
                            		if(x == r.RoverHistory[i][0] && y == r.RoverHistory[i][1])
                            		{grid[x][y].setBackground(Color.YELLOW);}
                            	}
                            }
                            frame.add(grid[x][y]); //adds button to grid
                    }
            }
            frame.setSize(2000, 2000);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack(); //sets appropriate size for frame
            frame.setVisible(true); //makes frame visible
    }
        
}

class Environment{
	char[][] EnvironmentData = new char[25][25];
	int SourceX,SourceY;
	int Obstacle[][] = new int[2000][2];
	Environment(int sizex,int sizey,rover r)
	{
		EnvironmentData = new char[sizex][sizey];
		for(int i=0;i<sizex;i++)
		{for(int j=0;j<sizey;j++)
		{EnvironmentData[i][j]='X';
		}
		}
		SourceX = r.RoverLocationX;
		SourceY = r.RoverLocationY;
		
	}
	void GenerateRandom(int percentage,rover r)
	{
		Random randomgenerator = new Random();
		int LoopCounter = 0;
		while(LoopCounter < ((EnvironmentData.length)*(EnvironmentData[0].length)*percentage/100))
		{
			int RandomX = randomgenerator.nextInt(EnvironmentData.length);
			int RandomY = randomgenerator.nextInt(EnvironmentData[0].length);
			
			if((EnvironmentData[RandomX][RandomY] == 'O') || (r.GoalX == RandomX && r.GoalY == RandomY) ||(RandomX == SourceX && RandomY == SourceY))
			{}
			else{EnvironmentData[RandomX][RandomY] = 'O';
				Obstacle[LoopCounter][0] = RandomX;
				Obstacle[LoopCounter][1] = RandomY;
				LoopCounter++;
			}
		}
	}
	
	void AddObstacle(int x,int y)
	{
		EnvironmentData[x][y]='O';
	}

	void PrintEnvironment(){
		for(int i=0;i< EnvironmentData.length;i++)
		{for (int j=0;j<EnvironmentData[0].length;j++)
		{
			System.out.print(EnvironmentData[i][j]);
		}
		System.out.println("");
		}
	}
}


public class rover {

	/**
	 * @param args
	 */
int actions[] = new int[5];
int RoverLocationX;
int RoverLocationY;
int RoverDirection;
int GoalX;
int GoalY;
int SizeX;
int SizeY;
int StraightObstacleDistance;
int LeftObstacleDistance;
int RightObstacleDistance;
int Moves = 0;
char[][] PerceivedEnvironment = new char[10][10];
char[][] RealEnvironment=new char[10][10];
int[][] RoverHistory = new int[10000][3];
public rover(int LocationX,int LocationY)
{
	RoverLocationX = LocationX;
	RoverLocationY = LocationY;
}

void GenerateEnvironment(int sizex,int sizey,int percentage)
{
	Random randomgenerator = new Random();
	int LoopCounter=0; 
	
	while(LoopCounter < (sizex*sizey*percentage/100))
	{
		int RandomX = randomgenerator.nextInt(sizex);
		int RandomY = randomgenerator.nextInt(sizey);
		
		if(RealEnvironment[RandomX][RandomY] == 'O')
		{}
		else{
			RealEnvironment[RandomX][RandomY] = 'O';
			LoopCounter++;
		}
	}
}

void print_environment(){
	for(int i=0;i< RealEnvironment.length;i++)
	{for (int j=0;j<RealEnvironment[0].length;j++)
	{
		System.out.println(RealEnvironment[i][j]);
	}
	}
}
void ScanEnvironment(Environment e)
{
	RightScanner(e);
	MiddleScanner(e);
	LeftScanner(e);
	RoverHistory[Moves][0]=RoverLocationX;
	RoverHistory[Moves][1]=RoverLocationY;
	RoverHistory[Moves][2]=RoverDirection;
	Moves++;
	
}
void MoveForward(Environment e){
	if(RoverDirection == 0)
	{	
		RoverLocationX+=1;
		ScanEnvironment(e);
	}
	else if(RoverDirection == 45)
	{
		RoverLocationX+=1;
		RoverLocationY-=1;
		ScanEnvironment(e);
	}
	else if(RoverDirection == 90)
	{
		RoverLocationY-=1;
		ScanEnvironment(e);
	}
	else if(RoverDirection == 135)
	{
		RoverLocationX-=1;
		RoverLocationY-=1;
		ScanEnvironment(e);
	}
	else if(RoverDirection == 180)
	{
		RoverLocationX-=1;
		ScanEnvironment(e);
	}
	else if(RoverDirection == 225)
	{
		RoverLocationX-=1;
		RoverLocationY+=1;
		ScanEnvironment(e);
	}
	else if(RoverDirection == 270)
	{
		RoverLocationY+=1;
		ScanEnvironment(e);
	}
	else if(RoverDirection == 315)
	{
		RoverLocationX+=1;
		RoverLocationY+=1;
		ScanEnvironment(e);
	}
}


void SteerLeft(Environment e){
		TurnLeft(e);
		MoveForward(e);
}
void TurnRight(Environment e){
	if(RoverDirection == 0)
	{RoverDirection = 315;
	ScanEnvironment(e);}
	else
	{RoverDirection -=45;
	ScanEnvironment(e);}
}

void TurnLeft(Environment e){
	if(RoverDirection == 315)
	{RoverDirection = 0;
	ScanEnvironment(e);}
	else
	{RoverDirection +=45;
	ScanEnvironment(e);}
}


void RightScanner(Environment e){
	int ScanX=RoverLocationX;
	int ScanY=RoverLocationY;
	RightObstacleDistance =0;
	if(RoverDirection == 0)
	{	
		while(ScanX < (e.EnvironmentData.length - 1) && ScanY < (e.EnvironmentData[0].length - 1) )
		{
			ScanX+=1;
			ScanY+=1;
			PerceivedEnvironment[ScanX][ScanY] = e.EnvironmentData[ScanX][ScanY];
			RightObstacleDistance++;
			if(e.EnvironmentData[ScanX][ScanY] == 'O')
			{
				break;
			}
		}
	}
	else if(RoverDirection == 45)
	{
		while(ScanX < (e.EnvironmentData.length - 1))
		{
			ScanX+=1;
			PerceivedEnvironment[ScanX][ScanY] = e.EnvironmentData[ScanX][ScanY];
			RightObstacleDistance++;
			if(e.EnvironmentData[ScanX][ScanY] == 'O')
			{
				break;
			}
		}
	}
	else if(RoverDirection == 90)
	{
		while(ScanX < (e.EnvironmentData.length - 1) && ScanY >0 )
		{
			ScanX+=1;
			ScanY-=1;
			RightObstacleDistance++;
			PerceivedEnvironment[ScanX][ScanY] = e.EnvironmentData[ScanX][ScanY];
			if(e.EnvironmentData[ScanX][ScanY] == 'O')
			{
				break;
			}
		}
	}
	else if(RoverDirection == 135)
	{
		while(ScanY > 0 )
		{
			ScanY-=1;
			PerceivedEnvironment[ScanX][ScanY] = e.EnvironmentData[ScanX][ScanY];
			RightObstacleDistance++;
			if(e.EnvironmentData[ScanX][ScanY] == 'O')
			{
				break;
			}
		}
	}
	else if(RoverDirection == 180)
	{
		while(ScanX > 0 && ScanY > 0 )
		{
			ScanX-=1;
			ScanY-=1;
			PerceivedEnvironment[ScanX][ScanY] = e.EnvironmentData[ScanX][ScanY];
			RightObstacleDistance++;
			if(e.EnvironmentData[ScanX][ScanY] == 'O')
			{
				break;
			}
		}
	}
	else if(RoverDirection == 225)
	{
		while(ScanX >0 )
		{
			ScanX-=1;
			PerceivedEnvironment[ScanX][ScanY] = e.EnvironmentData[ScanX][ScanY];
			RightObstacleDistance++;
			if(e.EnvironmentData[ScanX][ScanY] == 'O')
			{
				break;
			}
		}
	}
	else if(RoverDirection == 270)
	{
		while(ScanX > 0 && ScanY < (e.EnvironmentData[0].length - 1) )
		{
			ScanX-=1;
			ScanY+=1;
			RightObstacleDistance++;
			PerceivedEnvironment[ScanX][ScanY] = e.EnvironmentData[ScanX][ScanY];
			if(e.EnvironmentData[ScanX][ScanY] == 'O')
			{
				break;
			}
		}
	}
	else if(RoverDirection == 315)
	{
		while(ScanY < (e.EnvironmentData[0].length - 1) )
		{
			ScanY+=1;
			PerceivedEnvironment[ScanX][ScanY] = e.EnvironmentData[ScanX][ScanY];
			RightObstacleDistance++;
			if(e.EnvironmentData[ScanX][ScanY] == 'O')
			{
				break;
			}
		}
	}	
}


void MiddleScanner(Environment e){
	int ScanX=RoverLocationX;
	int ScanY=RoverLocationY;
	StraightObstacleDistance=0;
	if(RoverDirection == 0)
	{	
		while((ScanX < e.EnvironmentData.length - 1) )
		{
			ScanX+=1;
			PerceivedEnvironment[ScanX][ScanY] = e.EnvironmentData[ScanX][ScanY];
			StraightObstacleDistance++;
			if(e.EnvironmentData[ScanX][ScanY] == 'O')
			{
				break;
			}
		}
	}
	else if(RoverDirection == 45)
	{
		while(ScanX < (e.EnvironmentData.length - 1) && ScanY > 0)
		{
			ScanX+=1;
			ScanY-=1;
			PerceivedEnvironment[ScanX][ScanY] = e.EnvironmentData[ScanX][ScanY];
			StraightObstacleDistance++;
			if(e.EnvironmentData[ScanX][ScanY] == 'O')
			{
				break;
			}
		}
	}
	else if(RoverDirection == 90)
	{
		while(ScanY >0 )
		{
			ScanY-=1;
			PerceivedEnvironment[ScanX][ScanY] = e.EnvironmentData[ScanX][ScanY];
			StraightObstacleDistance++;
			if(e.EnvironmentData[ScanX][ScanY] == 'O')
			{
				break;
			}
		}
	}
	else if(RoverDirection == 135)
	{
		while(ScanY > 0 && ScanX > 0)
		{
			ScanX-=1;
			ScanY-=1;
			PerceivedEnvironment[ScanX][ScanY] = e.EnvironmentData[ScanX][ScanY];
			StraightObstacleDistance++;
			if(e.EnvironmentData[ScanX][ScanY] == 'O')
			{
				break;
			}
		}
	}
	else if(RoverDirection == 180)
	{
		while(ScanX > 0)
		{
			ScanX-=1;
			PerceivedEnvironment[ScanX][ScanY] = e.EnvironmentData[ScanX][ScanY];
			StraightObstacleDistance++;
			if(e.EnvironmentData[ScanX][ScanY] == 'O')
			{
				break;
			}
		}
	}
	else if(RoverDirection == 225)
	{
		while(ScanX >0 && ScanY < (e.EnvironmentData[0].length - 1))
		{
			ScanX-=1;
			ScanY+=1;
			PerceivedEnvironment[ScanX][ScanY] = e.EnvironmentData[ScanX][ScanY];
			StraightObstacleDistance++;
			if(e.EnvironmentData[ScanX][ScanY] == 'O')
			{
				break;
			}
		}
	}
	else if(RoverDirection == 270)
	{
		while(ScanY < (e.EnvironmentData[0].length - 1) )
		{
			ScanY+=1;
			PerceivedEnvironment[ScanX][ScanY] = e.EnvironmentData[ScanX][ScanY];
			StraightObstacleDistance++;
			if(e.EnvironmentData[ScanX][ScanY] == 'O')
			{
				break;
			}
		}
	}
	else if(RoverDirection == 315)
	{
		while(ScanX < (e.EnvironmentData.length - 1) && ScanY < (e.EnvironmentData[0].length - 1) )
		{
			ScanX+=1;
			ScanY+=1;
			PerceivedEnvironment[ScanX][ScanY] = e.EnvironmentData[ScanX][ScanY];
			StraightObstacleDistance++;
			if(e.EnvironmentData[ScanX][ScanY] == 'O')
			{
				break;
			}
		}
	}	
}


void LeftScanner(Environment e){
	int ScanX=RoverLocationX;
	int ScanY=RoverLocationY;
	LeftObstacleDistance =0;
	if(RoverDirection == 0)
	{	
		while(ScanX < (e.EnvironmentData.length - 1) && ScanY > 0)
		{
			ScanX+=1;
			ScanY-=1;
			PerceivedEnvironment[ScanX][ScanY] = e.EnvironmentData[ScanX][ScanY];
			LeftObstacleDistance++;
			if(e.EnvironmentData[ScanX][ScanY] == 'O')
			{
				break;
			}
		}
	}
	else if(RoverDirection == 45)
	{
		while(ScanY > 0)
		{
			ScanY-=1;
			PerceivedEnvironment[ScanX][ScanY] = e.EnvironmentData[ScanX][ScanY];
			LeftObstacleDistance++;
			if(e.EnvironmentData[ScanX][ScanY] == 'O')
			{
				break;
			}
		}
	}
	else if(RoverDirection == 90)
	{
		while(ScanX > 0 && ScanY >0 )
		{
			ScanX-=1;
			ScanY-=1;
			PerceivedEnvironment[ScanX][ScanY] = e.EnvironmentData[ScanX][ScanY];
			LeftObstacleDistance++;
			if(e.EnvironmentData[ScanX][ScanY] == 'O')
			{
				break;
			}
		}
	}
	else if(RoverDirection == 135)
	{
		while(ScanX > 0)
		{
			ScanX-=1;
			PerceivedEnvironment[ScanX][ScanY] = e.EnvironmentData[ScanX][ScanY];
			LeftObstacleDistance++;
			if(e.EnvironmentData[ScanX][ScanY] == 'O')
			{
				break;
			}
		}
	}
	else if(RoverDirection == 180)
	{
		while(ScanX > 0 && ScanY < (e.EnvironmentData[0].length - 1))
		{
			ScanY+=1;
			ScanX-=1;
			PerceivedEnvironment[ScanX][ScanY] = e.EnvironmentData[ScanX][ScanY];
			LeftObstacleDistance++;
			if(e.EnvironmentData[ScanX][ScanY] == 'O')
			{
				break;
			}
		}
	}
	else if(RoverDirection == 225)
	{
		while(ScanY < (e.EnvironmentData[0].length - 1))
		{
			ScanY+=1;
			PerceivedEnvironment[ScanX][ScanY] = e.EnvironmentData[ScanX][ScanY];
			LeftObstacleDistance++;
			if(e.EnvironmentData[ScanX][ScanY] == 'O')
			{
				break;
			}
		}
	}
	else if(RoverDirection == 270)
	{
		while(ScanX < (e.EnvironmentData.length - 1) && ScanY < (e.EnvironmentData[0].length - 1))
		{
			ScanX+=1;
			ScanY+=1;
			PerceivedEnvironment[ScanX][ScanY] = e.EnvironmentData[ScanX][ScanY];
			LeftObstacleDistance++;
			if(e.EnvironmentData[ScanX][ScanY] == 'O')
			{
				break;
			}
		}
	}
	else if(RoverDirection == 315)
	{
		while(ScanX < (e.EnvironmentData.length - 1))
		{
			ScanX+=1;
			PerceivedEnvironment[ScanX][ScanY] = e.EnvironmentData[ScanX][ScanY];
			LeftObstacleDistance++;
			if(e.EnvironmentData[ScanX][ScanY] == 'O')
			{
				break;
			}
		}
	}	
}

int DistanceFromGoal(){
	double SquareDistance;
	SquareDistance = ((RoverLocationX - GoalX)*(RoverLocationX - GoalX)) + ((RoverLocationY - GoalY)*(RoverLocationY - GoalY));
	return (int) (Math.sqrt(
			SquareDistance));
}

boolean OptimumMovePossible(){
	
	if (RoverLocationX <  GoalX && RoverLocationY < GoalY && (PerceivedEnvironment[RoverLocationX + 1][RoverLocationY + 1] != 'O' && !VisitedNodes(RoverLocationX + 1,RoverLocationY + 1)))
	{
		return true;
	}
	else if(RoverLocationX <  GoalX && RoverLocationY < GoalY && PerceivedEnvironment[RoverLocationX + 1][RoverLocationY + 1] == 'O')
	{
		return false;
	}
	else if(RoverLocationX >  GoalX && RoverLocationY < GoalY && (PerceivedEnvironment[RoverLocationX - 1][RoverLocationY + 1] != 'O' && !VisitedNodes(RoverLocationX - 1,RoverLocationY + 1)))
	{
		return true;	
	}
	else if(RoverLocationX >  GoalX && RoverLocationY < GoalY && PerceivedEnvironment[RoverLocationX - 1][RoverLocationY + 1] == 'O')
	{
		return false;
	}
	else if(RoverLocationX <  GoalX && RoverLocationY > GoalY && (PerceivedEnvironment[RoverLocationX + 1][RoverLocationY - 1] != 'O' && !VisitedNodes(RoverLocationX + 1,RoverLocationY - 1)))
	{
		return true;
	}
	else if(RoverLocationX <  GoalX && RoverLocationY > GoalY && PerceivedEnvironment[RoverLocationX + 1][RoverLocationY - 1] == 'O')
	{
		return false;
	}
	else if(RoverLocationX >  GoalX && RoverLocationY > GoalY && (PerceivedEnvironment[RoverLocationX - 1][RoverLocationY - 1] != 'O'  && !VisitedNodes(RoverLocationX - 1,RoverLocationY - 1)))
	{
		return true;
	}
	else if(RoverLocationX >  GoalX && RoverLocationY > GoalY && PerceivedEnvironment[RoverLocationX - 1][RoverLocationY - 1] == 'O')
	{
		return false;
	}
	else if(RoverLocationX == GoalX && RoverLocationY > GoalY && (PerceivedEnvironment[RoverLocationX][RoverLocationY - 1] !='O' && !VisitedNodes(RoverLocationX,RoverLocationY - 1)))
	{
		return true;
	}
	else if(RoverLocationX == GoalX && RoverLocationY > GoalY && PerceivedEnvironment[RoverLocationX][RoverLocationY - 1] =='O')
	{
		return false;
	}
	else if(RoverLocationX == GoalX && RoverLocationY < GoalY && (PerceivedEnvironment[RoverLocationX][RoverLocationY + 1] !='O' && !VisitedNodes(RoverLocationX,RoverLocationY + 1)))
	{
		return true;
	}
	else if(RoverLocationX == GoalX && RoverLocationY < GoalY && PerceivedEnvironment[RoverLocationX][RoverLocationY + 1] =='O')  
	{
		return false;
	}
	else if(RoverLocationX < GoalX && RoverLocationY == GoalY && (PerceivedEnvironment[RoverLocationX + 1][RoverLocationY] !='O' && !VisitedNodes(RoverLocationX + 1,RoverLocationY)))
	{
		return true;
	}
	else if(RoverLocationX < GoalX && RoverLocationY == GoalY && PerceivedEnvironment[RoverLocationX + 1][RoverLocationY] =='O')  
	{
		return false;
	}
	else if(RoverLocationX > GoalX && RoverLocationY == GoalY && (PerceivedEnvironment[RoverLocationX - 1][RoverLocationY] !='O' && !VisitedNodes(RoverLocationX - 1,RoverLocationY)))
	{
		return true;
	}
	else if(RoverLocationX > GoalX && RoverLocationY == GoalY && PerceivedEnvironment[RoverLocationX - 1][RoverLocationY] =='O') 
	{
		return false;
	}
	else if((RoverLocationX <= GoalX+1 &&  RoverLocationX >= GoalX-1) && (RoverLocationY <= GoalY+1 &&  RoverLocationY >= GoalY-1))
	{return true;}
	else
	{return false;}
}

int OptimumAngle(){
	if(RoverLocationX < GoalX && RoverLocationY < GoalY)
	{
		return 315;
	}
	else if(RoverLocationX < GoalX && RoverLocationY == GoalY)
	{
		return 0;
	}
	else if(RoverLocationX < GoalX && RoverLocationY > GoalY)
	{
		return 45;
	}
	else if(RoverLocationX > GoalX && RoverLocationY < GoalY)
	{
		return 225;
	}
	else if(RoverLocationX > GoalX && RoverLocationY == GoalY)
	{
		return 180;
	}
	else if(RoverLocationX > GoalX && RoverLocationY > GoalY)
	{
		return 135;
	}
	else if(RoverLocationX == GoalX && RoverLocationY < GoalY)
	{
		return 270;
	}
	else
	{
		return 90;
	}
}

void SteerRight(Environment e)
{
	TurnRight(e);
	MoveForward(e);
}

void TurnTowards(int Angle,Environment e){
	if(RoverDirection == 0 )
	{ if(Angle >=45 && Angle <= 180)
		{TurnLeft(e);}
	  else
		{TurnRight(e);}
	}
	else if(RoverDirection == 45)
	{
		if(Angle >=90 && Angle <= 225)
			{TurnLeft(e);}
		else 
			{TurnRight(e);}
	}
	else if(RoverDirection == 90){
		if(Angle >= 135 && Angle <=270)
			{TurnLeft(e);}
		else
			{TurnRight(e);}
	}
	else if(RoverDirection == 135)
	{
		if(Angle >= 180 && Angle <= 315)
		{TurnLeft(e);}
		else
		{TurnRight(e);}
	}
	else if(RoverDirection == 180)
	{
		if((Angle >= 225 && Angle <=315) || (Angle ==0))
			{TurnLeft(e);}
		else
			{TurnRight(e);}
	}
	else if(RoverDirection == 225)
	{
		if((Angle >=270 && Angle <=315) || (Angle <=45))
			{TurnLeft(e);}
		else
			{TurnRight(e);}
	}
	else if(RoverDirection == 270)
	{
		if((Angle == 315) || (Angle <=90))
			{TurnLeft(e);}
		else
			{TurnRight(e);}
	}
	else if(RoverDirection == 315)
	{
		if(Angle <= 135)
			{TurnLeft(e);}
		else
			{TurnRight(e);}
	}
}
boolean VisitedNodes(int x,int y)
{
	for(int i=0;i<RoverHistory.length;i++)
	{
		if(RoverHistory[i][0] == x && RoverHistory[i][1] == y)
		{return true;}
	}
	return false;
}

void NextMove(Environment e){
	
	if(OptimumMovePossible() && RoverDirection == OptimumAngle())
	{
		MoveForward(e);
	}
	if(OptimumMovePossible() && RoverDirection != OptimumAngle())
	{
		TurnTowards(OptimumAngle(),e);
	}
	if(!OptimumMovePossible() && StraightObstacleDistance > 1)
	{
		MoveForward(e);
	}
	if(!OptimumMovePossible() && RightObstacleDistance > 1)
	{
		SteerRight(e);
	}
	if(!OptimumMovePossible() && LeftObstacleDistance > 1)
	{
		SteerLeft(e);
	}
	if(!OptimumMovePossible())
	{
		TurnLeft(e);
	}
	
	if(!OptimumMovePossible() && (RoverLocationX == (SizeX - 1)))
	{
		TurnLeft(e);
	}
}

void MoveTowards(int goalx ,int goaly,Environment E)
{
	GoalX = goalx;
	GoalY = goaly;
	PerceivedEnvironment = new char[E.EnvironmentData.length][E.EnvironmentData[0].length];
	while(!(RoverLocationX == GoalX) && !(RoverLocationY == GoalY))
	{NextMove(E);
	Moves++;}
	
}
public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		String filename = args[0];
		Scanner scanner = new Scanner(new File(filename));
			int SizeX = scanner.nextInt();
			int SizeY = scanner.nextInt();
			int RoverLocationX = scanner.nextInt();
			int RoverLocationY = scanner.nextInt();
			rover self = new rover(RoverLocationX,RoverLocationY);
			self.RoverDirection = scanner.nextInt();
			self.GoalX = scanner.nextInt();
			self.GoalY = scanner.nextInt();
			Environment E = new Environment(SizeX,SizeY,self);
			while(scanner.hasNextInt())
			{
				int ObstacleX = scanner.nextInt();
				int ObstacleY = scanner.nextInt();
				E.AddObstacle(ObstacleX,ObstacleY);
			}
			//E.GenerateRandom(40, self);
			scanner.close();
			RoverBoard gui;
		Writer wr = new FileWriter("output.txt");
		/*rover self = new rover(24,24);
		self.RoverDirection = 90;
		self.GoalX = 0;
		self.GoalY = 0;
		Environment E = new Environment(25,25,self);
		
		
		for(int i=0;i<E.Obstacle.length;i++)
		{for(int j=0;j<E.Obstacle[0].length;j++)
		{}}*/
		self.PerceivedEnvironment = new char[E.EnvironmentData.length][E.EnvironmentData[0].length];
		while(true)
		{	
			self.NextMove(E);
			wr.write(new Integer(self.RoverLocationX).toString());
			wr.write(" ");
			wr.write(new Integer(self.RoverLocationY).toString());
			wr.write(" ");
			wr.write(new Integer(self.RoverDirection).toString());
			wr.write(System.getProperty("line.separator"));
			System.out.print("The number of moves is:");
			System.out.println(self.Moves);
			if(self.RoverLocationX == self.GoalX && self.RoverLocationY == self.GoalY)
				{break;}
			if(self.Moves>=9990)
			{
				System.out.println("Rover can't find a path");
				break;
			}
		}
		gui = new RoverBoard(self,E);
		wr.close();

		
		
	}

}
