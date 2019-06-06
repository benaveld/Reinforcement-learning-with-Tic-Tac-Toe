//Gruppnummer: 8
//Benjamin Avery
//Ludvig Svee
//Sebastian Gieser

package game.ai;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.LinkedList;

import game.XOEnum;

public class IOAI {
	public static File saveFile = new File("AI_save.dat");

	public static void save(LearningPlayer ai) throws FileNotFoundException, UnsupportedEncodingException {
		PrintWriter writer = new PrintWriter(saveFile, "UTF-8");
		for (ProbiliblityTable prob : ai.states) {
			writer.println(prob.toString());
		}
		writer.close();
	}

	public static Collection<ProbiliblityTable> load(XOEnum me) throws IOException {
		Collection<ProbiliblityTable> states = new LinkedList<ProbiliblityTable>();
		if (saveFile.exists()) {
			BufferedReader br = new BufferedReader(new FileReader(saveFile));
			String line;
			while ((line = br.readLine()) != null) {
				int i = 0;
				ProbiliblityTable prob = new ProbiliblityTable();
				// boolean part1 = true;
				String parts[] = line.split(";");

				for (char c : parts[0].toCharArray()) {
					switch (c) {
					case ' ':
						prob.boardState[i++] = XOEnum.empty;
						break;

					case 'X':
						prob.boardState[i++] = XOEnum.X;
						break;

					case 'O':
						prob.boardState[i++] = XOEnum.O;
						break;

					default:
						throw new IOException("Not compatible data file.");
					}
				}
				
				String[] strProbs = parts[1].split(",");
				if(strProbs.length != prob.probs.length) {
					throw new IOException("Not compatible data file.");
				}
				for(i = 0; i < prob.probs.length; i++) {
					prob.probs[i] = Integer.parseInt(strProbs[i]);
				}
				states.add(prob);
			}
			br.close();
			System.out.println("Loaded save file.");
		}
		return states;
	}
}
