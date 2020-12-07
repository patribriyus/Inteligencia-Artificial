package aima.gui.nqueens.csp;

import aima.core.search.csp.Variable;

/**
 * @author Patricia Briones Yus, 735576
 * 
 */

public class NQueensVariable extends Variable{
	private int columna;

	public NQueensVariable(String name, int columna) {
		super(name);
		this.columna = columna;
	}

	public int getColumna() {
		return columna;
	}

	public void setColumna(int columna) {
		this.columna = columna;
	}
}
