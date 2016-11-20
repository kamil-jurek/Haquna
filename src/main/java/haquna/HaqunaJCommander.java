package haquna;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.internal.Lists;

import java.util.ArrayList;
import java.util.List;

public class HaqunaJCommander {

    public static class JCommanderConfiguration {
        @Parameter
        public List<String> parameters = Lists.newArrayList();

        @Parameter(
                names = {"--model"},
                description = "path to the HMR file",
                required = true
        )
        private String modelPath;

        @Parameter(
                names = {"--tables"},
                description = " Order of tables used in the inference - comma separated",
                required = true
        )
        private String tables = "[]";

        @Parameter(
                names = {"--inference"},
                description = "inference mode",
                required = true
        )
        private String inference = "ddi";

        @Parameter(
                names = {"--state"},
                description = "Initial values of attributes - comma separated"
        )
        private String attributes = null;

        @Parameter(
                names = {"--conflict_strategy"},
                description = "conflict strategy"
        )
        private String conflictStrategy = "all";

        @Parameter(
                names = {"--help"},
                help = true,
                description = "display help (this message)"
        )
        private boolean help;

        @Parameter(
                names = {"--console"},
                help = true,
                description = "run "
        )
        private String scriptPath;

        public JCommanderConfiguration() {
        }
    }

    public static void commandline(String[] args, Haquna haquna) {
        JCommanderConfiguration jcc = new HaqunaJCommander.JCommanderConfiguration();
        JCommander jcomm = new JCommander(jcc, args);

        ArrayList<String> cmds = new ArrayList<String>();

        if(jcc.help) {
            jcomm.usage();
            HaqunaMain.startConsole = false;

        } else if(jcc.scriptPath != null) {
            HaqunaScript hs = new HaqunaScript(jcc.scriptPath);
            hs.executeScriptCmds(haquna);
            HaqunaMain.startConsole = true;

        } else {
                String cmd;
                cmd = "Mod = new Model('" + jcc.modelPath + "')";
                cmds.add(cmd);

                cmd ="Wm = new WorkingMemory(Mod)";
                cmds.add(cmd);

                if(jcc.attributes != null) {
                    String attrs = jcc.attributes;
                    attrs = attrs.replace("[", "");
                    attrs = attrs.replace("]", "");
                    attrs = attrs.replace(" ", "");

                    String[] splited = attrs.split("[=|,]");
                    for(int i = 0; i < splited.length; i=i+2 ) {
                        String argName = splited[i];
                        String argVal = splited[i+1];

                        cmd = "Wm.setValueOf('" + argName + "','" + argVal + "')";
                        cmds.add(cmd);
                    }
                }

                String tabs = jcc.tables;
                tabs = tabs.replace("[", "['");
                tabs = tabs.replace("]", "']");
                tabs = tabs.replace(",", "','");


                cmd = "Mod.run(Wm, mode=" + jcc.inference + ", " + "conflict_strategy=" + jcc.conflictStrategy+ ", " + tabs + ")";
                cmds.add(cmd);

                cmd = "Wm.showCurrentState()";
                cmds.add(cmd);


                for(String c : cmds) {
                    haquna.executeCmd(c);
                }
                HaqunaMain.startConsole = false;
            }

    }
}
