package tools;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ReadAntiPattern {
	

	private Map<String, Integer> nullCatch;
	private Map<String, Integer> throwsKitchen;
	private Map<String, Integer> finallyWithThrow;
	private Map<String, Integer> getCause;
	private Map<String, Integer> genericCatch;
	private Map<String, Integer> thrownGeneric;
	private Map<String, Integer> abortInCatch;
	private Map<String, Integer> inCompleteImpl;
	private Map<String, Integer> catchReturnNull;
	private Map<String, Integer> nestedTry;
	
	public ReadAntiPattern() {
		super();
		
		nullCatch = new HashMap<String, Integer>();
		throwsKitchen = new HashMap<String, Integer>();;
		finallyWithThrow = new HashMap<String, Integer>();;
		getCause = new HashMap<String, Integer>();;
		genericCatch = new HashMap<String, Integer>();;
		thrownGeneric = new HashMap<String, Integer>();;
		abortInCatch = new HashMap<String, Integer>();;
		inCompleteImpl = new HashMap<String, Integer>();;
		catchReturnNull = new HashMap<String, Integer>();;
		nestedTry = new HashMap<String, Integer>();;
		
	}
	
	public Map<String, Map<String, Integer>> generateData(String file) throws IOException {
		Map<String, Map<String, Integer>> map = new HashMap<String, Map<String, Integer>>();
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		
		String[] heading = br.readLine().split(",");
		
		String row = null;
		
		while((row = br.readLine()) != null) {
			String[] data = row.split(",");
			
			nullCatch.put(data[0], Integer.parseInt(data[1]));
			throwsKitchen.put(data[0], Integer.parseInt(data[2]));
			finallyWithThrow.put(data[0], Integer.parseInt(data[3]));
			nestedTry.put(data[0], Integer.parseInt(data[4]));
			getCause.put(data[0], Integer.parseInt(data[5]));
			genericCatch.put(data[0], Integer.parseInt(data[6]));
			abortInCatch.put(data[0], Integer.parseInt(data[7]));
			thrownGeneric.put(data[0], Integer.parseInt(data[8]));
			inCompleteImpl.put(data[0], Integer.parseInt(data[9]));
			catchReturnNull.put(data[0], Integer.parseInt(data[10]));
			
		}
		
		map.put("nullcatch", nullCatch);
		map.put("throwskitchen", throwsKitchen);
		map.put("finallywiththrow", finallyWithThrow);
		map.put("getcause", getCause);
		map.put("genericcatch", genericCatch);
		map.put("throwngeneric", thrownGeneric);
		map.put("abortincatch", abortInCatch);
		map.put("incompleteimpl", inCompleteImpl);
		map.put("catchreturnnull", catchReturnNull);
		map.put("nestedtry", nestedTry);
		
		return map;
		
	}
}
