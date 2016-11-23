package haquna.completer;

import haquna.Haquna;
import haquna.HaqunaMain;

public class VarNamesCompleter extends CompleterAbstract {

	@Override
	protected void setupStringCandidates() {
		stringCandidates.clear();

		HaqunaMain.log(0, this.getClass().getName(), "setupStringCandidates()", "argPos = " + argPos);
		HaqunaMain.log(1, "TEST", "buffor: ", buff);

        stringCandidates.addAll(Haquna.modelMap.keySet());
	    stringCandidates.addAll(Haquna.tableMap.keySet());
	    stringCandidates.addAll(Haquna.attrMap.keySet());
	    stringCandidates.addAll(Haquna.typeMap.keySet());
	    stringCandidates.addAll(Haquna.ruleMap.keySet());
	    stringCandidates.addAll(Haquna.callbackMap.keySet());
	    stringCandidates.addAll(Haquna.wmMap.keySet());
	    stringCandidates.addAll(Haquna.tableBuilderMap.keySet());
	    stringCandidates.addAll(Haquna.attrBuilderMap.keySet());
	    stringCandidates.addAll(Haquna.typeBuilderMap.keySet());
	    stringCandidates.addAll(Haquna.ruleBuilderMap.keySet());
	    		
	}

}
