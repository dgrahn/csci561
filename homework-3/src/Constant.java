

import java.util.HashMap;
import java.util.Map;

/**
 * A constant.
 *
 * @author Dan Grahn
 */
public class Constant extends Element {

	/**
	 * A map of the canonical instances of constants.
	 */
	private static final Map<String, Constant> CONSTANTS = new HashMap<>();

	/**
	 * Gets the canonical instance of the constant for the specified name.
	 *
	 * @param  name the name for which to get the constant
	 * @return		the canonical instance for the constant. Will not be {@code null}.
	 */
	public static Constant get(final String name) {

		Constant c = CONSTANTS.get(name);

		if(c == null) {
			c = new Constant(name);
			CONSTANTS.put(name, c);
		}

		return c;
	}

	/**
	 * Constructs a new {@code Constant}.
	 *
	 * @param name the name of the constant. Must not be {@code null}.
	 */
	private Constant(final String name) {
		super(name);
	}

	@Override
	public String toString() {
		return "c:" + getName();
	}

}
