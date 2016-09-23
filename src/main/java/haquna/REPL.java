/*
 * *
 *  *
 *  *     Copyright 2013-15 by Szymon Bobek, Grzegorz J. Nalepa, Mateusz ÅšlaÅ¼yÅ„ski
 *  *
 *  *
 *  *     This file is part of HeaRTDroid.
 *  *     HeaRTDroid is a rule engine that is based on HeaRT inference engine,
 *  *     XTT2 representation and other concepts developed within the HeKatE project .
 *  *
 *  *     HeaRTDroid is free software: you can redistribute it and/or modify
 *  *     it under the terms of the GNU General Public License as published by
 *  *     the Free Software Foundation, either version 3 of the License, or
 *  *     (at your option) any later version.
 *  *
 *  *     HeaRTDroid is distributed in the hope that it will be useful,
 *  *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  *     GNU General Public License for more details.
 *  *
 *  *     You should have received a copy of the GNU General Public License
 *  *     along with HeaRTDroid.  If not, see <http://www.gnu.org/licenses/>.
 *  *
 *  *
 */

package haquna;

import jline.console.ConsoleReader;
import jline.console.completer.AggregateCompleter;
import jline.console.completer.FileNameCompleter;
import jline.console.completer.StringsCompleter;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by msl on 13/11/14.
 */
public class REPL {

    public static void main(String[] args) throws IOException {
        try {
            Character mask = null;
            String trigger = null;
            boolean color = true;

            ConsoleReader reader = new ConsoleReader();

            reader.setPrompt("prompt> ");

            reader.addCompleter(
                    new AggregateCompleter(
                            new FileNameCompleter() ,
                            new StringsCompleter(
                                    "Hakuna Matata! What a wonderful phrase",
                                    "Hakuna Matata! Ain't no passing craze",
                                    "It means no worries for the rest of your days",
                                    "It's our problem-free philosophy",
                                    "Hakuna Matata!",
                                    "Hakuna Matata?",
                                    "Yeah. It's our motto!",
                                    "What's a motto?",
                                    "Nothing. What's a-motto with you?",
                                    "Those two words will solve all your problems",
                                    "That's right. Take Pumbaa here",
                                    "Why, when he was a young warthog...",
                                    "When I was a young wart hog",
                                    "Very nice",
                                    "Thanks",
                                    "He found his aroma lacked a certain appeal",
                                    "He could clear the savannah after every meal",
                                    "I'm a sensitive soul though I seem thick-skinned",
                                    "And it hurt that my friends never stood downwind",
                                    "And oh, the shame		He was ashamed",
                                    "Thought of changin' my name	What's in a name?",
                                    "And I got downhearted		How did ya feel?",
                                    "Everytime that I...",
                                    "Hey! Pumbaa! Not in front of the kids!",
                                    "Oh. Sorry",
                                    "Hakuna Matata! What a wonderful phrase",
                                    "Hakuna Matata! Ain't no passing craze",
                                    "It means no worries for the rest of your days",
                                    "It's our problem-free philosophy",
                                    "Hakuna Matata!",
                                    "Hakuna Matata! Hakuna matata!",
                                    "Hakuna Matata! Hakuna matata!",
                                    "Hakuna Matata! Hakuna matata!",
                                    "Hakuna Matata! Hakuna--",
                                    "It means no worries for the rest of your days",
                                    "It's our problem-free philosophy",
                                    "Hakuna Matata!",
                                    "(Repeats)",
                                    "I say Hakuna",
                                    "I say Matata"
                            )));

            String line;
            PrintWriter out = new PrintWriter(reader.getOutput());

            while ((line = reader.readLine()) != null) {
                if (color){
                    out.println("\u001B[33m======>\u001B[0m\"" + line + "\"");

                } else {
                    out.println("======>\"" + line + "\"");
                }
                out.flush();

                if (line.equalsIgnoreCase("quit") || line.equalsIgnoreCase("exit")) {
                    break;
                }
                if (line.equalsIgnoreCase("cls")) {
                    reader.clearScreen();
                }
            }
        }
        catch (Throwable t) {
            t.printStackTrace();
        }
    }

}
