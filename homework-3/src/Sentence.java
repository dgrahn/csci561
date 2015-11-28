

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A sentence. Assumes format "A ^ B => C" or "D".
 *
 * @author Dan Grahn
 */
public class Sentence {

	private Predicate implication;
	private String original;
	private final List<Predicate> conditions = new ArrayList<>();
	private final Map<String, Variable> variables = new HashMap<>();

	Variable getVariable(final String name) {

		if(!variables.containsKey(name)) {
			variables.put(name, new Variable(name));
		}

		return variables.get(name);
	}

	/**
	 * Constructs a new {@code Sentence}. If the sentence is a fact, the implication should be the
	 * only element in the sentence.
	 *
	 * @param sentence the string form of the sentence
	 */
	public Sentence(final String sentence) {

		this.original = sentence;


		final String[] fragments = sentence.split(" ");

		this.implication = new Predicate(this, fragments[fragments.length - 1]);

		for(int i = 0; i < fragments.length - 2; i++) {
			if("^".equals(fragments[i])) continue;
			addCondition(new Predicate(this, fragments[i]));
		}
	}

	Sentence() {
		this.original = "";
		this.implication = null;
	}

	public void standardize() {
		for(final Variable v : variables.values()) v.standardize();
	}

	/**
	 * Checks if the sentence is fact. It is a fact if there are no conditions.
	 *
	 * @return {@code true} if the sentence is a fact
	 */
	public boolean isFact() {
		return conditions.isEmpty();
	}

	public List<Predicate> getConditions() {
		return new ArrayList<Predicate>(conditions);
	}

	public Sentence apply(final Substitution sub) {

		final Sentence s = clone();

		s.implication.apply(sub);

		for(int i = 0; i < s.conditions.size(); i++) {
			s.conditions.set(i, s.conditions.get(i).apply(sub));
		}

		return s;
	}

	/**
	 * Gets the implication of the sentence.
	 *
	 * @return the implicate
	 */
	public Predicate getImplication() {
		return implication;
	}

	/**
	 * Adds a condition to the sentence. If the condition is {@code true}, the implication is
	 * {@code true}.
	 *
	 * @param condition the condition. Must not be {@code null}.
	 */
	public void addCondition(final Predicate condition) {
		this.conditions.add(condition);
	}

	@Override
	public String toString() {

		final StringBuilder b = new StringBuilder();

		b.append(isFact() ? "F: " : "I: ");

		for(int i = 0; i < conditions.size(); i++) {
			if(i != 0) b.append(" ^ ");
			b.append(conditions.get(i));
		}

		if(!conditions.isEmpty()) {
			b.append(" => ");
		}

		b.append(implication);

		return b.toString();
	}

	@Override
	public Sentence clone() {

		final Sentence s = new Sentence();
		s.implication = implication.clone(s);
		s.original = original;
		for(final Predicate c : conditions) s.conditions.add(c.clone(s));

		return s;
		//return new Sentence(original);
	}

	@Override
	public int hashCode() {
		return original.hashCode();
	}

	@Override
	public boolean equals(final Object o) {

		return o instanceof Sentence && original.equals(((Sentence) o).original);
	}

}
