package haquna;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import jline.console.completer.Completer;
import jline.console.completer.StringsCompleter;

public class HaqunaCompleter implements Completer {

	private final SortedSet<String> strings = new TreeSet<String>();

    public HaqunaCompleter() {
        // empty
    }

    public HaqunaCompleter(final Collection<String> strings) {
        //checkNotNull(strings);
        getStrings().addAll(strings);
    }

    public HaqunaCompleter(final String... strings) {
        this(Arrays.asList(strings));
    }

    public Collection<String> getStrings() {
        return strings;
    }

    public int complete(final String buffer, final int cursor, final List<CharSequence> candidates) {
        // buffer could be null
       // checkNotNull(candidates);
    	addVarNames();
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
            
            if(checkType(buffer).equals("model")) {
            	candidates.add(buffer+".showTableList()");
            	candidates.add(buffer+".showTypeList()");
            	candidates.add(buffer+".showAttributeList()");
            	strings.add(buffer+".showTableList()");
            	strings.add(buffer+".showTypeList()");
            	strings.add(buffer+".showAttributeList()");
            }
            
            if(checkType(buffer).equals("wm")) {
            	candidates.add(buffer+".showValueOf('");
            	candidates.add(buffer+".setValueOf('");
            	strings.add(buffer+".showValueOf('");
            	strings.add(buffer+".setValueOf('");
            }
        }

        return candidates.isEmpty() ? -1 : 0;
    }
    
    private void addVarNames() {
    	strings.addAll(Haquna.modelMap.keySet());
        strings.addAll(Haquna.tableMap.keySet());
        strings.addAll(Haquna.attribiuteMap.keySet());
        strings.addAll(Haquna.typeMap.keySet());
        strings.addAll(Haquna.ruleMap.keySet());
        strings.addAll(Haquna.callbackMap.keySet());
        strings.addAll(Haquna.wmMap.keySet());
    }
    
    private String checkType(String varName) {
    	if(Haquna.modelMap.containsKey(varName)) {
    		return "model";
    	
    	} else if(Haquna.wmMap.containsKey(varName)) {
    		return "wm";
     		
    	} else {
    		return "nullo";
    	}
    	
    }
}