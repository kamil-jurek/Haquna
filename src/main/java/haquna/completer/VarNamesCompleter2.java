package haquna.completer;

import haquna.HaqunaSingleton;

public class VarNamesCompleter2 extends CompleterAbstract {

	@Override
	protected void setupStringCandidates() {
		stringCandidates.clear();
		
		stringCandidates.addAll(HaqunaSingleton.modelMap.keySet());
	    stringCandidates.addAll(HaqunaSingleton.tableMap.keySet());
	    stringCandidates.addAll(HaqunaSingleton.attrMap.keySet());
	    stringCandidates.addAll(HaqunaSingleton.typeMap.keySet());
	    stringCandidates.addAll(HaqunaSingleton.ruleMap.keySet());
	    stringCandidates.addAll(HaqunaSingleton.callbackMap.keySet());
	    stringCandidates.addAll(HaqunaSingleton.wmMap.keySet());
	    stringCandidates.addAll(HaqunaSingleton.tableBuilderMap.keySet());
	    stringCandidates.addAll(HaqunaSingleton.attrBuilderMap.keySet());
	    stringCandidates.addAll(HaqunaSingleton.typeBuilderMap.keySet());
	    stringCandidates.addAll(HaqunaSingleton.ruleBuilderMap.keySet());
	    		
	}

}
