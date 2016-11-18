package haquna.completer;

import haquna.HaqunaSingleton;
public class ModelNamesCompleter extends CompleterAbstract {

	@Override
	protected void setupStringCandidates() {
		stringCandidates.clear();		
		stringCandidates.addAll(HaqunaSingleton.modelMap.keySet());		
	}

}
