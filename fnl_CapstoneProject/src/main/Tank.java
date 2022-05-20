/* 

Written By: 
 ___ ___ __   __  ___   _  ___  _  __  _   __  _ __  _  
| __| __| _\ /__\| _ \ | |/ / || |/  \| | | _\| |  \| | 
| _|| _|| v | \/ | v / |   <| >< | /\ | |_| v | | | ' | 
|_| |___|__/ \__/|_|_\ |_|\_\_||_|_||_|___|__/|_|_|\__| 
                                                                                                                
Modified Date: 5/13/22
Notes: The tank class is responsible for handling all of the tank specific logic not handled by Fieldelement class. 

                                             _..----.._                                   
                                                    ]_.--._____[                                  
                                                  ___|'--'__..|--._                               
                              __               """    ;            :                              
                            ()_ """"---...__.'""!":  /    ___       :                             
                               """---...__\]..__] | /    [ 0 ]      :                             
                                          """!--./ /      """        :                            
                                   __  ...._____;""'.__________..--..:_                           
                                  /  !"''''''!''''''''''|''''/' ' ' ' \"--..__  __..              
                                 /  /.--.    |          |  .'          \' ' '.""--.{'.            
             _...__            >=7 //.-.:    |          |.'             \ ._.__  ' '""'.          
          .-' /    """"----..../ "">==7-.....:______    |                \| |  "";.;-"> \         
          """";           __.."   .--"/"""""----...."""""----.....H_______\_!....'----""""]       
        _..---|._ __..--""       _!.-=_.            """""""""""""""                   ;"""        
       /   .-";-.'--...___     ." .-""; ';""-""-...^..__...-v.^___,  ,__v.__..--^"--""-v.^v,      
      ;   ;   |'.         """-/ ./;  ;   ;\P.        ;   ;        """"____;  ;.--""""// '""<,     
      ;   ;   | 1            ;  ;  '.: .'  ;<   ___.-'._.'------""""""____'..'.--""";;'  o ';     
      '.   \__:/__           ;  ;--""()_   ;'  /___ .-" ____---""""""" __.._ __._   '>.,  ,/;     
        \   \    /"""<--...__;  '_.-'/; ""; ;.'.'  "-..'    "-.      /"/    `__. '.   "---";      
         '.  'v ; ;     ;;    \  \ .'  \ ; ////    _.-" "-._   ;    : ;   .-'__ '. ;   .^".'      
           '.  '; '.   .'/     '. `-.__.' /;;;   .o__.---.__o. ;    : ;   '"";;""' ;v^" .^        
             '-. '-.___.'<__v.^,v'.  '-.-' ;|:   '    :      ` ;v^v^'.'.    .;'.__/_..-'          
                '-...__.___...---""'-.   '-'.;\     'WW\     .'_____..>."^"-""""""""    fsc       
                                      '--..__ '"._..'  '"-;;"""                                   
                                             """---'""""""
*/

package main;


