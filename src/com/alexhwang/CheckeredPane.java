package com.alexhwang;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
	private String name = "";
	private String iconName = "Dummy";
	private Color dark = new Color(0, 0, 0, 32);
	private Color light = new Color(232, 232, 232, 0);
	private Color movement = new Color(0, 0, 255, 128);
	private Color attack = new Color(255, 0, 0, 128);
	private Color both = new Color(192, 0, 192, 192);
	private Color range = new Color(192, 192, 0, 192);
	private Color retribution = new Color(0, 0, 0, 128);
	private Color error = new Color(0, 255, 0, 255);
	private JLabel icon = new JLabel(new ImageIcon(BASE_RESOURCE_PATH + "Icons\\" + iconName + ".png"));
	private JLabel flagTransport = new JLabel(new ImageIcon(BASE_RESOURCE_PATH + "Icons\\FlagTransport.png"));
	private JLabel flagShield = new JLabel(new ImageIcon(BASE_RESOURCE_PATH + "Icons\\FlagShield.png"));
	private JLabel flagInvisible = new JLabel(new ImageIcon(BASE_RESOURCE_PATH + "Icons\\FlagInvisible.png"));
	private JLabel flagNecromancer = new JLabel(new ImageIcon(BASE_RESOURCE_PATH + "Icons\\FlagNecromancer.png"));
	private JLabel flagRoyal = new JLabel(new ImageIcon(BASE_RESOURCE_PATH + "Icons\\FlagRoyal.png"));
	private JLabel flagJoker = new JLabel(new ImageIcon(BASE_RESOURCE_PATH + "Icons\\FlagJoker.png"));
	private JLabel costDisplay = new JLabel("Cost: 0");
	private ArrayList<JLabel> flagIcons = new ArrayList<JLabel>(Arrays.asList(flagTransport, flagShield, flagInvisible, flagNecromancer, flagRoyal, flagJoker));
	private PairArrayList movementArray = new PairArrayList();
	private PairArrayList attackArray = new PairArrayList();
	private PairArrayList backRangeArray = new PairArrayList(); //applies to position (consequent downgrade)
	private PairArrayList forwardRangeArray = new PairArrayList(); //applies to position (consequent upgrade)
	private PairArrayList retributionArray = new PairArrayList();
	private PairArrayList errorArray = new PairArrayList();
	private ArrayList<Boolean> flagArray = new ArrayList<Boolean>(Arrays.asList(false, false, false, false, false, false)); //t, s, i, n, r, j
	private ArrayList<String> promotionFromArray = new ArrayList<String>();
	private ArrayList<String> promotionToArray = new ArrayList<String>();
	
	public CheckeredPane() {
		this.setLayer(icon, 1);
		this.add(icon);
		icon.setBounds(TILE * 12, TILE * 12, TILE, TILE);
		icon.setVisible(false);
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
				 checkForAttackConflict(newPair);
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
				 if (checkForBackRangeAllowance(newPair)) {
					 checkForBackRangeConflict(newPair);
					 backRangeArray.add(newPair);
				 }
			 }
			 break;
		 case 5: //forward range
			 if (!forwardRangeArray.has(newPair)) {
				 forwardRangeArray.add(newPair);
			 }
			 break;
		 case 6: //retribution
			 if (!retributionArray.has(newPair)) {
				 checkForRetributionConflict(newPair);
				 retributionArray.add(newPair);
			 }
			 break;
		 } 
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
		 promotionFromArray = new ArrayList<String>();
		 promotionToArray = new ArrayList<String>();
		 totalCost = 0;
		 costDisplay.setVisible(false);
		 iconName = "Dummy";
		 icon.setIcon(new ImageIcon(BASE_RESOURCE_PATH + "Icons\\" + iconName + ".png"));
		 revalidate();
		 repaint();
	 }
	 
	 public void hide() {
		 icon.setVisible(false);
	 }
	 
	 public void show() {
		 icon.setVisible(true);
	 }

	 public void setFlags() {
		 JPanel flags = new JPanel(); //t, s, i, n, r, j
		 /*if (flagArray.size() != 6) {
			 for (int i = 0; i < 6; i++) {
				 flagArray.add(false);
			 }
		 }*/
		 flags.add(new JCheckBox("Transport", flagArray.get(0)));
		 flags.add(new JCheckBox("Shield", flagArray.get(1)));
		 flags.add(new JCheckBox("Invisible", flagArray.get(2)));
		 flags.add(new JCheckBox("Necromancer", flagArray.get(3)));
		 flags.add(new JCheckBox("Royal", flagArray.get(4)));
		 flags.add(new JCheckBox("Joker", flagArray.get(5))); 
		 JOptionPane.showConfirmDialog(null, flags, "Flags", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
		 for (int j = 0; j < flagArray.size(); j++) {
			 flagArray.set(j, ((JCheckBox) flags.getComponent(j)).isSelected());
		 }
		 for (int k = 0; k < flagIcons.size(); k++) {
			 flagIcons.get(k).setVisible(flagArray.get(k));
		 }
		 calculateCost();
	 }
	 
	 public boolean checkForBackRangeAllowance(Pair pair) {
		 boolean allowed = false;
		 for (int i = 0; i < attackArray.size(); i++) {
			 int x = (-1 * (((Pair) attackArray.get(i)).getA() - 12)) + 12;
			 int y = (-1 * (((Pair) attackArray.get(i)).getB() - 12)) + 12;
			 if (!noConflictCheck(x, y, pair.getA(), pair.getB())) {
				 if (relativePositionComparison(x, y, pair.getA(), pair.getB()) == 0) {
					 return false;
				 }
				 else if (relativePositionComparison(x, y, pair.getA(), pair.getB()) == 1) {
					 allowed = true;
				 }
			 }
		 }
		 return allowed;
	 }
	 
	 public boolean checkForAttackConflict(Pair pair) { 
		 for (int i = 0; i < backRangeArray.size(); i++) {
			 int x = (-1 * (((Pair) backRangeArray.get(i)).getA() - 12)) + 12;
			 int y = (-1 * (((Pair) backRangeArray.get(i)).getB() - 12)) + 12;
			 if (!noConflictCheck(x, y, pair.getA(), pair.getB())) {
				 if (relativePositionComparison(pair.getA(), pair.getB(), x, y) != 1) {
					 backRangeArray.remove(i);
					 return true;
				 }
			 }
		 }
		 return false;
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

	 public boolean checkForRetributionConflict(Pair pair) { 
		 for (int i = 0; i < retributionArray.size(); i++) {
			 int x = ((Pair) retributionArray.get(i)).getA();
			 int y = ((Pair) retributionArray.get(i)).getB();
			 if (!noConflictCheck(x, y, pair.getA(), pair.getB())) {
				 retributionArray.remove(i);
				 return true;
			 }
		 }
		 return false;
	 }
	 
	 public boolean checkPromotionFrom(String check) {
		 return (promotionFromArray.contains(check));
	 }
	 
	 public boolean checkPromotionTo(String check) {
		 return (promotionToArray.contains(check));
	 }
	 
	 public boolean togglePromotionFrom(String id) {
		 if (promotionFromArray.contains(id)) {
			 promotionFromArray.remove(id);
			 return true;
		 }
		 promotionFromArray.add(id);
		 return false;
	 }
	 
	 public boolean togglePromotionTo(String id) {
		 if (promotionToArray.contains(id)) {
			 promotionToArray.remove(id);
			 return true;
		 }
		 promotionToArray.add(id);
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
		 for (int i = 0; i < backRangeArray.size(); i++) {
			 int x = ((Pair) backRangeArray.get(i)).getA();
			 int y = ((Pair) backRangeArray.get(i)).getB();
			 totalCost -= (int) (genericMath(i, x, y) * 0.5);
		 }
		 for (int i = 0; i < forwardRangeArray.size(); i++) {
			 int x = ((Pair) forwardRangeArray.get(i)).getA();
			 int y = ((Pair) forwardRangeArray.get(i)).getB();
			 totalCost += (int) (genericMath(i, x, y) * 1.5);
		 }
		 for (int i = 0; i < retributionArray.size(); i++) {
			 int x = ((Pair) retributionArray.get(i)).getA();
			 int y = ((Pair) retributionArray.get(i)).getB();
			 totalCost += (int) (genericMath(i, x, y) * 0.75);
		 }
		 if (flagArray.size() > 0) {
			 if (flagArray.get(0)) { //t
				 totalCost = (int) (totalCost * 1.25);
			 }
			 if (flagArray.get(1)) { //s
				 totalCost = (int) (totalCost * 1.5);
			 }
			 if (flagArray.get(2)) { //i
				 totalCost = (int) (totalCost * 2);
			 }
			 if (flagArray.get(3)) { //n
				 totalCost = (int) (totalCost * 1.4);
			 }
			 if (flagArray.get(4)) { //r
				 totalCost = (int) (totalCost * 2); //TODO royalty
			 }
			 if (flagArray.get(5)) { //j
				 totalCost = (int) (totalCost * 0.25) + 400000; //TODO
			 }
		 }
		 costDisplay.setText("Cost: " + totalCost);
		 costDisplay.setVisible(true);
		 revalidate();
		 repaint();
	 }

	 public int relativePositionComparison(int x1, int y1, int x2, int y2) {
		 if ((Math.abs(x1 - 12) == Math.abs(x2 - 12)) && (Math.abs(y1 - 12) == Math.abs(y2 -12))) {
			 return 0;
		 }
		 else if ((Math.abs(x1 - 12) >= Math.abs(x2 - 12)) && (Math.abs(y1 - 12) >= Math.abs(y2 - 12))) {
			 return 1;
		 }
		 return -1;
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
	 
	 public boolean setIcon() {
	     Object iconObject = JOptionPane.showInputDialog(this, "", "Icon", JOptionPane.PLAIN_MESSAGE, null, null, iconName);
		 if (iconObject == null || iconName.equals((String) iconObject) || ((String) iconObject).length() == 0) {
			 return false;
		 }
		 else {
			 iconName = (String) iconObject;
			 icon.setIcon(new ImageIcon(BASE_RESOURCE_PATH + "Icons\\" + iconName + ".png"));
		 }
		 return true;
	 }
	 
	 public boolean save() throws IOException {
		 File file = new File(BASE_RESOURCE_PATH + "InnerData\\Pieces.kg");
		 int line;
	     if (!file.exists()) {
	       	try {
				file.createNewFile();
				FileWriter fileWriter = new FileWriter(BASE_RESOURCE_PATH + "InnerData\\Pieces.kg");
				fileWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    LineNumberReader lineReader = new LineNumberReader(new FileReader(file));
	    lineReader.skip(Long.MAX_VALUE);
	    line = lineReader.getLineNumber();
	    lineReader.close();
	    if (line > 99999) {
	    	//TODO error
	    	return false;
	    }
	    else {
	    	Object currentFileNameObject = JOptionPane.showInputDialog(this, "", "Name", JOptionPane.PLAIN_MESSAGE, null, null, "");
			
			if (currentFileNameObject == null || ((String) currentFileNameObject).length() == 0) {
				return false;
			}
			else {
				name = (String) currentFileNameObject;
			}
		    writeFile(line);
	    }
		return true;
	 }
	 
	 public void writeFile(int line) {
			String lineString = String.format("%05d", line);
			String flagString = "";
			for (boolean flag : flagArray) {
				if (flag) {
					flagString += "1";
				}
				else {
					flagString += "0";
				}
			}
			String pFString = "";
			for (String string : promotionFromArray) {
				pFString += string;
				pFString += " ";
			}
			String pTString = "";
			for (String string : promotionToArray) {
				pTString += string;
				pTString += " ";
			}
			if (iconName.length() == 0) {
				iconName = "Dummy";
			}
			List<String> lines = Arrays.asList(
				//ID
				"\t" + lineString + ";" +
				//Cost
				"\t" + totalCost + ";" +
				//Name
				"\t" + name + ";" +
				//Move
				"\t" + movementArray.toText() + ";" +
				//Attack
				"\t" + attackArray.toText() + ";" +
				//BRange
				"\t" + backRangeArray.toText() + ";" +
				//FRange
				"\t" + forwardRangeArray.toText() + ";" +
				//Retribution
				"\t" + retributionArray.toText() + ";" +
				//Flags
				"\t" + flagString + ";" +
				//PromotionFrom
				"\t" + pFString + ";" +
				//PromotionTo
				"\t" + pTString + ";" +
				//IconString
				"\t" + iconName + ";" + "\t"
				); //TODO finish saving all things
			try {
			    Files.write(Paths.get(BASE_RESOURCE_PATH + "InnerData\\Pieces.kg"), lines, StandardOpenOption.APPEND);
			} catch (IOException e) {
			    e.printStackTrace();
			}
			//TODO revise promotion from/to
			for (String string : promotionFromArray) {
				int stringVal = Integer.parseInt(string);
				System.out.println(stringVal);
			}
			for (String string : promotionToArray) {
				int stringVal = Integer.parseInt(string);
				System.out.println(stringVal);
			}
	 }
	 
	public void readFile(int id) {
		File file = new File(BASE_RESOURCE_PATH + "InnerData\\Pieces.kg");
		String idString = String.format("%05d", id);
		String line = "      ";
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			try {
				while (!line.substring(1, 6).equals(idString)) {
					line = bufferedReader.readLine();
				}
			}
			catch (NullPointerException e){
				line = "	?????;	0;	;	12,12 ;	12,12 ;	;	;	;	000000;	;	;	Dummy;	";
			}
			
				String lineClone = line;
				int end = line.indexOf(";");
				int progress = 0;
				
				while (progress < 11) {
					
					lineClone = lineClone.substring(end + 1);
					end = lineClone.indexOf(";");
					progress++;

					int indexingValue = (end - 1) / 6;
					switch (progress) {
					case 1:
						totalCost = Integer.parseInt(lineClone.substring(1, end));
						break;
					case 2:
						name = lineClone.substring(1, end);
						break;
					case 3:
						movementArray = new PairArrayList();
						for (int d = 0; d < indexingValue; d++) {
							int a = Integer.parseInt(lineClone.substring(1, 3));
							int b = Integer.parseInt(lineClone.substring(4, 6));
							movementArray.add(new Pair(a, b));
							lineClone = lineClone.substring(6);
							end -= 6;
						}
						break;
					case 4:
						attackArray = new PairArrayList();
						for (int d = 0; d < indexingValue; d++) {
							int a = Integer.parseInt(lineClone.substring(1, 3));
							int b = Integer.parseInt(lineClone.substring(4, 6));
							attackArray.add(new Pair(a, b));
							lineClone = lineClone.substring(6);
							end -= 6;
						}
						break;
					case 5:
						backRangeArray = new PairArrayList();
						for (int d = 0; d < indexingValue; d++) {
							int a = Integer.parseInt(lineClone.substring(1, 3));
							int b = Integer.parseInt(lineClone.substring(4, 6));
							backRangeArray.add(new Pair(a, b));
							lineClone = lineClone.substring(6);
							end -= 6;
						}
						break;
					case 6:
						forwardRangeArray = new PairArrayList();
						for (int d = 0; d < indexingValue; d++) {
							int a = Integer.parseInt(lineClone.substring(1, 3));
							int b = Integer.parseInt(lineClone.substring(4, 6));
							forwardRangeArray.add(new Pair(a, b));
							lineClone = lineClone.substring(6);
							end -= 6;
						}
						break;
					case 7:
						retributionArray = new PairArrayList();
						for (int d = 0; d < indexingValue; d++) {
							int a = Integer.parseInt(lineClone.substring(1, 3));
							int b = Integer.parseInt(lineClone.substring(4, 6));
							retributionArray.add(new Pair(a, b));
							lineClone = lineClone.substring(6);
							end -= 6;
						}
						break;
					case 8:
						for (int i = 0; i < 6; i++) {
							if (lineClone.charAt(i + 1) == '1') {
								flagArray.set(i, true);
							}
							else {
								flagArray.set(i, false);
							}
						}
						break;
					case 9:
						promotionFromArray = new ArrayList<String>();
						for (int d = 0; d < indexingValue; d++) {
							promotionFromArray.add(lineClone.substring(1, 6));
							lineClone = lineClone.substring(6);
							end -= 6;
						}
						break;
					case 10:
						promotionToArray = new ArrayList<String>();
						for (int d = 0; d < indexingValue; d++) {
							promotionToArray.add(lineClone.substring(1, 6));
							lineClone = lineClone.substring(6);
							end -= 6;
						}
						break;
					case 11:
						iconName = lineClone.substring(1, end);
						break;
					}
				}
				
			bufferedReader.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int k = 0; k < flagIcons.size(); k++) {
			flagIcons.get(k).setVisible(flagArray.get(k));
		}
		icon.setIcon(new ImageIcon(BASE_RESOURCE_PATH + "Icons\\" + iconName + ".png"));
		calculateCost();
	}
}
