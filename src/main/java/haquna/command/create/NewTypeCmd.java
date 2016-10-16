package haquna.command.create;

import haquna.Haquna;
import haquna.command.Command;
import heart.exceptions.BuilderException;
import heart.exceptions.ModelBuildingException;
import heart.exceptions.ParsingSyntaxException;
import heart.parser.hmr.HMRParser;
import heart.parser.hmr.runtime.SourceString;
import heart.xtt.Type;

public class NewTypeCmd implements Command {		
	
	public static final String pattern = "^" + Haquna.varName + "(\\s*)" + "[=]" + "(\\s*)" + "new" + "(\\s*)" + "Type[(](.*)[)](\\s*)";
	
	private String commandStr;
	private String varName;
	private String hmrCode;
	
	public NewTypeCmd() {
		
	}
	
	public NewTypeCmd(String _commandStr) {
		this.commandStr = _commandStr.replace(" ", "");
		
		String[] commandParts = this.commandStr.split("[=|(|)]");		
		this.varName = commandParts[0];		
		this.hmrCode = commandParts[2];
	}
	
	@Override
	public void execute() {				
		if(!Haquna.isVarUsed(varName)) {	
			Type type = null;
			
	        SourceString hmr_code = new SourceString(hmrCode);
	        HMRParser parser = new HMRParser();

	        try {
				parser.parse(hmr_code);
				Type.Builder typeBuilder = parser.getTypeBuilder();
				type = typeBuilder.build();
				
	        } catch (ParsingSyntaxException | BuilderException e1) {
				e1.printStackTrace();
				return;
			}
	        
	        Haquna.typeMap.put(varName, type);
	        
		} else {
			System.out.println("Variable name: " + varName + " already in use");
		}
	}		
	
	public boolean matches(String commandStr) {
		return commandStr.matches(pattern);
	}
	
	public Command getNewCommand(String cmdStr) {
		return new NewTypeCmd(cmdStr);
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