package com.alexhwang;

import java.awt.event.MouseAdapter;

public class MegaMouseAdapter extends MouseAdapter{
	private int savedValue;
	private CheckeredPane savedPane;

	public MegaMouseAdapter(final int savedValue) {
		this.savedValue = savedValue;
	}
	
	public MegaMouseAdapter(final CheckeredPane savedPane) {
		this.savedPane = savedPane;
	}
	
	public int getSavedValue() {
		return savedValue;
	}
	
	public CheckeredPane getSavedPane() {
		return savedPane;
	}
}
