/*
 * Author: Itay Volk
 * Date: 5/5/2022
 * Rev: 02
 * Notes: this class repreasents a heavy tank
 */

package tanks;

import javax.swing.JButton;
import main.*;

public class HeavyTank extends Tank {

	//Fields
	private int armor;
	
	//Constructor
	public HeavyTank(int x, int y, String name, int power, int shootingRange, int movementRange, int life, int maxLife,
			int energy, int maxEnergy, int armor, int votes, String password,
			JButton button, TankTactics tankTactics, boolean onJumper, boolean onShooter) {
		
				super(x, y, name, power, shootingRange, movementRange, life, maxLife,
						energy, maxEnergy, votes, password, button, tankTactics, onJumper, onShooter);
				this.armor = armor;
	}
	
	//Methods
	//Overriden to implement armor
	public void heal(int healAmt)
	{
		if (healAmt < 0)
		{
			super.heal(healAmt / armor);
		}
		else
		{
			super.heal(healAmt);
		}
	}
	
	//Implemented methods
	//Upgrades armor
	public void upgradeSpecial (int upgradeAmt)
	{
		armor += upgradeAmt;
		if (armor < 1)
			armor = 1;
	}
	
	//Returns the type of this tank
	public String getType()
	{
		return Tank.HEAVY;
	}
	
	//Returns armor
	public int getSpecial()
	{
		return armor;
	}
	
	//Returns a String representation of armor
	@Override
	public String getSpecialText() {
		return "Armor: " + armor + "\n";
	}
}
