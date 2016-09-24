package haquna;

import jline.console.completer.AggregateCompleter;
import jline.console.completer.ArgumentCompleter;

public class MyArgumentCompleter extends ArgumentCompleter{
	
	public MyArgumentCompleter(AggregateCompleter aggComp) {
		super(aggComp);
	}

	public abstract static class AbstractArgumentDelimiter extends ArgumentCompleter.AbstractArgumentDelimiter {
		
		@Override
		public boolean isDelimiterChar(CharSequence buffer, int pos) {
			System.out.print("is Delimiter call ");
			char c = buffer.charAt(pos);
			return !Character.isLetter(c) && c != '_' && c != '=';
		}
	}		
}
