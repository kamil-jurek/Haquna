package haquna.command.add;

import haquna.Haquna;
import haquna.HaqunaException;
import haquna.command.Command;
import haquna.utils.HaqunaUtils;
import heart.exceptions.ModelBuildingException;
import heart.xtt.Attribute;
import heart.xtt.Rule;
import heart.xtt.Table;
import heart.xtt.Type;
import heart.xtt.XTTModel;

public class AddCmd implements Command {		
	// NewModel = Model.add(Type)
	public static final String pattern = "^" + Haquna.varName + "(\\s*)=(\\s*)" + Haquna.varName + "[.]add[(]" + Haquna.varName + "[)](\\s*)";
	
	private String commandStr;
	private String modelName;
	private String newModelName;
	private String itemToAddName;
	
	public AddCmd() {
		
	}
	
	public AddCmd(String _commandStr) {
		this.commandStr = _commandStr.replace(" ", "");
		
		String[] commandParts = this.commandStr.split("[=|.|(|)]");		
		this.newModelName = commandParts[0];		
		this.modelName = commandParts[1];
		this.itemToAddName = commandParts[3];
	}
	
	@Override
	public void execute() {	
		try {
			HaqunaUtils.checkVarName(newModelName);
			HaqunaUtils.getModel(modelName);
			
			addItem();
						
			Haquna.wasSucces = true;
			
		} catch (HaqunaException e) {
			HaqunaUtils.printRed(e.getMessage());
			
			return;
		
		} catch (ModelBuildingException e) {
			HaqunaUtils.printRed(e.getMessage());
			e.printStackTrace();
			
			return;
		}
	}		
	
	public boolean matches(String commandStr) {
		return commandStr.matches(pattern);
	}
	
	public Command getNewCommand(String cmdStr) {
		return new AddCmd(cmdStr);
	}
	
	private void addItem() throws HaqunaException, ModelBuildingException {
		if(Haquna.typeBuMap.containsKey(itemToAddName)) {
			addType();		            				
		
		} else if(Haquna.attrBuMap.containsKey(itemToAddName)) {
			addAttribute();
		
		} else if(Haquna.ruleBuMap.containsKey(itemToAddName)) {
			addRule();
		
		} else if(Haquna.tableBuMap.containsKey(itemToAddName)) {
			addTable();
										
		} else {
			throw new HaqunaException("No " + itemToAddName + " variable in memory");
		}
	}
	
	private void addType() throws ModelBuildingException {
		XTTModel model = Haquna.modelMap.get(modelName);	
	         
        XTTModel.Builder builder = model.getBuilder();
        Type.Builder typeBuilder = Haquna.typeBuMap.get(itemToAddName);
        
        builder.addIncompleteType(typeBuilder);
		XTTModel newModel = builder.build();
			
		Haquna.modelMap.put(newModelName, newModel);		            
	}
	
	private void addAttribute() throws ModelBuildingException {
		XTTModel model = Haquna.modelMap.get(modelName);	
		
        XTTModel.Builder builder = model.getBuilder();
        Attribute.Builder attrBuilder = Haquna.attrBuMap.get(itemToAddName);
       
		builder.addIncompleteAttribute(attrBuilder);
		XTTModel newModel = builder.build();
			
		Haquna.modelMap.put(newModelName, newModel);
		                
	}
	
	private void addTable() throws ModelBuildingException {
		XTTModel model = Haquna.modelMap.get(modelName);			       
       
		XTTModel.Builder modelBuilder = model.getBuilder();
        Table.Builder tableBuilder = Haquna.tableBuMap.get(itemToAddName);
        
		modelBuilder.addIncompleteTable(tableBuilder);
		XTTModel newModel = modelBuilder.build();
		
		Haquna.modelMap.put(newModelName, newModel);
		           
	}
	
	private void addRule() throws ModelBuildingException {
		XTTModel model = Haquna.modelMap.get(modelName);	
		XTTModel.Builder modelBuilder = model.getBuilder();
        Rule.Builder ruleBuilder = Haquna.ruleBuMap.get(itemToAddName);
		
		modelBuilder.addIncompleteRule(ruleBuilder);
		XTTModel newModel = modelBuilder.build();
		
		Haquna.modelMap.put(newModelName, newModel);
			      
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