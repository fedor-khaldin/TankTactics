/*
 * Author: Itay Volk
 * Date: 4/28/2022
 * Rev: 01
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
		
		int startX = x - areaOfEffect;
		if (startX < 0)
		{
			startX = 0;
		}
		int startY = y - areaOfEffect;
		if (startY < 0)
		{
			startY = 0;
		}
		
		for (; startX < x + areaOfEffect && x < game.getFieldElements().length; startX++)
		{
			for (; startY < y + areaOfEffect && y < game.getFieldElements()[startX].length; startY++)
			{
				if (startX != x && startY != y)
				{//TODO correct to shoot tanks in area
				}
			}
		}
	}
	
	public void upgadeSpecial (int upgradeAmt)
	{
		areaOfEffect += upgradeAmt;
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
