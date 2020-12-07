package aima.gui.nqueens.csp;

import java.util.ArrayList;
import java.util.List;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.Constraint;
import aima.core.search.csp.Variable;

/**
 * @author Patricia Briones Yus, 735576
 * 
 */

public class NQueensConstraint implements Constraint {

	private NQueensVariable var1;
	private NQueensVariable var2;
	private List<Variable> scope;

	public NQueensConstraint(Variable var1, Variable var2) {
		this.var1 = (NQueensVariable) var1;
		this.var2 = (NQueensVariable) var2;
		scope = new ArrayList<Variable>(2);
		scope.add(var1);
		scope.add(var2);
	}

	@Override
	public List<Variable> getScope() {
		return scope;
	}

	@Override
	public boolean isSatisfiedWith(Assignment assignment) {
		Object value1 = assignment.getAssignment(var1);
		return value1 == null || !value1.equals(assignment.getAssignment(var2));
	}
}