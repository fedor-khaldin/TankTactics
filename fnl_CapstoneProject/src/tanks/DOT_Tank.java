/*
 * Author: Itay Volk
 * Date: 4/28/2022
 * Rev: 01
 * Notes: this class repreasents a DOT tank
 */

package tanks;

import javax.swing.JButton;
import main.*;

public class DOT_Tank extends Tank {
	
	//field
	private int damageOverTime;

	//constructor
	public DOT_Tank(int x, int y, String name, int power, int shootingRange, int movementRange, int life, int maxLife,
			int energy, int maxEnergy, int damageOverTime, int votes, String password, JButton button, TankTactics game) {
		
				super(x, y, name, power, shootingRange, movementRange, life, maxLife, energy, maxEnergy, votes, password, button, game);
				this.damageOverTime = damageOverTime;
	}

	//methods
	
	public void hit(Tank other)
	{
		super.hit(other);
		
		//TODO finsih DOT
	}
	
	public void upgadeSpecial (int upgradeAmt)
	{
		damageOverTime += upgradeAmt;
	}
	
	public String getType()
	{
		return Tank.AOE;
	}
	
	public int getSpecial()
	{
		return damageOverTime;
	}
	
	public void draw()
	{
		super.draw();
		game.getButtons()[x][y].setToolTipText(game.getButtons()[x][y].getToolTipText() + "\nDOT: " + damageOverTime);
	}
}
