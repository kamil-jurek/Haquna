package haquna.command.add;

import haquna.Haquna;
import haquna.command.Command;
import heart.exceptions.ModelBuildingException;
import heart.exceptions.ParsingSyntaxException;
import heart.parser.hmr.HMRParser;
import heart.parser.hmr.runtime.SourceString;

import heart.xtt.Type;
import heart.xtt.XTTModel;

public class AddTypeCodeCmd implements Command {		

	public static final String pattern = "^" + Haquna.varName + "(\\s*)=(\\s*)" + Haquna.varName + "[.]add[(]" + ".*" + "[)](\\s*)";
	
	private String commandStr;
	private String modelName;
	private String newModelName;
	private String typeHMRCode;

	
	public AddTypeCodeCmd() {
		
	}
	
	public AddTypeCodeCmd(String _commandStr) {
		this.commandStr = _commandStr.replace(" ", "");
		
		String[] commandParts = this.commandStr.split("[=|.|(|)]");		
		this.newModelName = commandParts[0];		
		this.modelName = commandParts[1];
		this.typeHMRCode = commandParts[3] + ".";
	}
	
	@Override
	public void execute() {				
		if(!Haquna.isVarUsed(newModelName)) {
			if(Haquna.modelMap.containsKey(modelName)) {				
					addType();		            				
								
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
		return new AddTypeCodeCmd(cmdStr);
	}

	private void addType() {
		XTTModel model = Haquna.modelMap.get(modelName);	
		Type type = null;
		
        SourceString hmr_code = new SourceString(typeHMRCode);
        System.out.println(typeHMRCode);
        HMRParser parser = new HMRParser();

        try {
			parser.parse(hmr_code);
			type = parser.getModel().getTypes().getFirst();
		} catch (ParsingSyntaxException | ModelBuildingException e1) {
			e1.printStackTrace();
			return;
		}
        
        Type.Builder newType = new Type.Builder();
        newType.setBase(type.getBase());	         
        newType.setDescription(type.getDescription());
        newType.setDomain(type.getDomain());
        newType.setId(type.getId());
        newType.setName(type.getName());
        newType.setLength(type.getLength());
        newType.setOrdered(type.getOrdered());
        newType.setPrecision(type.getPrecision());
        		          
        XTTModel.Builder builder = model.getBuilder();
        
        XTTModel newModel = null;
        try {
			builder.addIncompleteType(newType);
			newModel = builder.build();
		
        } catch (ModelBuildingException e) {
			e.printStackTrace();
			return;
		}
        
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