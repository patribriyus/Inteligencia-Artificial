package aima.gui.nqueens.csp;

import aima.core.search.csp.Variable;

/**
 * @author Patricia Briones Yus, 735576
 * 
 */

public class NQueensVariable extends Variable{
	private int col;

	public NQueensVariable(String name, int col) {
		super(name);
		this.col = col;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}
}
