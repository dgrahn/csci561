

import java.util.List;

/**
 * General purpose unifier.
 *
 * @author Dan Grahn
 */
public final class Unifier {

	private Unifier() { }

	/**
	 * Unify X and Y. Handles variables, predicates, and List<Element>s.
	 *
	 * @param  x the first unifier
	 * @param  y the second unifier
	 * @return   a substitution if x and y could be unified. {@code null} on failure
	 */
	public static Substitution unify(final Object x, final Object y) {
		return unify(x, y, new Substitution());
	}

	/**
	 * Unify X and Y. Handles variables, predicates, and List<Element>s.
	 *
	 * @param  x the first unifier
	 * @param  y the second unifier
	 * @param  s the current substitution list
	 * @return   a substitution if x and y could be unified. {@code null} on failure
	 */
	@SuppressWarnings("unchecked")
	public static Substitution unify(final Object x, final Object y, final Substitution s) {

		//System.out.println("Unify(" + x + ", " + y + ", " + s + ")");

		if(s == null) return null;
		if(x.equals(y)) return s;
		if(x instanceof Variable) return unifyVar((Variable) x, y, s);
		if(y instanceof Variable) return unifyVar((Variable) y, x, s);

		if(x instanceof Predicate && y instanceof Predicate) {
			final Predicate px = (Predicate) x;
			final Predicate py = (Predicate) y;

			if(px.isNegated() != py.isNegated()) return null;

			return unify(px.getArguments(), py.getArguments(), unify(px.getName(), py.getName(), s));
		}

		if(x instanceof List<?> && y instanceof List<?>) {
			final List<Element> lx = (List<Element>) x;
			final List<Element> ly = (List<Element>) y;
			if(lx.isEmpty() || ly.isEmpty()) return null;
			return unify(lx.subList(1, lx.size()), ly.subList(1, ly.size()), unify(lx.get(0), ly.get(0), s));
		}

		return null;
	}

	private static Substitution unifyVar(final Variable var, final Object x, final Substitution s) {

		//System.out.println("UnifyVar(" + var + ", " + x + ", " + s + ")");

		if(s.containsKey(var)) return unify(s.get(var), x, s);
		else if(s.containsKey(x)) return unify(var, s.get(x), s);
		else s.put(var, (Element) x);
		return s;
	}

}
