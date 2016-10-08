package haquna.command.add;

import java.util.LinkedList;

import haquna.Haquna;
import haquna.command.Command;
import heart.alsvfd.Formulae;
import heart.alsvfd.expressions.AttributeExpressionBuilderInterface;
import heart.exceptions.ModelBuildingException;
import heart.exceptions.ParsingSyntaxException;
import heart.parser.hmr.HMRParser;
import heart.parser.hmr.runtime.SourceString;
import heart.xtt.Attribute;
import heart.xtt.Decision;
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
		if(!Haquna.isVarUsed(newModelName)) {
			if(Haquna.modelMap.containsKey(modelName)) {
				if(Haquna.typeMap.containsKey(itemToAddName)) {
					addType();		            				
				
				} else if(Haquna.attribiuteMap.containsKey(itemToAddName)) {
					addAttribute();
				
				} else if(Haquna.ruleMap.containsKey(itemToAddName)) {
					addRule();
				
				} else if(Haquna.tableMap.containsKey(itemToAddName)) {
					addTable();
												
				} else {
					System.out.println("No " + itemToAddName + " variable in memory");
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
		return new AddCmd(cmdStr);
	}

	private void addType() {
		XTTModel model = Haquna.modelMap.get(modelName);	
		Type type = Haquna.typeMap.get(itemToAddName);
				
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
        
        try {
			builder.addIncompleteType(newType);
			XTTModel newModel = builder.build();
			
			Haquna.modelMap.put(newModelName, newModel);
		
        } catch (ModelBuildingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
	}
	
	private void addAttribute() {
		XTTModel model = Haquna.modelMap.get(modelName);	
		Attribute attr = Haquna.attribiuteMap.get(itemToAddName);
		
        Attribute.Builder newAttr = new Attribute.Builder();
        newAttr.setAbbreviation(attr.getAbbreviation());
        newAttr.setCallback(attr.getCallback());
        newAttr.setComm(attr.getComm());
        newAttr.setDescription(attr.getDescription());
        newAttr.setId(attr.getId());
        newAttr.setName(attr.getName());
        newAttr.setTypeName(attr.getType().getName());
        newAttr.setXTTClass(attr.getXTTClass());
                     		          
        XTTModel.Builder builder = model.getBuilder();
        
        try {
			builder.addIncompleteAttribute(newAttr);
			XTTModel newModel = builder.build();
			
			Haquna.modelMap.put(newModelName, newModel);
		
        } catch (ModelBuildingException e) {

			e.printStackTrace();
		}             
	}
	
	private void addTable() {
		XTTModel model = Haquna.modelMap.get(modelName);	
		Table tab = Haquna.tableMap.get(itemToAddName);
				
        Table.Builder newTab = new Table.Builder();
        
        newTab.setDescription(tab.getDescription());
        newTab.setName(tab.getName());
        
        LinkedList<String> condAtts = new LinkedList<String>();
        for(Attribute a : tab.getPrecondition()) {
        	condAtts.add(a.getName());
        }
        newTab.setConditionalAttributesNames(condAtts);
        
        LinkedList<String>decAtts = new LinkedList<String>();
        for(Attribute a : tab.getPrecondition()) {
        	decAtts.add(a.getName());
        }
        newTab.setDecisiveAttributesNames(decAtts);
         
        XTTModel.Builder builder = model.getBuilder();
        
        try {
			builder.addIncompleteTable(newTab);
			XTTModel newModel = builder.build();
			
			Haquna.modelMap.put(newModelName, newModel);
		
        } catch (ModelBuildingException e) {

			e.printStackTrace();
		}            
	}
	
	private void addRule() {
		/*XTTModel model = Haquna.modelMap.get(modelName);	
		Rule rule = Haquna.ruleMap.get(itemToAddName);
		
        Rule.Builder newRule = new Rule.Builder();
        newRule.setActions(rule.getActions());
        newRule.setCertaintyFactor(rule.getCertaintyFactor());
       
        LinkedList<Formulae.Builder> conds = new LinkedList<Formulae.Builder>();
        for(Formulae f : rule.getConditions()) {
        	 Formulae.Builder b = new Formulae.Builder();            
             b.setOp(f.getOp());
             b.setTBP(f.getTimeBasedParameter());
             b.setLHS((AttributeExpressionBuilderInterface)f.getLHS());
             //b.setRHS(f.getRHS());
             
             conds.add(b);
        }
        
        LinkedList<Decision.Builder> decs = new LinkedList<Decision.Builder>();
        for(Decision f : rule.getDecisions()) {
        	 Decision.Builder b = new Decision.Builder();            
             b.setAttributeName(attName);
        	 b.setOp(f.getOp());
             b.setTBP(f.getTimeBasedParameter());
             b.setLHS((AttributeExpressionBuilderInterface)f.getLHS());
             //b.setRHS(f.getRHS());
             
             decs.add(b);
        }
        
        
        
        newRule.setIncompleteConditions(conds);
        newRule.setIncompleteDecisions(decs);
       // newRule.setLinks(rule.getRuleLinks());
        
        rule.getConditions();
        rule.getDecisions();
        rule.getId();
        rule.getName();
        rule.getOrderNumber();
        
        rule.getSchemeName();
        rule.getTabLinks();
                     		          
        XTTModel.Builder builder = model.getBuilder();
        
        XTTModel newModel = null;
        try {
			builder.addIncompleteRule(newRule);
			newModel = builder.build();
		
        } catch (ModelBuildingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        
        //Haquna.modelMap.put(newModelName, newModel);
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