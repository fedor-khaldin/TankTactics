/*
 * Author: Itay Volk
 * Date: 5/4/2022
 * Rev: 06
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

@SuppressWarnings({"serial", "resource", "unused"})
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
	private Scanner reader = new Scanner(System.in);
	
	//Constructor
	public TankTactics ()
	{
		super ("Tank Tactics");
		
		startingTime = 0;
		cycleLength = 0;
		players = new Tank [0];
		alive = new Tank [0];
		jury = new Tank [0];
		boosters = new Booster [0];
		DOT = new DOT_Tank [0];
		try {//Reads the game save file and sets up the game.
			Scanner reader = new Scanner(System.in);
			System.out.print("Enter the directory of the save file ");
			String dir = reader.nextLine();
			File file = new File (dir);
			Scanner fileIn = null;
		    fileIn = new Scanner(file);
		      
		    if (file.exists() && fileIn.hasNext())
		    {
			      startingTime = fileIn.nextLong();
			      cycleLength = fileIn.nextInt();
			      int xField = fileIn.nextInt();
		    	  int yField = fileIn.nextInt();
		    	  buttons = new JButton [xField] [yField];
		    	  fieldElements = new FieldElement [xField] [yField];
			      
		    	  for (int i = 0; i < xField; i++)
		    	  {
		    		  for (int j = 0; j < yField; j++)
		    		  {
		    			  buttons [i] [j] = new JButton();
		    		  }
		    	  }
		    	  
			      while (fileIn.hasNextInt())
			      {
			    	  int x = fileIn.nextInt();
			    	  int y = fileIn.nextInt();
			    	  fileIn.nextLine();
			    	  String name = fileIn.nextLine();
			    	  int power = fileIn.nextInt();
			    	  int shootingRange = fileIn.nextInt();
			    	  int movementRange = fileIn.nextInt();
			    	  int life = fileIn.nextInt();
			    	  int maxLife = fileIn.nextInt();
			    	  int energy = fileIn.nextInt();
			    	  int maxEnergy = fileIn.nextInt();
			    	  int special = fileIn.nextInt();
			    	  int votes = fileIn.nextInt();
			    	  fileIn.nextLine();
			    	  String password = fileIn.nextLine();
			    	  String type = fileIn.nextLine();
			    	  
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
			    	  
			    	  Tank [] addedPlayers = new Tank [players.length + 1];
			    	  for (int i = 0; i < players.length; i++)
			    	  {
			    		  addedPlayers[i] = players[i];
			    	  }
			    	  addedPlayers[players.length] = nextPlayer;
			    	  players = addedPlayers;
			    	  
			    	  
			    	  if (nextPlayer.getLife() > 0)
			    	  {
			    		  Tank [] addedAlive = new Tank [alive.length + 1];
				    	  for (int i = 0; i < alive.length; i++)
				    	  {
				    		  addedAlive[i] = alive[i];
				    	  }
				    	  addedAlive[alive.length] = nextPlayer;
				    	  alive = addedAlive;
				    	  fieldElements[x][y] = nextPlayer;
			    	  }
			    	  else
			    	  {
			    		  Tank [] addedJury = new Tank [jury.length + 1];
				    	  for (int i = 0; i < jury.length; i++)
				    	  {
				    		  addedJury[i] = jury[i];
				    	  }
				    	  addedJury[jury.length] = nextPlayer;
				    	  jury = addedJury;
			    	  }
			    	  
			    	  nextPlayer.upgradeMaxEnergy(0);
			    	  nextPlayer.gainEnergy(0);
			      }
			      fileIn.nextLine();
			      setAlive(alive);
			      
			      for (int i = 0; i < alive.length; i++)
			      {
			    	  alive[i].upgradeMaxLife(0);
			    	  alive[i].upgradeMovementRange(0);
			    	  alive[i].upgradePower(0);
			    	  alive[i].upgradeShootingRange(0);
			    	  alive[i].upgradeSpecial(0);
			    	  alive[i].heal(0);
			    	  alive[i].gainVotes(0);
			      }
			      
			      while (fileIn.hasNextLine())
			      {
			    	  int x = fileIn.nextInt();
			    	  int y = fileIn.nextInt();
			    	  int strength = fileIn.nextInt();
			    	  fileIn.nextLine();
			    	  String type = fileIn.nextLine();
			    	  
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
			    	  
			    	  Booster [] addedBoosters = new Booster [boosters.length + 1];
			    	  for (int i = 0; i < boosters.length; i++)
			    	  {
			    		  addedBoosters[i] = boosters[i];
			    	  }
			    	  addedBoosters[boosters.length] = nextBooster;
			    	  boosters = addedBoosters;
			    	  fieldElements[x][y] = nextBooster;
			      }
		    }
		    else
		    {
		    	newGame();
		    }
		} catch (IOException e) {
			newGame();
		}

		for (int i = 0; i < fieldElements.length; i++)
	      {
	    	  for (int j = 0; j < fieldElements[i].length; j++)
	    	  {
	    		  if (fieldElements[i][j] == null)
	    		  {
	    			  Color tileColor;
	    			  if ((i+j) % 2 == 0)
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
		
		setSize(fieldElements.length * 100, fieldElements[0].length * 100);
		
		Container c = getContentPane();
		c.add(panel, BorderLayout.CENTER);
		draw();
		c.repaint();
		
		//Saves the data to the game save file when the window is closed, after asking the user if they want to log in again.
		addWindowListener(new WindowAdapter() {
			@Override
		    public void windowClosing(WindowEvent windowEvent) {
		        boolean continueAsking = true, saveToFile = false;
		        while (continueAsking)
		        {
		        	System.out.print("\nDo you want to play again? (answer yes or no) ");
		        	String answer = reader.nextLine();
		        	if(answer.equalsIgnoreCase("yes"))
		        	{
		        		continueAsking = false;
						newLogin();
		        	}
		        	else if(answer.equalsIgnoreCase("no"))
		        	{
		        		continueAsking = false;
		        		saveToFile = true;
		        	}
		        	else 
		        	{
		        		System.out.println("There isn't an option for " + answer + ", please input a valid answer.");
		        	}
		        }
		        
		        if(saveToFile)
		        {
		        	clock.stop();
		        	startingTime = System.currentTimeMillis();
		        	Runnable saver = new Runnable() {
		        		public void run() {
		        			System.out.print("Enter where you want to save (.txt or a folder) ");
							String dir = reader.nextLine();
							File file = null;
							if(dir.indexOf(".txt") != -1)
							{
								file = new File (dir);
							}
							else
							{
								if (!dir.endsWith("\\"));
									dir += "\\";
								file = new File (dir + "game save.txt");
								int i = 1;
								while (file.exists())
								{
									file = new File (dir + "/game save " + i + ".txt");
								}
							}
							PrintWriter fileOut = null;
					    	if(!file.exists())
					    	{
					    		File save = new File("game save.txt");
					    	}
					        try
					        {
					          fileOut = new PrintWriter(new FileWriter(file));
					        }
					        catch (IOException ex)
					        {
					        	System.out.println("Cannot access game save file.");
					        	System.exit(ERROR);
					        }
					        
					        String save = startingTime + "\n" + cycleLength + "\n" + fieldElements.length + "\n" + fieldElements[0].length;
					        for(int i = 0; i < players.length; i++)
					        {
					        	save += "\n" + players[i].getX() + "\n" + players[i].getY() + "\n" + players[i].getName() + "\n" + players[i].getPower() + "\n" + players[i].getShootingRange()
					        			+ "\n" + players[i].getMovementRange() + "\n" + players[i].getLife() + "\n" + players[i].getMaxLife()
					        			+ "\n" + players[i].getEnergy() + "\n" + players[i].getMaxEnergy() + "\n" + players[i].getSpecial()
					        			 + "\n" + players[i].getVotes() + "\n" + players[i].getPassword() + "\n";
					        	if (players[i].getType().equalsIgnoreCase(Tank.AOE))
					        		save +=  Tank.AOE;
					        	else if (players[i].getType().equalsIgnoreCase(Tank.BALANCED))
					        		save +=  Tank.BALANCED;
					        	else if (players[i].getType().equalsIgnoreCase(Tank.DOT))
					        		save +=  Tank.DOT;
					        	else if (players[i].getType().equalsIgnoreCase(Tank.HEAVY))
				        			save +=  Tank.HEAVY;
					        	else if (players[i].getType().equalsIgnoreCase(Tank.LIGHT))
					        		save +=  Tank.LIGHT;
					        }
					        
					        save += "\n*";
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
					        System.exit(0);
		        		}
			        };
			        saver.run();
		        }
			}
		});
		
		clock = new Timer((int)((long)cycleLength*1000 + startingTime - System.currentTimeMillis()), this);
		clock.start();
	}
	
	//Public methods
	
	//Draws all the field elements
	public void draw()
	{
		setVisible(false);
		for (int i = 0; i < fieldElements[0].length; i++)
		{
			for (int j = 0; j < fieldElements.length; j++)
			{
				fieldElements[j][i].draw();
			}
		}
		setVisible(true);
	}
	
	//Prompts the user to input the password and tank name, checks which Tank fits those, and sets that Tank as currentPlayer
	public void newLogin()
	{
		boolean continueAsking = true;
		do
		{
			System.out.print("Enter the current player's name ");
			String name = reader.nextLine();
			System.out.print("Enter the current player's password ");
			String password = reader.nextLine();

			currentPlayer = null;
			for(int i = 0; i < players.length; i++)
			{
				if (players[i].checkPassword(name, password))
				{
					currentPlayer = players[i];
				}
			}
			if(currentPlayer == null)
			{
	    		  System.out.println("No player named " + name + " with the inputted password exists in this game.");
			}
			else
			{
				continueAsking = false;
			}
		} while (continueAsking);
	}
	
	//Called whenever the timer reaches zero, symbolizes a new cycle.
	@Override
	public void actionPerformed(ActionEvent e) {
		clock.stop();
		if (boosters.length < (fieldElements.length * fieldElements[0].length - alive.length)/3)
		{
			int newX = (int)(Math.random() * fieldElements.length);
			int newY = (int)(Math.random() * fieldElements[0].length);
			int i = 0;
			while (i < boosters.length)
			{
				int xDistance = Math.abs(newX - boosters[i].getX());
				int yDistance = Math.abs(newY - boosters[i].getY());
				if (xDistance + yDistance <= 1)
				{
					newX = (int)(Math.random() * fieldElements.length);
					newY = (int)(Math.random() * fieldElements[0].length);
					i = 0;
				}
				else
				{
					i++;
					int j = 0;
					while (j < alive.length)
					{
						if(alive[j].getX() == newX && alive[j].getY() == newY)
						{
							newX = (int)(Math.random() * fieldElements.length);
							newY = (int)(Math.random() * fieldElements[0].length);
							j = 0;
							i = 0;
						}
						else
						{
							j++;
						}
					}
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
		  	boosters = addedBoosters;
		  	fieldElements[newX][newY] = newBooster;
		  	
		  	for (int j = 0; j < players.length; j++)
		  	{
		  		players[j].gainEnergy(1);
		  	}
		  	for(int j = 0; j < DOT.length; j++)
		  	{
		  		DOT[j].newCycle();
		  	}
		}
		else
		{
			for (; startingTime < System.currentTimeMillis(); startingTime += cycleLength*1000)
			{
			  	for (int i = 0; i < players.length; i++)
			  	{
			  		players[i].gainEnergy(1);
			  	}
			  	
			  	for(int i = 0; i < DOT.length; i++)
			  	{
			  		DOT[i].newCycle();
			  	}
			}
		}
		
	  	
	  	for (int i = 0; i < alive.length; i++)
	  	{
	  		alive[i].resetVotes();
	  	}
	  	
	  	draw();  
		clock = new Timer((int)(cycleLength*1000 + startingTime - System.currentTimeMillis()), this);
		startingTime += cycleLength*1000;
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
		if (alive.length <= 1)
		{//Ends the game and deletes the save file when there is only one player left alive.
			System.out.println(alive[0].getName() + " won.");
			File currentDirFile = new File(".");
			String helper = currentDirFile.getAbsolutePath();
			String currentDir = "";
			try {
				currentDir = helper.substring(0, helper.length() - currentDirFile.getCanonicalPath().length());
			} catch (IOException e) {
				System.exit(ABORT);
			}
			File file = new File (currentDir + "game save.txt");
			PrintWriter fileOut = null;
	        try
	        {
	          fileOut = new PrintWriter(new FileWriter(file));
	          fileOut.print("");
	          fileOut.close();
	        }
	        catch (IOException ex)
	        {
		    	if(!file.exists())
		    	{
		    		File save = new File("game save.txt");
		    	}
	        }
	        System.exit(0);
		}
	}
	
	//Sets jury to the inputted value
	public void setJury (Tank[] newJury)
	{
		jury = newJury;
	}
	
	//Private methods
	
	//Prompts the user to input the information about the new game
	private void newGame()
	{
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
			reader.nextLine();
			String name = "";
			boolean nameExists;
			do
			{
				nameExists = false;
				System.out.print("Enter the name of the number " + (i+1) +" player ");
				name = reader.nextLine();
				for (int j = 0; players[j] != null; j++)
				{
					nameExists = nameExists || name.equals(players[j].getName());
				}
				if(nameExists)
				{
					System.out.println("There already is a player named " + name + ", please input a new name.");
				}
			} while(nameExists);
			System.out.print("Enter the password of that player ");
			String password = reader.nextLine();
			boolean keepAsking = true;
			String type = "";
			while (keepAsking)
			{
				System.out.print("Enter the type of that player (the types are: " + Tank.AOE + ", " + Tank.BALANCED + ", " + Tank.DOT + ", " + Tank.HEAVY + ", and " + Tank.LIGHT +") ");
				type = reader.next();
				if (type.equalsIgnoreCase(Tank.AOE))
					keepAsking = false;
				else if (type.equalsIgnoreCase(Tank.BALANCED))
					keepAsking = false;
				else if (type.equalsIgnoreCase(Tank.DOT))
					keepAsking = false;
				else if (type.equalsIgnoreCase(Tank.HEAVY))
					keepAsking = false;
				else if (type.equalsIgnoreCase(Tank.LIGHT))
					keepAsking = false;
				else
				{
					System.out.println("No tank type for " + type + ", please input a valid type.");
				}
			}
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
			players[i] = nextPlayer;
			
			fieldElements[x][y] = nextPlayer;
		}
		alive = players;
		jury = new Tank[0];
		
		System.out.print("Enter the length of a cycle in seconds ");
		cycleLength = reader.nextInt();
		startingTime = System.currentTimeMillis();
		reader.nextLine();
		alive = players;
	    jury = new Tank [0];
	}
}
