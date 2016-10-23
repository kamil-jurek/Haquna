package haquna.completer;

import java.util.Arrays;
import java.util.LinkedList;

public class HaqunaDelimiter extends MyArgumentCompleter.AbstractArgumentDelimiter {	
	private LinkedList<Character> delimiters;
	
	public HaqunaDelimiter(final Character ... delims) {
		this.delimiters = new LinkedList<Character>();
		this.delimiters.addAll(Arrays.asList(delims));
		
		//this.delimiters.add("=");
		this.delimiters.add(' ');
		
	}
	
	@Override
	public boolean isDelimiterChar(CharSequence buffer, int pos) {
		//System.out.print("isDelimiter call ");
		char c = buffer.charAt(pos);
		
		for(Character s : delimiters) {		
			if(s.equals(c)) {
				return true;
			}
		}
		
		return false;
		//return !Character.isLetter(c) && c != '_';
	}
}
