

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BackwardChaining {

	private final KnowledgeBase kb;

	private final Map<Sentence, List<String>> loopDetector = new HashMap<>();

	public BackwardChaining(final KnowledgeBase kb) {
		this.kb = kb;
	}

	public List<Substitution> ask(final Sentence query) {

		return ask(Arrays.asList(query.getImplication()), new Substitution());
	}

	//Function FOL-BC-Ask(KB,goals,@) returns a set of substitutions
	//	inputs: KB, a knowledge base
	//			goals, a list of conjuncts forming a query (@ already applied)
	//			@, the current substitution, initially the empty subs. { }
	private List<Substitution> ask(final List<Predicate> goals, final Substitution s) {

		//local variables: answers, a set of substitutions, initially empty { }
		final List<Substitution> answers = new ArrayList<>();

		//if goals is empty then return {@}
		if(goals.isEmpty()) {
			return Arrays.asList(s);
		}

		//q' <- Subst(@,First(goals))
		final Predicate qp = goals.get(0).apply(s);

		//for each sentence r in KB where STANDARDIZE-APART(r)=(p1 ^...^pn => q)
		for(final Sentence rule : kb.getRulesForGoal(qp)) {

			rule.standardize();

			//and @' <- Unify (q,q') succeeds
			final Substitution sp = Unifier.unify(rule.getImplication(), qp);
			if(sp == null) continue;


			// If we haven't been to this sentence already, initialize the list
			if(!loopDetector.containsKey(rule)) loopDetector.put(rule, new ArrayList<>());

			// Check for a loop
			boolean beenHere = !rule.getConditions().isEmpty();

			for(final Predicate p : rule.getConditions()) {
				final Predicate ps = p.apply(sp);

				if(!loopDetector.get(rule).contains(ps.toString())) beenHere = false;

				loopDetector.get(rule).add(ps.toString());
			}

			if(beenHere) continue;

			//new_goals <- [p1, lll, pn|Rest(goals)]
			final List<Predicate> newGoals = new ArrayList<>();
			for(final Predicate p : rule.getConditions()) newGoals.add(p.apply(sp).apply(s));
			for(final Predicate p : goals.subList(1, goals.size())) newGoals.add(p.apply(sp).apply(s));

			// new_sub <- Compose(@',@)
			final Substitution newSub = sp.clone();
			for(final Variable v : s.keySet()) newSub.put(v, s.get(v));

			//answers <- FOL-BC-Ask(KB, new_goals, new_sub) U answers
			final List<Substitution> newAnswers = ask(newGoals, newSub);
			answers.addAll(newAnswers);
		}

		//return answers
		return answers;
	}

}
