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
	// Se satisface cuando dos reinas no están en la misma fila ni diagonal
	public boolean isSatisfiedWith(Assignment assignment) {
		Object value1 = assignment.getAssignment(var1);
		Object value2 = assignment.getAssignment(var2);
		
		return !sonDiagonal(var1.getCol(), (int)value1, var2.getCol(), (int)value2) && !value1.equals(value2);
	}
	
	private boolean sonDiagonal(int i, int k, int j, int s) {
	   	if(k < s) return Math.abs(j-i)+k == s;
	   	else return Math.abs(j-i)+s == k;
    }
}