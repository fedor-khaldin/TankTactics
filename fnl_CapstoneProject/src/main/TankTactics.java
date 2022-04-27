package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

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
		      
		      if (!input.isBlank())
		      {
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
	    			  
	    			  fieldElements[i][j] = new FieldElement(i, j, "", buttons[i][j], tileColor);
	    		  }
	    	  }
	      }
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(fieldElements.length, fieldElements[0].length));
		for (int i = 0; i < fieldElements[0].length; i++)
		{
			for (int j = 0; j < fieldElements.length; j++)
			{
				panel.add(buttons[j][i]);
			}
		}
		draw();
		Container c = getContentPane();
		c.add(panel, BorderLayout.CENTER);
	}
	
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
	
	public void newLogin()
	{
		
	}
	
	private void newGame()
	{
		
	}
	
	public Tank getCurrentPlayer()
	{
		return currentPlayer;
	}
	
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
		
		Booster newBooster;
		switch (type)
		{
			case 0:
				newBooster = new EnergySupplier(newX, newY, strength, buttons[newX][newY]);
				break;
			case 1:
				newBooster = new Healer(newX, newY, strength, buttons[newX][newY]);
				break;
			case 2:
				newBooster = new HiddenBooster(newX, newY, strength, buttons[newX][newY]);
				break;
			case 3:
				newBooster = new Jumper(newX, newY, strength, buttons[newX][newY]);
				break;
			case 4:
				newBooster = new MaxEnergyBooster(newX, newY, strength, buttons[newX][newY]);
				break;
			case 5:
				newBooster = new MaxLifeBooster(newX, newY, strength, buttons[newX][newY]);
				break;
			case 6:
				newBooster = new MovementRangeBooster(newX, newY, strength, buttons[newX][newY]);
				break;
			case 7:
				newBooster = new PowerBooster(newX, newY, strength, buttons[newX][newY]);
				break;
			case 8:
				newBooster = new Shooter(newX, newY, strength, buttons[newX][newY]);
				break;
			case 9:
				newBooster = new ShootingRangeBooster(newX, newY, strength, buttons[newX][newY]);
				break;
			case 10:
				newBooster = new SpecialBooster(newX, newY, strength, buttons[newX][newY]);
				break;
			case 11:
				newBooster = new UnknownBooster(newX, newY, strength, buttons[newX][newY]);
				break;
		}
	  	  Booster [] addedBoosters = new Booster [boosters.length + 1];
	  	  for (i = 0; i < boosters.length; i++)
	  	  {
	  		  addedBoosters[i] = boosters[i];
	  	  }
	  	  addedBoosters[boosters.length] = newBooster;
  	  boosters = addedBoosters;
		clock = new Timer((int)(cycleLength*60000 + startingTime - System.currentTimeMillis()), this);
		clock.start();
	}
}
