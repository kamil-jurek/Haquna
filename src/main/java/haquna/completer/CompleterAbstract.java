package haquna.completer;

import static jline.internal.Preconditions.checkNotNull;

import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import jline.console.completer.Completer;

public abstract class CompleterAbstract implements Completer {
	protected final SortedSet<String> stringCandidates = new TreeSet<String>();
	protected String[] arguments;
	protected int argPos;
	
	public int complete(String buffer, int cursor, List<CharSequence> candidates){					
		setupStringCandidates();
		
		// buffer could be null
        checkNotNull(candidates);
        if (buffer == null) {
            candidates.addAll(stringCandidates);
        
        } else {
            for (String match : stringCandidates.tailSet(buffer)) {
                if (!match.startsWith(buffer)) {
                    break;
                }

                candidates.add(match);
            }
        }

        return candidates.isEmpty() ? -1 : 0;
    }	
	
	public void setContext(String[] args, int argP) {
		this.arguments = args;
		this.argPos = argP;		
	}
	
	abstract protected void setupStringCandidates();
}
