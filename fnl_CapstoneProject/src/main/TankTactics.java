/*
 * Author: Itay Volk
 * Date: 4/30/2022
 * Rev: 04
 * Notes: this class manages a TankTactics game
 */

package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import boosters.*;
import tanks.*;

@SuppressWarnings("serial")
public class TankTactics extends JFrame
				implements ActionListener{
	//Fields
	private JButton [] [] buttons;
	private FieldElement [] [] fieldElements;
	private Tank currentPlayer;
	private Tank [] players, alive, jury;
	private DOT_Tank [] DOT;
	private Booster [] boosters;
	private long startingTime;
	private int cycleLength;
	private Timer clock;
	
	//Constructor
	public TankTactics ()
	{
		super ("Tank Tactics");
		
		File currentDirFile = new File(".");
		String helper = currentDirFile.getAbsolutePath();
		startingTime = 0;
		cycleLength = 0;
		players = new Tank [0];
		boosters = new Booster [0];
		DOT = new DOT_Tank [0];
		try {//Reads the game save file and sets up the game.
			String currentDir = helper.substring(0, helper.length() - currentDirFile.getCanonicalPath().length());
			File file = new File (currentDir + "game save.txt");
			Scanner fileIn = null;
		      try
		      {
		        fileIn = new Scanner(file);
		      }
		      catch (IOException ex) {}
		      
		      StringBuffer buffer = new StringBuffer((int)file.length());
		      while (fileIn.hasNextLine())
		        buffer.append(fileIn.nextLine());
		      String input = buffer.toString();
		      Scanner reader = new Scanner(input);
		      
		      if (!input.isBlank())
		      {
			      startingTime = reader.nextLong();
			      cycleLength = reader.nextInt();
			      int xField = reader.nextInt();
		    	  int yField = reader.nextInt();
		    	  buttons = new JButton [xField] [yField];
		    	  fieldElements = new FieldElement [xField] [yField];
			      
		    	  for (int i = 0; i < xField; i++)
		    	  {
		    		  for (int j = 0; j < yField; j++)
		    		  {
		    			  buttons [i] [j] = new JButton();
		    		  }
		    	  }
		    	  
			      while (!reader.nextLine().equals("****"))
			      {
			    	  int x = reader.nextInt();
			    	  int y = reader.nextInt();
			    	  String name = reader.nextLine();
			    	  int power = reader.nextInt();
			    	  int shootingRange = reader.nextInt();
			    	  int movementRange = reader.nextInt();
			    	  int life = reader.nextInt();
			    	  int maxLife = reader.nextInt();
			    	  int energy = reader.nextInt();
			    	  int maxEnergy = reader.nextInt();
			    	  int special = reader.nextInt();
			    	  int votes = reader.nextInt();
			    	  String password = reader.nextLine();
			    	  String type = reader.next();
			    	  
			    	  Tank nextPlayer = null;
			    	  if (type.equalsIgnoreCase(Tank.AOE))
			    		  nextPlayer = new AOE_Tank (x, y, name, power, shootingRange, movementRange, life, maxLife, energy, maxEnergy, special, votes, password, buttons[x][y], this);
			    	  else 	if (type.equalsIgnoreCase(Tank.BALANCED))
			    		  nextPlayer = new BalancedTank (x, y, name, power, shootingRange, movementRange, life, maxLife, energy, maxEnergy, special, votes, password, buttons[x][y], this);
			    	  else 	if (type.equalsIgnoreCase(Tank.DOT))
			    	  {
			    		  nextPlayer = new DOT_Tank (x, y, name, power, shootingRange, movementRange, life, maxLife, energy, maxEnergy, special, votes, password, buttons[x][y], this);
			    		  DOT_Tank [] addedDOT = new DOT_Tank [DOT.length + 1];
				    	  for (int i = 0; i < DOT.length; i++)
				    	  {
				    		  addedDOT[i] = DOT[i];
				    	  }
				    	  addedDOT[players.length] = (DOT_Tank) nextPlayer;
				    	  players = addedDOT;
			    	  }
		    		  else 	if (type.equalsIgnoreCase(Tank.HEAVY))
			    		  nextPlayer = new HeavyTank (x, y, name, power, shootingRange, movementRange, life, maxLife, energy, maxEnergy, special, votes, password, buttons[x][y], this);
			    	  else 	if (type.equalsIgnoreCase(Tank.LIGHT))
			    		  nextPlayer = new LightTank (x, y, name, power, shootingRange, movementRange, life, maxLife, energy, maxEnergy, special, votes, password, buttons[x][y], this);
			    	  else
			    	  {
			    		  throw new IOException(
			    				  "No tank type for " + type);
			    	  }
			    	  
			    	  
			    	  
			    	  Tank [] addedPlayers = new Tank [players.length + 1];
			    	  for (int i = 0; i < DOT.length; i++)
			    	  {
			    		  addedPlayers[i] = players[i];
			    	  }
			    	  addedPlayers[players.length] = nextPlayer;
			    	  players = addedPlayers;
			      }
			      
			      while (reader.hasNextLine())
			      {
			    	  int x = reader.nextInt();
			    	  int y = reader.nextInt();
			    	  int strength = reader.nextInt();
			    	  String type = reader.next();
			    	  
			    	  Booster nextBooster = null;
			    	  if (type.equalsIgnoreCase(Booster.ENERGY))
			    		  nextBooster = new EnergySupplier (x, y, strength, buttons[x][y], this);
			    	  else 	if (type.equalsIgnoreCase(Booster.HEAL))
			    		  nextBooster = new Healer (x, y, strength, buttons[x][y], this);
			    	  else 	if (type.equalsIgnoreCase(Booster.HIDDEN))
			    	  {
			    		  Color tileColor;
		    			  if (x + y % 2 == 0)
		    				  tileColor = new Color(69, 177, 72);
		    			  else
		    				  tileColor = new Color(82, 188, 82);
			    		  nextBooster = new HiddenBooster (x, y, strength, buttons[x][y], this, tileColor);
			    	  }
			    	  else 	if (type.equalsIgnoreCase(Booster.JUMPER))
			    		  nextBooster = new Jumper (x, y, strength, buttons[x][y], this);
			    	  else 	if (type.equalsIgnoreCase(Booster.MAX_ENERGY))
			    		  nextBooster = new MaxEnergyBooster (x, y, strength, buttons[x][y], this);
			    	  else 	if (type.equalsIgnoreCase(Booster.MAX_LIFE))
			    		  nextBooster = new MaxLifeBooster (x, y, strength, buttons[x][y], this);
			    	  else 	if (type.equalsIgnoreCase(Booster.MOVEMENT_RANGE))
			    		  nextBooster = new MovementRangeBooster (x, y, strength, buttons[x][y], this);
			    	  else 	if (type.equalsIgnoreCase(Booster.POWER))
			    		  nextBooster = new PowerBooster (x, y, strength, buttons[x][y], this);
			    	  else 	if (type.equalsIgnoreCase(Booster.SHOOT))
			    		  nextBooster = new Shooter (x, y, strength, buttons[x][y], this);
			    	  else 	if (type.equalsIgnoreCase(Booster.SHOOTING_RANGE))
			    		  nextBooster = new ShootingRangeBooster (x, y, strength, buttons[x][y], this);
			    	  else 	if (type.equalsIgnoreCase(Booster.SPECIAL))
			    		  nextBooster = new SpecialBooster (x, y, strength, buttons[x][y], this);
			    	  else 	if (type.equalsIgnoreCase(Booster.UNKNOWN))
			    		  nextBooster = new UnknownBooster (x, y, strength, buttons[x][y], this);
			    	  else
			    	  {
			    		  throw new IOException(
			    				  "No booster type for " + type);
			    	  }
			    	  
			    	  Booster [] addedBoosters = new Booster [boosters.length + 1];
			    	  for (int i = 0; i < boosters.length; i++)
			    	  {
			    		  addedBoosters[i] = boosters[i];
			    	  }
			    	  addedBoosters[boosters.length] = nextBooster;
			    	  boosters = addedBoosters;
			      }
		      }
		      else
		    	  newGame();
		} catch (IOException e) {
			newGame();
		}
	    alive = players;
	    jury = new Tank [0];
		for (int i = 0; i < fieldElements.length; i++)
	      {
	    	  for (int j = 0; j < fieldElements[i].length; i++)
	    	  {
	    		  if (fieldElements[i][j] == null)
	    		  {
	    			  Color tileColor;
	    			  if (i+j % 2 == 0)
	    				  tileColor = new Color(69, 177, 72);
	    			  else
	    				  tileColor = new Color(82, 188, 82);
	    			  
	    			  fieldElements[i][j] = new FieldElement(i, j, buttons[i][j], this, tileColor, "");
	    		  }
	    	  }
	      }
		newLogin();
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(fieldElements.length, fieldElements[0].length));
		for (int i = 0; i < fieldElements[0].length; i++)
		{
			for (int j = 0; j < fieldElements.length; j++)
			{
				panel.add(buttons[j][i]);
			}
		}
		Container c = getContentPane();
		c.add(panel, BorderLayout.CENTER);
		
		setSize(fieldElements.length * 50, fieldElements[0].length * 50);
		
		//Saves the data to the game save file when the window is closed, after asking the user if they want to log in again.
		addWindowListener(new WindowAdapter() {
			@Override
		    public void windowClosing(WindowEvent windowEvent) {
		        boolean continueAsking = true, saveToFile = false;
		        while (continueAsking)
		        {
		        	
		        	System.out.print("Do you want to play again? (answer yes or no) ");
		        	String answer = new Scanner(System.in).next();
		        	if(answer.equalsIgnoreCase("yes"))
		        	{
		        		continueAsking = false;
				        newLogin();
		        	}
		        	if(answer.equalsIgnoreCase("no"))
		        	{
		        		continueAsking = false;
		        		saveToFile = true;
		        	}
		        }
		        
		        if(saveToFile)
		        {
		        	clock.stop();
		        	startingTime = System.currentTimeMillis();
		        	Runnable saver = new Runnable() {
		        		public void run() {
							String currentDir = helper.substring(0, helper.length() - currentDirFile.getCanonicalPath().length());
							File file = new File (currentDir + "game save.txt");
							PrintWriter fileOut;
					        try
					        {
					          fileOut = new PrintWriter(new FileWriter(file));
					        }
					        catch (IOException ex)
					        {
					        	throw new IOException (
					        			"Cannot access game save file.");
					        }
					        
					        String save = startingTime + "\n" + cycleLength + "\n" + fieldElements.length + "\n" + fieldElements[0].length;
					        for(int i = 0; i < players.length; i++)
					        {
					        	save += "\n" + alive[i].getX() + "\n" + alive[i].getY() + "\n" + alive[i].getName() + "\n" + alive[i].getPower() + "\n" + alive[i].getShootingRange() + "\n" + alive[i].getMovementRange()
					        			 + "\n" + alive[i].getLife() + "\n" + alive[i].getMaxLife() + "\n" + alive[i].getEnergy() + "\n" + alive[i].getMaxEnergy() + "\n" + alive[i].getSpecial()
					        			 + "\n" + alive[i].getVotes() + "\n" + alive[i].getPassword() + "\n";
					        	if (alive[i].getType().equalsIgnoreCase(Tank.AOE))
					        		save +=  Tank.AOE;
					        	else if (alive[i].getType().equalsIgnoreCase(Tank.BALANCED))
					        		save +=  Tank.BALANCED;
					        	else if (alive[i].getType().equalsIgnoreCase(Tank.DOT))
					        		save +=  Tank.DOT;
					        	else if (alive[i].getType().equalsIgnoreCase(Tank.HEAVY))
				        			save +=  Tank.HEAVY;
					        	else if (alive[i].getType().equalsIgnoreCase(Tank.LIGHT))
					        		save +=  Tank.LIGHT;
					        }
					        
					        save += "\n****";
					        for(int i = 0; i < boosters.length; i++)
					        {
					        	save += "\n" + boosters[i].getX() + "\n" + boosters[i].getY() + "\n" + boosters[i].getStrength() + "\n";
						    	  if (boosters[i].getType().equalsIgnoreCase(Booster.ENERGY))
						    		  save += Booster.ENERGY;
						    	  else if (boosters[i].getType().equalsIgnoreCase(Booster.HEAL))
						    		  save += Booster.HEAL;
						    	  else if (boosters[i].getType().equalsIgnoreCase(Booster.HIDDEN))
						    		  save += Booster.HIDDEN;
						    	  else if (boosters[i].getType().equalsIgnoreCase(Booster.JUMPER))
						    		  save += Booster.JUMPER;
						    	  else if (boosters[i].getType().equalsIgnoreCase(Booster.MAX_ENERGY))
						    		  save += Booster.MAX_ENERGY;
						    	  else if (boosters[i].getType().equalsIgnoreCase(Booster.MAX_LIFE))
						    		  save += Booster.MAX_LIFE;
						    	  else if (boosters[i].getType().equalsIgnoreCase(Booster.MOVEMENT_RANGE))
						    		  save += Booster.MOVEMENT_RANGE;
						    	  else if (boosters[i].getType().equalsIgnoreCase(Booster.POWER))
						    		  save += Booster.POWER;
						    	  else if (boosters[i].getType().equalsIgnoreCase(Booster.SHOOT))
						    		  save += Booster.SHOOT;
						    	  else if (boosters[i].getType().equalsIgnoreCase(Booster.SHOOTING_RANGE))
						    		  save += Booster.SHOOTING_RANGE;
						    	  else if (boosters[i].getType().equalsIgnoreCase(Booster.SPECIAL))
						    		  save += Booster.SPECIAL;
						    	  else if (boosters[i].getType().equalsIgnoreCase(Booster.UNKNOWN))
						    		  save += Booster.UNKNOWN;
					        }
					        
					        fileOut.print(save);
					        fileOut.close();
		        		}
			        };
		        }
			}
		});
		
		draw();
		clock = new Timer((int)((long)cycleLength*1000 + startingTime - System.currentTimeMillis()), this);
		clock.start();
	}
	
	//Public methods
	
	//Draws all the field elements
	public void draw()
	{
		for (int i = 0; i < fieldElements[0].length; i++)
		{
			for (int j = 0; j < fieldElements.length; j++)
			{
				fieldElements[j][i].draw();
			}
		}
	}
	
	//Prompts the user to input the password and tank name, checks which Tank fits those, and sets that Tank as currentPlayer
	public void newLogin() throws IOException
	{
		Scanner reader = new Scanner(System.in);
		
		boolean continueAsking = true;
		do
		{
			System.out.println("Enter the currect player's name ");
			String name = reader.nextLine();
			System.out.println("Enter the currect player's password ");
			String password = reader.nextLine();
			
			currentPlayer = null;
			for(int i = 0; i < players.length; i++)
			{
				if (players[i].checkPassword(name, password))
				{
					currentPlayer = players[i];
				}
			}
			if(currentPlayer != null)
			{
	    		  throw new IOException(
	    				  "No player named " + name + " with the inputted password exists in this game.");
			}
			else
			{
				continueAsking = false;
			}
		} while (continueAsking);
		
		reader.close();
	}
	
	//Called whenever the timer reaches zero, symbolizes a new cycle.
	@Override
	public void actionPerformed(ActionEvent e) {
		clock.stop();
		int newX = (int)(Math.random() * fieldElements.length);
		int newY = (int)(Math.random() * fieldElements[0].length);
		int i = 0;
		while (i < boosters.length)
		{
			if (boosters[i].getX() == newX && boosters[i].getY() == newY)
			{
				newX = (int)(Math.random() * fieldElements.length);
				newY = (int)(Math.random() * fieldElements[0].length);
				i = 0;
			}
		}
		
		int type = (int)(Math.random() * 12);
		int strength = (int)(Math.random() * 5) + 1;
		if ((int)(Math.random() * 2) == 0)
		{
			strength *= -1;
		}
		
		Booster newBooster = null;
		switch (type)
		{
			case 0:
				newBooster = new EnergySupplier(newX, newY, strength, buttons[newX][newY], this);
				break;
			case 1:
				newBooster = new Healer(newX, newY, strength, buttons[newX][newY], this);
				break;
			case 2:
				Color tileColor;
				if (newX+newY % 2 == 0)
					tileColor = new Color(69, 177, 72);
				else
					tileColor = new Color(82, 188, 82);
				newBooster = new HiddenBooster(newX, newY, strength, buttons[newX][newY], this, tileColor);
				break;
			case 3:
				newBooster = new Jumper(newX, newY, strength, buttons[newX][newY], this);
				break;
			case 4:
				newBooster = new MaxEnergyBooster(newX, newY, strength, buttons[newX][newY], this);
				break;
			case 5:
				newBooster = new MaxLifeBooster(newX, newY, strength, buttons[newX][newY], this);
				break;
			case 6:
				newBooster = new MovementRangeBooster(newX, newY, strength, buttons[newX][newY], this);
				break;
			case 7:
				newBooster = new PowerBooster(newX, newY, strength, buttons[newX][newY], this);
				break;
			case 8:
				newBooster = new Shooter(newX, newY, strength, buttons[newX][newY], this);
				break;
			case 9:
				newBooster = new ShootingRangeBooster(newX, newY, strength, buttons[newX][newY], this);
				break;
			case 10:
				newBooster = new SpecialBooster(newX, newY, strength, buttons[newX][newY], this);
				break;
			case 11:
				newBooster = new UnknownBooster(newX, newY, strength, buttons[newX][newY], this);
				break;
		}
	  	Booster [] addedBoosters = new Booster [boosters.length + 1];
	  	for (i = 0; i < boosters.length; i++)
	  	{
	  		addedBoosters[i] = boosters[i];
	  	}
	  	addedBoosters[boosters.length] = newBooster;
	  	  
	  	for (i = 0; i < alive.length; i++)
	  	{
	  		alive[i].gainEnergy(1);
	  	}
	  	boosters = addedBoosters;
	  	
	  	for(i = 0; i < DOT.length; i++)
	  	{
	  		DOT[i].newCycle();
	  	}
	  	draw();
		clock = new Timer((int)(cycleLength*1000 + startingTime - System.currentTimeMillis()), this);
		clock.start();
	}
	
	//Getters
	//Returns currentPlayer
	public Tank getCurrentPlayer()
	{
		return currentPlayer;
	}
	
	//Returns fieldElements
	public FieldElement[][] getFieldElements()
	{
		return fieldElements;
	}
	
	public JButton[][] getButtons()
	{
		return buttons;
	}
	
	//Returns buttons
	public Tank[] getPlayers()
	{
		return players;
	}
	
	//Returns alive
	public Tank[] getAlive()
	{
		return alive;
	}
	
	//Returns juty
	public Tank[] getJury()
	{
		return jury;
	}
	
	//Setters
	//Sets fieldElements to the inputted value
	public void setFieldElements (FieldElement[][] newField)
	{
		fieldElements = newField;
	}
	
	//Sets buttons to the inputted value
	public void setButtons (JButton[][] newButtons)
	{
		buttons = newButtons;
	}
	
	//Sets players to the inputted value
	public void setPlayers (Tank[] newPlayers)
	{
		players = newPlayers;
	}
	
	//Sets alive to the inputted value
	public void setAlive (Tank[] newAlive)
	{
		alive = newAlive;
	}
	
	//Sets jury to the inputted value
	public void setJury (Tank[] newJury)
	{
		jury = newJury;
	}
	
	//Private methods
	
	//Prompts the user to input the information about the new game
	private void newGame() throws IOException
	{
		Scanner reader = new Scanner(System.in);
		
		System.out.print("Enter the width of the field ");
		int xField = reader.nextInt();
		System.out.print("Enter the height of the field ");
		int yField = reader.nextInt();
		buttons = new JButton[xField][yField];
		fieldElements = new FieldElement[xField][yField];
		for (int i = 0; i < buttons.length; i++)
		{
			for (int j = 0; j < buttons[0].length; j++)
			{
				buttons [i] [j] = new JButton();
			}
		}
		
		System.out.print("Enter the amount of players ");
		players = new Tank[reader.nextInt()];
		for(int i = 0; i < players.length; i++)
		{
			System.out.print("Enter the name of the player ");
			String name = reader.nextLine();
			System.out.print("Enter the password of the player ");
			String password = reader.nextLine();
			System.out.print("Enter the type of the player (types are: " + Tank.AOE + ", " + Tank.BALANCED + ", " + Tank.DOT + ", " + Tank.HEAVY + ", and " + Tank.LIGHT +".");
			String type = reader.nextLine();
			int x = (int)(Math.random() * xField); 
			int y = (int)(Math.random() * yField);
			int j = 0;
			while (players[j] != null && j < players.length)
			{
				if (x == players[j].getX() && y == players[j].getY())
				{
					x = (int)(Math.random() * xField); 
					y = (int)(Math.random() * yField);
					j = 0;
				}
				else
				{
					j++;
				}
			}
			Tank nextPlayer = null;
			if (type.equalsIgnoreCase(Tank.AOE))
				nextPlayer = new AOE_Tank (x, y, name, 1, 1, 1, 3, 3, 1, 5, 1, 0, password, buttons[x][y], this);
			else 	if (type.equalsIgnoreCase(Tank.BALANCED))
				nextPlayer = new BalancedTank (x, y, name, 1, 1, 1, 3, 3, 1, 5, 1, 0, password, buttons[x][y], this);
			else 	if (type.equalsIgnoreCase(Tank.DOT))
				nextPlayer = new DOT_Tank (x, y, name, 1, 1, 1, 3, 3, 1, 5, 1, 0, password, buttons[x][y], this);
			else 	if (type.equalsIgnoreCase(Tank.HEAVY))
				nextPlayer = new HeavyTank (x, y, name, 1, 1, 1, 3, 3, 1, 5, 1, 0, password, buttons[x][y], this);
			else 	if (type.equalsIgnoreCase(Tank.LIGHT))
				nextPlayer = new LightTank (x, y, name, 1, 1, 1, 3, 3, 1, 5, 1, 0, password, buttons[x][y], this);
			else
			{
				i--;
				throw new IOException(
						"No tank type for " + type);
			}
			players[i] = nextPlayer;
		}
		alive = players;
		jury = new Tank[0];
		
		System.out.print("Enter the length of a cycle in secends ");
		cycleLength = reader.nextInt();
		startingTime = System.currentTimeMillis();
		
		reader.close();
	}
}
