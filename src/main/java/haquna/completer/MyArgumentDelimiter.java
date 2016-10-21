package haquna.completer;

import jline.console.completer.ArgumentCompleter;

public class MyArgumentDelimiter extends MyArgumentCompleter.AbstractArgumentDelimiter {
	
	@Override
	public boolean isDelimiterChar(CharSequence buffer, int pos) {
		//System.out.print("is Delimiter call ");
		char c = buffer.charAt(pos);
		return !Character.isLetter(c) && c != '_';
	}

}
