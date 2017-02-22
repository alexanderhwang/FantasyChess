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
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;


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
	
	private String icon = BASE_RESOURCE_PATH + "Icons\\Pawn.png"; //TODO change
	private int menuButton = 1;
	private PiecePage piecePage;
	
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
    			menuArray3.get(i).setBounds(0, 15 + i * 40, 140, 25);
    			menuArray3.get(i).setFont(new Font("Arial", Font.BOLD, 18));
    			menuArray3.get(i).setForeground(Color.BLACK);
    		}
    		else
    		{
    			menuArray3.get(i).setBounds(0, 15 + i * 40, 140, 25);
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
    			menuArray31.get(i).setBounds(0, 15 + i * 40, 140, 25);
    			menuArray31.get(i).setFont(new Font("Arial", Font.BOLD, 18));
    			menuArray31.get(i).setForeground(Color.BLACK);
    		}
    		else
    		{
    			menuArray31.get(i).setBounds(0, 15 + i * 40, 140, 25);
    			menuArray31.get(i).setFont(new Font("Arial", Font.BOLD, 15));
    			menuArray31.get(i).setForeground(Color.GRAY);
    		}
    		menuArray31.get(i).setVisible(false);
    		menuPanel.setLayer(menuArray31.get(i), 1);
    		menuPanel.add(menuArray31.get(i));
    	}

    	for (int i = 0; i < menuArray0.size(); i++) {
    		menuArray0.get(i).addMouseListener(new MegaMouseAdapter(1 + i) {
    	    	public void mouseClicked(MouseEvent c) {
    	    		int buttonIndex = getSavedValue();
    	    		if (menuButton >= 1 && menuButton <= 4) {
    	    			if (menuButton == buttonIndex) {
    	    				menuPress();
    	    			}
    	    			else {
    	    				playSound(BASE_RESOURCE_PATH + "Sounds\\MenuMove.wav");
    	        			menuButton = buttonIndex;
    	    			}
    		   			menuSet();
    	    		}
    	    	}
    	    });
    	}

    	for (int i = 1; i < menuArray3.size(); i++) {
    		menuArray3.get(i).addMouseListener(new MegaMouseAdapter(30 + i) {
    	    	public void mouseClicked(MouseEvent c) {
    	    		int buttonIndex = getSavedValue();
    	    		if (menuButton >= 31 && menuButton <= 33) {
    	    			if (menuButton == buttonIndex) {
    	    				menuPress();
    	    			}
    	    			else {
    	    				playSound(BASE_RESOURCE_PATH + "Sounds\\MenuMove.wav");
    	        			menuButton = buttonIndex;
    	    			}
    		   			menuSet();
    	    		}
    	    	}
    	    });
    	}

    	for (int i = 1; i < menuArray31.size(); i++) {
    		menuArray31.get(i).addMouseListener(new MegaMouseAdapter(3100 + i) {
    	    	public void mouseClicked(MouseEvent c) {
    	    		int buttonIndex = getSavedValue();
    	    		if (menuButton >= 3101 && menuButton <= 3119) {
    	    			if (menuButton == buttonIndex) {
    	    				menuPress();
    	    			}
    	    			else {
    	    				playSound(BASE_RESOURCE_PATH + "Sounds\\MenuMove.wav");
    	        			menuButton = buttonIndex;
    	    			}
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
    			if (i == menuButton - 3100) {
    				menuArray31.get(i).setFont(new Font("Arial", Font.BOLD, 15));
    				menuArray31.get(i).setForeground(Color.BLACK);
    			}
    			else {
    				menuArray31.get(i).setFont(new Font("Arial", Font.PLAIN, 15));
    				menuArray31.get(i).setForeground(Color.GRAY);
    			}
    		}
    	}
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
			piecePage = new PiecePage(0, 15);
			int mIndex = 0;
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
			menuButton = 3201;
			//TODO put icon, etc.
			for (JLabel menuLabel : menuArray3) {
				menuLabel.setVisible(false);
			}
			//TODO 
			menuSet();
			break;
		case 33: //Piecemaker - Back
			playSound(BASE_RESOURCE_PATH + "Sounds\\MenuBack.wav");
			menuButton = 3;
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
			playSound(BASE_RESOURCE_PATH + "Sounds\\MenuSelect.wav");
			//choosePiece();
			break;
		case 3119: //Piecemaker - List - Back
			playSound(BASE_RESOURCE_PATH + "Sounds\\MenuBack.wav");
			menuButton = 31;
			for (JLabel menuLabel : menuArray31) {
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
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
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
