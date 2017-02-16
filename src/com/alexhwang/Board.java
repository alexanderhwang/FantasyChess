package com.alexhwang;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;


public class Board extends JFrame implements KeyListener {
	static final int TILE = 32;

	private final JFrame frame = new JFrame("FC");
	private final CheckeredPane mainPanel = new CheckeredPane();
	private final JLayeredPane menuPanel = new JLayeredPane();

	//private String icon = BASE_RESOURCE_PATH + "Objects\\Rock1.png"; //TODO change
	
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
    	//frame.setIconImage(new ImageIcon(icon).getImage());
    	frame.setSize(960,800);
    	frame.setLocation(0, 0);
    	frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    	frame.setResizable(false);
    	frame.pack();
    	frame.setVisible(true);
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
}
