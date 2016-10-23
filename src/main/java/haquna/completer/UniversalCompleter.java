package haquna.completer;

import static jline.internal.Preconditions.checkNotNull;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import haquna.Haquna;
import jline.console.completer.Completer;


public final class UniversalCompleter implements Completer
{	
	private final SortedSet<String> strings = new TreeSet<String>();
	
	public UniversalCompleter() {
		addVarNames();
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

        return 0;
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
}