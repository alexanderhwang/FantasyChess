package com.alexhwang;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class Piece {
	private int price = 0;
	private String name = "";
	private String move = ""; //1-10; A:oF, B:oB, C:oL, D:oR, E:dFL, F:dFR, G:dBL, H:dBR; +j:j;
	//KA:2-1FLL, KB:2-1FML, KC:2-1FMR, KD:2-1FRR, KE:2-1BLL, KF:2-1BML, KG:2-1BMR, KH:2-1BRR;
	//KI:3-1FLL, KJ:3-1FML, KK:3-1FMR, KL:3-1FRR, KM:3-1BLL, KN:3-1BML, KO:3-1BMR, KP:3-1BRR;
	//KQ:3-2FLL, KR:3-2FML, KS:3-2FMR, KT:3-2FRR, KU:3-2BLL, KV:3-2BML, KW:3-2BMR, KX:3-2BRR;
	//LA:4-1; LI:4-2; LQ:4-3; MA:5-1; MI:5-2; MQ:5-3; NA:5-4; NI:6-1; NQ:6-2; OA:6-3; OI:6-4; OQ:6-5; PA:7-1; PI:7-2; PQ:7-3; QA:7-4; QI:7-5; QQ:7-6;
	//RA:8-1; RI:8-2; RQ:8-3; SA:8-4; SI:8-5; SQ:8-6; TA:8-7; TI:9-1; TQ:9-2; UA:9-3; UI:9-4; UQ:9-5; VA:9-6; VI:9-7; VQ:9-8;
	//WA:10-1; WI:10-2; WQ:10-3; XA:10-4; XI:10-5; XQ:10-6; YA:10-7; YI:10-8; YQ:10-9
	private String attack = ""; //q:-, r:+, z:{}
	private String flag = ""; //i, r
	private ArrayList<String> promotionTo = new ArrayList<String>();
	private ArrayList<String> promotionFrom = new ArrayList<String>();
	private String iconString = "";
	
	public Piece(final String id) {
		String line = findPiece(id);
		if (line.equals("NOT FOUND")) {
			price = -1;
		}
		else {
			parsePiece(line);
		}
	}

	public String findPiece(final String id) {
		final File pieces = new File("src\\resources\\InnerData\\Pieces.kg");
		String idCheck = "00000";
		String line;
		Boolean found = false;
    	try (BufferedReader bufferedReader = new BufferedReader(new FileReader(pieces))) {
    		line = bufferedReader.readLine();
    		while (!found) {
        		idCheck = line.substring(0, 7);
    			if (idCheck.equals("\t" + id + ";")) {
    				found = true;
        			return line;
    			}
    			else {
        			line = bufferedReader.readLine();
    			}
    		}
    		
			bufferedReader.close();
		} catch (final Exception e) {
			//e.printStackTrace();
		}
    	return "NOT FOUND";
	}
	
	public void parsePiece(String line) {
		String dataString;
		int progress = 0;
		Boolean done = false;

		line = line.substring(7, line.length() - 1);
		while (!done) {
			if (!line.contains(";")) {
				done = true;
			}
			else {
				dataString = line.substring(1, line.indexOf(';'));
				switch (progress) {
				case 0:
					price = Integer.parseInt(dataString);
					break;
				case 1:
					name = dataString;
					break;
				case 2:
					move = dataString;
					break;
				case 3:
					attack = dataString;
					break;
				case 4:
					flag = dataString;
					break;
				case 5:
					promotionTo = new ArrayList<String>(Arrays.asList(dataString.split("\\s*,\\s*")));
					break;
				case 6:
					promotionFrom = new ArrayList<String>(Arrays.asList(dataString.split("\\s*,\\s*")));
					break;
				case 7:
					iconString = dataString;
					break;
				}
				line = line.substring(dataString.length() + 2, line.length());
				//TODO check completion
				progress++;
			}
		}
	}
	
	public int getPrice() {
		return price;
	}
	
	public String getName() {
		return name;
	}
	
	public String getMove() {
		return move;
	}
	
	public String getAttack() {
		return attack;
	}
	
	public String getFlag() {
		return flag;
	}
	
	public ArrayList<String> getPromotionTo() {
		return promotionTo;
	}
	
	public ArrayList<String> getPromotionFrom() {
		return promotionFrom;
	}
	
	public String getIconString() {
		if (iconString.equals("")) {
			return "Pawn"; //default icon string
		}
		return iconString;
	}
}
