package haquna;

import java.util.LinkedList;

import heart.xtt.Type;
import heart.xtt.XTTModel;

public class ShowTypesListCmd implements Command {
	
	public static final String pattern = "^[A-Z](.*)[.]showTypesList[(][)](\\s*)";
	
	private String commandStr;
	private String varName;
	
	public ShowTypesListCmd(String _commandStr) {
		this.commandStr = _commandStr.replace(" ", "");
		
		String[] commandParts = this.commandStr.split("[.]");	
		this.varName = commandParts[0];
	}
	
	public void execute() {
		try {				
			if(Haquna.modelMap.containsKey(varName)) {
				XTTModel model = Haquna.modelMap.get(varName);
			
				LinkedList<Type> types = model.getTypes();
			    for(Type t : types){
			        System.out.println("Type id: "+t.getId());
			        System.out.println("Type name: "+t.getName());
			        System.out.println("Type base: "+t.getBase());
			        System.out.println("Type length: "+t.getLength());
			        System.out.println("Type scale: "+t.getPrecision());
			        System.out.println("desc: "+t.getDescription());
			 
			        System.out.println("==========================");
			    }		     					
			} else {
				System.out.println("No such model in memory");
			}
		} catch(Exception e) {
			System.out.println("No such model in memory");
			e.printStackTrace();			    	
			    	
		}
	}
	
	public static boolean matches(String commandStr) {
		return commandStr.matches(pattern);
	}
}
