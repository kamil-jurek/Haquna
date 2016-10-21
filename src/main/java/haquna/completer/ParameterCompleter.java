package haquna.completer;

import static jline.internal.Preconditions.checkNotNull;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import haquna.Haquna;
import heart.State;
import heart.StateElement;
import heart.WorkingMemory;
import heart.xtt.Attribute;
import heart.xtt.Rule;
import heart.xtt.Table;
import heart.xtt.Type;
import heart.xtt.XTTModel;
import jline.console.completer.Completer;

public class ParameterCompleter implements Completer{
private final SortedSet<String> strings = new TreeSet<String>();
	
	private String varName;
	private String functionName;
	
	public ParameterCompleter() {
		
	}
	
	@Override
	public int complete(final String buffer, final int cursor, final List<CharSequence> candidates) {
        // buffer could be null        				
		checkNotNull(candidates);
        if (buffer == null) {
            candidates.addAll(strings);
        }
        else {
            for (String match : strings.tailSet(buffer)) {
                if (!match.startsWith(buffer)) {
                    break;
                }

                candidates.add(match);
            }
        }

        return candidates.isEmpty() ? -1 : 0;
    }
	
	private void addParameters() {
		if(functionName.equals("WorkingMemory")) {
			strings.addAll(Haquna.modelMap.keySet());
		}
		
		if(Haquna.tableMap.containsKey(varName)) {
			Table table = Haquna.tableMap.get(varName);
			
			if(functionName.contains("getRuleByName")) {
				for(Rule r : table.getRules()) {
					strings.add(r.getName());
				}				
			}						
		}     					
		/*if(Haquna.attribiuteMap.containsKey(varName)){
			
		}
		if(Haquna.typeMap.containsKey(varName)){
					
		}   		
		if(Haquna.ruleMap.containsKey(varName)){
			
		}*/
		if(Haquna.wmMap.containsKey(varName)){
			WorkingMemory wm = Haquna.wmMap.get(varName);
			
			if(functionName.contains("showValueOf")) {
				State current = wm.getCurrentState();
				for(StateElement se : current) {
					strings.add(se.getAttributeName());
				}				
			}
		}			
		
		if(Haquna.modelMap.containsKey(varName)){
			XTTModel model = Haquna.modelMap.get(varName);
			
			if(functionName.contains("getTableByName")) {
				for(Table t : model.getTables()) {
					strings.add(t.getName());
				}				
			}
			
			if(functionName.contains("getTypeByName")) {
				for(Type t : model.getTypes()) {
					strings.add(t.getName());
				}	
			}
			
			if(functionName.contains("getAttributeByName")) {
				for(Attribute a : model.getAttributes()) {
					strings.add(a.getName());
				}				
			}
			
			if(functionName.contains("remove")) {
				for(Attribute a : model.getAttributes()) {
					strings.add(a.getName());
				}
				for(Table t : model.getTables()) {
					strings.add(t.getName());
				}	
				for(Type t : model.getTypes()) {
					strings.add(t.getName());
				}
				for(Table t : model.getTables()) {
					for(Rule r : t.getRules()) {
						strings.add(r.getName());
					}
				}
			}
		}		
    }
	
	public void setVarName(String varName) {
		this.varName = varName;
		//addFunctionNames();
	}
	
	public void setFunctionName(String functionName) {
		this.functionName = functionName;
		addParameters();
		
	}
}
