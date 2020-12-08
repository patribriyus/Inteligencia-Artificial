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
		
		int maxSteps = 1;
		double start = 0, end = 0;
		
		NQueensProblem nqueens = new NQueensProblem();
		SolutionStrategy strategy = null;
		Assignment sol = null;
		
		while(sol == null) {
			strategy = new MinConflictsStrategy(maxSteps);
			
			start = System.currentTimeMillis();
			sol = strategy.solve(nqueens);
			end = System.currentTimeMillis();
			
			maxSteps++;
		}
		
		System.out.println(sol + "\n");
		System.out.println("Time to solve = " + (end - start)/1000 + " segundos\n");
		
		NQueensBoard solucion = newBoard(sol);
		System.out.println("SOLUCION:");
		solucion.print();
	}
	
	public static NQueensBoard newBoard(Assignment sol) {
		int col, fila;
		
		NQueensBoard board = new NQueensBoard(8);

		for (Variable var : sol.getVariables()) {		
			col = ((NQueensVariable) var).getCol();
			fila = (int)sol.getAssignment((NQueensVariable) var);
			
			board.addQueenAt(new XYLocation(col,fila)); 
		}
		
		return board;
	}
}