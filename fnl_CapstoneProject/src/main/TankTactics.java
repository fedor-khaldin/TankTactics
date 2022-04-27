package main;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Timer;

import javax.swing.JFrame;
import boosters.*;
import tanks.*;

public class TankTactics extends JFrame{
	
	public TankTactics ()
	{
		File currentDirFile = new File(".");
		String helper = currentDirFile.getAbsolutePath();
		int startingTime = 0, cycleLength = 0;
		Tank [] players = new Tank [0];
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
		      
		      startingTime = Integer.parseInt(input.substring(0, input.indexOf('\n')));
		      cycleLength = Integer.parseInt(input.substring(input.indexOf('\n') + 1, input.indexOf('\n', input.indexOf('\n') + 1) + 1));
		      
		      int lastLine = input.indexOf('\n', input.indexOf('\n', input.indexOf('\n') + 1) + 1);
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
		    	  
		    	  Tank nextTank = null;
		    	  if (type.equalsIgnoreCase(Tank.AOE))
		    		  nextTank = new AOE_Tank (x, y, name, power, shootingRange, movementRange, life, maxLife, energy, maxEnergy, votes, password);
		    	  else 	if (type.equalsIgnoreCase(Tank.DOT))
		    		  nextTank = new DOT_Tank (x, y, name, power, shootingRange, movementRange, life, maxLife, energy, maxEnergy, votes, password);
		    	  else 	if (type.equalsIgnoreCase(Tank.BALANCED))
		    		  nextTank = new BalancedTank (x, y, name, power, shootingRange, movementRange, life, maxLife, energy, maxEnergy, votes, password);
		    	  else 	if (type.equalsIgnoreCase(Tank.HEAVY))
		    		  nextTank = new HeavyTank (x, y, name, power, shootingRange, movementRange, life, maxLife, energy, maxEnergy, votes, password);
		    	  else 	if (type.equalsIgnoreCase(Tank.LIGHT))
		    		  nextTank = new LightTank (x, y, name, power, shootingRange, movementRange, life, maxLife, energy, maxEnergy, votes, password);
		    	  else
		    	  {
		    		  throw new IllegalArgumentException(
		    				  "No tank type for " + type);
		    	  }
		    	  
		    	  Tank [] addedPlayers = new Tank [players.length + 1];
		    	  for (int i = 0; i < players.length; i++)
		    	  {
		    		  addedPlayers[i] = players[i];
		    	  }
		    	  addedPlayers[players.length] = nextTank;
		    	  players = addedPlayers;
		    	  
			      while (lastLine < input.indexOf("****"))
			      {
			    	  x = Integer.parseInt(input.substring(lastLine + 1, input.indexOf('\n', lastLine + 1)));
			    	  lastLine = input.indexOf('\n', lastLine + 1);
			    	  y = Integer.parseInt(input.substring(lastLine + 1, input.indexOf('\n', lastLine + 1)));
			    	  lastLine = input.indexOf('\n', lastLine + 1);
			    	  int strength = Integer.parseInt(input.substring(lastLine + 1, input.indexOf('\n', lastLine + 1)));
			    	  lastLine = input.indexOf('\n', lastLine + 1);
			    	  type = input.substring(lastLine + 1, input.indexOf('\n', lastLine + 1));
			    	  lastLine = input.indexOf('\n', lastLine + 1);
			    	  
			    	  Booster nextBooster = null;
			    	  if (type.equalsIgnoreCase(Tank.AOE))
			    		  nextTank = new AOE_Tank (x, y, name, power, shootingRange, movementRange, life, maxLife, energy, maxEnergy, votes, password);
			    	  else 	if (type.equalsIgnoreCase(Tank.DOT))
			    		  nextTank = new DOT_Tank (x, y, name, power, shootingRange, movementRange, life, maxLife, energy, maxEnergy, votes, password);
			    	  else 	if (type.equalsIgnoreCase(Tank.BALANCED))
			    		  nextTank = new BalancedTank (x, y, name, power, shootingRange, movementRange, life, maxLife, energy, maxEnergy, votes, password);
			    	  else 	if (type.equalsIgnoreCase(Tank.HEAVY))
			    		  nextTank = new HeavyTank (x, y, name, power, shootingRange, movementRange, life, maxLife, energy, maxEnergy, votes, password);
			    	  else 	if (type.equalsIgnoreCase(Tank.LIGHT))
			    		  nextTank = new LightTank (x, y, name, power, shootingRange, movementRange, life, maxLife, energy, maxEnergy, votes, password);
			    	  else
			    	  {
			    		  throw new IllegalArgumentException(
			    				  "No tank type for " + type);
			    	  }
			    	  
			    	  Tank [] addedPlayers = new Tank [players.length + 1];
			    	  for (int i = 0; i < players.length; i++)
			    	  {
			    		  addedPlayers[i] = players[i];
			    	  }
			    	  addedPlayers[players.length] = nextTank;
			    	  players = addedPlayers;
		      }
		      
		      
		} catch (IOException e) {
			
		}
		
		
	}
}
