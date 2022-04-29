/*
 * Author: Itay Volk
 * Date: 4/28/2022
 * Rev: 01
 * Notes: this class repreasents a balanced tank
 */

package tanks;

import javax.swing.JButton;
import main.*;

public class BalancedTank extends Tank {

	//fields
	private int lifeGain;
	
	//constructor
	public BalncedTank(int x, int y, String name, int power, int shootingRange, int movementRange, int life, int maxLife,
			int energy, int maxEnergy, int lifeGain, int votes, String password, JButton button, TankTactics game) {
				super(x, y, name, power, shootingRange, movementRange, life, maxLife, energy, maxEnergy, votes, password, button, game);
				this.lifeGain = lifeGain;
	}
	
	//methods
	
	public void heal(int healAmt)
	{
		if (healAmt > 0)
		{
			super.heal(healAmt * lifeGain);
		}
		else
		{
			super.heal(healAmt / lifeGain);
		}
	}
	
	public void upgadeSpecial (int upgradeAmt)
	{
		lifeGain += upgradeAmt;
	}
	
	public String getType()
	{
		return Tank.BALANCED;
	}
	
	public int getSpecial()
	{
		return lifeGain;
	}
	
	public void draw()
	{
		super.draw();
		game.getButtons()[x][y].setToolTipText(game.getButtons()[x][y].getToolTipText() + "\nlife gain: " + lifeGain);
	}

}
