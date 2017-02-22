package com.alexhwang;

import java.util.ArrayList;

public class PiecePage {
	private ArrayList<Piece> pieces = new ArrayList<Piece>();

	public PiecePage(int start, int finish) { //16 for board pages
		for (int i = start; i <= finish; i++) {
			Piece newPiece = new Piece(String.format("%05d", i));
			if (newPiece.getPrice() != -1) {
				pieces.add(newPiece);
			}
		}
	}
	
	public ArrayList<Piece> getPieces() {
		return pieces;
	}
}
