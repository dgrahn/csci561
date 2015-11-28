

/**
 * A variable.
 *
 * @author Dan Grahn
 */
public class Variable extends Element {

	private static int counter = 1;

	public void standardize() {
		this.name = "x" + counter++;
	}

	/**
	 * Checks if the name matches the format of a variable.
	 *
	 * @param  name the name to check. Must not be {@code null}.
	 * @return		{@code true} if the name matches the variable format
	 */
	public static boolean isVariable(final String name) {
		return name.matches("[a-z]");
	}

	/**
	 * Constructs a new variable.
	 *
	 * @param name the name of the variable. Should be local to a sentence. Must not be {@code null}.
	 */
	public Variable(final String name) {
		super(name);
	}

	@Override
	public String toString() {
		return "v:" + getName();
	}

}
