package com.alexhwang;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

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
	private Color movement = new Color(0, 0, 255, 128);
	private Color attack = new Color(255, 0, 0, 128);
	private Color both = new Color(192, 0, 192, 192);
	private Color range = new Color(192, 64, 0, 128);
	private Color retribution = new Color(0, 0, 0, 128);
	private PairArrayList movementArray = new PairArrayList();
	private PairArrayList attackArray = new PairArrayList();
	private PairArrayList backRangeArray = new PairArrayList();
	private PairArrayList forwardRangeArray = new PairArrayList();
	private PairArrayList retributionArray = new PairArrayList();
	
	 @Override
     protected void paintComponent(Graphics g) {
         super.paintComponent(g);
         Graphics2D g2 = (Graphics2D) g;

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
         for (Pair pair : (ArrayList<Pair>) movementArray) {
        	if (!attackArray.has(pair)) {
         		g.setColor(movement);
        	}
        	else {
                g.setColor(Color.WHITE);
        		g.fillRect(pair.getA() * TILE, pair.getB() * TILE, TILE, TILE);
	           	if ((pair.getA() % 2 == 0 && pair.getB() % 2 == 1) || (pair.getA() % 2 == 1 && pair.getB() % 2 == 0)) {
	                g.setColor(dark);
	        	}
	        	else {
	                g.setColor(light);
	        	}
        	}
    		g.fillRect(pair.getA() * TILE, pair.getB() * TILE, TILE, TILE);
         }
         for (Pair pair : (ArrayList<Pair>) attackArray) {
        	if (!movementArray.has(pair)) {
        		g.setColor(attack);
        	}
        	else {
        		g.setColor(both);
        	}
    		g.fillRect(pair.getA() * TILE, pair.getB() * TILE, TILE, TILE);
         }
         g2.setStroke(new BasicStroke(2));
    	 g.setColor(range);
         for (Pair pair : (ArrayList<Pair>) backRangeArray) {
        	 g2.draw(new Rectangle2D.Double(pair.getA() * TILE, pair.getB() * TILE, TILE, TILE));
         }
         for (Pair pair : (ArrayList<Pair>) forwardRangeArray) {
        	 g2.draw(new Line2D.Double((pair.getA() * TILE) + (TILE / 2), pair.getB() * TILE, (pair.getA() * TILE) + (TILE / 2), (pair.getB() + 1) * TILE));
        	 g2.draw(new Line2D.Double(pair.getA() * TILE, (pair.getB() * TILE) + (TILE / 2), (pair.getA() + 1) * TILE, (pair.getB() * TILE) + (TILE / 2)));
         }
    	 g.setColor(retribution);
         for (Pair pair : (ArrayList<Pair>) retributionArray) {
        	 g2.draw(new Line2D.Double(pair.getA() * TILE, pair.getB() * TILE, (pair.getA() + 1) * TILE, (pair.getB() + 1) * TILE));
        	 g2.draw(new Line2D.Double(pair.getA() * TILE, (pair.getB() + 1) * TILE, (pair.getA() + 1) * TILE, pair.getB() * TILE));
         }
	 }
	 
	 public void colorSquare(int creationFlag, int oldX, int oldY) {
		 int x = oldX / 32;
		 int y = oldY / 32;
		 Pair newPair = new Pair(x, y);
		 switch (creationFlag) {
		 case 1: //movement
			 if (!movementArray.has(newPair)) {
				 movementArray.add(newPair);
			 }
			 break;
		 case 2: //attack
			 if (!attackArray.has(newPair)) {
				 attackArray.add(newPair);
			 }
			 break;
		 case 3: //both
			 if (!movementArray.has(newPair)) {
				 movementArray.add(newPair);
			 }
			 if (!attackArray.has(newPair)) {
				 attackArray.add(newPair);
			 }
			 break;
		 case 4: //back range
			 if (!backRangeArray.has(newPair)) {
				 backRangeArray.add(newPair);
			 }
			 break;
		 case 5: //forward range
			 if (!forwardRangeArray.has(newPair)) {
				 forwardRangeArray.add(newPair);
			 }
			 break;
		 case 6: //retribution
			 if (!retributionArray.has(newPair)) {
				 retributionArray.add(newPair);
			 }
			 break;
		 }
		 revalidate();
		 repaint();
	 }
	 
	 public void clearSquare(int creationFlag, int x, int y) {
		 //TODO
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
