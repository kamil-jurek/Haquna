package haquna.completer;

import haquna.Haquna;
import haquna.HaqunaMain;

public class VarNamesCompleter extends CompleterAbstract {

	@Override
	protected void setupStringCandidates() {
		stringCandidates.clear();

		HaqunaMain.log(0, this.getClass().getName(), "setupStringCandidates()", "argPos = " + argPos);
		HaqunaMain.log(1, "TEST", "buffor: ", buff);

        if(argPos > 0) {
            //if last buff char is '.' then adding var name makes no sens
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
                stringCandidates.addAll(Haquna.tableMap.keySet());
                stringCandidates.addAll(Haquna.attrMap.keySet());
            }


        } else {
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
}
