package com.alexhwang;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class Board extends JFrame implements KeyListener {
	static final int TILE = 32;
	static final String BASE_RESOURCE_PATH = "src\\resources\\";

	private final JFrame frame = new JFrame("FC");
	private final CheckeredPane mainPanel = new CheckeredPane();
	private final JLayeredPane menuPanel = new JLayeredPane();
	private final ArrayList<JLabel> menuArray0 = new ArrayList<JLabel>();
	private final JLabel menu01 = new JLabel("?");
	private final JLabel menu02 = new JLabel("Sandbox");
	private final JLabel menu03 = new JLabel("Piecemaker");
	private final JLabel menu04 = new JLabel("Quit");
	private final ArrayList<JLabel> menuArray3 = new ArrayList<JLabel>();
	private final JLabel menu30 = new JLabel("Piecemaker");
	private final JLabel menu31 = new JLabel("List");
	private final JLabel menu32 = new JLabel("Create");
	private final JLabel menu33 = new JLabel("Back");
	private final ArrayList<JLabel> menuArray31 = new ArrayList<JLabel>();
	private final JLabel menu3100 = new JLabel("List");
	private final JLabel menu3101 = new JLabel("Previous Page");
	private final JLabel menu3102 = new JLabel("Next Page");
	private final JLabel menu3103 = new JLabel("-\t");
	private final JLabel menu3104 = new JLabel("-\t");
	private final JLabel menu3105 = new JLabel("-\t");
	private final JLabel menu3106 = new JLabel("-\t");
	private final JLabel menu3107 = new JLabel("-\t");
	private final JLabel menu3108 = new JLabel("-\t");
	private final JLabel menu3109 = new JLabel("-\t");
	private final JLabel menu3110 = new JLabel("-\t");
	private final JLabel menu3111 = new JLabel("-\t");
	private final JLabel menu3112 = new JLabel("-\t");
	private final JLabel menu3113 = new JLabel("-\t");
	private final JLabel menu3114 = new JLabel("-\t");
	private final JLabel menu3115 = new JLabel("-\t");
	private final JLabel menu3116 = new JLabel("-\t");
	private final JLabel menu3117 = new JLabel("-\t");
	private final JLabel menu3118 = new JLabel("-\t");
	private final JLabel menu3119 = new JLabel("Back");
	private final ArrayList<JLabel> menuArray32 = new ArrayList<JLabel>();
	private final JLabel menu3200 = new JLabel("Create");
	private final JLabel menu3201 = new JLabel("Base");
	private final JLabel menu3202 = new JLabel("Clear");
	private final JLabel menu3203 = new JLabel("Set Movement");
	private final JLabel menu3204 = new JLabel("Set Attack");
	private final JLabel menu3205 = new JLabel("Set Both");
	private final JLabel menu3206 = new JLabel("Set Back Range");
	private final JLabel menu3207 = new JLabel("Set Forward Range");
	private final JLabel menu3208 = new JLabel("Set Retribution");
	private final JLabel menu3209 = new JLabel("Flags");
	private final JLabel menu3210 = new JLabel("Promotion From");
	private final JLabel menu3211 = new JLabel("Promotion To");
	private final JLabel menu3212 = new JLabel("Icon");
	private final JLabel menu3213 = new JLabel("Save");
	private final JLabel menu3214 = new JLabel("Back");
	private final ArrayList<JLabel> menuArray32a = new ArrayList<JLabel>();
	private final JLabel menu3200a = new JLabel("Edit");
	private final JLabel menu3201a = new JLabel("Base");
	private final JLabel menu3202a = new JLabel("Clear");
	private final JLabel menu3203a = new JLabel("Set Movement");
	private final JLabel menu3204a = new JLabel("Set Attack");
	private final JLabel menu3205a = new JLabel("Set Both");
	private final JLabel menu3206a = new JLabel("Set Back Range");
	private final JLabel menu3207a = new JLabel("Set Forward Range");
	private final JLabel menu3208a = new JLabel("Set Retribution");
	private final JLabel menu3209a = new JLabel("Flags");
	private final JLabel menu3210a = new JLabel("Promotion From");
	private final JLabel menu3211a = new JLabel("Promotion To");
	private final JLabel menu3212a = new JLabel("Icon");
	private final JLabel menu3213a = new JLabel("Save");
	private final JLabel menu3214a = new JLabel("Back");
	
	private ArrayList<Piece> pieceArray = new ArrayList<Piece>();
	private String icon = BASE_RESOURCE_PATH + "Icons\\Pawn.png"; //TODO change
	private int menuButton = 1;
	private int menuTop = 1;
	private int menuBottom = 4;
	private PiecePage piecePage;
	private int pieceListPage = 0;
	private int pieceArraySize = 0;
	private int mIndex = 0;
	private boolean creator = true;
	private int menuFlag = 0; //1: Piecemaker - Create - Base, 2: Piecemaker - Create - PromotionFrom, 3: Piecemaker - Create - PromotionTo
	private int creationFlag = 0; //1: Movement, 2: Attack, 3: Both, 4: Back Range, 5: Forward Range, 6: Retribution
	
    public Board() {
    	
    	menuPanel.setPreferredSize(new Dimension(160, 400));
    	menuPanel.setLayout(null);
    	menuPanel.setBackground(new Color(224, 224, 232));
    	menuPanel.setOpaque(true);
    	
    	mainPanel.setPreferredSize(new Dimension(800, 800));
    	mainPanel.setLayout(null);
    	mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
    	mainPanel.setBackground(Color.WHITE);
    	mainPanel.setOpaque(true);

    	menuArray0.addAll(Arrays.asList(menu01, menu02, menu03, menu04));
    	for (int i = 0; i < menuArray0.size(); i++) {
    		menuArray0.get(i).setBounds(20, 20 + i * 30, 140, 25);
    		menuArray0.get(i).setFont(new Font("Arial", Font.PLAIN, 18));
    		menuArray0.get(i).setForeground(Color.GRAY);
    		menuArray0.get(i).setVisible(true);
    		menuPanel.setLayer(menuArray0.get(i), 1);
    		menuPanel.add(menuArray0.get(i));
    	}
    	menuArray3.addAll(Arrays.asList(menu30, menu31, menu32, menu33));
    	for (int i = 0; i < menuArray3.size(); i++) {
    		if (i == 0) {
    			menuArray3.get(i).setBounds(10, 15 + i * 40, 140, 25);
    			menuArray3.get(i).setFont(new Font("Arial", Font.BOLD, 18));
    			menuArray3.get(i).setForeground(Color.BLACK);
    		}
    		else
    		{
    			menuArray3.get(i).setBounds(10, 15 + i * 40, 140, 25);
    			menuArray3.get(i).setFont(new Font("Arial", Font.BOLD, 15));
    			menuArray3.get(i).setForeground(Color.GRAY);
    		}
    		menuArray3.get(i).setVisible(false);
    		menuPanel.setLayer(menuArray3.get(i), 1);
    		menuPanel.add(menuArray3.get(i));
    	}
    	menuArray31.addAll(Arrays.asList(menu3100, menu3101, menu3102, menu3103, menu3104, menu3105, menu3106, menu3107, menu3108, menu3109, 
    			menu3110, menu3111, menu3112, menu3113, menu3114, menu3115, menu3116, menu3117, menu3118, menu3119));
    	for (int i = 0; i < menuArray31.size(); i++) {
    		if (i == 0) {
    			menuArray31.get(i).setBounds(10, 15 + i * 40, 140, 25);
    			menuArray31.get(i).setFont(new Font("Arial", Font.BOLD, 18));
    			menuArray31.get(i).setForeground(Color.BLACK);
    		}
    		else
    		{
    			menuArray31.get(i).setBounds(10, 15 + i * 40, 140, 25);
    			menuArray31.get(i).setFont(new Font("Arial", Font.BOLD, 15));
    			menuArray31.get(i).setForeground(Color.GRAY);
    		}
    		menuArray31.get(i).setVisible(false);
    		menuPanel.setLayer(menuArray31.get(i), 1);
    		menuPanel.add(menuArray31.get(i));
    	}
    	menuArray32.addAll(Arrays.asList(menu3200, menu3201, menu3202, menu3203, menu3204, menu3205, menu3206, menu3207, menu3208, menu3209, 
    			menu3210, menu3211, menu3212, menu3213, menu3214));
    	for (int i = 0; i < menuArray32.size(); i++) {
    		if (i == 0) {
    			menuArray32.get(i).setBounds(10, 15 + i * 40, 140, 25);
    			menuArray32.get(i).setFont(new Font("Arial", Font.BOLD, 18));
    			menuArray32.get(i).setForeground(Color.BLACK);
    		}
    		else
    		{
    			menuArray32.get(i).setBounds(10, 15 + i * 40, 140, 25);
    			menuArray32.get(i).setFont(new Font("Arial", Font.BOLD, 15));
    			menuArray32.get(i).setForeground(Color.GRAY);
    		}
    		menuArray32.get(i).setVisible(false);
    		menuPanel.setLayer(menuArray32.get(i), 1);
    		menuPanel.add(menuArray32.get(i));
    	}
    	menuArray32a.addAll(Arrays.asList(menu3200a, menu3201a, menu3202a, menu3203a, menu3204a, menu3205a, menu3206a, menu3207a, menu3208a, menu3209a, 
    			menu3210a, menu3211a, menu3212a, menu3213a, menu3214a));
    	for (int i = 0; i < menuArray32a.size(); i++) {
    		if (i == 0) {
    			menuArray32a.get(i).setBounds(10, 15 + i * 40, 140, 25);
    			menuArray32a.get(i).setFont(new Font("Arial", Font.BOLD, 18));
    			menuArray32a.get(i).setForeground(Color.BLACK);
    		}
    		else
    		{
    			menuArray32a.get(i).setBounds(10, 15 + i * 40, 140, 25);
    			menuArray32a.get(i).setFont(new Font("Arial", Font.BOLD, 15));
    			menuArray32a.get(i).setForeground(Color.GRAY);
    		}
    		menuArray32a.get(i).setVisible(false);
    		menuPanel.setLayer(menuArray32a.get(i), 1);
    		menuPanel.add(menuArray32a.get(i));
    	}
    	
    	mainPanel.addMouseListener(new MegaMouseAdapter(mainPanel) {
    		/*public void mouseClicked(MouseEvent c) {
    			if (creationFlag != 0) {
        			playSound(BASE_RESOURCE_PATH + "Sounds\\MenuMove.wav");
        			int mouseX = c.getX();
        			int mouseY = c.getY();
        			mainPanel.colorSquare(creationFlag, mouseX, mouseY);
    			}
    		}*/
    		
    		public void mousePressed(MouseEvent c) { //The Board
    			//TODO generalize
    			if (creationFlag != 0) { //Piecemaker
        			int mouseX = c.getX();
        			int mouseY = c.getY();
        			/*if (menuFlag == 0 && menuButton >= 3103 && menuButton <= 3118 && mouseX / 32 == 12 && mouseY / 32 == 12 && mainPanel.checkValidity()) {
        				System.out.println("Check");
        				creator = false;
        				menuButton = 32;
        				menuPress();
        				//TODO enforce editing on center click?
        			}*/
        			if (mouseX > 2 * TILE && mouseX < 23 * TILE && mouseY > 2 * TILE && mouseY < 23 * TILE && 
        					!(mouseX / 32 == 12 && mouseY / 32 == 12)) {
             			if (c.getButton() == MouseEvent.BUTTON1) {
                			playSound(BASE_RESOURCE_PATH + "Sounds\\MenuMove.wav");
                			mainPanel.colorSquare(creationFlag, mouseX, mouseY);
            			}
            			else if (c.getButton() == MouseEvent.BUTTON3) {
                			playSound(BASE_RESOURCE_PATH + "Sounds\\MenuBack.wav");
                			mainPanel.clearSquare(creationFlag, mouseX, mouseY);
            			}
        			}
    			}
    		}
    	});

    	for (int i = 0; i < menuArray0.size(); i++) {
    		menuArray0.get(i).addMouseListener(new MegaMouseAdapter(1 + i) {
    	    	public void mousePressed(MouseEvent c) {
    	    		int buttonIndex = getSavedValue();
    	    		if (menuButton >= 1 && menuButton <= 4) {
    	    			/*if (menuButton == buttonIndex) {
    	    				menuPress();
    	    			}
    	    			else {
    	    				playSound(BASE_RESOURCE_PATH + "Sounds\\MenuMove.wav");
    	        			menuButton = buttonIndex;
    	    			}*/
    	    			menuButton = buttonIndex;
    	    			menuPress();
    		   			menuSet();
    	    		}
    	    	}
    	    });
    	}

    	for (int i = 1; i < menuArray3.size(); i++) {
    		menuArray3.get(i).addMouseListener(new MegaMouseAdapter(30 + i) {
    	    	public void mousePressed(MouseEvent c) {
    	    		int buttonIndex = getSavedValue();
    	    		if (menuButton >= 31 && menuButton <= 33) {
    	    			/*if (menuButton == buttonIndex) {
    	    				menuPress();
    	    			}
    	    			else {
    	    				playSound(BASE_RESOURCE_PATH + "Sounds\\MenuMove.wav");
    	        			menuButton = buttonIndex;
    	    			}*/
    	    			menuButton = buttonIndex;
    	    			menuPress();
    		   			menuSet();
    	    		}
    	    	}
    	    });
    	}

    	for (int i = 1; i < menuArray31.size(); i++) {
    		menuArray31.get(i).addMouseListener(new MegaMouseAdapter(3100 + i) {
    	    	public void mousePressed(MouseEvent c) {
    	    		int buttonIndex = getSavedValue();
    	    		if (menuButton >= 3101 && menuButton <= 3119) {
    	    			if (menuButton == buttonIndex) {
    	    				menuPress();
    	    			}
    	    			else {
		        			menuButton = buttonIndex;
    	    	    		if (menuButton == 3101 || menuButton == 3102 || menuButton == 3119) {
    		    				menuPress();
    	    	    		}
    	    	    		else {
        	    				playSound(BASE_RESOURCE_PATH + "Sounds\\MenuMove.wav");
        	        			mainPanel.readFile((menuButton - 3103) + (pieceListPage * 16));
        	        			mainPanel.show();
    	    	    		}
    	    			}
    	    			/*menuButton = buttonIndex;
    	    			menuPress();*/
    		   			menuSet();
    	    		}
    	    	}
    	    });
    	}

    	for (int i = 1; i < menuArray32.size(); i++) {
    		menuArray32.get(i).addMouseListener(new MegaMouseAdapter(3200 + i) {
    	    	public void mousePressed(MouseEvent c) {
    	    		int buttonIndex = getSavedValue();
    	    		if (menuButton >= 3201 && menuButton <= 3214 && creator) {
    	    			/*if (menuButton == buttonIndex) {
    	    				menuPress();
    	    			}
    	    			else {
    	    				playSound(BASE_RESOURCE_PATH + "Sounds\\MenuMove.wav");
    	        			menuButton = buttonIndex;
    	    			}*/
    	    			menuButton = buttonIndex;
    	    			menuPress();
    		   			menuSet();
    	    		}
    	    	}
    	    });
    	}

    	for (int i = 1; i < menuArray32a.size(); i++) {
    		menuArray32a.get(i).addMouseListener(new MegaMouseAdapter(3200 + i) {
    	    	public void mousePressed(MouseEvent c) {
    	    		int buttonIndex = getSavedValue();
    	    		if (menuButton >= 3201 && menuButton <= 3214 && !creator) {
    	    			/*if (menuButton == buttonIndex) {
    	    				menuPress();
    	    			}
    	    			else {
    	    				playSound(BASE_RESOURCE_PATH + "Sounds\\MenuMove.wav");
    	        			menuButton = buttonIndex;
    	    			}*/
    	    			menuButton = buttonIndex;
    	    			menuPress();
    		   			menuSet();
    	    		}
    	    	}
    	    });
    	}
    	
    	frame.addWindowListener(new java.awt.event.WindowAdapter() {
    	    @Override
    	    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
    			/*if (autosave && !going && !paused && !superPaused) {
    				saveGame();
    				System.exit(0);
    			}
    			else if (autosave) {
					Object[] choice = {"Go back", "Quit anyway"};
					int n = JOptionPane.showOptionDialog(frame, "File has not been saved. \nAutosave requires no running threads.", "Exit warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, choice, choice[0]);
					if (n == JOptionPane.NO_OPTION) {
						System.exit(0);
					}
					else {
    					playSound(BASE_RESOURCE_PATH + "Sounds\\MenuBack.wav");
					}
    			}
    			else if (!saved) {
					Object[] choice = {"Go back", "Quit anyway"};
					int n = JOptionPane.showOptionDialog(frame, "File has not been saved.", "Exit warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, choice, choice[0]);
					if (n == JOptionPane.NO_OPTION) {
						System.exit(0);
					}
					else {
    					playSound(BASE_RESOURCE_PATH + "Sounds\\MenuBack.wav");
					}
				}
				else {*/
					System.exit(0);
				//}
    	    }
    	});
    	
    	frame.addKeyListener(this);
    	frame.add(mainPanel, BorderLayout.WEST);
    	frame.add(menuPanel, BorderLayout.EAST);
    	frame.setIconImage(new ImageIcon(icon).getImage());
    	frame.setSize(960,800);
    	frame.setLocation(0, 0);
    	frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    	frame.setResizable(false);
    	frame.pack();
    	frame.setVisible(true);
     }
    
    public void menuSet() {
    	if (menuButton >= 1 && menuButton <= 4) {
    		for (int i = 0; i < menuArray0.size(); i++) {
    			if (i == menuButton - 1) {
    				menuArray0.get(i).setFont(new Font("Arial", Font.BOLD, 18));
    				menuArray0.get(i).setForeground(Color.BLACK);
    			}
    			else {
    				menuArray0.get(i).setFont(new Font("Arial", Font.PLAIN, 18));
    				menuArray0.get(i).setForeground(Color.GRAY);
    			}
    		}
    	}
    	else if (menuButton >= 31 && menuButton <= 33) {
    		for (int i = 1; i < menuArray3.size(); i++) {
    			if (i == menuButton - 30) {
    				menuArray3.get(i).setFont(new Font("Arial", Font.BOLD, 15));
    				menuArray3.get(i).setForeground(Color.BLACK);
    			}
    			else {
    				menuArray3.get(i).setFont(new Font("Arial", Font.PLAIN, 15));
    				menuArray3.get(i).setForeground(Color.GRAY);
    			}
    		}
    	}
    	else if (menuButton >= 3101 && menuButton <= 3119) { 
    		for (int i = 1; i < menuArray31.size(); i++) {
    			int fileId = (i - 3) + (pieceListPage * 16);
    			String idString = String.format("%05d", fileId);
    			//System.out.println(idString);
    			
    			if (i == menuButton - 3100) {
    				menuArray31.get(i).setFont(new Font("Arial", Font.BOLD, 15));
    				menuArray31.get(i).setForeground(Color.BLACK);
    			}
    			else {
    				menuArray31.get(i).setFont(new Font("Arial", Font.PLAIN, 15));
    				menuArray31.get(i).setForeground(Color.GRAY);
    			}
    			if (i > 2 && i <= 18) {
        			if (menuFlag == 2 && mainPanel.checkPromotionFrom(idString)) { //PromotionFrom
            			menuArray31.get(i).setForeground(new Color(0, 128, 255));
            		}
            		if (menuFlag == 3 && mainPanel.checkPromotionTo(idString)) { //PromotionTo
            			menuArray31.get(i).setForeground(new Color(255, 0, 128));
            		}
    			}
    		}
    	}
    	else if (menuButton >= 3201 && menuButton <= 3214) {
    		if (creator) {
        		for (int i = 1; i < menuArray32.size(); i++) {
        			if (i == menuButton - 3200) {
        				menuArray32.get(i).setFont(new Font("Arial", Font.BOLD, 15));
        				menuArray32.get(i).setForeground(Color.BLACK);
        			}
        			else {
        				menuArray32.get(i).setFont(new Font("Arial", Font.PLAIN, 15));
        				menuArray32.get(i).setForeground(Color.GRAY);
        			}
        			if (creationFlag != 0 && i == creationFlag + 2) {
        				menuArray32.get(i).setForeground(new Color(0, 128, 64));
        			}
        		}
    		}
    		else {
        		for (int i = 1; i < menuArray32a.size(); i++) {
        			if (i == menuButton - 3200) {
        				menuArray32a.get(i).setFont(new Font("Arial", Font.BOLD, 15));
        				menuArray32a.get(i).setForeground(Color.BLACK);
        			}
        			else {
        				menuArray32a.get(i).setFont(new Font("Arial", Font.PLAIN, 15));
        				menuArray32a.get(i).setForeground(Color.GRAY);
        			}
        			if (creationFlag != 0 && i == creationFlag + 2) {
        				menuArray32a.get(i).setForeground(new Color(0, 128, 64));
        			}
        		}
    		}
    	}
    }
    
    public void menuMove(String direction)
    {
		playSound(BASE_RESOURCE_PATH + "Sounds\\MenuMove.wav");
    	if (direction.equals("UP")) {
    		if (menuButton > menuTop && menuButton <= menuBottom) {
    			menuButton--;
    		}
    		else if (menuButton == menuTop) {
    			menuButton = menuBottom;
    		}
    	}
    	else {
    		if (menuButton < menuBottom && menuButton >= menuTop) {
    			menuButton++;
    		}
    		else if (menuButton == menuBottom) {
    			menuButton = menuTop;
    		}
    	}
    	if (menuFlag == 0 && menuButton >= 3103 && menuButton <= 3118) {
    		//menuPress();
			mainPanel.readFile((menuButton - 3103) + (pieceListPage * 16));
			mainPanel.show();
    	}
    	menuSet();
    }
    
    public void menuPress() {
    	switch (menuButton) {
		case 1: //?
			playSound(BASE_RESOURCE_PATH + "Sounds\\MenuSelect.wav");
			//TODO
			menuSet();
			break;
		case 2: //Sandbox
			playSound(BASE_RESOURCE_PATH + "Sounds\\MenuSelect.wav");
			//TODO 
			menuSet();
			break;
		case 3: //Piecemaker
			playSound(BASE_RESOURCE_PATH + "Sounds\\MenuSelect.wav");
			menuButton = 33;
			menuTop = 31;
			menuBottom = 33;
			for (JLabel menuLabel : menuArray3) {
				menuLabel.setVisible(true);
			}
			for (JLabel menuLabel : menuArray0) {
				menuLabel.setVisible(false);
			}
			menuSet();
			break;
		case 4: //Quit
			playSound(BASE_RESOURCE_PATH + "Sounds\\MenuSelect.wav");
			/*if (autosave && !going && !paused && !superPaused) {
				saveGame();
				System.exit(0);
			}
			else if (autosave) {
				Object[] choice = {"Go back", "Quit anyway"};
				int n = JOptionPane.showOptionDialog(frame, "File has not been saved. \nAutosave requires no running threads.", "Exit warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, choice, choice[0]);
				if (n == JOptionPane.NO_OPTION) {
					System.exit(0);
				}
			}
			else if (!saved) {
				Object[] choice = {"Go back", "Quit anyway"};
				int n = JOptionPane.showOptionDialog(frame, "File has not been saved.", "Exit warning", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, choice, choice[0]);
				if (n == JOptionPane.NO_OPTION) {
					System.exit(0);
				}
			}
			else {*/
				System.exit(0);
			//}
			break;
		case 31: //Piecemaker - List
			playSound(BASE_RESOURCE_PATH + "Sounds\\MenuSelect.wav");
			menuButton = 3119;
			menuTop = 3101;
			menuBottom = 3119;
			piecePage = new PiecePage(0, 15);
			mIndex = 0;
			for (JLabel menuLabel : menuArray31) {
				if (mIndex >= 3 && piecePage.getPieces().size() > mIndex - 3) {
					Piece newPiece = piecePage.getPieces().get(mIndex - 3);
					String newName = newPiece.getName();
					menuLabel.setText("- " + newName);
				}
				mIndex++;
				menuLabel.setVisible(true);
			}
			for (JLabel menuLabel : menuArray3) {
				menuLabel.setVisible(false);
			}
			menuSet();
			break;
		case 32: //Piecemaker - Create
			playSound(BASE_RESOURCE_PATH + "Sounds\\MenuSelect.wav");
			menuButton = 3214;
			menuTop = 3201;
			menuBottom = 3214;
			if (creator) {
				for (JLabel menuLabel : menuArray32) {
					menuLabel.setVisible(true);
				}
				pieceArraySize = pieceArray.size();
				pieceArray.add(new Piece("00000"));
				mainPanel.show();
				creationFlag = 3;
				mainPanel.colorSquare(3, TILE * 12, TILE * 12);
				creationFlag = 0;
				mainPanel.setCreator(true);
			}
			else {
				for (JLabel menuLabel : menuArray32a) {
					menuLabel.setVisible(true);
				}
				mainPanel.setCreator(false);
			}
			for (JLabel menuLabel : menuArray3) {
				menuLabel.setVisible(false);
			}
			menuSet();
			break;
		case 33: //Piecemaker - Back
			playSound(BASE_RESOURCE_PATH + "Sounds\\MenuBack.wav");
			menuButton = 3;
			menuTop = 1;
			menuBottom = 4;
			for (JLabel menuLabel : menuArray3) {
				menuLabel.setVisible(false);
			}
			for (JLabel menuLabel : menuArray0) {
				menuLabel.setVisible(true);
			}
			menuSet();
			break;
		case 3101: //Piecemaker - List - Previous Page
			playSound(BASE_RESOURCE_PATH + "Sounds\\MenuSelect.wav");
			//TODO 
			menuSet();
			break;
		case 3102: //Piecemaker - List - Next Page
			playSound(BASE_RESOURCE_PATH + "Sounds\\MenuSelect.wav");
			//TODO 
			menuSet();
			break;
		case 3103: //Piecemaker - List - Choose Piece
		case 3104:
		case 3105:
		case 3106:
		case 3107:
		case 3108:
		case 3109:
		case 3110:
		case 3111:
		case 3112:
		case 3113:
		case 3114:
		case 3115:
		case 3116:
		case 3117:
		case 3118:
			int fileId = (menuButton - 3103) + (pieceListPage * 16);
			if (menuFlag == 1) { //Piecemaker - Create - Base
				playSound(BASE_RESOURCE_PATH + "Sounds\\MenuSelect.wav");
				menuButton = 3201;
				menuTop = 3201;
				menuBottom = 3214;
				mainPanel.readFile(fileId);
				for (JLabel menuLabel : menuArray31) {
					menuLabel.setVisible(false);
				}
				for (JLabel menuLabel : menuArray32) {
					menuLabel.setVisible(true);
				}
				menuFlag = 0;
			}
			else if (menuFlag == 2) { //Piecemaker - Create - PromotionFrom
    			String idString = String.format("%05d", fileId);
				if (mainPanel.togglePromotionFrom(idString)) {
					playSound(BASE_RESOURCE_PATH + "Sounds\\MenuBack.wav");
				}
				else {
					playSound(BASE_RESOURCE_PATH + "Sounds\\MenuSelect.wav");
				}
			}
			else if (menuFlag == 3) { //Piecemaker - Create - PromotionTo
    			String idString = String.format("%05d", fileId);
				if (mainPanel.togglePromotionTo(idString)) {
					playSound(BASE_RESOURCE_PATH + "Sounds\\MenuBack.wav");
				}
				else {
					playSound(BASE_RESOURCE_PATH + "Sounds\\MenuSelect.wav");
				}
			}
			else {
				/*mainPanel.readFile(fileId);
				mainPanel.show();*/
				//TODO change to edit
			}
			menuSet();
			break;
		case 3119: //Piecemaker - List - Back
			playSound(BASE_RESOURCE_PATH + "Sounds\\MenuBack.wav");
			if (menuFlag >= 1 && menuFlag <= 3) {
				menuButton = 3201;
				if (menuFlag > 1) {
					menuButton = 3208 + menuFlag;
				}
				menuTop = 3201;
				menuBottom = 3214;
				for (JLabel menuLabel : menuArray32) {
					menuLabel.setVisible(true);
				}
				menuFlag = 0;
			}
			else {
				menuButton = 31;
				menuTop = 31;
				menuBottom = 33;
				mainPanel.clear();
				mainPanel.hide();
		    	mainPanel.repaint();
				for (JLabel menuLabel : menuArray3) {
					menuLabel.setVisible(true);
				}
			}
			for (JLabel menuLabel : menuArray31) {
				menuLabel.setVisible(false);
			}
			menuSet();
			break;
		case 3201: //Piecemaker - Create - Base
			playSound(BASE_RESOURCE_PATH + "Sounds\\MenuSelect.wav");
			menuFlag = 1;
			menuButton = 3119;
			menuTop = 3101;
			menuBottom = 3119;
			piecePage = new PiecePage(0, 15);
			mIndex = 0;
			for (JLabel menuLabel : menuArray31) {
				if (mIndex >= 3 && piecePage.getPieces().size() > mIndex - 3) {
					Piece newPiece = piecePage.getPieces().get(mIndex - 3);
					String newName = newPiece.getName();
					menuLabel.setText("- " + newName);
				} 
				mIndex++;
				menuLabel.setVisible(true);
			}
			for (JLabel menuLabel : menuArray32) {
				menuLabel.setVisible(false);
			}
			menuSet();
			break;
		case 3202: //Piecemaker - Create - Clear
			Object[] choice = {"Clear", "Don't clear"};
			int n = JOptionPane.showOptionDialog(frame, "Are you sure you want to clear?", "Clear", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, choice, choice[1]);
			if (n == JOptionPane.NO_OPTION) {
				playSound(BASE_RESOURCE_PATH + "Sounds\\MenuBack.wav");
			}
			else {
				playSound(BASE_RESOURCE_PATH + "Sounds\\Error.wav");
				mainPanel.clear();
				creationFlag = 3;
				mainPanel.colorSquare(3, TILE * 12, TILE * 12);
				creationFlag = 0;
			}
			break;
		case 3203: //Piecemaker - Create - Set Movement
			playSound(BASE_RESOURCE_PATH + "Sounds\\MenuSelect.wav");
			creationFlag = 1; //blue fill
			break;
		case 3204: //Piecemaker - Create - Set Attack
			playSound(BASE_RESOURCE_PATH + "Sounds\\MenuSelect.wav");
			creationFlag = 2; //red fill
			break;
		case 3205: //Piecemaker - Create - Set Both
			playSound(BASE_RESOURCE_PATH + "Sounds\\MenuSelect.wav");
			creationFlag = 3; //magenta fill
			break;
		case 3206: //Piecemaker - Create - Set Back Range
			playSound(BASE_RESOURCE_PATH + "Sounds\\MenuSelect.wav");
			creationFlag = 4; //yellow square
			break;
		case 3207: //Piecemaker - Create - Set Forward Range
			playSound(BASE_RESOURCE_PATH + "Sounds\\MenuSelect.wav");
			creationFlag = 5; //yellow cross
			break;
		case 3208: //Piecemaker - Create - Set Retribution
			playSound(BASE_RESOURCE_PATH + "Sounds\\MenuSelect.wav");
			creationFlag = 6; //black x
			break;
		case 3209: //Piecemaker - Create - Flags
			playSound(BASE_RESOURCE_PATH + "Sounds\\MenuSelect.wav");
			mainPanel.setFlags();
			playSound(BASE_RESOURCE_PATH + "Sounds\\MenuSelect.wav");
			break;
		case 3210: //Piecemaker - Create - Promotion From
			playSound(BASE_RESOURCE_PATH + "Sounds\\MenuSelect.wav");
			menuFlag = 2;
			menuButton = 3119;
			menuTop = 3101;
			menuBottom = 3119;
			piecePage = new PiecePage(0, 15);
			mIndex = 0;
			for (JLabel menuLabel : menuArray31) {
				if (mIndex >= 3 && piecePage.getPieces().size() > mIndex - 3) {
					Piece newPiece = piecePage.getPieces().get(mIndex - 3);
					String newName = newPiece.getName();
					menuLabel.setText("- " + newName);
				}
				mIndex++;
				menuLabel.setVisible(true);
			}
			for (JLabel menuLabel : menuArray32) {
				menuLabel.setVisible(false);
			}
			menuSet();
			break;
		case 3211: //Piecemaker - Create - Promotion To
			playSound(BASE_RESOURCE_PATH + "Sounds\\MenuSelect.wav");
			menuFlag = 3;
			menuButton = 3119;
			menuTop = 3101;
			menuBottom = 3119;
			piecePage = new PiecePage(0, 15);
			mIndex = 0;
			for (JLabel menuLabel : menuArray31) {
				if (mIndex >= 3 && piecePage.getPieces().size() > mIndex - 3) {
					Piece newPiece = piecePage.getPieces().get(mIndex - 3);
					String newName = newPiece.getName();
					menuLabel.setText("- " + newName);
				}
				mIndex++;
				menuLabel.setVisible(true);
			}
			for (JLabel menuLabel : menuArray32) {
				menuLabel.setVisible(false);
			}
			menuSet();
			break;
		case 3212: //Piecemaker - Create - Icon
			playSound(BASE_RESOURCE_PATH + "Sounds\\MenuSelect.wav");
			if (mainPanel.setIcon()) {
				playSound(BASE_RESOURCE_PATH + "Sounds\\MenuSelect.wav");
			}
			else {
				playSound(BASE_RESOURCE_PATH + "Sounds\\MenuBack.wav");
			}
			break;
		case 3213: //Piecemaker - Create - Save
			//TODO check for editing
			playSound(BASE_RESOURCE_PATH + "Sounds\\MenuSelect.wav");
			try {
				if (mainPanel.save()) {
					
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			//TODO
			break;
		case 3214: //Piecemaker - Create - Back
			//TODO ask to save, clear
			playSound(BASE_RESOURCE_PATH + "Sounds\\MenuBack.wav");
			mainPanel.clear();
			creationFlag = 0;
			menuButton = 32;
			menuTop = 31;
			menuBottom = 33;
			pieceArraySize = pieceArray.size() - 1;
			mainPanel.hide();
	    	mainPanel.repaint();
			pieceArray.remove(pieceArraySize);
			for (JLabel menuLabel : menuArray32) {
				menuLabel.setVisible(false);
			}
			for (JLabel menuLabel : menuArray3) {
				menuLabel.setVisible(true);
			}
			menuSet();
			break;
    	}
    }

	@Override
	public void keyPressed(KeyEvent k) {
		if (menuButton >= 0) {
			//TODO check if any other conditions apply
			if (k.getKeyCode() == KeyEvent.VK_UP || k.getKeyCode() == KeyEvent.VK_NUMPAD8 || k.getKeyCode() == KeyEvent.VK_W) {
				menuMove("UP"); 
			}
			else if (k.getKeyCode() == KeyEvent.VK_DOWN || k.getKeyCode() == KeyEvent.VK_NUMPAD2 || k.getKeyCode() == KeyEvent.VK_S) {
				menuMove("DOWN");
			}
			else if (k.getKeyCode() == KeyEvent.VK_LEFT || k.getKeyCode() == KeyEvent.VK_NUMPAD4 || k.getKeyCode() == KeyEvent.VK_A) {
				menuMove("UP");
			}
			else if (k.getKeyCode() == KeyEvent.VK_RIGHT || k.getKeyCode() == KeyEvent.VK_NUMPAD6 || k.getKeyCode() == KeyEvent.VK_D) {
				menuMove("DOWN"); 
			}
			else if (k.getKeyCode() == KeyEvent.VK_SPACE || k.getKeyCode() == KeyEvent.VK_ENTER) {
				menuPress(); 
				menuSet();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent k) {
	}
	
	@Override
	public void keyReleased(KeyEvent k) {
	}
	
	public void playSound(String filepath) {
		try {
			SoundEffect.play(filepath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void playMusic(String filepath) {
		try {
			Music.play(filepath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void pauseMusic() {
		try {
			Music.pause();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void resumeMusic() {
		try {
			Music.resume();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void musicVolume(float value) {
		try {
			Music.changeVolume(value);;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
