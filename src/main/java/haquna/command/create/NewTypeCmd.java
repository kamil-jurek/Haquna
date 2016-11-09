package haquna.command.create;

import haquna.Haquna;
import haquna.HaqunaException;
import haquna.command.Command;
import haquna.utils.HaqunaUtils;
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
		try {
			HaqunaUtils.checkVarName(varName);
			
	        SourceString hmr_code = new SourceString(hmrCode);
	        HMRParser parser = new HMRParser();
	        
	        parser.parse(hmr_code);
	        Type.Builder typeBuilder = parser.getTypeBuilder();
			
	        Haquna.typeBuMap.put(varName, typeBuilder);
			
			Haquna.wasSucces = true;
			
		} catch (HaqunaException | ParsingSyntaxException e) {
			HaqunaUtils.printRed(e.getMessage());
			
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
		return new NewTypeCmd(cmdStr);
	}

	public String getCommandStr() {
		return commandStr;
	}

	public String getVarName() {
		return varName;
	}
}