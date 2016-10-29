package haquna.command.io;

import haquna.Haquna;
import haquna.HaqunaException;
import haquna.XttModelUtils;
import haquna.command.Command;
import haquna.utils.HaqunaUtils;
import heart.xtt.XTTModel;

public class XsaveCmd implements Command {		
	
	public static final String pattern = "^" + Haquna.varName + "(\\s*)" + "[.]"  + "(\\s*)" + "xsave[(]['](.*)['][)](\\s*)";
	
	private String commandStr;
	private String modelName;
	private String modelPath;
	
	public XsaveCmd() {
		
	}
	
 	public XsaveCmd(String _commandStr) {
		this.commandStr = _commandStr.replace(" ", "");
		
		String[] commandParts = this.commandStr.split(".xsave|'");		
		this.modelName = commandParts[0];		
		this.modelPath = commandParts[2];
		
		if(!this.modelPath.contains("/home/")) {
			this.modelPath = System.getProperty("user.dir") + "/" + this.modelPath;
		}
	}
	
	@Override
	public void execute() {						
		try {
			XTTModel model = HaqunaUtils.getModel(modelName);
			
			XttModelUtils.saveToFile(model, modelPath);
			
			Haquna.wasSucces = true;
			
		} catch (HaqunaException e) {
			HaqunaUtils.printRed(e.getMessage());
			e.printStackTrace();
			return;
		
		} catch (Exception e) {
			HaqunaUtils.printRed(e.getMessage());
			e.printStackTrace();
			
			return;
		}			
	}		
	
	public boolean matches(String commandStr) {
		return commandStr.matches(pattern);
	}
	
	public Command getNewCommand(String cmdStr) {
		return new XsaveCmd(cmdStr);
	}
	
	public String getCommandStr() {
		return commandStr;
	}

	public void setCommandStr(String commandStr) {
		this.commandStr = commandStr;
	}

	public String getModelPath() {
		return modelPath;
	}

	public void setModelPath(String modelPath) {
		this.modelPath = modelPath;
	}
	
	
	
}
