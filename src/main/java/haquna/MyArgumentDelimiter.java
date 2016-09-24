package haquna;

import jline.console.completer.ArgumentCompleter;

public class MyArgumentDelimiter extends ArgumentCompleter.AbstractArgumentDelimiter {
	
	@Override
	public boolean isDelimiterChar(CharSequence buffer, int pos) {
		//System.out.print("is Delimiter call ");
		char c = buffer.charAt(pos);
		return !Character.isLetter(c) && c != '_';
	}

}
