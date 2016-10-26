package haquna.command.io;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import haquna.Haquna;
import haquna.HaqunaException;
import haquna.command.Command;
import haquna.utils.HaqunaUtils;
import heart.exceptions.ModelBuildingException;
import heart.exceptions.ParsingSyntaxException;
import heart.parser.hmr.HMRParser;
import heart.parser.hmr.runtime.SourceFile;
import heart.xtt.XTTModel;

public class XloadCmd implements Command {		
	
	public static final String pattern = "^" + Haquna.varName + "(\\s*)=(\\s*)xload[(]['](.*)['][)](\\s*)";
	
	private String commandStr;
	private String varName;
	private String modelPath;
	private String path;
	
	public XloadCmd() {
		
	}
	
 	public XloadCmd(String _commandStr) {
		this.commandStr = _commandStr.replace(" ", "");
		
		String[] commandParts = this.commandStr.split("=xload");		
		this.varName = commandParts[0];		
		this.modelPath = commandParts[1].substring(2, commandParts[1].length()-2);
				
	}
	
	@Override
	public void execute() {						
		try {	
			setupPath();
			HaqunaUtils.checkVarName(varName);
			
			XTTModel model = parseHMR();
						
			Haquna.modelMap.put(varName, model);	
				
			Haquna.wasSucces = true;
			
		} catch(Exception e) {
			HaqunaUtils.printRed(e.getMessage());
			e.printStackTrace();
			return;
		}	
	}		
	
	public boolean matches(String commandStr) {
		return commandStr.matches(pattern);
	}
	
	public Command getNewCommand(String cmdStr) {
		return new XloadCmd(cmdStr);
	}
	
	public String getCommandStr() {
		return commandStr;
	}

	public void setCommandStr(String commandStr) {
		this.commandStr = commandStr;
	}

	public String getVarName() {
		return varName;
	}

	public void setVarName(String varName) {
		this.varName = varName;
	}

	public String getModelPath() {
		return modelPath;
	}

	public void setModelPath(String modelPath) {
		this.modelPath = modelPath;
	}

	private void setupPath() throws IOException {
		URL website = null;
		try {
			website = new URL(modelPath);
			ReadableByteChannel rbc = Channels.newChannel(website.openStream());
			FileOutputStream fos = new FileOutputStream("test.hmr");
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);			
			fos.close();
			
			this.path = "test.hmr";
		
		} catch (MalformedURLException e) {
					
			if(!this.modelPath.contains("/home/")) {
				this.modelPath = System.getProperty("user.dir") + "/" + this.modelPath;
			}
			
			path = modelPath;
			return;
		}				
	}
	
	private XTTModel parseHMR() throws HaqunaException {
		XTTModel model = null;
		SourceFile hmr = new SourceFile(path);
		HMRParser parser = new HMRParser();
	   	try {			    
		    parser.parse(hmr);
		    model = parser.getModel();
	   	
		    return model;
	   	} catch(ParsingSyntaxException | ModelBuildingException e) {
	   		throw new HaqunaException("ParsingSynatxException parsing '" + path + "'");
	   	}
	}
	
}
