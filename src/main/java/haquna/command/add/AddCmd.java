package haquna.command.add;

import java.util.LinkedList;

import haquna.Haquna;
import haquna.command.Command;
import heart.alsvfd.Formulae;
import heart.alsvfd.expressions.AttributeExpressionBuilderInterface;
import heart.alsvfd.expressions.ExpressionBuilderInterface;
import heart.alsvfd.expressions.ExpressionInterface;
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
				
				} else if(Haquna.attrMap.containsKey(itemToAddName)) {
					addAttribute();
				
				} else if(Haquna.ruleBuilderMap.containsKey(itemToAddName+"B")) {
					addRule();
				
				} else if(Haquna.tableBuilderMap.containsKey(itemToAddName+"B")) {
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
	         
        XTTModel.Builder builder = model.getBuilder();
        Type.Builder typeBuilder = Haquna.typeMap.get(itemToAddName).typeBuilder;
        
        try {
			builder.addIncompleteType(typeBuilder);
			XTTModel newModel = builder.build();
			
			Haquna.modelMap.put(newModelName, newModel);
		
        } catch (ModelBuildingException e) {
			e.printStackTrace();
			return;
		}
        
        
	}
	
	private void addAttribute() {
		XTTModel model = Haquna.modelMap.get(modelName);	
		
        XTTModel.Builder builder = model.getBuilder();
        Attribute.Builder attrBuilder = Haquna.attrMap.get(itemToAddName).attrBuilder;
        try {
			builder.addIncompleteAttribute(attrBuilder);
			XTTModel newModel = builder.build();
			
			Haquna.modelMap.put(newModelName, newModel);
		
        } catch (ModelBuildingException e) {
			e.printStackTrace();
			
			return;
		}             
	}
	
	private void addTable() {
		XTTModel model = Haquna.modelMap.get(modelName);	
		/*Table tab = Haquna.tableMap.get(itemToAddName);
				
        Table.Builder tableBuilder = new Table.Builder();
         
        tableBuilder.setName(tab.getName());
        tableBuilder.setDescription(tab.getDescription());
             
        LinkedList<String> condAtts = new LinkedList<String>();
        for(Attribute a : tab.getPrecondition()) {
        	System.out.println(a.getName());
        	condAtts.add(a.getName());
        }
        tableBuilder.setConditionalAttributesNames(condAtts);
        
        LinkedList<String>decAtts = new LinkedList<String>();
        for(Attribute a : tab.getConclusion()) {
        	System.out.println(a.getName());
        	decAtts.add(a.getName());
        }
        tableBuilder.setDecisiveAttributesNames(decAtts);*/
        
        XTTModel.Builder modelBuilder = model.getBuilder();
        Table.Builder tableBuilder = Haquna.tableBuilderMap.get(itemToAddName+"B");
        
        try {
			modelBuilder.addIncompleteTable(tableBuilder);
			XTTModel newModel = modelBuilder.build();
			
			Haquna.modelMap.put(newModelName, newModel);
		
        } catch (ModelBuildingException e) {
			e.printStackTrace();
			
			return;
		}            
	}
	
	private void addRule() {
		XTTModel model = Haquna.modelMap.get(modelName);	
		XTTModel.Builder modelBuilder = model.getBuilder();
        Rule.Builder ruleBuilder = Haquna.ruleBuilderMap.get(itemToAddName+"B");
		
		try {
			modelBuilder.addIncompleteRule(ruleBuilder);
			XTTModel newModel = modelBuilder.build();
		
			Haquna.modelMap.put(newModelName, newModel);
			
        } catch (ModelBuildingException e) {
			e.printStackTrace();
			
			return;
		}
		/*Rule rule = Haquna.ruleMap.get(itemToAddName);
	
        Rule.Builder ruleBuilder = new Rule.Builder();
        ruleBuilder.setActions(rule.getActions());
        ruleBuilder.setCertaintyFactor(rule.getCertaintyFactor());
        
        LinkedList<Formulae.Builder> conds = new LinkedList<Formulae.Builder>();
        for(Formulae f : rule.getConditions()) {
        	 Formulae.Builder b = new Formulae.Builder();            
             b.setOp(f.getOp());
             b.setTBP(f.getTimeBasedParameter());
             b.setLHS(f.getLHS());
             b.setRHS(f.getRHS());
                        
             conds.add(b);
        }
        ruleBuilder.setIncompleteConditions(conds);
        
        LinkedList<Decision.Builder> decs = new LinkedList<Decision.Builder>();
        for(Decision d : rule.getDecisions()) {
        	 Decision.Builder db = new Decision.Builder();            
             db.setAttributeName(d.getAttribute().getAttributeName());
        	 //db.setOp(b.set);
             ExpressionBuilderInterface a = d.getDecision().B
             db.setIncompleteDecision(d.getDecision());
             
             decs.add(b);
        }
        
        
        
        
        //ruleBuilder.setIncompleteDecisions(decs);
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
			builder.addIncompleteRule(ruleBuilder);
			newModel = builder.build();
		
			Haquna.modelMap.put(newModelName, newModel);
			
        } catch (ModelBuildingException e) {
			e.printStackTrace();
			
			return;
		}*/
        
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