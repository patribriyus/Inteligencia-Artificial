package aima.gui.nqueens.csp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

import aima.core.search.csp.CSP;

/**
 * @author Patricia Briones Yus, 735576
 * 
 */

public class NQueensAssignment {
	/**
	 * Contains all assigned variables. Positions reflect the the order in which
	 * the variables were assigned to values.
	 */
	List<NQueensVariable> variables;
	/** Maps variables to their assigned values. */
	Hashtable<NQueensVariable, Object> variableToValue;

	public NQueensAssignment() {
		variables = new ArrayList<NQueensVariable>();
		variableToValue = new Hashtable<NQueensVariable, Object>();
	}

	public List<NQueensVariable> getVariables() {
		return Collections.unmodifiableList(variables);
	}

	public Object getAssignment(NQueensVariable var) {
		return variableToValue.get(var);
	}

	public void setAssignment(NQueensVariable var, Object value) {
		if (!variableToValue.containsKey(var))
			variables.add(var);
		variableToValue.put(var, value);
	}

	public void removeAssignment(NQueensVariable var) {
		if (hasAssignmentFor(var)) {
			variables.remove(var);
			variableToValue.remove(var);
		}
	}

	public boolean hasAssignmentFor(NQueensVariable var) {
		return variableToValue.get(var) != null;
	}

	/**
	 * Returns true if this assignment does not violate any constraints of
	 * <code>constraints</code>.
	 */
	public boolean isConsistent(List<NQueensConstraint> constraints) {
		for (NQueensConstraint cons : constraints)
			if (!cons.isSatisfiedWith(this))
				return false;
		return true;
	}

	/**
	 * Returns true if this assignment assigns values to every variable of
	 * <code>vars</code>.
	 */
	public boolean isComplete(List<NQueensVariable> vars) {
		for (NQueensVariable var : vars) {
			if (!hasAssignmentFor(var))
				return false;
		}
		return true;
	}

	/**
	 * Returns true if this assignment assigns values to every variable of
	 * <code>vars</code>.
	 */
	public boolean isComplete(NQueensVariable[] vars) {
		for (NQueensVariable var : vars) {
			if (!hasAssignmentFor(var))
				return false;
		}
		return true;
	}

	/**
	 * Returns true if this assignment is consistent as well as complete with
	 * respect to the given CSP.
	 */
	public boolean isSolution(CSP csp) {
		return isConsistent(csp.getConstraints())
				&& isComplete(csp.getVariables());
	}

	public NQueensAssignment copy() {
		NQueensAssignment copy = new NQueensAssignment();
		for (NQueensVariable var : variables) {
			copy.setAssignment(var, variableToValue.get(var));
		}
		return copy;
	}

	@Override
	public String toString() {
		boolean comma = false;
		StringBuffer result = new StringBuffer("{");
		for (NQueensVariable var : variables) {
			if (comma)
				result.append(", ");
			result.append(var + "=" + variableToValue.get(var));
			comma = true;
		}
		result.append("}");
		return result.toString();
	}
}