package haquna.completer;

import haquna.Haquna;
import haquna.HaqunaMain;
import heart.xtt.Table;

import java.util.Arrays;
import java.util.List;

public class RunCompleter2 extends CompleterAbstract {
    @Override
    protected void setupStringCandidates() {
        stringCandidates.clear();

        HaqunaMain.log(0, this.getClass().getName(), "setupStringCandidates()", "argPos = " + argPos);
        HaqunaMain.log(0, this.getClass().getName(), "setupStringCandidates()", "arguments = " + Arrays.toString(arguments));

        if(argPos == 2 && CompleterMenager.getLastDelimiter(arguments,buff,argPos) == '.') {
            stringCandidates.add("run");
        }

        if(argPos > 2) {
            String prevArg = arguments[argPos - 1];
            if (prevArg.equals("mode")) {
                stringCandidates.add("gdi");
                stringCandidates.add("foi");
                stringCandidates.add("ddi");
            } else if (prevArg.equals("token")) {
                stringCandidates.add("true");
                stringCandidates.add("false");
            } else if (prevArg.equals("uncertainty")) {
                stringCandidates.add("true");
                stringCandidates.add("false");
            } else if (prevArg.equals("conflict_strategy")) {
                stringCandidates.add("first");
                stringCandidates.add("last");
                stringCandidates.add("all");
            }
            else {
                if(buff.contains("[")) {
                    for (Table t : Haquna.modelMap.get(arguments[1]).getTables()) {
                        if (!Arrays.asList(arguments).contains(t.getName())) {
                            stringCandidates.add(t.getName());
                        }
                    }
                } else {
                    List<String> args = Arrays.asList(arguments);
                    if (!args.contains("mode")) stringCandidates.add("mode");
                    if (!args.contains("token")) stringCandidates.add("token");
                    if (!args.contains("uncertainty")) stringCandidates.add("uncertainty");
                    if (!args.contains("conflict_strategy")) stringCandidates.add("conflict_strategy");
                }


            }
        }

    }
}
