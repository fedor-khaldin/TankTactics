/*
 * Author: Itay Volk
 * Date: 4/29/2022
 * Rev: 01
 * Notes: this class repreasents a heavy tank
 */

package tanks;

import javax.swing.JButton;
import main.*;

public class HeavyTank extends Tank {

	//fields
	private int armor;
	
	//constructor
	public HeavyTank(int x, int y, String name, int power, int shootingRange, int movementRange, int life, int maxLife,
			int energy, int maxEnergy, int armor, int votes, String password, JButton button, TankTactics tankTactics) {
				super(x, y, name, power, shootingRange, movementRange, life, maxLife, energy, maxEnergy, votes, password, button, tankTactics);
				this.armor = armor;
	}
	
	//methods
	
	public void heal(int healAmt)
	{
		if (healAmt < 0)
		{
			super.heal(healAmt / armor);
		}
	}
	
	public void upgradeSpecial (int upgradeAmt)
	{
		armor += upgradeAmt;
		if (armor < 1)
			armor = 1;
	}
	
	public String getType()
	{
		return Tank.HEAVY;
	}
	
	public int getSpecial()
	{
		return armor;
	}
	
	@Override
	public String getSpecialText() {
		return "Armor: " + armor + "\n";
	}
}
