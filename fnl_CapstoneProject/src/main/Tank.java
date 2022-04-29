package main;

import java.awt.Color;

import javax.swing.JButton;

public abstract class Tank extends FieldElement {

	// Tank Type Constants
	public static final String AOE = "AOE";
	public static final String DOT = "DOT";
	public static final String LIGHT = "light";
	public static final String HEAVY = "heavy";
	public static final String BALANCED = "balanced";

	// Tank Fields
	protected int power;
	protected int shootingRange;
	protected int movementRange;
	protected int life;
	protected int maxLife;
	protected int energy;
	protected int maxEnergy;
	protected int votes;
	protected String password;
	protected TankTactics game;

	// Tank Constructor
	public Tank(int x, int y, String name, int power, int shootingRange, int movementRange, int life, int maxLife,
			int energy, int maxEnergy, int votes, String password, JButton button, TankTactics game) {

		super(x, y, name, button, new Color(0, 0, 0), game);
		this.power = power;
		this.shootingRange = shootingRange;
		this.movementRange = movementRange;
		this.life = life;
		this.maxLife = maxLife;
		this.energy = energy;
		this.maxEnergy = maxEnergy;
		this.votes = votes;
		this.password = password;
		this.game = game;

	}

	// Action Performed Override Method for Tank Clas
	@Override
	public void actionPerformed(java.awt.event.ActionEvent e) {
		if (game.getCurrentPlayer().getLife() != 0) {
			if (this.equals(game.getCurrentPlayer())) {
				upgradeMenu();
			} else {

				if (e.getActionCommand().equals("Left Click")) {
					game.getCurrentPlayer().hit(this);
					this.heal(0);
				}

				if (e.getActionCommand().equals("Right Click")) {
					this.heal(1);
				}
			}
		} else {
			if (e.getActionCommand().equals("Left Click")) {
				this.votes++;
			}
		}

	}

	@Override
	public void draw() {
		super.draw();
		game.getButtons()[x][y].setToolTipText("");


	}

	public String toToolTipText() {
		String toolTipText = "";
		toolTipText += "Power: " + power + "\n";
		toolTipText += "Shooting Range: " + shootingRange + "\n";
		toolTipText += "Movement Range: " + movementRange + "\n";
		toolTipText += "Life: " + life + "\n";
		toolTipText += "Max Life: " + maxLife + "\n";
		toolTipText += "Energy: " + energy + "\n";
		toolTipText += "Max Energy: " + maxEnergy + "\n";
		toolTipText += getSpecialText();
		toolTipText += "Votes: " + votes + "\n";
		
		return toolTipText;
	}

	// Tank Upgrades
	public void upgradePower(int upgradeAmt) {
		this.power += upgradeAmt;

		if (this.power < 0) this.power = 1;
	}

	public void upgradeShootingRange(int upgradeAmt) {
		this.shootingRange += upgradeAmt;
		if (this.shootingRange < 0) this.shootingRange = 1;
	}

	public void upgradeMovementRange(int upgradeAmt) {
		this.movementRange += upgradeAmt;
		if (this.movementRange < 0) this.movementRange = 1;
	}

	public void upgradeMaxLife(int upgradeAmt) {
		this.maxLife += upgradeAmt;
		if (this.maxLife < 0) this.maxLife = 1;
	}

	public void upgradeMaxEnergy(int upgradeAmt) {
		this.maxEnergy += upgradeAmt;
		if (this.maxEnergy < 0) this.maxEnergy = 1;
	}

	public abstract void upgradeSpecial(int upgradeAmt);

	// Tank Getters
	public int getPower() {
		return power;
	}

	public int getShootingRange() {
		return shootingRange;
	}

	public int getMovementRange() {
		return movementRange;
	}

	public int getLife() {
		return life;
	}

	public int getMaxLife() {
		return maxLife;
	}

	public int getEnergy() {
		return energy;
	}

	public int getMaxEnergy() {
		return maxEnergy;
	}

	public int getVotes() {
		return votes;
	}

	public abstract String getType();

	public abstract int getSpecial();

	public abstract int getSpecialText();

	// Misc Tank Methods

	public void heal(int healAmt) {
		this.life += healAmt;
		if (this.life > this.maxLife) {
			this.life = this.maxLife;
		}

		else if (this.life < 0) {

			Tank[] copyTankArray = new Tank[game.getAlive().length - 1];

			for (int i = 0, j = 0; i < game.getAlive().length; i++) {
				if (copyTankArray[i] != this) {
					copyTankArray[i] = game.getAlive()[i];
				}
			}

			Tank[] addedPlayers = new Tank[game.getJury().length + 1];
				for (int i = 0; i < game.getJury().length; i++) {
					addedPlayers[i] = game.getJury()[i];
				}
				addedPlayers[game.getJury().length] = this;
				game.setJury(addedPlayers);
				
				this.life = 0;
		}
	}

	public void gainEnergy(int rechargeAmt) {
		this.energy += rechargeAmt;
		if (this.energy > this.maxEnergy) {
			this.energy = this.maxEnergy;
		}
	}

	public void hit(Tank target) {
		target.life -= game.getCurrentPlayer().getPower();
	
	}

	public Boolean checkPassword(String name, String password) {
		if (super.getName().equals(name)&&this.password.equals(password)) {
			return true;
		} else
			return false;
	}

	public void upgradeMenu() {

		System.out.println("\nUpgrade Menu");
		System.out.println("1. Power: " + this.power);
		System.out.println("2. Shooting Range: " + this.shootingRange);
		System.out.println("3. Movement Range: " + this.movementRange);
		System.out.println("4. Max Life: " + this.maxLife);
		System.out.println("5. Max Energy: " + this.maxEnergy);
		System.out.println("6. Special: " + this.getSpecial());
		System.out.println("7. Back");

		java.util.Scanner input = new java.util.Scanner(System.in);
		System.out.print("Input the number of the upgrade you would like to purchase: ");
		int upgradeChoice = input.nextInt();

		System.out.print("Input the amount of the upgrade you would like to purchase: ");
		int upgradeAmt = input.nextInt();
		input.close();

		if (upgradeAmt > this.energy) {
			System.out.println("You do not have enough energy to purchase this upgrade.");
			return;
		} else {
			this.energy -= upgradeAmt;
			switch (upgradeChoice) {
				case 1:
					this.upgradePower(upgradeAmt);
					break;
				case 2:
					this.upgradeShootingRange(upgradeAmt);
					break;
				case 3:
					this.upgradeMovementRange(upgradeAmt);
					break;
				case 4:
					this.upgradeMaxLife(upgradeAmt);
					break;
				case 5:
					this.upgradeMaxEnergy(upgradeAmt);
					break;
				case 6:
					this.upgradeSpecial(upgradeAmt);
					break;
				case 7:
					break;
				default:
					System.out.println("Invalid input.");
					break;
			}
		}

	}

}
