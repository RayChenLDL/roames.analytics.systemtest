package com.roames.test.utilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;

public class CommonUtilities {
	
	/**
	 * Compare 2 string arrays and list all those values in array1 and not in array2
	 * @param array1
	 * @param array2
	 * @return
	 */
	public static ArrayList findValuesInArray1NotInArray2(String[] array1, String[] array2){
		
		ArrayList<String> result = new ArrayList<String>();		
		
		for(int i=0;i<array1.length;i++) {
			
			if(!Arrays.asList(array2).contains(array1[i])) {
				result.add(array1[i]);
			}			
		}	
		
		return result;		
	}	
	
	/**
	 * Delete previous download files if they exist
	 * @throws IOException
	 */
	public static void deleteDownloadedFilesIfExist() throws IOException{
				
		String[] filesList = new String[6];
		filesList[0] = System.getProperty("user.home") + "/Downloads/Export.xls";
		filesList[1] = System.getProperty("user.home") + "/Downloads/Export.csv";
		filesList[2] = System.getProperty("user.home") + "/Downloads/Export.kml";
		filesList[3] = System.getProperty("user.home") + "/Downloads/export.xls";
		filesList[4] = System.getProperty("user.home") + "/Downloads/export.csv";
		filesList[5] = System.getProperty("user.home") + "/Downloads/export.kml";
		
		for(int i=0;i<filesList.length;i++) {			
			Files.deleteIfExists(Paths.get(filesList[i]));	
		}		
	}
	
	/**
	 * Return the random number with the range of min and max
	 * @param min
	 * @param max
	 * @return
	 */
	public static int getRandomNumber(int min, int max) {
		Random rand = new Random(); 
		return (rand.nextInt((max - min) + 1) + min);
	}
	
	public static String getTimestampInteger() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return dateFormat.format(new Date());
	}
	
	
	/**
	 * Print out the Key / Value pair for the Hashtable
	 * @param ht
	 */
	public static void printHashTableKeysAndValues(Hashtable ht) {
		Iterator<Map.Entry>  it;
		Map.Entry            entry;

		it = ht.entrySet().iterator();
		while (it.hasNext()) {
		    entry = it.next();
		    System.out.println(
		        entry.getKey().toString() + " / " +
		        entry.getValue().toString());
		}
	}
}
