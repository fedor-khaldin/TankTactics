package main;

import javax.swing.JButton;

public class Tank extends FieldElement {
	
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
	
	// Tank Constructor
	public Tank(int x, int y, String name, int power, int shootingRange, int movementRange, int life, int maxLife,
			int energy, int maxEnergy, int votes, String password, JButton button) {
		super(x, y, name, button);
		this.power = power;
		this.shootingRange = shootingRange;
		this.movementRange = movementRange;
		this.life = life;
		this.maxLife = maxLife;
		this.energy = energy;
		this.maxEnergy = maxEnergy;
		this.votes = votes;
		this.password = password;
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

	public void upgradeSpecial(int upgradeAmt) {
		System.out.println("not implemented");
	}

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

	public String getType() {
		System.out.println("Get Type Not Implemented");
		return "error in get type";
	}

	public int getSpecial() {
		System.out.println("Get type not implemented");
		return 0;
	}

	// Misc Tank Methods

	public void heal(int healAmt){
		this.life += healAmt;
		if(this.life > this.maxLife){
			this.life = this.maxLife;
		}
	}

	public void gainEnergy(int rechargeAmt){
		this.energy += rechargeAmt;
		if(this.energy > this.maxEnergy){
			this.energy = this.maxEnergy;
		}
	}

	public void hit(int damage){
		this.life -= damage;
		if(this.life < 0){
			this.life = 0;
		}
	}

	public Boolean checkPassword(String password){
		if(this.password.equals(password)){
			return true;
		}
		else return false;
	}

	public void upgradeMenu() {
		System.out.println("not implemented");
	}

	



    
}
