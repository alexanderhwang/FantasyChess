package com.alexhwang;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;

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
	private int totalCost = 0;
	private Color dark = new Color(0, 0, 0, 32);
	private Color light = new Color(232, 232, 232, 0);
	private Color movement = new Color(0, 0, 255, 128);
	private Color attack = new Color(255, 0, 0, 128);
	private Color both = new Color(192, 0, 192, 192);
	private Color range = new Color(192, 192, 0, 192);
	private Color retribution = new Color(0, 0, 0, 128);
	private Color error = new Color(0, 255, 0, 255);
	private JLabel flagTransport = new JLabel(new ImageIcon(BASE_RESOURCE_PATH + "Icons\\FlagTransport.png"));
	private JLabel flagShield = new JLabel(new ImageIcon(BASE_RESOURCE_PATH + "Icons\\FlagShield.png"));
	private JLabel flagInvisible = new JLabel(new ImageIcon(BASE_RESOURCE_PATH + "Icons\\FlagInvisible.png"));
	private JLabel flagNecromancer = new JLabel(new ImageIcon(BASE_RESOURCE_PATH + "Icons\\FlagNecromancer.png"));
	private JLabel flagRoyal = new JLabel(new ImageIcon(BASE_RESOURCE_PATH + "Icons\\FlagRoyal.png"));
	private JLabel flagJoker = new JLabel(new ImageIcon(BASE_RESOURCE_PATH + "Icons\\FlagJoker.png"));
	private JLabel costDisplay = new JLabel("Cost: 0");
	private final ArrayList<JLabel> flagIcons = new ArrayList<JLabel>(Arrays.asList(flagTransport, flagShield, flagInvisible, flagNecromancer, flagRoyal, flagJoker));
	private PairArrayList movementArray = new PairArrayList();
	private PairArrayList attackArray = new PairArrayList();
	private PairArrayList backRangeArray = new PairArrayList(); //applies to attack tiles (consequent downgrade)
	private PairArrayList forwardRangeArray = new PairArrayList(); //applies to attack tiles (consequent upgrade)
	private PairArrayList retributionArray = new PairArrayList();
	private PairArrayList errorArray = new PairArrayList();
	private ArrayList<Boolean> flagArray = new ArrayList<Boolean>(); //t, s, i, n, r, j
	
	public CheckeredPane() {
		for (int i = 0; i < flagIcons.size(); i++) {
			this.setLayer(flagIcons.get(i), 1);
			this.add(flagIcons.get(i));
			flagIcons.get(i).setBounds((7 + (2 * i)) * TILE, 23 * TILE, TILE, TILE);
			flagIcons.get(i).setVisible(false);
		}
		this.setLayer(costDisplay, 1);
		this.add(costDisplay);
		costDisplay.setFont(new Font("Arial", Font.BOLD, 24));
		costDisplay.setBounds((int) (10.5 * TILE), TILE, 6 * TILE, TILE);
		costDisplay.setVisible(false);
	}
	
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
    	 g.setColor(error);
         for (Pair pair : (ArrayList<Pair>) errorArray) {
    		g.fillRect(pair.getA() * TILE, pair.getB() * TILE, TILE, TILE);
         }
	 }
	 
	 public void colorSquare(int creationFlag, int oldX, int oldY) {
		 int x = oldX / TILE;
		 int y = oldY / TILE;
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
				 checkForBackRangeConflict(newPair);
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
		 calculateCost();
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
		 calculateCost();
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
		 for (int j = 0; j < flagArray.size(); j++) {
			 flagArray.set(j, false);
		 }
		 for (int k = 0; k < flagIcons.size(); k++) {
			 flagIcons.get(k).setVisible(false);
		 }
		 totalCost = 0;
		 costDisplay.setVisible(false);
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
		 for (int k = 0; k < flagIcons.size(); k++) {
			 flagIcons.get(k).setVisible(flagArray.get(k));
		 }
		 calculateCost();
	 }
	 
	 public boolean checkForBackRangeConflict(Pair pair) { 
		 for (int i = 0; i < backRangeArray.size(); i++) {
			 int x = ((Pair) backRangeArray.get(i)).getA();
			 int y = ((Pair) backRangeArray.get(i)).getB();
			 if (!noConflictCheck(x, y, pair.getA(), pair.getB())) {
				 backRangeArray.remove(i);
				 return true;
			 }
		 }
		 return false;
	 }
	 
	 public void calculateCost() {
		 totalCost = 0;
		 for (int i = 0; i < movementArray.size(); i++) {
			 int x = ((Pair) movementArray.get(i)).getA();
			 int y = ((Pair) movementArray.get(i)).getB();
			 totalCost += genericMath(i, x, y);
		 }
		 for (int i = 0; i < attackArray.size(); i++) {
			 int x = ((Pair) attackArray.get(i)).getA();
			 int y = ((Pair) attackArray.get(i)).getB();
			 totalCost += genericMath(i, x, y);
		 }
		 costDisplay.setText("Cost: " + totalCost);
		 costDisplay.setVisible(true);
		 revalidate();
		 repaint();
	 }
	 
	 public boolean noConflictCheck(int x1, int y1, int x2, int y2) { //n, s, e, w, ne, nw, se, sw/ 2-1/ 3-1/ 3-2/ 4-1/ 4-3/ 5-1/ 5-2/ 5-3/ 5-4
		 if ( ((x1 - 12 == 0) && (x2 - 12 == 0) && ((y1 - 12 > 0) && (y2 - 12 > 0) || (y1 - 12 < 0) && (y2 - 12 < 0))) || //n,s
				 ((y1 - 12 == 0) && (y2 - 12 == 0) && ((x1 - 12 > 0) && (x2 - 12 > 0) || (x1 - 12 < 0) && (x2 - 12 < 0))) || //e,w
				 //((x1 == y1) && (x2 == y2) && (x1 / Math.abs(x1) == x2 / Math.abs(x2)) && (y1 / Math.abs(y1) == y2 / Math.abs(y2))) || //ne,nw,se,sw
				 ((x1 - 12 != 0 && x2 - 12 != 0 && y1 - 12 != 0 && y2 - 12 != 0) && 
						 ((double) (x1 - 12) / (double) (y1 - 12) == (double) (x2 - 12) / (double) (y2 - 12)) && 
						 ((x1 - 12) / Math.abs(x1 - 12) == (x2 - 12) / Math.abs(x2 - 12)) && ((y1 - 12) / Math.abs(y1 - 12) == (y2 - 12) / Math.abs(y2 - 12))) ) { //etc. 
			 return false;
		 }
		 return true;
	 }
	 
	 public int genericMath(int i, int x, int y) {
		 int cost = 0;
		//relative value = abs(v - 12)
		 //value list o: 100, 200, 300, 400, 500, 600, 700, 800, 900, 1000
		 if (Math.abs(x - 12) == 0) {
			 cost += (Math.abs(y - 12)) * 100;
		 }
		 else if (Math.abs(y - 12) == 0) {
			 cost += (Math.abs(x - 12)) * 100;
		 }
		 //value list d: 60, 120, 180, 240, 300, 360, 420, 480, 540, 600
		 else if (Math.abs(x - 12) == Math.abs(y - 12)) {
			 cost += (Math.abs(x - 12)) * 60;
		 }
		 //value list 2-1: 160
		 else if (Math.abs(x - 12) == 2 * Math.abs(y - 12)) {
			 cost += (Math.abs(y - 12)) * 160;
		 }
		 else if (Math.abs(y - 12) == 2 * Math.abs(x - 12)) {
			 cost += (Math.abs(x - 12)) * 160;
		 }
		 //value list 3-1: 260
		 else if (Math.abs(x - 12) == 3 * Math.abs(y - 12)) {
			 cost += (Math.abs(y - 12)) * 260;
		 }
		 else if (Math.abs(y - 12) == 3 * Math.abs(x - 12)) {
			 cost += (Math.abs(x - 12)) * 260;
		 }
		 //value list 3-2: 220
		 else if (2 * Math.abs(x - 12) == 3 * Math.abs(y - 12)) {
			 cost += (Math.abs(y - 12)) * 110;
		 }
		 else if (2 * Math.abs(y - 12) == 3 * Math.abs(x - 12)) {
			 cost += (Math.abs(x - 12)) * 110;
		 }
		 //value list 4-1: 360
		 else if (Math.abs(x - 12) == 4 * Math.abs(y - 12)) {
			 cost += (Math.abs(y - 12)) * 360;
		 }
		 else if (Math.abs(y - 12) == 4 * Math.abs(x - 12)) {
			 cost += (Math.abs(x - 12)) * 360;
		 }
		 //value list 4-3: 280
		 else if (3 * Math.abs(x - 12) == 4 * Math.abs(y - 12)) {
			 cost += (Math.abs(x - 12)) * 70;
		 }
		 else if (3 * Math.abs(y - 12) == 4 * Math.abs(x - 12)) {
			 cost += (Math.abs(y - 12)) * 70;
		 }
		 //value list 5-1: 460
		 else if (Math.abs(x - 12) == 5 * Math.abs(y - 12)) {
			 cost += (Math.abs(y - 12)) * 460;
		 }
		 else if (Math.abs(y - 12) == 5 * Math.abs(x - 12)) {
			 cost += (Math.abs(x - 12)) * 460;
		 }
		 //value list 5-2: 420
		 else if (2 * Math.abs(x - 12) == 5 * Math.abs(y - 12)) {
			 cost += (Math.abs(y - 12)) * 210;
		 }
		 else if (2 * Math.abs(y - 12) == 5 * Math.abs(x - 12)) {
			 cost += (Math.abs(x - 12)) * 210;
		 }
		 //value list 5-3: 380
		 else if (3 * Math.abs(x - 12) == 5 * Math.abs(y - 12)) {
			 cost += (Math.abs(x - 12)) * 76;
		 }
		 else if (3 * Math.abs(y - 12) == 5 * Math.abs(x - 12)) {
			 cost += (Math.abs(y - 12)) * 76;
		 }
		 //value list 5-4: 340
		 else if (4 * Math.abs(x - 12) == 5 * Math.abs(y - 12)) {
			 cost += (Math.abs(y - 12)) * 85;
		 }
		 else if (4 * Math.abs(y - 12) == 5 * Math.abs(x - 12)) {
			 cost += (Math.abs(x - 12)) * 85;
		 }
		 //value list 6-1: 560
		 else if ((Math.abs(x - 12) == 6 * Math.abs(y - 12)) || (Math.abs(y - 12) == 6 * Math.abs(x - 12))) {
			 cost += 560;
		 }
		 //value list 6-5: 400
		 else if ((5 * Math.abs(x - 12) == 6 * Math.abs(y - 12)) || (5 * Math.abs(y - 12) == 6 * Math.abs(x - 12))) {
			 cost += 400;
		 }
		 //value list 7-1: 660
		 else if ((Math.abs(x - 12) == 7 * Math.abs(y - 12)) || (Math.abs(y - 12) == 7 * Math.abs(x - 12))) {
			 cost += 660;
		 }
		 //value list 7-2: 620
		 else if ((2 * Math.abs(x - 12) == 7 * Math.abs(y - 12)) || (2 * Math.abs(y - 12) == 7 * Math.abs(x - 12))) {
			 cost += 620;
		 }
		 //value list 7-3: 580
		 else if ((3 * Math.abs(x - 12) == 7 * Math.abs(y - 12)) || (3 * Math.abs(y - 12) == 7 * Math.abs(x - 12))) {
			 cost += 580;
		 }
		 //value list 7-4: 540
		 else if ((4 * Math.abs(x - 12) == 7 * Math.abs(y - 12)) || (4 * Math.abs(y - 12) == 7 * Math.abs(x - 12))) {
			 cost += 540;
		 }
		 //value list 7-5: 500
		 else if ((5 * Math.abs(x - 12) == 7 * Math.abs(y - 12)) || (5 * Math.abs(y - 12) == 7 * Math.abs(x - 12))) {
			 cost += 500;
		 }
		 //value list 7-6: 460
		 else if ((6 * Math.abs(x - 12) == 7 * Math.abs(y - 12)) || (6 * Math.abs(y - 12) == 7 * Math.abs(x - 12))) {
			 cost += 460;
		 }
		 //value list 8-1: 760
		 else if ((Math.abs(x - 12) == 8 * Math.abs(y - 12)) || (Math.abs(y - 12) == 8 * Math.abs(x - 12))) {
			 cost += 760;
		 }
		 //value list 8-3: 680
		 else if ((3 * Math.abs(x - 12) == 8 * Math.abs(y - 12)) || (3 * Math.abs(y - 12) == 8 * Math.abs(x - 12))) {
			 cost += 680;
		 }
		 //value list 8-5: 600
		 else if ((5 * Math.abs(x - 12) == 8 * Math.abs(y - 12)) || (5 * Math.abs(y - 12) == 8 * Math.abs(x - 12))) {
			 cost += 600;
		 }
		 //value list 8-7: 520
		 else if ((7 * Math.abs(x - 12) == 8 * Math.abs(y - 12)) || (7 * Math.abs(y - 12) == 8 * Math.abs(x - 12))) {
			 cost += 520;
		 }
		 //value list 9-1: 860
		 else if ((Math.abs(x - 12) == 9 * Math.abs(y - 12)) || (Math.abs(y - 12) == 9 * Math.abs(x - 12))) {
			 cost += 860;
		 }
		 //value list 9-2: 820
		 else if ((2 * Math.abs(x - 12) == 9 * Math.abs(y - 12)) || (2 * Math.abs(y - 12) == 9 * Math.abs(x - 12))) {
			 cost += 820;
		 }
		 //value list 9-4: 740
		 else if ((4 * Math.abs(x - 12) == 9 * Math.abs(y - 12)) || (4 * Math.abs(y - 12) == 9 * Math.abs(x - 12))) {
			 cost += 740;
		 }
		 //value list 9-5: 700
		 else if ((5 * Math.abs(x - 12) == 9 * Math.abs(y - 12)) || (5 * Math.abs(y - 12) == 9 * Math.abs(x - 12))) {
			 cost += 700;
		 }
		 //value list 9-7: 620
		 else if ((7 * Math.abs(x - 12) == 9 * Math.abs(y - 12)) || (7 * Math.abs(y - 12) == 9 * Math.abs(x - 12))) {
			 cost += 620;
		 }
		 //value list 9-8: 580
		 else if ((8 * Math.abs(x - 12) == 9 * Math.abs(y - 12)) || (8 * Math.abs(y - 12) == 9 * Math.abs(x - 12))) {
			 cost += 580;
		 }
		 //value list 10-1: 960
		 else if ((Math.abs(x - 12) == 10 * Math.abs(y - 12)) || (Math.abs(y - 12) == 10 * Math.abs(x - 12))) {
			 cost += 960;
		 }
		 //value list 10-3: 880
		 else if ((3 * Math.abs(x - 12) == 10 * Math.abs(y - 12)) || (3 * Math.abs(y - 12) == 10 * Math.abs(x - 12))) {
			 cost += 880;
		 }
		 //value list 10-7: 720
		 else if ((7 * Math.abs(x - 12) == 10 * Math.abs(y - 12)) || (7 * Math.abs(y - 12) == 10 * Math.abs(x - 12))) {
			 cost += 720;
		 }
		 //value list 10-9: 640
		 else if ((9 * Math.abs(x - 12) == 10 * Math.abs(y - 12)) || (9 * Math.abs(y - 12) == 10 * Math.abs(x - 12))) {
			 cost += 640;
		 }
		 else {
			 if (!errorArray.has((Pair) movementArray.get(i))) {
				 errorArray.add((Pair) movementArray.get(i));
			 }
		 }
		 return cost;
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
