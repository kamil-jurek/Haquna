package haquna.completer;

import static jline.internal.Preconditions.checkNotNull;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import haquna.Haquna;
import jline.console.completer.Completer;

public class FunctionNameCompleter implements Completer{

	private final SortedSet<String> strings = new TreeSet<String>();
	
	private String varName;
	
	public FunctionNameCompleter() {
		
	}
	
	@Override
	public int complete(final String buffer, final int cursor, final List<CharSequence> candidates) {
		addFunctionNames();
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
	
	private void addFunctionNames() {
		strings.clear();
		
		if(Haquna.tableMap.containsKey(varName)) {
			strings.add("showRulesList()");
			strings.add("show()");
			strings.add("getRuleByName");
		}
		
		if(Haquna.attribiuteMap.containsKey(varName)){
			strings.add("show()");
			strings.add("getType()");
			strings.add("getCallback()");
		}
		
		if(Haquna.typeMap.containsKey(varName)){
			strings.add("show()");		
		}
		
		if(Haquna.ruleMap.containsKey(varName)){
			strings.add("show()");
		}
		
		if(Haquna.wmMap.containsKey(varName)){
			strings.add("showValueOf");
			strings.add("showCurrentState()");
		}
		
		if(Haquna.modelMap.containsKey(varName)){
			strings.add("show()");
			strings.add("showTablesList()");
			strings.add("showAttributesList()");
			strings.add("showTypesList()");
			strings.add("getTableByName");
			strings.add("getAttributeByName");
			strings.add("getTypeByName");
			strings.add("remove");
		}
		
		if(varName != null && varName.equals("new")){
			strings.add("WorkingMemory");
			strings.add("Type");
			
		} 
    }
	
	public void setVarName(String varName) {
		this.varName = varName;		
	}
}
