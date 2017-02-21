package com.alexhwang;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class PieceList {
	
	public void loadPieceList(int page) { //16 per page
		final File pieces = new File("src\\resources\\InnerData\\Pieces.kg");
		String idCheck = "00000";
		String line;
		String dataString;
		Boolean found = false;
		int progress = 0;
		Boolean done = false;
    	try (BufferedReader bufferedReader = new BufferedReader(new FileReader(pieces))) {
    		line = bufferedReader.readLine();
    		while (!found) {
        		idCheck = line.substring(0, 7);
    			if (idCheck.equals("\t" + "\\d\\d\\d\\d\\d" + ";")) {
    				found = true;
    			}
    			else {
        			line = bufferedReader.readLine();
    			}
    		}
    		line = line.substring(7, line.length() - 1);
    		while (!done) {
    			if (!line.contains(";")) {
    				done = true;
    			}
    			else {
    				dataString = line.substring(1, line.indexOf(';'));
    				switch (progress) {
    				case 0:
    					name = dataString;
    					break;
    				case 1:
    					rankRequirement = Integer.parseInt(dataString);
    					break;
    				case 2:
    					aspectType = Integer.parseInt(dataString);
    					break;
    				case 3:
    					affectedEffects = new ArrayList<String>(Arrays.asList(dataString.split("\\s*,\\s*")));
    					break;
    				case 4:
						final ArrayList<String> tempArray1 = new ArrayList<String>(Arrays.asList(dataString.split("\\s*,\\s*")));
						affectedValues = new ArrayList<Integer>();
    					for (String element : tempArray1) {
    						affectedValues.add((int) Integer.parseInt(element));
    					}
    				case 5:
    					valueType = Integer.parseInt(dataString);
    					break;
    				case 6:
    					preferences = new ArrayList<String>(Arrays.asList(dataString.split("\\s*,\\s*")));
    					break;
    				case 7:
    					description = dataString;
    					break;
    				}
    				line = line.substring(dataString.length() + 2, line.length());
    				//TODO check completion
    				progress++;
    			}
    		}
    		
			bufferedReader.close();
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}
	

}
