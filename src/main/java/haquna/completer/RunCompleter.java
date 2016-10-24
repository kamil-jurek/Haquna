package haquna.completer;

import static jline.internal.Preconditions.checkNotNull;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import haquna.Haquna;
import heart.xtt.Table;
import jline.console.completer.Completer;

public class RunCompleter implements Completer{

	private final SortedSet<String> strings = new TreeSet<String>();
	
	private String prevArg;
	private String modelName = null;
	
	public RunCompleter() {
		
	}
	
	@Override
	public int complete(final String buffer, final int cursor, final List<CharSequence> candidates) {
		addRuns();
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
	
	private void addRuns() {
		strings.clear();
		
		if(modelName != null && Haquna.modelMap.containsKey(modelName)) {
			for(Table t : Haquna.modelMap.get(modelName).getTables()) {
				strings.add(t.getName());
			}
		}

		if(prevArg.equals("run")) {
			for(String k : Haquna.modelMap.keySet()) {
				strings.add(k);
			}
		}
		
		if(prevArg.matches(Haquna.varName)) {
			for(String k : Haquna.wmMap.keySet()) {				
				strings.add(k);
				modelName = prevArg;	
			}			
		}
		
		if(prevArg.equals("mode")){
			strings.add("gdi");
			strings.add("foi");
			strings.add("ddi");
		}
		
		else if(prevArg.equals("token")){
			strings.add("true");
			strings.add("false");
		}
		
		else if(prevArg.equals("uncertainty")){
			strings.add("true");
			strings.add("false");
		}
		
		else if(prevArg.equals("conflict_strategy")){
			strings.add("first");
			strings.add("last");
			strings.add("all");
		}
		
		else {
			strings.add("mode");
			strings.add("token");
			strings.add("uncertainty");
			strings.add("conflict_stratrgy");
		}
    }
	
	public void setVarName(String varName) {
		this.prevArg = varName;		
	}
}
