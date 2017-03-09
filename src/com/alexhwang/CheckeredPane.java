package com.alexhwang;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class CheckeredPane extends JLayeredPane {
	static final String BASE_RESOURCE_PATH = "src\\resources\\";
	static final int TILE = 32;
	static final int LIMIT = 800;
	
	//TODO variable for current piece if applicable
	private int offsetXL = 0;
	private int offsetXR = 0;
	private int offsetYU = 0;
	private int offsetYD = 0;
	private int arraySize = 0;
	private Color dark = new Color(0, 0, 0, 32);
	private Color light = new Color(232, 232, 232, 0);
	private Color movement = new Color(0, 0, 255, 128);
	private Color attack = new Color(255, 0, 0, 128);
	private Color both = new Color(192, 0, 192, 192);
	private Color range = new Color(192, 192, 0, 192);
	private Color retribution = new Color(0, 0, 0, 128);
	private JLabel flagTransport = new JLabel(new ImageIcon(BASE_RESOURCE_PATH + "Icons\\FlagTransport.png"));
	private PairArrayList movementArray = new PairArrayList();
	private PairArrayList attackArray = new PairArrayList();
	private PairArrayList backRangeArray = new PairArrayList(); //applies to attack tiles (consequent downgrade)
	private PairArrayList forwardRangeArray = new PairArrayList(); //applies to attack tiles (consequent upgrade)
	private PairArrayList retributionArray = new PairArrayList();
	private ArrayList<Boolean> flagArray = new ArrayList<Boolean>(); //t, s, i, n, r, j
	
	 @Override
     protected void paintComponent(Graphics g) {
         super.paintComponent(g);
         Graphics2D g2 = (Graphics2D) g;

         for (int i = offsetXL; i < (LIMIT / TILE) - offsetXR; i++) {
             for (int j = offsetYU; j < (LIMIT / TILE) - offsetYD; j++) {
                 g.setColor(Color.WHITE);
                 g.fillRect(i * TILE, j * TILE, TILE, TILE);
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
		 } //TODO check logic of 4 and 5, determine cost overall
		 revalidate();
		 repaint();
	 }
	 
	 public void clearSquare(int creationFlag, int oldX, int oldY) {
		 int x = oldX / 32;
		 int y = oldY / 32;
		 Pair newPair = new Pair(x, y);
		 switch (creationFlag) {
		 case 1: //movement
			 if (movementArray.has(newPair)) {
				 movementArray.destroy(newPair);
			 }
			 break;
		 case 2: //attack
			 if (attackArray.has(newPair)) {
				 attackArray.destroy(newPair);
			 }
			 break;
		 case 3: //both
			 if (movementArray.has(newPair)) {
				 movementArray.destroy(newPair);
			 }
			 if (attackArray.has(newPair)) {
				 attackArray.destroy(newPair);
			 }
			 break;
		 case 4: //back range
			 if (backRangeArray.has(newPair)) {
				 backRangeArray.destroy(newPair);
			 }
			 break;
		 case 5: //forward range
			 if (forwardRangeArray.has(newPair)) {
				 forwardRangeArray.destroy(newPair);
			 }
			 break;
		 case 6: //retribution
			 if (retributionArray.has(newPair)) {
				 retributionArray.destroy(newPair);
			 }
			 break;
		 }
		 revalidate();
		 repaint();
	 }

	 public void clear() {
		 arraySize = movementArray.size();
		 if (arraySize > 0) {
			 for (int i = 0; i < arraySize; i++) {
				 movementArray.remove(0);
			 }
		 }
		 arraySize = attackArray.size();
		 if (arraySize > 0) {
			 for (int i = 0; i < arraySize; i++) {
				 attackArray.remove(0);
			 }
		 }
		 arraySize = backRangeArray.size();
		 if (arraySize > 0) {
			 for (int i = 0; i < arraySize; i++) {
				 backRangeArray.remove(0);
			 }
		 }
		 arraySize = forwardRangeArray.size();
		 if (arraySize > 0) {
			 for (int i = 0; i < arraySize; i++) {
				 forwardRangeArray.remove(0);
			 }
		 }
		 arraySize = retributionArray.size();
		 if (arraySize > 0) {
			 for (int i = 0; i < arraySize; i++) {
				 retributionArray.remove(0);
			 }
		 }
		 revalidate();
		 repaint();
	 }

	 public void setFlags() {
		 JPanel flags = new JPanel(); //t, s, i, n, r, j
		 if (flagArray.size() != 6) {
			 for (int i = 0; i < 6; i++) {
				 flagArray.add(false);
			 }
		 }
		 flags.add(new JCheckBox("Transport", flagArray.get(0)));
		 flags.add(new JCheckBox("Shield", flagArray.get(1)));
		 flags.add(new JCheckBox("Invisible", flagArray.get(2)));
		 flags.add(new JCheckBox("Necromancer", flagArray.get(3)));
		 flags.add(new JCheckBox("Royal", flagArray.get(4)));
		 flags.add(new JCheckBox("Joker", flagArray.get(5))); 
		 JOptionPane.showConfirmDialog(null, flags, "Flags", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		 for (int j = 0; j < flagArray.size(); j++) {
			 flagArray.set(j, ((JCheckBox) flags.getComponent(j)).isSelected()); //TODO determine costs
		 }
		 if (flagArray.get(0)) {
             //g.fillRect(7 * TILE, 23 * TILE, TILE, TILE);
	    	 this.setLayer(flagTransport, 1);
    		 this.add(flagTransport);
    		 flagTransport.setBounds(7 * TILE, 23 * TILE, TILE, TILE);
         }
         if (flagArray.get(1)) {
             //g.fillRect(9 * TILE, 23 * TILE, TILE, TILE);
         }
         if (flagArray.get(2)) {
             //g.fillRect(11 * TILE, 23 * TILE, TILE, TILE);
         }
         if (flagArray.get(3)) {
             //g.fillRect(13 * TILE, 23 * TILE, TILE, TILE);
         }
         if (flagArray.get(4)) {
             //g.fillRect(15 * TILE, 23 * TILE, TILE, TILE);
         }
         if (flagArray.get(5)) {
             //g.fillRect(17 * TILE, 23 * TILE, TILE, TILE);
         }
		 revalidate();
		 repaint();
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
