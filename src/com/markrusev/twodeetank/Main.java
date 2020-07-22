package com.markrusev.twodeetank;

import java.awt.Color;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		JFrame window = new JFrame("2D Tank");
		GamePlay gameplay = new GamePlay();
		
		
		window.setBounds(10, 10, 800, 630);
		window.setBackground(Color.GRAY);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.add(gameplay);
		window.setVisible(true);

	}

}
