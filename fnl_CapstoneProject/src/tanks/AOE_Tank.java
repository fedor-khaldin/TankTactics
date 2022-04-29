/*
 * Author: Itay Volk
 * Date: 4/29/2022
 * Rev: 02
 * Notes: this class repreasents an AOE tank
 */

package tanks;

import javax.swing.JButton;
import main.*;

public class AOE_Tank extends Tank {
	
	//field
	private int areaOfEffect;

	//constructor
	public AOE_Tank(int x, int y, String name, int power, int shootingRange, int movementRange, int life, int maxLife,
			int energy, int maxEnergy, int areaOfEffect, int votes, String password, JButton button, TankTactics game) {
		
				super(x, y, name, power, shootingRange, movementRange, life, maxLife, energy, maxEnergy, votes, password, button, game);
				this.areaOfEffect = areaOfEffect;
	}

	//methods
	
	public void hit(Tank other)
	{
		super.hit(other);
		
		for (int i = 0; i < game.getPlayers().length; i++)
		{
			if(game.getPlayers()[i].getX() <= x + areaOfEffect && game.getPlayers()[i].getX() >= x - areaOfEffect 
					&& game.getPlayers()[i].getY() <= y + areaOfEffect && game.getPlayers()[i].getY() >= y - areaOfEffect && game.getPlayers()[i] != this)
			{
				super.hit(game.getPlayers()[i]);
			}
		}
	}
	
	public void upgadeSpecial (int upgradeAmt)
	{
		areaOfEffect += upgradeAmt;
		if (areaOfEffect < 1)
			areaOfEffect = 1;
	}
	
	public String getType()
	{
		return Tank.AOE;
	}
	
	public int getSpecial()
	{
		return areaOfEffect;
	}
	
	public void draw()
	{
		super.draw();
		game.getButtons()[x][y].setToolTipText(game.getButtons()[x][y].getToolTipText() + "\nAOE: " + areaOfEffect);
	}
}
