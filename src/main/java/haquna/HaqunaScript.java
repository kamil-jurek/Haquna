package haquna;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

public class HaqunaScript {
	private List<String> scriptCommands;
	
	public HaqunaScript(String filePath) {
		scriptCommands = new LinkedList<String>();
		String line;
		try (
		    InputStream fis = new FileInputStream(filePath);
		    InputStreamReader isr = new InputStreamReader(fis, Charset.forName("UTF-8"));
		    BufferedReader br = new BufferedReader(isr);
		) {
		    while ((line = br.readLine()) != null) {
		    	scriptCommands.add(line);
		        // Deal with the line
		    }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<String> getScriptCommands() {
		return this.scriptCommands;
	}
}
