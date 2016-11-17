package haquna.completer;

import haquna.Haquna;

public class ModelNamesCompleter extends CompleterAbstract {

	@Override
	protected void setupStringCandidates() {
		stringCandidates.clear();		
		stringCandidates.addAll(Haquna.modelMap.keySet());		
	}

}
