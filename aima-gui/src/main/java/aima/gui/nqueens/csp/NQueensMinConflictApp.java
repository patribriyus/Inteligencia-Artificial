package aima.gui.nqueens.csp;

import aima.core.environment.nqueens.NQueensBoard;
import aima.core.search.csp.Assignment;
import aima.core.search.csp.MinConflictsStrategy;
import aima.core.search.csp.SolutionStrategy;
import aima.core.search.csp.Variable;
import aima.core.util.datastructure.XYLocation;

/**
 * @author Patricia Briones Yus, 735576
 * 
 */

public class NQueensMinConflictApp {
	
	public static void main(String[] args) {
		
//		NQueensProblem nqueens = new NQueensProblem();
//		SolutionStrategy strategy = new MinConflictsStrategy(50);
//		
//		double start = System.currentTimeMillis();
//		Assignment sol = strategy.solve(nqueens);
//		double end = System.currentTimeMillis();
		
		//*****************************************************************************
		
		NQueensProblem nqueens = new NQueensProblem();
		int intento = 5;
		double start = 0, end = 0;
		SolutionStrategy strategy = null;
		Assignment sol = null;
		
		while(sol == null) {
			strategy = new MinConflictsStrategy(intento);
			start = System.currentTimeMillis();
			sol = strategy.solve(nqueens);
			end = System.currentTimeMillis();
			intento+=3;
		}
		
		
		
		System.out.println(sol + "\n");
		System.out.println("Time to solve = " + (end - start)/1000 + " segundos\n");
		
		NQueensBoard solucion = newBoard(sol);
		System.out.println("SOLUCION:");
		solucion.print();
	}
	
	public static NQueensBoard newBoard(Assignment sol) {
		// implementar
		
		NQueensBoard tab=new NQueensBoard(8);
		tab.clear();

		for (Variable var : sol.getVariables()) {
		
			NQueensVariable v = (NQueensVariable) var;
			int columna=v.getCol();
			int fila=(int)sol.getAssignment(v);
			tab.addQueenAt(new XYLocation(fila,columna)); 
		}
		return tab;
		//return null;
	}
}