import java.io.File;
import javax.swing.ImageIcon;
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
	private boolean onJumper;
	private boolean atMax;
	private boolean onShooter;
	private ImageIcon regularTankIcon;
	

	// Tank Constructor
	public Tank(int x, int y, String name, int power, int shootingRange, int movementRange, int life, int maxLife,
			int energy, int maxEnergy, int votes, String password, JButton button, TankTactics game, boolean onJumper,
			boolean onShooter, ImageIcon icon) {

		super(x, y, button, game, icon, name);
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
		this.onJumper = onJumper;
		this.atMax = false;
		this.onShooter = onShooter;
		this.button.setIcon(icon);

	}

	// Custom ActionPerformed method that is called whenever a tank is clicked.
	@Override
	public void actionPerformed(java.awt.event.ActionEvent e) {
		// This was moved to the constructor.

		int mods = e.getModifiers();

		if (!this.equals(game.getCurrentPlayer())) {
			if (game.getCurrentPlayer().life > 0) {
				if (mods == 17) {
					if (game.getCurrentPlayer().getEnergy() >= 1 && !this.atMax) {
						this.gainEnergy(1);

						game.playSound("transfer-tank-energy.wav", true, false, false);

						game.getCurrentPlayer().gainEnergy(-1);
						game.setActionsText("You have transferred 1 energy to " + this.getName() + ".");
					}

					else if (this.atMax) {
						game.setActionsText("The tank " + this.name + " is at max energy.");
						
					}

					else {
						game.setActionsText("You don't have enough energy.");
					}
				}

				else {
					if (game.getCurrentPlayer().getEnergy() >= 1
							&& this.x < game.getCurrentPlayer().getX() + game.getCurrentPlayer().getShootingRange()
							&& this.y < game.getCurrentPlayer().getY() + game.getCurrentPlayer().getMovementRange()) {
						hit(this);
						game.setActionsText("You have hit " + this.getName() + ".");
					}

					else if (game.getCurrentPlayer().getEnergy() < 1) {
						game.setActionsText("You don't have enough energy.");
					}

					else {
						game.setActionsText("You are out of range.");
					}
				}

				game.draw();
			}

			else {
				if (mods == 17) {
					this.gainVotes(-1);
					game.getCurrentPlayer().gainEnergy(-1);
					game.setActionsText("You have voted 1 against " + this.getName() + ".");
				}

				else {
					this.gainVotes(1);
					game.getCurrentPlayer().gainEnergy(-1);
					game.setActionsText("You have voted 1 for " + this.getName() + ".");
				}
			}
		}

		else {
			if (game.getCurrentPlayer().getEnergy() >= 1)
				game.setActionsText("You have opened upgrade menu");
				game.getCurrentPlayer().upgradeMenu();
				
		}

		game.draw();
	}

	// Custom draw method that draws super field element and sets custom tooltip
	@Override
	public void draw() {
		super.draw();
		game.getButtons()[x][y].setToolTipText(toToolTipText());

	}

	// Custom toToolTipText method that returns a string of the tank's stats
	public String toToolTipText() {
		String toolTipText = "";

		toolTipText += "<html>";
		toolTipText += "<b> Name: " + name + "</b><br>";
		toolTipText += "Power: " + power + "<br>";
		toolTipText += "Shooting Range: " + shootingRange + "<br>";
		toolTipText += "Movement Range: " + movementRange + "<br>";
		toolTipText += "Life: " + life + "/" + maxLife + "<br>";
		toolTipText += "Energy: " + energy + "/" + maxEnergy + "<br>";
		toolTipText += "Votes: " + votes + "<br>";

		toolTipText += getSpecialText();
		toolTipText += "</html>";
		return toolTipText;
	}

	// Tank Upgrades

	// Upgrades Tank Power
	public void upgradePower(int upgradeAmt) {
		this.power += upgradeAmt;
		game.setActionsText("You have upgraded " + this.getName() + "'s power by " + upgradeAmt + ".");
		if (this.power < 0)
			this.power = 1;

	}

	// Upgrades Tank Shooting Range
	public void upgradeShootingRange(int upgradeAmt) {
		this.shootingRange += upgradeAmt;
		game.setActionsText("You have upgraded " + this.getName() + "'s shooting range by " + upgradeAmt + ".");
		if (this.shootingRange < 0)
			this.shootingRange = 1;

	}

	// Upgrades Tank Movement Range
	public void upgradeMovementRange(int upgradeAmt) {
		this.movementRange += upgradeAmt;
		game.setActionsText("You have upgraded " + this.getName() + "'s movement range by " + upgradeAmt + ".");
		if (this.movementRange < 0)
			this.movementRange = 1;

	}

	// Upgrades Tank Max Life
	public void upgradeMaxLife(int upgradeAmt) {
		this.maxLife += upgradeAmt;
		game.setActionsText("You have upgraded " + this.getName() + "'s max life by " + upgradeAmt + ".");
		if (this.maxLife < 0)
			this.maxLife = 1;

	}

	// Upgrades Tank Max Energy
	public void upgradeMaxEnergy(int upgradeAmt) {
		this.maxEnergy += upgradeAmt;
		game.setActionsText("You have upgraded " + this.getName() + "'s max energy by " + upgradeAmt + ".");
		if (this.maxEnergy < 0)
			this.maxEnergy = 1;

	}


	// Abstract method that upgrades tank's special ability
	public abstract void upgradeSpecial(int upgradeAmt);

	// Tank Getters

	// Returns Tank Power
	public int getPower() {
		return power;
	}

	// Returns Tank Shooting Range
	public int getShootingRange() {
		return shootingRange;
	}

	// Returns Tank Movement Range
	public int getMovementRange() {
		return movementRange;
	}

	// Returns Tank Life
	public int getLife() {
		return life;
	}

	// Returns Tank Max Life
	public int getMaxLife() {
		return maxLife;
	}

	// Returns Tank Energy
	public int getEnergy() {
		return energy;
	}

	// Returns Tank Max Energy
	public int getMaxEnergy() {
		return maxEnergy;
	}

	// Returns Tank Votes
	public int getVotes() {
		return votes;
	}

	// Returns Tank Password
	public String getPassword() {
		return password;
	}

	// Setter for On Jumper boolean
	public void setOnJumper(Boolean onJumper) {
		this.onJumper = onJumper;
	}

	// Getter for On Jumper boolean
	public Boolean getOnJumper() {
		return onJumper;
	}

	// Abstract method that returns tank's type
	public abstract String getType();

	// Abstract method that returns tank's special ability
	public abstract int getSpecial();

	// Abstract method that returns tank's special ability and type in string form
	public abstract String getSpecialText();

	// Misc Tank Methods

	// Heals a tank by a specified amount, including handling the tanks lives.
	public void heal(int healAmt) {
		this.life += healAmt;
		if (this.life > this.maxLife) {
			this.life = this.maxLife;
		}

		if (healAmt > 0) {
			game.setActionsText(this.name + " has healed by " + healAmt + ".");
		}

		else if (this.life <= 0) {

			game.playSound("tank-killed.wav", true, false, false);
			game.setActionsText("You have killed " + this.getName() + ".");
			Tank[] copyTankArray = new Tank[game.getAlive().length - 1];
			int offset = 0;
			for (int i = 0; i < game.getAlive().length; i++) {
				if (game.getAlive()[i] != this) {
					copyTankArray[i] = game.getAlive()[i + offset];
				}

				else {
					offset = 1;
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

	// Recharges tanks energy
	public void gainEnergy(int rechargeAmt) {
		this.energy += rechargeAmt;
		game.setActionsText(this.name + " has gained " + rechargeAmt + " energy.");
		if (this.energy < 0)
			this.energy = 0;

		if (this.energy >= this.maxEnergy) {
			this.atMax = true;
			this.energy = maxEnergy;
		}

		else
			this.atMax = false;
	}

	// Makes a tank take damage
	public void hit(Tank target) {

		game.playSound("tank-fire.wav", true, false, false);
		target.life -= game.getCurrentPlayer().getPower();
		this.heal(0);

	}

	// Checks if inputted string matches password
	public Boolean checkPassword(String name, String password) {
		return super.getName().equals(name) && this.password.equals(password);
	}

	// Outputs an upgrade menu giving the user the ability to adjust tank stats
	// using energy
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
		System.out.println("You have " + this.energy + " energy points");
		System.out.print("Input the number of the upgrade you would like to purchase: ");
		int upgradeChoice = input.nextInt();

		if (upgradeChoice != 7) {
			System.out.print("Input the amount of the upgrade you would like to purchase: ");
			int upgradeAmt = input.nextInt();

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

			game.draw();
		}
	}

	// On Shooter handling

	public boolean getOnShooter() {
		return onShooter;
	}

	public void setOnShooter(boolean onShooter) {
		this.onShooter = onShooter;
	}
	// Handles Voting:

	// Resets Vote
	public void resetVotes() {
		this.votes = 0;
	}

	// Adds a vote to the tank
	public void gainVotes(int amountOfVotes) {
		this.votes += amountOfVotes;

		if (votes >= 3) {
			this.gainEnergy(1);
		}

		if (votes <= -3) {
			this.gainEnergy(-1);
		}
	}

	// SetButton
	@Override
	public void setButton(JButton button) {
		this.button.setIcon(null);
		this.button.setToolTipText(null);
		super.setButton(button);
		this.button.setIcon(regularTankIcon);
	}
}

/*
 
                                                                                                                             
                                                                                                                     dddddddd
         tttt         hhhhhhh                                                                                        d::::::d
      ttt:::t         h:::::h                                                                                        d::::::d
      t:::::t         h:::::h                                                                                        d::::::d
      t:::::t         h:::::h                                                                                        d:::::d 
ttttttt:::::ttttttt    h::::h hhhhh           eeeeeeeeeeee             eeeeeeeeeeee    nnnn  nnnnnnnn        ddddddddd:::::d 
t:::::::::::::::::t    h::::hh:::::hhh      ee::::::::::::ee         ee::::::::::::ee  n:::nn::::::::nn    dd::::::::::::::d 
t:::::::::::::::::t    h::::::::::::::hh   e::::::eeeee:::::ee      e::::::eeeee:::::een::::::::::::::nn  d::::::::::::::::d 
tttttt:::::::tttttt    h:::::::hhh::::::h e::::::e     e:::::e     e::::::e     e:::::enn:::::::::::::::nd:::::::ddddd:::::d 
      t:::::t          h::::::h   h::::::he:::::::eeeee::::::e     e:::::::eeeee::::::e  n:::::nnnn:::::nd::::::d    d:::::d 
      t:::::t          h:::::h     h:::::he:::::::::::::::::e      e:::::::::::::::::e   n::::n    n::::nd:::::d     d:::::d 
      t:::::t          h:::::h     h:::::he::::::eeeeeeeeeee       e::::::eeeeeeeeeee    n::::n    n::::nd:::::d     d:::::d 
      t:::::t    tttttth:::::h     h:::::he:::::::e                e:::::::e             n::::n    n::::nd:::::d     d:::::d 
      t::::::tttt:::::th:::::h     h:::::he::::::::e               e::::::::e            n::::n    n::::nd::::::ddddd::::::dd
      tt::::::::::::::th:::::h     h:::::h e::::::::eeeeeeee        e::::::::eeeeeeee    n::::n    n::::n d:::::::::::::::::d
        tt:::::::::::tth:::::h     h:::::h  ee:::::::::::::e         ee:::::::::::::e    n::::n    n::::n  d:::::::::ddd::::d
          ttttttttttt  hhhhhhh     hhhhhhh    eeeeeeeeeeeeee           eeeeeeeeeeeeee    nnnnnn    nnnnnn   ddddddddd   ddddd
                                                                                                                             
                                                                                                                                                                                                                          
                                                                                                                    
 */