package haquna.command.io;

import haquna.Haquna;
import haquna.command.Command;
import heart.parser.hmr.HMRParser;
import heart.parser.hmr.runtime.SourceFile;
import heart.xtt.XTTModel;

public class XloadCmd implements Command {		
	
	public static final String pattern = "^[A-Z].*=(\\s*)xload[(]['](.*)['][)](\\s*)";
	
	private String commandStr;
	private String varName;
	private String modelPath;
	
	public XloadCmd() {
		
	}
	
 	public XloadCmd(String _commandStr) {
		this.commandStr = _commandStr.replace(" ", "");
		
		String[] commandParts = this.commandStr.split("=xload");		
		this.varName = commandParts[0];		
		this.modelPath = commandParts[1].substring(2, commandParts[1].length()-2);
		
		if(!this.modelPath.contains("/home/")) {
			this.modelPath = System.getProperty("user.dir") + "/" + this.modelPath;
		}
	}
	
	@Override
	public void execute() {				
		XTTModel model = null;
		SourceFile hmr = new SourceFile(modelPath);
		HMRParser parser = new HMRParser();
		   	
		try {		    
		    parser.parse(hmr);
		    model = parser.getModel();
			
			if(!Haquna.isVarUsed(varName)) {
				Haquna.modelMap.put(varName, model);					
			} else {
				System.out.println("Variable name: " + varName + " already in use");
			}

		} catch(Exception e) {
			System.out.println("File '" + modelPath + "' was found");
			//e.printStackTrace();		    			    	
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
	
	
	
}
