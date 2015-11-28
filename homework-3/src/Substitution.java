

import java.util.HashMap;

/**
 * A simple substitution list backed by a hash map.
 *
 * @author Dan Grahn
 */
public class Substitution extends HashMap<Variable, Element> {

	private static final long serialVersionUID = 1L;

	/**
	 * Gets an item from the substitution list by the name of the variable.
	 *
	 * @param  variableName the name of the variable to retrieve
	 * @return				the binding for the variable name
	 */
	public Element get(final String variableName) {
		return get(new Variable(variableName));
	}

	@Override
	public String toString() {

		final StringBuilder b = new StringBuilder();
		b.append("{");

		int i = 0;
		for(final Variable key : keySet()) {
			if(i++ != 0) b.append(", ");
			b.append(key);
			b.append("/");
			b.append(get(key));
		}

		b.append("} -> ");
		b.append(size());

		return b.toString();
	}

	@Override
	public Substitution clone() {
		final Substitution s = new Substitution();

		for(final Variable key : keySet()) {
			s.put(key, get(key));
		}

		return s;
	}

	@Override
	public boolean equals(final Object o) {

		if(!(o instanceof Substitution)) return false;

		final Substitution s = (Substitution) o;

		for(final Variable v : keySet()) {
			if(!get(v).equals(s.get(v))) return false;
		}

		return true;

	}


}
