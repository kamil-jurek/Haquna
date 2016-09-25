package haquna.command.show;

import java.util.LinkedList;

import haquna.Haquna;
import haquna.command.Command;
import heart.xtt.Type;
import heart.xtt.XTTModel;

public class ShowTypesListCmd implements Command {
	
	public static final String pattern = "^[A-Z](.*)[.]showTypesList[(][)](\\s*)";
	
	private String commandStr;
	private String varName;
	
	public ShowTypesListCmd() {
		
	}
	
	public ShowTypesListCmd(String _commandStr) {
		this.commandStr = _commandStr.replace(" ", "");
		
		String[] commandParts = this.commandStr.split("[.]");	
		this.varName = commandParts[0];
	}
	
	public void execute() {
		if(Haquna.modelMap.containsKey(varName)) {
			XTTModel model = Haquna.modelMap.get(varName);
			
			LinkedList<Type> types = model.getTypes();
			for(Type t : types){
				System.out.println("=======================================");
			    System.out.println("Type id:     "+t.getId());
			    System.out.println("Type name:   "+t.getName());
			    System.out.println("Type base:   "+t.getBase());
			    System.out.println("Type length: "+t.getLength());
			    System.out.println("Type scale:  "+t.getPrecision());
			    System.out.println("desc:        "+t.getDescription());		 
			    System.out.println("=======================================");
			    System.out.println();
			}		     					
		} else {
			System.out.println("No " + varName + " model in memory");
		}		
	}
	
	public boolean matches(String commandStr) {
		return commandStr.matches(pattern);
	}
	
	public Command getNewCommand(String cmdStr) {
		return new ShowTypesListCmd(cmdStr);
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
