package haquna.command.add;

import haquna.Haquna;
import haquna.command.Command;
import heart.exceptions.ModelBuildingException;
import heart.xtt.Attribute;
import heart.xtt.Rule;
import heart.xtt.Table;
import heart.xtt.Type;
import heart.xtt.XTTModel;

public class RemoveCmd implements Command {		
	// NewModel = Model.add(Type)
	public static final String pattern = "^" + Haquna.varName + "(\\s*)=(\\s*)" + Haquna.varName + "[.]remove[(][']" + "(.*)" + "['][)](\\s*)";
	
	private String commandStr;
	private String modelName;
	private String newModelName;
	private String itemToRemoveName;

	
	public RemoveCmd() {
		
	}
	
	public RemoveCmd(String _commandStr) {
		this.commandStr = _commandStr.replace(" ", "");
		
		String[] commandParts = this.commandStr.split("[=|.|']");		
		this.newModelName = commandParts[0];		
		this.modelName = commandParts[1];
		this.itemToRemoveName = commandParts[3];
				
	}
	
	@Override
	public void execute() {				
		if(!Haquna.isVarUsed(newModelName)) {
			if(Haquna.modelMap.containsKey(modelName)) {
				XTTModel model = Haquna.modelMap.get(modelName);
				
				switch(determineWhatToRemove(model)) {
				  case "type": {
					System.out.println("type");
					removeType();
					break;
				
				  }				  
				  case "attribute": {
					System.out.println("attr");
					removeAttribute();
					break;
					
				  }
				  case "rule": {
					System.out.println("rule");
					removeRule();
					break;
					
				  }
				  case "table": {
				    System.out.println("table");
					removeTable();
					break;
					
				  }
				  default: {
					System.out.println("No type, attribute, rule nor table with '" + itemToRemoveName + "' name");
					break;
				  }
				}
				
				
				
			} else {
				System.out.println("No " + modelName + " model in memory");
			}			
		} else {
			System.out.println("Variable name: " + newModelName + " already in use");
		}
	}		
	
	public boolean matches(String commandStr) {
		return commandStr.matches(pattern);
	}
	
	public Command getNewCommand(String cmdStr) {
		return new RemoveCmd(cmdStr);
	}

	private void removeType() {
		XTTModel model = Haquna.modelMap.get(modelName);	

        XTTModel.Builder builder = model.getBuilder();       
        XTTModel newModel = null;
        
        try {
			builder.removeIncompleteTypeNamed(itemToRemoveName);
			newModel = builder.build();
			
			Haquna.modelMap.put(newModelName, newModel);
		
        } catch (ModelBuildingException e) {
			e.printStackTrace();
		}              
	}
	
	private void removeAttribute() {
		XTTModel model = Haquna.modelMap.get(modelName);	

        XTTModel.Builder builder = model.getBuilder();       
        XTTModel newModel = null;
        
        try {
			builder.removeIncompleteAttributeNamed(itemToRemoveName);
			newModel = builder.build();
			
			Haquna.modelMap.put(newModelName, newModel);
		
        } catch (ModelBuildingException e) {
			e.printStackTrace();
		}              
	}
	
	private void removeTable() {
		XTTModel model = Haquna.modelMap.get(modelName);	

        XTTModel.Builder builder = model.getBuilder();       
        XTTModel newModel = null;
        
        try {
			builder.removeIncompleteTableNamed(itemToRemoveName);
			newModel = builder.build();
			
			Haquna.modelMap.put(newModelName, newModel);
		
        } catch (ModelBuildingException e) {
			e.printStackTrace();
		}              
	}
	
	private void removeRule() {
		XTTModel model = Haquna.modelMap.get(modelName);	

        XTTModel.Builder builder = model.getBuilder();       
        XTTModel newModel = null;
        
        try {
			//builder.removeIncompleteRuleNamed(itemToRemoveName);
			newModel = builder.build();
			
			//Haquna.modelMap.put(newModelName, newModel);
		
        } catch (ModelBuildingException e) {
			e.printStackTrace();
		}              
	}
	
	private String determineWhatToRemove(XTTModel model) {
		for(Type type : model.getTypes()) {
			if(type.getName().equals(itemToRemoveName)) {
				return "type";
			}
		}
		
		for(Attribute attr : model.getAttributes()) {
			if(attr.getName().equals(itemToRemoveName)) {
				return "attribute";
			}
		}
		
		for(Table table : model.getTables()) {
			for(Rule rule : table.getRules()) {
				if(rule.getName().equals(itemToRemoveName)) {
					return "rule";
				}
			}			
		}
		
		for(Table table : model.getTables()) {
			if(table.getName().equals(itemToRemoveName)) {
				return "table";
			}
		}
		
		return "nothing";
	}
	
	public String getCommandStr() {
		return commandStr;
	}

	public void setCommandStr(String commandStr) {
		this.commandStr = commandStr;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	
}