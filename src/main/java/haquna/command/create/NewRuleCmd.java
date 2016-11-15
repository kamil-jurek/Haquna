package haquna.command.create;

import haquna.Haquna;
import haquna.HaqunaException;
import haquna.command.Command;
import haquna.utils.HaqunaUtils;
import heart.exceptions.ParsingSyntaxException;
import heart.parser.hmr.HMRParser;
import heart.parser.hmr.runtime.SourceString;
import heart.xtt.Rule;

public class NewRuleCmd implements Command {		
	
	public static final String pattern = "^" + Haquna.varName + "(\\s*)" + "[=]" + "(\\s*)" + "new" + "(\\s*)" + "Rule[(]['](.*)['][)](\\s*)";
	
	private String commandStr;
	private String varName;
	private String hmrCode;
	
	public NewRuleCmd() {
		
	}
	
	public NewRuleCmd(String _commandStr) {
		this.commandStr = _commandStr.replaceFirst(" ", "");
		this.commandStr = commandStr.replace("==", "--");
		
		String[] commandParts = this.commandStr.split("[=|(|)]");		
		this.varName = commandParts[0];		
		this.hmrCode = commandParts[2];
		this.hmrCode = hmrCode.replace("--", "==");
		this.hmrCode = hmrCode.substring(1, hmrCode.length()-1);
		
		if(!hmrCode.substring(hmrCode.length()-1).equals(".")) {
			hmrCode += ".";
		}
		
		System.out.println("'" + varName + "'");
		System.out.println("HMR Code: " + hmrCode);
	}
	
	@Override
	public void execute() {				
		try {
			HaqunaUtils.checkVarName(varName);
			
	        SourceString hmr_code = new SourceString(hmrCode);
	        HMRParser parser = new HMRParser();
	        
	        parser.parse(hmr_code);
	        Rule.Builder ruleBuilder = parser.getRuleBuilder();
			
	        Haquna.ruleBuilderMap.put(varName, ruleBuilder);

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
		return new NewRuleCmd(cmdStr);
	}

	public String getCommandStr() {
		return commandStr;
	}

	public String getVarName() {
		return varName;
	}
}