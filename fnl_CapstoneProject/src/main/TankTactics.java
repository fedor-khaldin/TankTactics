package main;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JFrame;
import boosters.*;
import tanks.*;

public class TankTactics extends JFrame
				implements ActionListener{
	
	private JButton [] [] buttons;
	private FieldElement [] [] fieldElements;
	private Tank currentPlayer;
	private Tank [] players, alive, jury;
	private Booster [] boosters;
	private long startingTime, cycleLength;
	private Timer clock;
	
	public TankTactics ()
	{
		super ("Tank Tactics");
		
		File currentDirFile = new File(".");
		String helper = currentDirFile.getAbsolutePath();
		startingTime = 0;
		cycleLength = 0;
		players = new Tank [0];
		boosters = new Booster [0];
		try {
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
		      
		      int lastLine = 0;
		      startingTime = Long.parseLong(input.substring(lastLine + 1, input.indexOf('\n', lastLine + 1)));
		      lastLine = input.indexOf('\n', lastLine + 1);
		      cycleLength = Integer.parseInt(input.substring(lastLine + 1, input.indexOf('\n', lastLine + 1)));
		      lastLine = input.indexOf('\n', lastLine + 1);
		      int xField = Integer.parseInt(input.substring(lastLine + 1, input.indexOf('\n', lastLine + 1)));
	    	  lastLine = input.indexOf('\n', lastLine + 1);
	    	  int yField = Integer.parseInt(input.substring(lastLine + 1, input.indexOf('\n', lastLine + 1)));
	    	  lastLine = input.indexOf('\n', lastLine + 1);
	    	  buttons = new JButton [xField] [yField];
	    	  fieldElements = new FieldElement [xField] [yField];

	    	  clock = new Timer((int)(cycleLength*60000 + startingTime - System.currentTimeMillis()), this);
	    	  clock.start();
		      
	    	  for (int i = 0; i < xField; i++)
	    	  {
	    		  for (int j = 0; j < yField; j++)
	    		  {
	    			  buttons [i] [j] = new JButton();
	    		  }
	    	  }
	    	  
		      while (lastLine < input.indexOf("****"))
		      {
		    	  int x = Integer.parseInt(input.substring(lastLine + 1, input.indexOf('\n', lastLine + 1)));
		    	  lastLine = input.indexOf('\n', lastLine + 1);
		    	  int y = Integer.parseInt(input.substring(lastLine + 1, input.indexOf('\n', lastLine + 1)));
		    	  lastLine = input.indexOf('\n', lastLine + 1);
		    	  String name = input.substring(lastLine + 1, input.indexOf('\n', lastLine + 1));
		    	  lastLine = input.indexOf('\n', lastLine + 1);
		    	  int power = Integer.parseInt(input.substring(lastLine + 1, input.indexOf('\n', lastLine + 1)));
		    	  lastLine = input.indexOf('\n', lastLine + 1);
		    	  int shootingRange = Integer.parseInt(input.substring(lastLine + 1, input.indexOf('\n', lastLine + 1)));
		    	  lastLine = input.indexOf('\n', lastLine + 1);
		    	  int movementRange = Integer.parseInt(input.substring(lastLine + 1, input.indexOf('\n', lastLine + 1)));
		    	  lastLine = input.indexOf('\n', lastLine + 1);
		    	  int life = Integer.parseInt(input.substring(lastLine + 1, input.indexOf('\n', lastLine + 1)));
		    	  lastLine = input.indexOf('\n', lastLine + 1);
		    	  int maxLife = Integer.parseInt(input.substring(lastLine + 1, input.indexOf('\n', lastLine + 1)));
		    	  lastLine = input.indexOf('\n', lastLine + 1);
		    	  int energy = Integer.parseInt(input.substring(lastLine + 1, input.indexOf('\n', lastLine + 1)));
		    	  lastLine = input.indexOf('\n', lastLine + 1);
		    	  int maxEnergy = Integer.parseInt(input.substring(lastLine + 1, input.indexOf('\n', lastLine + 1)));
		    	  lastLine = input.indexOf('\n', lastLine + 1);
		    	  int votes = Integer.parseInt(input.substring(lastLine + 1, input.indexOf('\n', lastLine + 1)));
		    	  lastLine = input.indexOf('\n', lastLine + 1);
		    	  String password = input.substring(lastLine + 1, input.indexOf('\n', lastLine + 1));
		    	  lastLine = input.indexOf('\n', lastLine + 1);
		    	  String type = input.substring(lastLine + 1, input.indexOf('\n', lastLine + 1));
		    	  lastLine = input.indexOf('\n', lastLine + 1);
		    	  
		    	  Tank nextPlayer = null;
		    	  if (type.equalsIgnoreCase(Tank.AOE))
		    		  nextPlayer = new AOE_Tank (x, y, name, power, shootingRange, movementRange, life, maxLife, energy, maxEnergy, votes, password, buttons[x][y]);
		    	  else 	if (type.equalsIgnoreCase(Tank.BALANCED))
		    		  nextPlayer = new BalancedTank (x, y, name, power, shootingRange, movementRange, life, maxLife, energy, maxEnergy, votes, password, buttons[x][y]);
		    	  else 	if (type.equalsIgnoreCase(Tank.DOT))
		    		  nextPlayer = new DOT_Tank (x, y, name, power, shootingRange, movementRange, life, maxLife, energy, maxEnergy, votes, password, buttons[x][y]);
		    	  else 	if (type.equalsIgnoreCase(Tank.HEAVY))
		    		  nextPlayer = new HeavyTank (x, y, name, power, shootingRange, movementRange, life, maxLife, energy, maxEnergy, votes, password, buttons[x][y]);
		    	  else 	if (type.equalsIgnoreCase(Tank.LIGHT))
		    		  nextPlayer = new LightTank (x, y, name, power, shootingRange, movementRange, life, maxLife, energy, maxEnergy, votes, password, buttons[x][y]);
		    	  else
		    	  {
		    		  throw new IOException(
		    				  "No booster type for " + type);
		    	  }
		    	  
		    	  
		    	  
		    	  Tank [] addedPlayers = new Tank [players.length + 1];
		    	  for (int i = 0; i < boosters.length; i++)
		    	  {
		    		  addedPlayers[i] = players[i];
		    	  }
		    	  addedPlayers[players.length] = nextPlayer;
		    	  players = addedPlayers;
		      }
		      
		      while (lastLine < input.indexOf("****"))
		      {
		    	  int x = Integer.parseInt(input.substring(lastLine + 1, input.indexOf('\n', lastLine + 1)));
		    	  lastLine = input.indexOf('\n', lastLine + 1);
		    	  int y = Integer.parseInt(input.substring(lastLine + 1, input.indexOf('\n', lastLine + 1)));
		    	  lastLine = input.indexOf('\n', lastLine + 1);
		    	  int strength = Integer.parseInt(input.substring(lastLine + 1, input.indexOf('\n', lastLine + 1)));
		    	  lastLine = input.indexOf('\n', lastLine + 1);
		    	  String type = input.substring(lastLine + 1, input.indexOf('\n', lastLine + 1));
		    	  lastLine = input.indexOf('\n', lastLine + 1);
		    	  
		    	  Booster nextBooster = null;
		    	  if (type.equalsIgnoreCase(Booster.ENERGY))
		    		  nextBooster = new EnergySupplier (x, y, strength, buttons[x][y]);
		    	  else 	if (type.equalsIgnoreCase(Booster.HEAL))
		    		  nextBooster = new Healer (x, y, strength, buttons[x][y]);
		    	  else 	if (type.equalsIgnoreCase(Booster.HIDDEN))
		    		  nextBooster = new HiddenBooster (x, y, strength, buttons[x][y]);
		    	  else 	if (type.equalsIgnoreCase(Booster.JUMPER))
		    		  nextBooster = new Jumper (x, y, strength, buttons[x][y]);
		    	  else 	if (type.equalsIgnoreCase(Booster.MAX_ENERGY))
		    		  nextBooster = new MaxEnergyBooster (x, y, strength, buttons[x][y]);
		    	  else 	if (type.equalsIgnoreCase(Booster.MAX_LIFE))
		    		  nextBooster = new MaxLifeBooster (x, y, strength, buttons[x][y]);
		    	  else 	if (type.equalsIgnoreCase(Booster.MOVEMENT_RANGE))
		    		  nextBooster = new MovementRangeBooster (x, y, strength, buttons[x][y]);
		    	  else 	if (type.equalsIgnoreCase(Booster.POWER))
		    		  nextBooster = new PowerBooster (x, y, strength, buttons[x][y]);
		    	  else 	if (type.equalsIgnoreCase(Booster.SHOOT))
		    		  nextBooster = new Shooter (x, y, strength, buttons[x][y]);
		    	  else 	if (type.equalsIgnoreCase(Booster.SHOOTING_RANGE))
		    		  nextBooster = new ShootingRangeBooster (x, y, strength, buttons[x][y]);
		    	  else 	if (type.equalsIgnoreCase(Booster.SPECIAL))
		    		  nextBooster = new SpecialBooster (x, y, strength, buttons[x][y]);
		    	  else 	if (type.equalsIgnoreCase(Booster.UNKNOWN))
		    		  nextBooster = new UnknownBooster (x, y, strength, buttons[x][y]);
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
		      
		      
		      
		} catch (IOException e) {
			
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
	    			  
	    			  fieldElements[i][j] = new FieldElement(i, j, "", buttons[i][j], tileColor);
	    		  }
	    	  }
	      }
	}
	
	public void draw()
	{
		
	}
	
	public void newLogin()
	{
		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		clock.stop();
		//TODO new cycle code
		clock = new Timer((int)(cycleLength*60000 + startingTime - System.currentTimeMillis()), this);
		clock.start();
	}
}
