package haquna;

import java.util.LinkedList;

import heart.xtt.Attribute;
import heart.xtt.XTTModel;

public class ShowAttributesListCmd implements Command {
	
	public static final String pattern = "^[A-Z](.*)[.]showAttributesList[(][)](\\s*)";
	
	private String commandStr;
	private String varName;
	
	public ShowAttributesListCmd(String _commandStr) {
		this.commandStr = _commandStr.replace(" ", "");
		
		String[] commandParts = this.commandStr.split("[.]");	
		this.varName = commandParts[0];
	}
	
	public void execute() {
		try {				
			if(Haquna.modelMap.containsKey(varName)) {
				XTTModel model = Haquna.modelMap.get(varName);
			
				//Printing all the attributes within the model
			    LinkedList<Attribute> atts = model.getAttributes();
			    for(Attribute att: atts){
			        System.out.println("Att Id: "+att.getId());
			        System.out.println("Att name: "+att.getName());
			        System.out.println("Att typeName: "+att.getTypeId());
			        System.out.println("Att abbrev: "+att.getAbbreviation());
			        System.out.println("Att comm: "+att.getComm());
			        System.out.println("Att desc: "+att.getDescription());
			        System.out.println("Att class: "+att.getXTTClass());
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
