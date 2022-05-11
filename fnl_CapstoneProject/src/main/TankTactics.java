/*
 * Author: Itay Volk
 * Date: 5/10/2022
 * Rev: 09
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
import java.util.InputMismatchException;
import java.util.Scanner;
import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

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
	private JTextField actions;
	private JButton rules;
	private boolean rulesShowed;
	private JPanel panel;
	
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
			try {
				System.out.print("Enter the full location or name of the save file (enter \"nothing\" to create a new game) ");
				String dir = reader.nextLine();
				boolean added = !dir.endsWith(".txt");
				if (!dir.endsWith(".txt"))
				{
					dir += ".txt";
				}
				File file = new File (dir);
				Scanner fileIn = null;
			    fileIn = new Scanner(file);
			      
			    if (!(dir.substring(0, dir.indexOf(".txt")).equalsIgnoreCase("nothing") && added)&& !dir.equals(".txt") && file.exists() && fileIn.hasNext())
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
				    	  boolean onJumper = fileIn.nextBoolean();
				    	  boolean onShooter = fileIn.nextBoolean();
				    	  fileIn.nextLine();
				    	  String password = fileIn.nextLine();
				    	  String type = fileIn.nextLine();
				    	  
				    	  Tank nextPlayer = null;
				    	  if (type.equalsIgnoreCase(Tank.AOE))
				    		  nextPlayer = new AOE_Tank (x, y, name, power, shootingRange, movementRange, life, maxLife,
				    				  				energy, maxEnergy, special, votes, password, buttons[x][y], this,
			    				  					onJumper, onShooter);
				    	  else 	if (type.equalsIgnoreCase(Tank.BALANCED))
				    		  nextPlayer = new BalancedTank (x, y, name, power, shootingRange, movementRange, life,
				    				  				maxLife, energy, maxEnergy, special, votes, password, buttons[x][y],
				    				  				this, onJumper, onShooter);
				    	  else 	if (type.equalsIgnoreCase(Tank.DOT))
				    	  {
				    		  nextPlayer = new DOT_Tank (x, y, name, power, shootingRange, movementRange, life, maxLife,
			    				  					energy, maxEnergy, special, votes, password, buttons[x][y], this,
			    				  					onJumper, onShooter);
				    		  DOT_Tank [] addedDOT = new DOT_Tank [DOT.length + 1];
					    	  for (int i = 0; i < DOT.length; i++)
					    	  {
					    		  addedDOT[i] = DOT[i];
					    	  }
					    	  addedDOT[DOT.length] = (DOT_Tank) nextPlayer;
					    	  DOT = addedDOT;
				    	  }
			    		  else 	if (type.equalsIgnoreCase(Tank.HEAVY))
				    		  nextPlayer = new HeavyTank (x, y, name, power, shootingRange, movementRange, life, maxLife,
			    				  					energy, maxEnergy, special, votes, password, buttons[x][y], this,
			    				  					onJumper, onShooter);
				    	  else 	if (type.equalsIgnoreCase(Tank.LIGHT))
				    		  nextPlayer = new LightTank (x, y, name, power, shootingRange, movementRange, life, maxLife,
				  									energy, maxEnergy, special, votes, password, buttons[x][y], this,
				  									onJumper, onShooter);
				    	  
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
			    			  if ((x+y) % 2 == 0)
			    				  tileColor = new Color(69, 177, 72);
			    			  else
			    				  tileColor = new Color(82, 188, 82);
				    		  nextBooster = new HiddenBooster (x, y, strength, buttons[x][y], this, tileColor);
				    	  }
				    	  else 	if (type.equalsIgnoreCase(Booster.JUMPER))
				    		  nextBooster = new Jumper (x, y, buttons[x][y], this);
				    	  else 	if (type.equalsIgnoreCase(Booster.MAX_ENERGY))
				    		  nextBooster = new MaxEnergyBooster (x, y, strength, buttons[x][y], this);
				    	  else 	if (type.equalsIgnoreCase(Booster.MAX_LIFE))
				    		  nextBooster = new MaxLifeBooster (x, y, strength, buttons[x][y], this);
				    	  else 	if (type.equalsIgnoreCase(Booster.MOVEMENT_RANGE))
				    		  nextBooster = new MovementRangeBooster (x, y, strength, buttons[x][y], this);
				    	  else 	if (type.equalsIgnoreCase(Booster.POWER))
				    		  nextBooster = new PowerBooster (x, y, strength, buttons[x][y], this);
				    	  else 	if (type.equalsIgnoreCase(Booster.SHOOT))
				    		  nextBooster = new Shooter (x, y, buttons[x][y], this);
				    	  else 	if (type.equalsIgnoreCase(Booster.SHOOTING_RANGE))
				    		  nextBooster = new ShootingRangeBooster (x, y, strength, buttons[x][y], this);
				    	  else 	if (type.equalsIgnoreCase(Booster.SPECIAL))
				    		  nextBooster = new SpecialBooster (x, y, strength, buttons[x][y], this);
				    	  else 	if (type.equalsIgnoreCase(Booster.UNKNOWN))
				    		  nextBooster = new UnknownBooster (x, y, strength, buttons[x][y], this);
				    	  else 	if (type.equalsIgnoreCase(Booster.DEBUFF))
				    		  nextBooster = new DebuffBooster (x, y, strength, buttons[x][y], this);
				    	  
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
			} catch (InputMismatchException e)
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
		panel = new JPanel();
		panel.setLayout(new GridLayout(fieldElements.length, fieldElements[0].length));
		for (int i = 0; i < fieldElements[0].length; i++)
		{
			for (int j = 0; j < fieldElements.length; j++)
			{
				panel.add(buttons[j][i]);
			}
		}
		
		rules = new JButton("Show rules and instractions.");
		rules.addActionListener(this);
		actions = new JTextField ("");
		actions.setEditable(false);
		
		setSize(fieldElements.length * 100, fieldElements[0].length * 100);
		
		Container c = getContentPane();
		c.add(rules, BorderLayout.NORTH);
		c.add(panel, BorderLayout.CENTER);
		c.add(actions, BorderLayout.SOUTH);
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
				        while (continueAsking)
				        {
				        	System.out.print("Do you want to save the game? (answer yes or no) ");
				        	answer = reader.nextLine();
				        	if(answer.equalsIgnoreCase("yes"))
				        	{
				        		continueAsking = false;
				        		saveToFile = true;
				        	}
				        	else if(answer.equalsIgnoreCase("no"))
				        	{
						        System.exit(0);
				        	}
				        	else 
				        	{
				        		System.out.println("There isn't an option for " + answer + ", please input a valid answer.");
				        	}
				        }
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
							if(dir.endsWith(".txt"))
							{
								file = new File (dir);
							}
							else
							{
								if (!dir.endsWith("\\"));
									dir += "\\";
								if (new File(".").getAbsolutePath().endsWith(dir + "."))
								{
									dir = "";
								}
								System.out.print("Enter the name of the file you want to save ");
								String name = reader.nextLine();
								if (name.endsWith(".txt"))
									name = name.substring(0, name.length() - 4);
								file = new File (dir + name + ".txt");
								int i = 1;
								while (file.exists())
								{
									file = new File (dir + name + " ("+i+")" + ".txt");
									i++;
								}
								try {
									file.createNewFile();
								} catch (IOException e) {}
							}
							
							PrintWriter fileOut = null;
					        try
					        {
					          fileOut = new PrintWriter(file);
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
					        			 + "\n" + players[i].getVotes() + "\n" + players[i].getOnJumper() + "\n" + players[i].getOnShooter()
					        			 + "\n" + players[i].getPassword() + "\n" + players[i].getType();
					        }
					        
					        save += "\n*";
					        for(int i = 0; i < boosters.length; i++)
					        {
					        	save += "\n" + boosters[i].getX() + "\n" + boosters[i].getY() + "\n" + boosters[i].getStrength() + "\n" + boosters[i].getType();
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(clock)) 	//Called whenever the timer reaches zero, symbolizes a new cycle.
		{
			clock.stop();
			if (boosters.length < (fieldElements.length * fieldElements[0].length - alive.length)/5)
			{
				int newX = (int)(Math.random() * fieldElements.length);
				int newY = (int)(Math.random() * fieldElements[0].length);
				int i = 0;
				while (i < boosters.length)
				{
					int xDistance = Math.abs(newX - boosters[i].getX());
					int yDistance = Math.abs(newY - boosters[i].getY());
					if (xDistance <= 2 || yDistance <= 2)
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
				
				int type = (int)(Math.random() * 13);
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
						if ((newX+newY) % 2 == 0)
							tileColor = new Color(69, 177, 72);
						else
							tileColor = new Color(82, 188, 82);
						newBooster = new HiddenBooster(newX, newY, strength, buttons[newX][newY], this, tileColor);
						break;
					case 3:
						newBooster = new Jumper(newX, newY, buttons[newX][newY], this);
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
						newBooster = new Shooter(newX, newY, buttons[newX][newY], this);
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
					case 12:
						newBooster = new DebuffBooster(newX, newY, strength, buttons[newX][newY], this);
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
			startingTime += cycleLength*1000;
			clock = new Timer((int)(cycleLength*1000 + startingTime - System.currentTimeMillis()), this);
			clock.start();
		}
		
		else if(rulesShowed)	//Called when the JButton is pressed and the rules are desplayed, redrawes the field.
		{
			Container c = getContentPane();
			c.removeAll();
			c.add(rules, BorderLayout.NORTH);
			c.add(panel, BorderLayout.CENTER);
			c.add(actions, BorderLayout.SOUTH);
			draw();
			c.repaint();
			rulesShowed = false;
			clock = new Timer((int)(cycleLength*1000 + startingTime - System.currentTimeMillis()), this);
			clock.start();
		}
		else	//Called when the JButton is pressed and the rules aren't desplayed, desplayes the rules.
		{
			Container c = getContentPane();
			c.removeAll();
			clock.stop();
			setVisible(false);
			JTextField rulesBox = new JTextField();
			rulesBox.setEditable(false);
			rulesBox.setSize(WIDTH, HEIGHT);
			rules.setText("Stop showing rules.");
			c.add(rules, BorderLayout.NORTH);
			c.add(rulesBox, BorderLayout.CENTER);
			c.repaint();
			rulesShowed = true;
			setVisible(true);
		}
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
	
	//Returns jury
	public Tank[] getJury()
	{
		return jury;
	}
	
	//Setters
	//Sets fieldElements to the inputed value
	public void setFieldElements (FieldElement[][] newField)
	{
		fieldElements = newField;
	}
	
	//Sets buttons to the inputed value
	public void setButtons (JButton[][] newButtons)
	{
		buttons = newButtons;
	}
	
	//Sets players to the inputed value
	public void setPlayers (Tank[] newPlayers)
	{
		players = newPlayers;
	}
	
	//Sets alive to the inputed value
	public void setAlive (Tank[] newAlive)
	{
		alive = newAlive;
		if (alive.length <= 1)
		{//Ends the game if there is only one player left alive.
			System.out.println(alive[0].getName() + " won.");
	        System.exit(0);
		}
	}
	
	//Sets jury to the inputed value
	public void setJury (Tank[] newJury)
	{
		jury = newJury;
	}
	
	//Sets the text of actions to the inputed value
	public void setActionsText (String action)
	{
		setVisible(false);
		actions.setText(action);
		setVisible(true);
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
				if (type.equalsIgnoreCase(Tank.AOE) || type.equalsIgnoreCase(Tank.BALANCED) || 
						type.equalsIgnoreCase(Tank.DOT) || type.equalsIgnoreCase(Tank.HEAVY) || 
						type.equalsIgnoreCase(Tank.LIGHT))
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
				int xDistance = Math.abs(x - players[j].getX());
				int yDistance = Math.abs(y - players[j].getY());
				if (xDistance <= 2 || yDistance <= 2)
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
			System.out.println("created");
			Tank nextPlayer = null;
			if (type.equalsIgnoreCase(Tank.AOE))
				nextPlayer = new AOE_Tank (x, y, name, 1, 1, 1, 3, 3, 1, 5, 1, 0, password, buttons[x][y],this,
										false, false);
			else if (type.equalsIgnoreCase(Tank.BALANCED))
				nextPlayer = new BalancedTank (x, y, name, 1, 1, 1, 3, 3, 1, 5, 1, 0, password, buttons[x][y], this,
											false, false);
			else if (type.equalsIgnoreCase(Tank.DOT))
				nextPlayer = new DOT_Tank (x, y, name, 1, 1, 1, 3, 3, 1, 5, 1, 0, password, buttons[x][y], this,
										false, false);
			else if (type.equalsIgnoreCase(Tank.HEAVY))
				nextPlayer = new HeavyTank (x, y, name, 1, 1, 1, 3, 3, 1, 5, 1, 0, password, buttons[x][y], this,
										false, false);
			else if (type.equalsIgnoreCase(Tank.LIGHT))
				nextPlayer = new LightTank (x, y, name, 1, 1, 1, 3, 3, 1, 5, 1, 0, password, buttons[x][y], this,
											false, false);
			players[i] = nextPlayer;
			
			fieldElements[x][y] = nextPlayer;
		}
		setAlive(players);
		jury = new Tank[0];
		
		System.out.print("Enter the length of a cycle in seconds ");
		cycleLength = reader.nextInt();
		startingTime = System.currentTimeMillis();
		reader.nextLine();
		alive = players;
	    jury = new Tank [0];
	}
}
