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
	private int power;
	private int shootingRange;
	private int movementRange;
	private int life;
	private int maxLife;
	private int energy;
	private int maxEnergy;
	private int votes;
	private String password;
	private TankTactics game;

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

	// Action Performed Override Method for Tank Class
	@Override
	public void actionPerformed(java.awt.event.ActionEvent e) {
		
		if (this.equals(game.getCurrentPlayer())) {
			 upgradeMenu();
		}
		else {
			
			if (e.getActionCommand().equals("Left Click") {
				// Fire a shot from current player to location.
			}

			if (e.getActionCommand().equals("Right Click")) {
				// Heal selected palyer.
			}
		}
		
		
		
	}

	@Override
	public void draw() {
		super.Draw();

	}

	// Tank Upgrades
	public void upgradePower(int upgradeAmt) {
		this.power += upgradeAmt;
	}

	public void upgradeShootingRange(int upgradeAmt) {
		this.shootingRange += upgradeAmt;
	}

	public void upgradeMovementRange(int upgradeAmt) {
		this.movementRange += upgradeAmt;
	}

	public void upgradeMaxLife(int upgradeAmt) {
		this.maxLife += upgradeAmt;
	}

	public void upgradeMaxEnergy(int upgradeAmt) {
		this.maxEnergy += upgradeAmt;
	}

	abstract void upgradeSpecial(int upgradeAmt);

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

	abstract String getType();

	abstract int getSpecial();

	// Misc Tank Methods

	public void heal(int healAmt) {
		this.life += healAmt;
		if (this.life > this.maxLife) {
			this.life = this.maxLife;
		}
	}

	public void gainEnergy(int rechargeAmt) {
		this.energy += rechargeAmt;
		if (this.energy > this.maxEnergy) {
			this.energy = this.maxEnergy;
		}
	}

	public void hit(int damage) {
		this.life -= damage;
		if (this.life < 0) {
			this.life = 0;
		}
	}

	public Boolean checkPassword(String password) {
		if (this.password.equals(password)) {
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
