package com.roames.test.wip;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;

public class JSONDataReader {
	
	@Test
	public void test() throws IOException {
		
		File jsonFile = new File(System.getProperty("user.dir")+"\\src\\test\\resources\\testdata\\APISampleTest.json");
      		
		List<LinkedHashMap> jsonFileDataRows = JsonPath.read(jsonFile, "$");

		System.out.println(jsonFileDataRows.size());
		
		// Total number of rows for the current test scripts
		for (int ii=0;ii<jsonFileDataRows.size();ii++) {
			
			LinkedHashMap currRow = jsonFileDataRows.get(ii);
			Iterator it = currRow.keySet().iterator();
			
			// For each row, retrieve all the key/value pair
			while(it.hasNext()) {
				Object key =it.next();
				System.out.println(key);
				System.out.println(currRow.get(key));
				System.out.println("");
			}

		}
		
	}
	
}
