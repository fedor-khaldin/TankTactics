/*
 * Author: Itay Volk
 * Date: 5/1/2022
 * Rev: 01
 * Notes: this class starts a TankTactics game
 */

package main;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		TankTactics window = new TankTactics();
		window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		window.setVisible(true);

	}

}
