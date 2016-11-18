package haquna.completer;

import static jline.internal.Preconditions.checkNotNull;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import haquna.HaqunaSingleton;
import jline.console.completer.Completer;

public class VarNamesCompleter implements Completer {
	
	private final SortedSet<String> strings = new TreeSet<String>();
	
	public VarNamesCompleter() {
		addVarNames();
	}
	
	@Override
	public int complete(final String buffer, final int cursor, final List<CharSequence> candidates) {
		addVarNames();
		
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
	
	private void addVarNames() {
		strings.clear();
		
    	strings.addAll(HaqunaSingleton.modelMap.keySet());
        strings.addAll(HaqunaSingleton.tableMap.keySet());
        strings.addAll(HaqunaSingleton.attrMap.keySet());
        strings.addAll(HaqunaSingleton.typeMap.keySet());
        strings.addAll(HaqunaSingleton.ruleMap.keySet());
        strings.addAll(HaqunaSingleton.callbackMap.keySet());
        strings.addAll(HaqunaSingleton.wmMap.keySet());
        strings.addAll(HaqunaSingleton.tableBuilderMap.keySet());
        strings.addAll(HaqunaSingleton.attrBuilderMap.keySet());
        strings.addAll(HaqunaSingleton.typeBuilderMap.keySet());
        strings.addAll(HaqunaSingleton.ruleBuilderMap.keySet());
    }

}
