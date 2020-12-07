package aima.gui.nqueens.csp;

import java.util.stream.Stream;

import aima.core.search.csp.Assignment;
import aima.core.search.csp.ImprovedBacktrackingStrategy;
import aima.core.search.csp.MinConflictsStrategy;
import aima.core.search.csp.SolutionStrategy;

/**
 * @author Patricia Briones Yus, 735576
 * 
 */

public class NQueensMinConflictApp {
	
	public static void main(String[] args) {
		
		Sudoku [] listaSudokus = union (union(Sudoku.listaSudokus2("easy50.txt"),
				Sudoku.listaSudokus2("top95.txt")),
				Sudoku.listaSudokus2("hardest.txt"));
		
		int nSudokusCorrect = 0;
		NQueensProblem sudoku;
		Sudoku solucion;
		SolutionStrategy strategy = new MinConflictsStrategy(50);
		
		for(int i=0; i<listaSudokus.length; i++) {
			
			System.out.println("---------");
			listaSudokus[i].imprimeSudoku();
			System.out.println("SUDOKU INCOMPLETO - Resolviendo");
			
			sudoku = new NQueensProblem(listaSudokus[i].pack_celdasAsignadas());
			
			double start = System.currentTimeMillis();
			Assignment sol = strategy.solve(sudoku);
			double end = System.currentTimeMillis();
			
			System.out.println(sol);
			System.out.println("Time to solve = " + (end - start));
			
			// Si es correcto y completo entonces --> la solución es correcta
			solucion = new Sudoku(sol);
			if(solucion.correcto() && solucion.correcto()) {
				System.out.println("SOLUCION:");
				solucion.imprimeSudoku();
				System.out.println("Sudoku solucionado correctamente");
				nSudokusCorrect++;
			}
		}
		
		System.out.println("+++++++++");
		System.out.println("Numero de sudokus solucionados: " + nSudokusCorrect);
		
	}
	
	private static Sudoku[] union(Sudoku[] lista1, Sudoku[] lista2) {
		return Stream.of(lista1, lista2).flatMap(Stream::of).toArray(Sudoku[]::new);
	}

}