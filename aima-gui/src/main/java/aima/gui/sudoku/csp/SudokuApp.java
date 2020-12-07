package aima.gui.sudoku.csp;

import java.util.ArrayList;
import java.util.stream.Stream;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.BacktrackingStrategy;
import aima.core.search.csp.CSP;
import aima.core.search.csp.CSPStateListener;
import aima.core.search.csp.ImprovedBacktrackingStrategy;
import aima.core.search.csp.MapCSP;
import aima.core.search.csp.MinConflictsStrategy;
import aima.core.search.csp.SolutionStrategy;

/**
 * @author Patricia Briones Yus, 735576
 * 
 */

public class SudokuApp {
	
	public static void main(String[] args) {
//		Sudoku [] lista = union (union(Sudoku.listaSudokus2("easy50.txt"),
//				Sudoku.listaSudokus2("top95.txt")),
//				Sudoku.listaSudokus2("hardest.txt"));
//		
//		CSP csp = new MapCSP();
//		StepCounter stepCounter = new StepCounter();
//		SolutionStrategy solver;
//		
//		solver = new MinConflictsStrategy(1000);
//		solver.addCSPStateListener(stepCounter);
//		stepCounter.reset();
//		System.out.println("Map Coloring (Minimum Conflicts)");
//		System.out.println(solver.solve(csp.copyDomains()));
//		System.out.println(stepCounter.getResults() + "\n");
//		
//		solver = new ImprovedBacktrackingStrategy(true, true, true, true);
//		solver.addCSPStateListener(stepCounter);
//		stepCounter.reset();
//		System.out.println("Map Coloring (Backtracking + MRV + DEG + AC3 + LCV)");
//		System.out.println(solver.solve(csp.copyDomains()));
//		System.out.println(stepCounter.getResults() + "\n");
//		
//		solver = new BacktrackingStrategy();
//		solver.addCSPStateListener(stepCounter);
//		stepCounter.reset();
//		System.out.println("Map Coloring (Backtracking)");
//		System.out.println(solver.solve(csp.copyDomains()));
//		System.out.println(stepCounter.getResults() + "\n");
//	}
//	
//	/** Counts assignment and domain changes during CSP solving. */
//	protected static class StepCounter implements CSPStateListener {
//		private int assignmentCount = 0;
//		private int domainCount = 0;
//		
//		@Override
//		public void stateChanged(Assignment assignment, CSP csp) {
//			++assignmentCount;
//		}
//		
//		@Override
//		public void stateChanged(CSP csp) {
//			++domainCount;
//		}
//		
//		public void reset() {
//			assignmentCount = 0;
//			domainCount = 0;
//		}
//		
//		public String getResults() {
//			StringBuffer result = new StringBuffer();
//			result.append("assignment changes: " + assignmentCount);
//			if (domainCount != 0)
//				result.append(", domain changes: " + domainCount);
//			return result.toString();
//		}
		Sudoku [] lista = union (union(Sudoku.listaSudokus2("easy50.txt"),
				Sudoku.listaSudokus2("top95.txt")),
				Sudoku.listaSudokus2("hardest.txt"));
				
		SudokuProblem sudoku = new SudokuProblem(lista[0].pack_celdasAsignadas());
		BacktrackingStrategy bts = new BacktrackingStrategy();
		bts.addCSPStateListener(new CSPStateListener() {
			@Override
			public void stateChanged(Assignment assignment, CSP csp) {
			//System.out.println("Assignment evolved : " + assignment);
			}
			@Override
			public void stateChanged(CSP csp) {
			//System.out.println("CSP evolved : " + csp);
			}
		});
		double start = System.currentTimeMillis();
		Assignment sol = bts.solve(sudoku);
		double end = System.currentTimeMillis();
		System.out.println(sol);
		System.out.println("Time to solve = " + (end - start));
	}
	
	private static Sudoku[] union(Sudoku[] listaSudokus2, Sudoku[] listaSudokus22) {
		return Stream.of(listaSudokus2, listaSudokus22).flatMap(Stream::of).toArray(Sudoku[]::new);
	}

}