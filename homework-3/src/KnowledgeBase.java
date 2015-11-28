

import java.util.ArrayList;
import java.util.List;

public class KnowledgeBase {

	private final List<Sentence> sentences = new ArrayList<>();

	public void addSentence(final Sentence sentence) {
		sentences.add(sentence);
	}

	public List<Sentence> getSentences() {
		return sentences;
	}

	public List<Sentence> getRulesForGoal(final Predicate goal) {

		final List<Sentence> rules = new ArrayList<>();

		for(final Sentence s : sentences) {
			if(s.getImplication().getName().equals(goal.getName())
					&& s.getImplication().isNegated() == goal.isNegated()) {
				rules.add(s.clone());
			}
		}

		return rules;
	}

	public int size() {
		return sentences.size();
	}

	@Override
	public String toString() {

		final StringBuilder b = new StringBuilder();

		b.append("Knowledge Base:");

		for(final Sentence s : getSentences()) {
			b.append("\t");
			b.append(s);
			b.append("\n");
		}

		return b.toString();
	}

}
