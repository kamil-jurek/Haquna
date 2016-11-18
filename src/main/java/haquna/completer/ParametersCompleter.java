package haquna.completer;

import haquna.HaqunaSingleton;
import heart.State;
import heart.StateElement;
import heart.WorkingMemory;
import heart.xtt.Attribute;
import heart.xtt.Rule;
import heart.xtt.Table;
import heart.xtt.Type;
import heart.xtt.XTTModel;

public class ParametersCompleter extends CompleterAbstract {

	@Override
	protected void setupStringCandidates() {
		stringCandidates.clear();
		String varName = arguments[argPos-2];
		String functionName = arguments[argPos-1];
		
		if(varName.equals("new")) {
			if(functionName.equals("WorkingMemory")) {
				stringCandidates.addAll(HaqunaSingleton.modelMap.keySet());
			}
		}
						
		/*if(functionName.equals("Type")) {
			stringCandidates.addAll(HaqunaSingleton.modelMap.keySet());
		}*/
		
		if(HaqunaSingleton.tableMap.containsKey(varName)) {
			Table table = HaqunaSingleton.tableMap.get(varName);
			
			if(functionName.contains("getRuleByName")) {
				for(Rule r : table.getRules()) {
					stringCandidates.add(r.getName());
				}				
			}						
		}     					
				
		if(HaqunaSingleton.wmMap.containsKey(varName)){
			WorkingMemory wm = HaqunaSingleton.wmMap.get(varName);
			
			if(functionName.contains("showValueOf")) {
				State current = wm.getCurrentState();
				for(StateElement se : current) {
					stringCandidates.add(se.getAttributeName());					
				}				
			}
			
			if(functionName.contains("setValueOf")) {
				State current = wm.getCurrentState();
				for(StateElement se : current) {
					stringCandidates.add(se.getAttributeName());					
				}				
			}
		}			
		
		if(HaqunaSingleton.modelMap.containsKey(varName)){
			XTTModel model = HaqunaSingleton.modelMap.get(varName);
			
			if(functionName.contains("getTableByName")) {
				for(Table t : model.getTables()) {
					stringCandidates.add(t.getName());
				}				
			}
			
			if(functionName.contains("getTypeByName")) {
				for(Type t : model.getTypes()) {
					stringCandidates.add(t.getName());
				}	
			}
			
			if(functionName.contains("getAttributeByName")) {
				for(Attribute a : model.getAttributes()) {
					stringCandidates.add(a.getName());
				}				
			}
			
			if(functionName.contains("add")) {
				for(String a : HaqunaSingleton.attrMap.keySet()) {
					stringCandidates.add(a);
				}
				for(String t : HaqunaSingleton.tableMap.keySet()) {
					stringCandidates.add(t);
				}	
				for(String t : HaqunaSingleton.typeMap.keySet()) {
					stringCandidates.add(t);
				}
				for(String r : HaqunaSingleton.ruleMap.keySet()) {
					stringCandidates.add(r);					
				}
				for(String a : HaqunaSingleton.attrBuilderMap.keySet()) {
					stringCandidates.add(a);
				}
				for(String t : HaqunaSingleton.tableBuilderMap.keySet()) {
					stringCandidates.add(t);
				}	
				for(String t : HaqunaSingleton.typeBuilderMap.keySet()) {
					stringCandidates.add(t);
				}
				for(String r : HaqunaSingleton.ruleBuilderMap.keySet()) {
					stringCandidates.add(r);					
				}
			}
			
			if(functionName.contains("remove")) {
				for(Attribute a : model.getAttributes()) {
					stringCandidates.add(a.getName());
				}
				for(Table t : model.getTables()) {
					stringCandidates.add(t.getName());
				}	
				for(Type t : model.getTypes()) {
					stringCandidates.add(t.getName());
				}
				for(Table t : model.getTables()) {
					for(Rule r : t.getRules()) {
						stringCandidates.add(r.getName());
					}
				}
			}
		}		
		
	}

}
