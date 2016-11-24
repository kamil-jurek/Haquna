package haquna.completer;

import haquna.Haquna;
import haquna.HaqunaMain;

public class ModelNamesCompleter extends CompleterAbstract {

	@Override
	protected void setupStringCandidates() {
		stringCandidates.clear();

		if(argPos > 0) {
			int index = 0;
			for(int i = 0; i < argPos; i++) {
				index += arguments[i].length();
				if(buff.charAt(index) == ' ' ||
						(buff.length() < index+1 && buff.charAt(index+1) == '=')) {
					index++;
				}
			}
			HaqunaMain.log(1, "TEST", "buffor[index]: ", buff.charAt(index) + " ");
			if(buff.charAt(index) != '.') {
				stringCandidates.addAll(Haquna.modelMap.keySet());
			}


		} else {
			stringCandidates.addAll(Haquna.modelMap.keySet());
		}
	}

}
