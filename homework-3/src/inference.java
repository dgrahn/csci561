

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * The entry point for the FOL engine.
 *
 * @author Dan Grahn
 */
public class inference {

	private final KnowledgeBase kb = new KnowledgeBase();

	/**
	 * Runs the engine on the specified file.
	 *
	 * @param  path the path to the file
	 * @throws IOException
	 */
	public inference(final Path path) throws IOException {

		final List<String> lines = Files.readAllLines(path);

		final int numberOfQueries   = Integer.parseInt(lines.get(0));
		final int numberOfSentences = Integer.parseInt(lines.get(numberOfQueries + 1));
		final List<String> queries  = lines.subList(1, numberOfQueries + 1);
		final List<String> setences = lines.subList(numberOfQueries + 2, lines.size());
		final FileWriter writer = new FileWriter("output.txt");

		System.out.println("  Number of Queries = " + numberOfQueries);
		System.out.println("Number of Sentences = " + numberOfSentences);

		for(final String sentence : setences) {
			kb.addSentence(new Sentence(sentence));
		}

		for(final String q : queries) {
			final Sentence s = new Sentence(q);
			final List<Substitution> result = new BackwardChaining(kb).ask(s);
			writer.write(Boolean.toString(!result.isEmpty()).toUpperCase() + "\r\n");
			writer.flush();
			System.out.println(s + " ==> " + result);
		}

		writer.close();
		System.out.println("Done");
	}


	public static void main(final String[] args) throws IOException {

		new inference(new File(args[1]).toPath());
	}

}
