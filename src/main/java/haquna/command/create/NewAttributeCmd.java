package haquna.command.create;

import haquna.AttrVar;
import haquna.Haquna;
import haquna.command.Command;
import heart.exceptions.ParsingSyntaxException;
import heart.parser.hmr.HMRParser;
import heart.parser.hmr.runtime.SourceString;
import heart.xtt.Attribute;

public class NewAttributeCmd implements Command {		
	
	public static final String pattern = "^" + Haquna.varName + "(\\s*)" + "[=]" + "(\\s*)" + "new" + "(\\s*)" + "Attribute[(](.*)[)](\\s*)";
	
	private String commandStr;
	private String varName;
	private String hmrCode;
	
	public NewAttributeCmd() {
		
	}
	
	public NewAttributeCmd(String _commandStr) {
		this.commandStr = _commandStr.replace(" ", "");
		
		String[] commandParts = this.commandStr.split("[=|(|)]");		
		this.varName = commandParts[0];		
		this.hmrCode = commandParts[2];
	}
	
	@Override
	public void execute() {				
		if(!Haquna.isVarUsed(varName)) {	
			Attribute.Builder attrBuilder = null;
			
	        SourceString hmr_code = new SourceString(hmrCode);
	        HMRParser parser = new HMRParser();

	        try {
				parser.parse(hmr_code);
				attrBuilder = parser.getAttributeBuilder();
				
	        } catch (ParsingSyntaxException e1) {
				e1.printStackTrace();
				return;
			}
	        
	        Haquna.attrMap.put(varName, new AttrVar(null, attrBuilder));
	        
		} else {
			System.out.println("Variable name: " + varName + " already in use");
		}
	}		
	
	public boolean matches(String commandStr) {
		return commandStr.matches(pattern);
	}
	
	public Command getNewCommand(String cmdStr) {
		return new NewAttributeCmd(cmdStr);
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

}