/*
 * Author: Itay Volk
 * Date: 5/20/2022
 * Rev: 11
 * Notes: this class manages a TankTactics game
 */

package main;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;
import javax.swing.Timer;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

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
	private int cycleLength, cyclesPlayed, maxCycles;
	private Timer clock, soundPlaying;
	private Scanner reader = new Scanner(System.in);
	private JTextField actions;
	private JButton rules;
	private boolean rulesShowed, full, override, loop;
	private JPanel panel;
	private String sound;
	
	public static final String RULES = "Welcome to Tank Tactics, a fairly simple turn based multiplayer strategy game.\r\n"
			+ "In this game, each user will take control of their own unique tank with a unique special stat.\r\n"
			+ "\r\n"
			+ "Each round, you can use the command line to sign into your tank using your unique password and name, "
			+ "from which you can then use your energy points to move around, collect boosts, and shoot at other players.\r\n"
			+ "However, be aware that you have a limited amount of energy and you can only play a set amount of cycles in a row.\r\n"
			+ "\r\n"
			+ "You win when all of the other players are eliminated, good luck!\r\n"
			+ "\r\n"
			+ "Booster Types:\r\n"
			+ "Debuff Booster - Lower a random stat of all players (except player who triggered it) by X amount.\r\n"
			+ "Energy Giver Booster- Gives player who triggered it random amount of energy.\r\n"
			+ "Healer Booster - Heals player who triggered it by random amount.\r\n"
			+ "Jumper Booster - Gives player who triggered it a bonus energy point that can only be used to move.\r\n"
			+ "Max Energy Booster- Increases the maximum amount of energy player who triggered it can hold.\r\n"
			+ "Max Life Booster - Increases the maximum number of lives current player can have.\r\n"
			+ "Power Booster - Increases the power of the player who triggered it by a random amount.\r\n"
			+ "Shooting Range Booster - Increases the range of the player who triggered it by a random amount.\r\n"
			+ "Special Booster - Increases the special of the player who triggered it by a random amount.\r\n"
			+ "Movement Range Booster - Increases the range of the player who triggered it by a random amount.\r\n"
			+ "Shooter Booster - Gives player who triggered it a bonus energy point that can only be used to shoot.\r\n"
			+ "Unknown Booster - Upgrades a random stat of the current player by a random amount.\r\n"
			+ "Hidden Booster - Similar to unknown booster only it looks exactly like a regular tile and thus can not be found.\r\n"
			+ "\r\n"
			+ "Tank Types:\r\n"
			+ "AOE Tank - Shoots all the tanks in the area of the target (in a square where each side is two times the AOE stat plus one).\r\n"
			+ "Balanced Tank - Gains more life whenever it is healed (the amount of life gained is multiplied by the life gain stat).\r\n"
			+ "DOT Tank - Hits the target multiple cycles in a row (shoots for an amount of cycles that is equal to the DOT stat).\r\n"
			+ "Heavy Tank - Takes less damage whenever it is hit (the damage it takes is divided by the armor stat).\r\n"
			+ "Light Tank - Gains more energy whenever it is supposed to gain energy (the amount of energy gained is multiplied by the energy gain stat).";
	
	//Constructor
	public TankTactics ()
	{
		super ("Tank Tactics");
		
		startingTime = 0;
		cycleLength = 0;
		cyclesPlayed = 0;
		players = new Tank [0];
		alive = new Tank [0];
		jury = new Tank [0];
		boosters = new Booster [0];
		DOT = new DOT_Tank [0];
		
		boolean continueAsking = true;
        while (continueAsking)
        {
    		System.out.print("Do you want to read the rules of the game? (enter yes or no) ");
        	String answer = reader.nextLine();
        	if(answer.equalsIgnoreCase("yes"))
        	{
        		System.out.println(RULES + "\n");
        		continueAsking = false;
        	}
        	else if(answer.equalsIgnoreCase("no"))
        	{
        		continueAsking = false;
        	}
        	else
        	{
        		System.out.println("There isn't an option for " + answer + ", please input a valid answer.");
        	}
        }
        
		try {//Reads the game save file and sets up the game.
			File file = null;
			if (System.getProperty("os.name").indexOf("Windows") != -1)
			{
				File helper = new File(".");
				file = new File (helper.getAbsolutePath().substring(0, helper.getAbsolutePath().length() - 1)
						+ File.separator + "game save.txt");
			}
			else
			{
				file = new File ("fnl_CapstoneProject" + File.separator + "game save.txt");
			}
			Scanner fileIn = new Scanner(file);
		      
		    startingTime = fileIn.nextLong();
		    cycleLength = fileIn.nextInt();
		    maxCycles = fileIn.nextInt();
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
		    
			actions = new JTextField ("");
		    actions.setEditable(false);
		    actions.setHorizontalAlignment(JTextField.CENTER);
		    actions.setPreferredSize(new Dimension(fieldElements.length * 90, 30));
		    
	    	String iconPath = "";
			if (System.getProperty("os.name").contains("Windows")) {
				iconPath = "assets" + File.separator + "icons" + File.separator;
			} else {
				iconPath = "fnl_CapstoneProject" + File.separator + "assets" + File.separator + "icons" + File.separator;
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
	    				  					onJumper, onShooter, new ImageIcon(iconPath + "tank_aoe.png"));
	    	  	else if (type.equalsIgnoreCase(Tank.BALANCED))
	    	  		nextPlayer = new BalancedTank (x, y, name, power, shootingRange, movementRange, life,
		    				  				maxLife, energy, maxEnergy, special, votes, password, buttons[x][y],
		    				  				this, onJumper, onShooter, new ImageIcon(iconPath + "tank_balanced.png"));
	    	  else if (type.equalsIgnoreCase(Tank.DOT))
	    	  {
	    		  nextPlayer = new DOT_Tank (x, y, name, power, shootingRange, movementRange, life, maxLife,
	    				  					energy, maxEnergy, special, votes, password, buttons[x][y], this,
	    				  					onJumper, onShooter, new ImageIcon(iconPath + "tank_dot.png"));
	    		  DOT_Tank [] addedDOT = new DOT_Tank [DOT.length + 1];
	    		  for (int i = 0; i < DOT.length; i++)
	    		  {
	    			  addedDOT[i] = DOT[i];
	    		  }
	    		  addedDOT[DOT.length] = (DOT_Tank) nextPlayer;
	    		  DOT = addedDOT;
	    	  }
	    	  else if (type.equalsIgnoreCase(Tank.HEAVY))
	    		  nextPlayer = new HeavyTank (x, y, name, power, shootingRange, movementRange, life, maxLife,
	    				  					energy, maxEnergy, special, votes, password, buttons[x][y], this,
	    				  					onJumper, onShooter, new ImageIcon(iconPath + "tank_heavy.png"));
	    	  else if (type.equalsIgnoreCase(Tank.LIGHT))
	    		  nextPlayer = new LightTank (x, y, name, power, shootingRange, movementRange, life, maxLife,
		  									energy, maxEnergy, special, votes, password, buttons[x][y], this,
		  									onJumper, onShooter, new ImageIcon(iconPath + "tank_light.png"));
		    	
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
		    	
		    	buttons[x][y].setPreferredSize(new Dimension(100, 100));
		    	
		    	Booster nextBooster = null;
		    	if (type.equalsIgnoreCase(Booster.ENERGY))
		    		nextBooster = new EnergySupplier (x, y, strength, buttons[x][y], this, new ImageIcon(iconPath + "booster-energygiver.png"));
	    	  	else if (type.equalsIgnoreCase(Booster.HEAL))
	    	  		nextBooster = new Healer (x, y, strength, buttons[x][y], this, new ImageIcon(iconPath + "booster-healer.png"));
	    	  	else if (type.equalsIgnoreCase(Booster.HIDDEN))
	    	  	{
	    	  		ImageIcon grass = null;
	    			  if ((x+y) % 2 == 0)
	    				  grass = new ImageIcon(iconPath + "grass1.png");
	    			  else
	    				  grass = new ImageIcon(iconPath + "grass2.png");
	    	  		nextBooster = new HiddenBooster (x, y, strength, buttons[x][y], this, grass);
	    	  	}
	    	  	else if (type.equalsIgnoreCase(Booster.JUMPER))
	    	  		nextBooster = new Jumper (x, y, buttons[x][y], this, new ImageIcon(iconPath + "booster-jumper.png"));
	    	  	else if (type.equalsIgnoreCase(Booster.MAX_ENERGY))
	    	  		nextBooster = new MaxEnergyBooster (x, y, strength, buttons[x][y], this, new ImageIcon(iconPath + "booster-maxenergy.png"));
	    	  	else if (type.equalsIgnoreCase(Booster.MAX_LIFE))
	    	  		nextBooster = new MaxLifeBooster (x, y, strength, buttons[x][y], this, new ImageIcon(iconPath + "booster-maxlife.png"));
	    	  	else if (type.equalsIgnoreCase(Booster.MOVEMENT_RANGE))
	    	  		nextBooster = new MovementRangeBooster (x, y, strength, buttons[x][y], this, new ImageIcon(iconPath + "booster-movementrange.png"));
	    	  	else if (type.equalsIgnoreCase(Booster.POWER))
	    	  		nextBooster = new PowerBooster (x, y, strength, buttons[x][y], this, new ImageIcon(iconPath + "booster-power.png"));
	    	  	else if (type.equalsIgnoreCase(Booster.SHOOT))
	    	  		nextBooster = new Shooter (x, y, buttons[x][y], this, new ImageIcon(iconPath + "booster-shooter.png"));
	    	  	else if (type.equalsIgnoreCase(Booster.SHOOTING_RANGE))
	    	  		nextBooster = new ShootingRangeBooster (x, y, strength, buttons[x][y], this, new ImageIcon(iconPath + "booster-shootrange.png"));
	    	  	else if (type.equalsIgnoreCase(Booster.SPECIAL))
	    	  		nextBooster = new SpecialBooster (x, y, strength, buttons[x][y], this, new ImageIcon(iconPath + "booster-special.png"));
	    	  	else if (type.equalsIgnoreCase(Booster.UNKNOWN))
	    	  		nextBooster = new UnknownBooster (x, y, strength, buttons[x][y], this, new ImageIcon(iconPath + "booster-unknown.png"));
	    	  	else if (type.equalsIgnoreCase(Booster.DEBUFF))
	    	  		nextBooster = new DebuffBooster (x, y, strength, buttons[x][y], this, new ImageIcon(iconPath + "booster-debuff.png"));
		    	  
		    	Booster [] addedBoosters = new Booster [boosters.length + 1];
		    	for (int i = 0; i < boosters.length; i++)
		    	{
		    		addedBoosters[i] = boosters[i];
		    	}
		    	addedBoosters[boosters.length] = nextBooster;
		    	boosters = addedBoosters;
		    	fieldElements[x][y] = nextBooster;
		    }	
		      
		    fileIn.close();
		    
		    newLogin();
		} catch (Exception e) {
			newGame();
		}
		setSize(fieldElements.length * 100, fieldElements[0].length * 100 + 30);

    	String iconPath = "";
		if (System.getProperty("os.name").contains("Windows")) {
			iconPath = "assets" + File.separator + "icons" + File.separator;
		} else {
			iconPath = "fnl_CapstoneProject" + File.separator + "assets" + File.separator + "icons" + File.separator;
		}
		
		for (int i = 0; i < fieldElements.length; i++)
	    {
	    	  for (int j = 0; j < fieldElements[i].length; j++)
	    	  {
	    		  if (fieldElements[i][j] == null)
	    		  {
	    			  ImageIcon grass = null;
	    			  if ((i+j) % 2 == 0)
	    				  grass = new ImageIcon(iconPath + "grass1.png");
	    			  else
	    				  grass = new ImageIcon(iconPath + "grass2.png");
	    			  fieldElements[i][j] = new FieldElement(i, j, buttons[i][j], this, grass, "");
	    		  }
	    	  }
	    }

		panel = new JPanel(new GridLayout(fieldElements[0].length, fieldElements.length));
		for (int i = 0; i < fieldElements[0].length; i++)
		{
			for (int j = 0; j < fieldElements.length; j++)
			{
				panel.add(buttons[j][i]);
			}
		}
		
		rules = new JButton("R");
		rules.addActionListener(this);
		rules.setPreferredSize(new Dimension(fieldElements.length * 10, 30));
		Box bar = Box.createHorizontalBox();
		bar.add(actions);
		bar.add(rules);
		
		Container c = getContentPane();
		c.add(bar, BorderLayout.NORTH);
		c.add(panel, BorderLayout.CENTER);
		c.repaint();
		setVisible(true);
		draw();
		
		full = false;
		
		//Saves the data to the game save file when the window is closed, after asking the user if they want to log in again.
		addWindowListener(new WindowAdapter() {
			@Override
		    public void windowClosing(WindowEvent windowEvent) {
		        boolean continueAsking = true, saveToFile = false, delete = false;
		        while (continueAsking)
		        {
		        	System.out.print("\nDo you want to let another player play now? (answer yes or no) ");
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
				        		while (continueAsking)
						        {
						        	System.out.print("Do you want to delete the game? (answer yes or no) ");
						        	answer = reader.nextLine();
						        	if(answer.equalsIgnoreCase("yes"))
						        	{
						        		continueAsking = false;
						        		delete = true;
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
		        	}
		        	else 
		        	{
		        		System.out.println("There isn't an option for " + answer + ", please input a valid answer.");
		        	}
		        }
		        
		        if(saveToFile)
		        {
		        	clock.stop();
		        	Runnable saver = new Runnable() {
		        		public void run() {
				        	startingTime = System.currentTimeMillis();
				        	File file = null;
							if (System.getProperty("os.name").indexOf("Windows") != -1)
							{
								File helper = new File(".");
								file = new File (helper.getAbsolutePath().substring(0, helper.getAbsolutePath().length() - 1)
										+ File.separator + "game save.txt");
							}
							else
							{
								file = new File ("fnl_CapstoneProject" + File.separator + "game save.txt");
							}
							
							if(!file.exists())
							{
								try {
									file.createNewFile();
								} catch (Exception e) {
									System.out.println("Cannot create game save file.");
						        	System.exit(ERROR);
								}
							}
							
							PrintWriter fileOut = null;
					        try
					        {
					          fileOut = new PrintWriter(file);
					        }
					        catch (Exception ex)
					        {
					        	System.out.println("Cannot access game save file.");
					        	System.exit(ERROR);
					        }
					        
					        String save = startingTime + "\n" + cycleLength + "\n" + maxCycles + "\n" + fieldElements.length + "\n" + fieldElements[0].length;
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
		        else if(delete)
		        {
					File file = null;
					if (System.getProperty("os.name").indexOf("Windows") != -1)
					{
						File helper = new File(".");
						file = new File (helper.getAbsolutePath().substring(0, helper.getAbsolutePath().length() - 1)
								+ File.separator + "game save.txt");
					}
					else
					{
						file = new File ("fnl_CapstoneProject" + File.separator + "game save.txt");
					}
					
					if(file.exists())
					{
				        try
				        {
				        	System.gc();
				        	new PrintWriter(file).close();
				        	new Scanner(file).close();
							file.delete();
				        }
				        catch (Exception ex)
				        {
				        	System.out.println("Cannot access game save file.");
				        	System.exit(ERROR);
				        }
					}
			        System.exit(0);
		        }
			}
		});
		
		//Redraws the window when it is resized
		addComponentListener(new ComponentAdapter() {
			public void componentResized (ComponentEvent e)
			{
				draw();
			}
		});
		
		clock = new Timer((int)((long)cycleLength*1000 + startingTime - System.currentTimeMillis()), this);
		clock.start();
		
		playSound("background-game.wav", true, true, true);
	}
	
	//Public methods
	
	//Draws all the field elements
	public void draw()
	{
		new Runnable() {
			public void run() {
				for (int i = 0; i < fieldElements[0].length; i++)
				{
					for (int j = 0; j < fieldElements.length; j++)
					{
						fieldElements[j][i].draw();
					}
				}
			}
		}.run();
	}
	
	//Prompts the user to input the password and tank name, checks which Tank fits those, and sets that Tank as currentPlayer
	public void newLogin()
	{
		boolean continueAsking = true;
		Tank previusPlayer = currentPlayer;
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
			else if (currentPlayer != previusPlayer || name.equalsIgnoreCase("quit"))
			{
				continueAsking = false;
			}
			else
			{
				System.out.println("You have already played, please let another person played or enter \"quit\" to stop playing.");
			}
		} while (continueAsking);
		actions.setText("This game has started again with " + currentPlayer.getName() + " as the current player.");
		
		playSound("start-game.wav", false, false, false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == clock && !full) 	//Called whenever the timer reaches zero and there are remaining spaces, symbolizes a new cycle.
		{
			clock.stop();
			playSound("new-cycle.wav", false, false, false);
			
			int [][] possible = new int [0][2];
			for (int i = 0; i < fieldElements.length; i++)
			{
				for (int j = 0; j < fieldElements[i].length; j++)
				{
					if (!Tank.class.isAssignableFrom(fieldElements[i][j].getClass()) && !Booster.class.isAssignableFrom(fieldElements[i][j].getClass()))
					{
						boolean adjacent = false;
						int k = 0;
						while (k < boosters.length && !adjacent)
						{
							int xDistance = Math.abs(i - boosters[k].getX());
							int yDistance = Math.abs(j - boosters[k].getY());
							if (xDistance <= 1 && yDistance <= 1)
							{
								adjacent = true;
							}
							k++;
						}
						
						if(!adjacent)
						{
							int [][] newPossible = new int [possible.length + 1][2];
							for (k = 0; k < possible.length; k++)
							{
								newPossible[k] = possible[k];
							}
							newPossible[possible.length][0] = i;
							newPossible[possible.length][1] = j;
							possible = newPossible;
						}
					}
				}
			}
				
			if (possible.length > 0)
			{
				int rndPosition = (int)(Math.random() * possible.length);
				int newX = possible[rndPosition][0];
				int newY = possible[rndPosition][1];
				int type = (int)(Math.random() * 13);
				int strength = (int)(Math.random() * 5) + 1;
				if ((int)(Math.random() * 2) == 0)
				{
					strength *= -1;
				}
				
		    	String iconPath = "";
				if (System.getProperty("os.name").contains("Windows")) {
					iconPath = "assets" + File.separator + "icons" + File.separator;
				} else {
					iconPath = "fnl_CapstoneProject" + File.separator + "assets" + File.separator + "icons" + File.separator;
				}
				
				Booster newBooster = null;
				switch (type)
				{
					case 0:
						newBooster = new EnergySupplier (newX, newY, strength, buttons[newX][newY], this, new ImageIcon(iconPath + "booster-energygiver.png"));
						break;
					case 1:
						newBooster = new Healer (newX, newY, strength, buttons[newX][newY], this, new ImageIcon(iconPath + "booster-healer.png"));
						break;
					case 2:
						ImageIcon grass = null;
		    			  if ((newX+newY) % 2 == 0)
		    				  grass = new ImageIcon(iconPath + "grass1.png");
		    			  else
		    				  grass = new ImageIcon(iconPath + "grass2.png");
						newBooster = new HiddenBooster (newX, newY, strength, buttons[newX][newY], this, grass);
						break;
					case 3:
						newBooster = new Jumper (newX, newY, buttons[newX][newY], this, new ImageIcon(iconPath + "booster-jumper.png"));
						break;
					case 4:
						newBooster = new MaxEnergyBooster (newX, newY, strength, buttons[newX][newY], this, new ImageIcon(iconPath + "booster-maxenergy.png"));
						break;
					case 5:
						newBooster = new MaxLifeBooster (newX, newY, strength, buttons[newX][newY], this, new ImageIcon(iconPath + "booster-maxlife.png"));
						break;
					case 6:
						newBooster = new MovementRangeBooster (newX, newY, strength, buttons[newX][newY], this, new ImageIcon(iconPath + "booster-movementrange.png"));
						break;
					case 7:
						newBooster = new PowerBooster (newX, newY, strength, buttons[newX][newY], this, new ImageIcon(iconPath + "booster-power.png"));
						break;
					case 8:
						newBooster = new Shooter (newX, newY, buttons[newX][newY], this, new ImageIcon(iconPath + "booster-shooter.png"));
						break;
					case 9:
						newBooster = new ShootingRangeBooster (newX, newY, strength, buttons[newX][newY], this, new ImageIcon(iconPath + "booster-shootrange.png"));
						break;
					case 10:
						newBooster = new SpecialBooster (newX, newY, strength, buttons[newX][newY], this, new ImageIcon(iconPath + "booster-special.png"));
						break;
					case 11:
						newBooster = new UnknownBooster (newX, newY, strength, buttons[newX][newY], this, new ImageIcon(iconPath + "booster-unknown.png"));
						break;
					case 12:
						newBooster = new DebuffBooster (newX, newY, strength, buttons[newX][newY], this, new ImageIcon(iconPath + "booster-debuff.png"));
						break;
				}
			  	Booster [] addedBoosters = new Booster [boosters.length + 1];
			  	for (int i = 0; i < boosters.length; i++)
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
				full = true;
			}
			
		  	
		  	for (int i = 0; i < alive.length; i++)
		  	{
		  		alive[i].resetVotes();
		  	}
		  	
		  	draw();
			startingTime += cycleLength*1000;
			clock = new Timer((int)(cycleLength*1000 + startingTime - System.currentTimeMillis()), this);
			cyclesPlayed++;
			if (cyclesPlayed == maxCycles)
			{
				System.out.println("You have reached the maximum amount of cycles you can play in a row.");
				newLogin();
			}
			clock.start();
		}
		
		else if(e.getSource() == clock) //Called whenever the timer reaches zero and there are no remaining spaces, symbolizes a new cycle
		{
			clock.stop();
			playSound("new-cycle.wav", false, false, false);
			
			startingTime += cycleLength*1000;
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
			 
			for (int i = 0; i < alive.length; i++)
		  	{
		  		alive[i].resetVotes();
		  	}
		  	
		  	draw();
			clock = new Timer((int)(cycleLength*1000 + startingTime - System.currentTimeMillis()), this);
			cyclesPlayed++;
			if (cyclesPlayed == maxCycles)
			{
				System.out.println("You have reached the maximum amount of cycles you can play in a row.");
				newLogin();
			}
			clock.start();
		}
	
		else if(e.getSource() == soundPlaying) //Called whenever the timer reaches zero, symbolizes the song ending.
		{
			soundPlaying.stop();
			if(sound.length() > 0)
			{
				playSound(sound, override, false, loop);
				sound = "";
			}
		}
		
		else if(rulesShowed)	//Called when the JButton is pressed and the rules are displayed, redraws the field.
		{
			setSize(fieldElements.length * 100, fieldElements[0].length * 100 + 30);
			
			rules.setText("R");
			Box bar = Box.createHorizontalBox();
			bar.add(actions);
			bar.add(rules);
			
			Container c = getContentPane();
			c.removeAll();
			c.add(bar, BorderLayout.NORTH);
			c.add(panel, BorderLayout.CENTER);
			draw();
			c.repaint();
			
			rulesShowed = false;
			clock = new Timer((int)(cycleLength*1000 + startingTime - System.currentTimeMillis()), this);
			clock.start();
		}
		else	//Called when the JButton is pressed and the rules aren't displayed, displays the rules.
		{
			Container c = getContentPane();
			c.removeAll();
			clock.stop();
			JLabel rulesBox = new JLabel(RULES);
			rulesBox.setSize(600, 750);
			rulesBox.setHorizontalAlignment(SwingConstants.CENTER);
			rulesBox.setText("<html>" + RULES.replace("\r\n", "<br>") + "</html>");
			rules.setSize(new Dimension(600, 30));
			rules.setText("Stop showing rules.");
			setSize(600, 780);
			c.add(rules, BorderLayout.NORTH);
			c.add(rulesBox, BorderLayout.CENTER);
			c.repaint();
			setVisible(true);
			rulesShowed = true;
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
		for (int i = 0; i < fieldElements.length; i++)
		{
			for (int j = 0; j < fieldElements[i].length; j++)
			{
				if (!Booster.class.isAssignableFrom(newField[i][j].getClass()) && Booster.class.isAssignableFrom(fieldElements[i][j].getClass()))
				{
					full = false;
				}
			}
		}
		
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
			playSound("win-game.wav", true, true, false);
			File file = null;
			if (System.getProperty("os.name").indexOf("Windows") != -1)
			{
				File helper = new File(".");
				file = new File (helper.getAbsolutePath().substring(0, helper.getAbsolutePath().length() - 1)
						+ File.separator + "game save.txt");
			}
			else
			{
				file = new File ("fnl_CapstoneProject" + File.separator + "game save.txt");
			}
			
			if(file.exists())
			{
		        try
		        {
		        	System.gc();
		        	new PrintWriter(file).close();
		        	new Scanner(file).close();
					file.delete();
		        }
		        catch (Exception ex)
		        {
		        	System.out.println("Cannot access game save file.");
		        	System.exit(ERROR);
		        }
			}
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
		actions.setText(action);
	}
	
	//Plays the sound file with the name file(which should include the type).
	public void playSound(String file, boolean canBeOverided, boolean runLater, boolean loop)
	{
		Clip clip = null;
		String soundPath = "";
		if (System.getProperty("os.name").contains("Windows")) {
			soundPath = "assets" +File.separator + "sounds" + File.separator;
			
		} else {
			soundPath = "fnl_CapstoneProject" +File.separator+ "assets" +File.separator + "sounds" + File.separator;
		}
		
		if (soundPlaying != null)
		{
			if (!soundPlaying.isRunning())
			{
				try {
					Clip originalClip = AudioSystem.getClip();
					AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundPath + file).getAbsoluteFile());
					clip = AudioSystem.getClip();
					if(!originalClip.equals(clip))
					{
						clip.open(audioInputStream);
						if(loop)
						{
							clip.loop(Clip.LOOP_CONTINUOUSLY);
						}
						clip.start();
						
						if (!canBeOverided)
						{
							soundPlaying = new Timer((int)(clip.getMicrosecondLength() / 1000.0 + 0.5), this);
							soundPlaying.start();
						}
					}
				} catch(Exception e) {
					System.out.println("Error with playing sound1.");
				}
			}
			else if(runLater)
			{
				sound = file;
				override = canBeOverided;
				this.loop = loop;
			}
		}
		else
		{
			try {
				Clip originalClip = AudioSystem.getClip();
				AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundPath + file).getAbsoluteFile());
				clip = AudioSystem.getClip();
				if(!originalClip.equals(clip))
				{
					clip.open(audioInputStream);
					if(loop)
					{
						clip.loop(Clip.LOOP_CONTINUOUSLY);
					}
					clip.start();
					
					if (!canBeOverided)
					{
						soundPlaying = new Timer((int)(clip.getMicrosecondLength() / 1000.0 + 0.5), this);
						soundPlaying.start();
					}
				}
			} catch(Exception e) {
				System.out.println("Error with playing sound4.");
			}
		}
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
			
			int [][] possible;
			boolean retry;
			do
			{
				retry = false;
				possible = new int [0][2];
				for (int j = 0; j < fieldElements.length; j++)
				{
					for (int k = 0; k < fieldElements[j].length; k++)
					{
						if (fieldElements[j][k] == null)
						{
							boolean adjacent = false;
							int l = 0;
							while (players[l] != null && !adjacent)
							{
								int xDistance = Math.abs(j - players[l].getX());
								int yDistance = Math.abs(k - players[l].getY());
								if (xDistance <= 1 && yDistance <= 1)
								{
									adjacent = true;
								}
								l++;
							}
							
							if(!adjacent)
							{
								int [][] newPossible = new int [possible.length + 1][2];
								for (l = 0; l < possible.length; l++)
								{
									newPossible[l] = possible[l];
								}
								newPossible[possible.length][0] = j;
								newPossible[possible.length][1] = k;
								possible = newPossible;
							}
						}
					}
				}
					
				if (possible.length == 0)
				{
					retry = true;
					boolean continueAsking = true;
					while (continueAsking)
					{
						System.out.print("There isn't enough room for the current amount of players, would you like to add a row, a column, or both? ");
						String choice = reader.next();
						if (choice.equalsIgnoreCase("row"))
						{
							xField++;
							continueAsking = false;
						}
						else if (choice.equalsIgnoreCase("column"))
						{
							yField++;
							continueAsking = false;
						}
						else if (choice.equalsIgnoreCase("both"))
						{
							xField++;
							yField++;
							continueAsking = false;
						}
						else
						{
							System.out.println("There isn't an option for " + choice + ", please input a valid answer.");
						}
					}
					
					JButton [][] newButtons = new JButton[xField][yField];
					fieldElements = new FieldElement[xField][yField];
					for (int k = 0; k < newButtons.length; k++)
					{
						for (int l = 0; l < newButtons[k].length; l++)
						{
							if (k < buttons.length && l < buttons.length)
							{
								newButtons [k][l] = buttons[k][l];
							}
							else
							{
								newButtons [k][l] = new JButton();
							}
						}
					}
					
					buttons = newButtons;
				}
			} while(retry);
			
			int position = (int)(Math.random() * possible.length);
			int x = possible[position][0];
			int y = possible[position][1];
			
	    	String iconPath = "";
			if (System.getProperty("os.name").contains("Windows")) {
				iconPath = "assets" + File.separator + "icons" + File.separator;
			} else {
				iconPath = "fnl_CapstoneProject" + File.separator + "assets" + File.separator + "icons" + File.separator;
			}
			
			Tank nextPlayer = null;
			if (type.equalsIgnoreCase(Tank.AOE))
				nextPlayer = new AOE_Tank (x, y, name, 1, 1, 1, 3, 3, 1, 5, 1, 0, password, buttons[x][y],this,
										false, false, new ImageIcon(iconPath + "tank_aoe.png"));
			else if (type.equalsIgnoreCase(Tank.BALANCED))
				nextPlayer = new BalancedTank (x, y, name, 1, 1, 1, 3, 3, 1, 5, 1, 0, password, buttons[x][y], this,
											false, false, new ImageIcon(iconPath + "tank_balanced.png"));
			else if (type.equalsIgnoreCase(Tank.DOT))
				nextPlayer = new DOT_Tank (x, y, name, 1, 1, 1, 3, 3, 1, 5, 1, 0, password, buttons[x][y], this,
										false, false, new ImageIcon(iconPath + "tank_dot.png"));
			else if (type.equalsIgnoreCase(Tank.HEAVY))
				nextPlayer = new HeavyTank (x, y, name, 1, 1, 1, 3, 3, 1, 5, 1, 0, password, buttons[x][y], this,
										false, false, new ImageIcon(iconPath + "tank_heavy.png"));
			else if (type.equalsIgnoreCase(Tank.LIGHT))
				nextPlayer = new LightTank (x, y, name, 1, 1, 1, 3, 3, 1, 5, 1, 0, password, buttons[x][y], this,
											false, false, new ImageIcon(iconPath + "tank_light.png"));
			players[i] = nextPlayer;
			
			fieldElements[x][y] = nextPlayer;
		}
		setAlive(players);
		jury = new Tank[0];
		
		System.out.print("Enter the length of a cycle in seconds ");
		cycleLength = reader.nextInt();
		System.out.print("Enter the amount of cycles one player can play in a row (enter 0 for infinite) ");
		maxCycles = reader.nextInt();
		startingTime = System.currentTimeMillis();
		reader.nextLine();
		alive = players;
	    jury = new Tank [0];
	    
	    actions = new JTextField("");
	    actions.setEditable(false);
	    actions.setHorizontalAlignment(JTextField.CENTER);
	    actions.setPreferredSize(new Dimension(fieldElements.length * 90, 30));
	    
	    newLogin();
	    actions.setText("This game has started with " + currentPlayer.getName() + " as the current player.");
	}
}
