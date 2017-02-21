package com.alexhwang;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JLayeredPane;

public class CheckeredPane extends JLayeredPane {
	static final int TILE = 32;
	static final int LIMIT = 800;
	
	private int offsetXL = 0;
	private int offsetXR = 0;
	private int offsetYU = 0;
	private int offsetYD = 0;
	private Color dark = new Color(0, 0, 0, 32);
	private Color light = new Color(255, 255, 255, 0);
	
	 @Override
     protected void paintComponent(Graphics g) {
         super.paintComponent(g);
         
         for (int i = offsetXL; i < (LIMIT / TILE) - offsetXR; i++) {
             for (int j = offsetYU; j < (LIMIT / TILE) - offsetYD; j++) {
            	 if ((i % 2 == 0 && j % 2 == 1) || (i % 2 == 1 && j % 2 == 0)) {
                     g.setColor(dark);
                     g.fillRect(i * TILE, j * TILE, TILE, TILE);
            	 }
            	 else {
                     g.setColor(light);
                     g.fillRect(i * TILE, j * TILE, TILE, TILE);
            	 }
    		 }
		 }
	 }
	 
	 public void setXL(int offset) {
		 offsetXL = offset;
		 repaint();
	 }
	 public void setXR(int offset) {
		 offsetXR = offset;
		 repaint();
	 }
	 public void setYU(int offset) {
		 offsetYU = offset;
		 repaint();
	 }
	 public void setYD(int offset) {
		 offsetYD = offset;
		 repaint();
	 }
	 public void setDark(Color color) {
		 dark = color;
		 repaint();
	 }
	 public void setLight(Color color) {
		 light = color;
		 repaint();
	 }
}
