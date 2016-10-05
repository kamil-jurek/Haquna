package haquna.command.add;

import java.text.Normalizer.Form;
import java.util.LinkedList;

import javax.swing.text.html.HTMLEditorKit.Parser;

import haquna.Haquna;
import haquna.command.Command;
import heart.alsvfd.Formulae;
import heart.alsvfd.SetValue;
import heart.alsvfd.SimpleSymbolic;
import heart.alsvfd.Value;
import heart.alsvfd.expressions.AttributeExpressionBuilderInterface;
import heart.alsvfd.expressions.ExpressionBuilderInterface;
import heart.exceptions.ModelBuildingException;
import heart.exceptions.ParsingSyntaxException;
import heart.parser.hmr.HMRParser;
import heart.parser.hmr.runtime.SourceFile;
import heart.parser.hmr.runtime.SourceString;
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
	private String toAddName;

	
	public AddCmd() {
		
	}
	
	public AddCmd(String _commandStr) {
		this.commandStr = _commandStr.replace(" ", "");
		
		String[] commandParts = this.commandStr.split("[=|.|(|)]");		
		this.newModelName = commandParts[0];		
		this.modelName = commandParts[1];
		this.toAddName = commandParts[3];
	}
	
	@Override
	public void execute() {				
		if(!Haquna.isVarUsed(newModelName)) {
			if(Haquna.modelMap.containsKey(modelName)) {
				if(Haquna.typeMap.containsKey(toAddName)) {
					addType();		            				
				
				} else if(Haquna.attribiuteMap.containsKey(toAddName)) {
					addAttribute();
				
				} else if(Haquna.ruleMap.containsKey(toAddName)) {
					addRule();
				
				} else if(Haquna.tableMap.containsKey(toAddName)) {
					addTable();
												
				} else {
					System.out.println("No " + toAddName + " variable in memory");
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
		Type type = null; // = Haquna.typeMap.get(toAddName);
		
		/////////////////////////////////////
		 XTTModel mod = null;
         SourceString hmr_code = new SourceString("xtype [name: weather_type, "
         		+ " base: symbolic,"
         		+ " desc: 'Wheater type',"
         		+ " domain: [sunny,rainy,cloudy]"
         		+ "].");
         HMRParser parser = new HMRParser();

         //Parsing the file with the model
         try {
			parser.parse(hmr_code);
			type = parser.getModel().getTypes().getFirst();
		} catch (ParsingSyntaxException | ModelBuildingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
		/////////////////////////////////////
		
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        Haquna.modelMap.put(newModelName, newModel);
	}
	
	private void addAttribute() {
		XTTModel model = Haquna.modelMap.get(modelName);	
		Attribute attr = Haquna.attribiuteMap.get(toAddName);
		
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
        
        XTTModel newModel = null;
        try {
			builder.addIncompleteAttribute(newAttr);
			newModel = builder.build();
		
        } catch (ModelBuildingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        Haquna.modelMap.put(newModelName, newModel);
	}
	
	private void addTable() {
		XTTModel model = Haquna.modelMap.get(modelName);	
		Table tab = Haquna.tableMap.get(toAddName);
				
        Table.Builder newTab = new Table.Builder();
        
        newTab.setDescription(tab.getDescription());
        newTab.setName(tab.getName());
        //newTab.set
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
        
        XTTModel newModel = null;
        try {
			builder.addIncompleteTable(newTab);
			newModel = builder.build();
		
        } catch (ModelBuildingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        Haquna.modelMap.put(newModelName, newModel);
	}
	
	private void addRule() {
		XTTModel model = Haquna.modelMap.get(modelName);	
		Rule rule = Haquna.ruleMap.get(toAddName);
		
        Rule.Builder newRule = new Rule.Builder();
        newRule.setActions(rule.getActions());
        newRule.setCertaintyFactor(rule.getCertaintyFactor());
       
        LinkedList<Formulae.Builder> conds = new LinkedList<Formulae.Builder>();
        LinkedList<Formulae> formulaes= rule.getConditions();
        for(Formulae f : formulaes) {
        	 Formulae.Builder b = new Formulae.Builder();            
             b.setOp(f.getOp());
             b.setTBP(f.getTimeBasedParameter());
             //f.getRHS().
             //AttributeExpressionBuilderInterface aebi;
             //b.set
            // b.setLHS(f.getLHS());
             //b.setRHS(f.getRHS());
             
             
             conds.add(b);
        }
       
        
        
        
        newRule.setIncompleteConditions(conds);
        //newRule.setIncompleteDecisions(decs);
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
		}
        
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