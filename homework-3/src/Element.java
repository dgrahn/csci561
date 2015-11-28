

/**
 * An element of a sentence. Either a constant, predicate, or variable.
 *
 * @author Dan Grahn
 */
public class Element implements Cloneable {

	protected String name;

	/**
	 * Constructs a new {@code Element}.
	 *
	 * @param name the name of the element. Must not be {@code null}.
	 */
	Element(final String name) {
		this.name = name;
	}

	Element() {

	}

	/**
	 * Sets the name for the element.
	 *
	 * @param name the new name for the element
	 */
	public void setName(final String name) {
		this.name = name;
	}

	/**
	 * Gets the name of the element.
	 *
	 * @return the name of the element. Will not be {@code null}.
	 */
	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		return getName().hashCode();
	}

	@Override
	public boolean equals(final Object o) {
		if(o instanceof Element) return getName().equals(((Element) o).getName());
		if(o instanceof String) return getName().equals(o);
		else return super.equals(o);
	}

}
