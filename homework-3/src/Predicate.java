

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A predicate.
 *
 * @author Dan Grahn
 */
public class Predicate extends Element {

	private boolean isNegated = false;
	private final List<Element> arguments = new ArrayList<>();
	private Sentence sentence;

	public static Predicate parse(final String p) {
		return new Predicate(new Sentence(), p);
	}

	public Predicate clone(final Sentence s) {
		final Predicate p = new Predicate(name);
		p.sentence = s;
		p.isNegated = isNegated;

		for(final Element e : arguments) {
			p.arguments.add(e instanceof Variable ? s.getVariable(e.getName()) : e);
		}

		return p;
	}


	public Predicate(final Sentence s, final String p) {

		final Matcher m = Pattern.compile("(~?)(\\w+)\\(([\\w,]+)\\)").matcher(p);
		m.find();

		this.sentence = s;
		this.name = m.group(2);
		setNegated(!m.group(1).isEmpty());

		for(final String arg : m.group(3).split(",")) {
			addArgument(Variable.isVariable(arg) ? s.getVariable(arg) : Constant.get(arg));
		}
	}

	/**
	 * Constructs a new {@code Predicate}.
	 *
	 * @param name the name of the predicate. Must not be {@code null}.
	 */
	public Predicate(final String name) {
		super(name);
	}

	/**
	 * Adds an argument to the predicate.
	 *
	 * @param argument the argument. Must be a {@code Variable} or {@code Constant}.
	 */
	public void addArgument(final Element argument) {
		arguments.add(argument);
	}

	/**
	 * Gets the arguments.
	 *
	 * @return the arguments. Will not be {@code null}.
	 */
	public List<Element> getArguments() {
		return new ArrayList<>(arguments);
	}

	/**
	 * Sets whether the predicate is negated.
	 *
	 * @param negated {@code true} if the predicate is negated
	 */
	public void setNegated(final boolean negated) {
		this.isNegated = negated;
	}

	/**
	 * Checks whether the predicate is negated.
	 *
	 * @return {@code true} if the predicate is negated
	 */
	public boolean isNegated() {
		return isNegated;
	}

	@Override
	public String toString() {

		final StringBuilder b = new StringBuilder();

		b.append(isNegated() ? "~" : "");
		b.append(getName());
		b.append("(");

		for(int i = 0; i < arguments.size(); i++) {
			if(i != 0) b.append(",");
			b.append(arguments.get(i));
		}

		b.append(")");

		return b.toString();
	}

	@Override
	public int hashCode() {
		return System.identityHashCode(this);
	}

	@Override
	public boolean equals(final Object o) {
		return this == o;
	}

	public Predicate apply(final Substitution s) {

		final Predicate p = clone(sentence);

		for(int i = 0; i < p.arguments.size(); i++) {

			final Element e = p.arguments.get(i);
			if(!(e instanceof Variable)) continue;
			if(!s.containsKey(e)) continue;

			p.arguments.set(i, s.get(e));
		}

		return p;
	}

}
