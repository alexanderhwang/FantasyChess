package com.alexhwang;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLayeredPane;

public class CheckeredPane extends JLayeredPane {
	static final int TILE = 32;
	static final int LIMIT = 800;
	
	 @Override
     protected void paintComponent(Graphics g) {
         super.paintComponent(g);
         g.setColor(new Color(0, 0, 0, 32));
         
         for (int i = 0; i < (LIMIT / TILE); i++) {
             for (int j = 0; j < (LIMIT / TILE); j++) {
            	 if ((i % 2 == 0 && j % 2 == 1) || (i % 2 == 1 && j % 2 == 0)) {
                     g.fillRect(i * TILE, j * TILE, TILE, TILE);
            	 }
    		 }
		 }
	 }
}
