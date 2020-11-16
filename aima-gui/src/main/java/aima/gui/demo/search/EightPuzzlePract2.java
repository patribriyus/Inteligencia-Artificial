package aima.gui.demo.search;

import aima.core.environment.eightpuzzle.EightPuzzleBoard;
import aima.core.environment.eightpuzzle.EightPuzzleFunctionFactory;
import aima.core.environment.eightpuzzle.EightPuzzleGoalTest;
import aima.core.environment.eightpuzzle.EightPuzzleGoalTest2;
import aima.core.environment.eightpuzzle.ManhattanHeuristicFunction2;
import aima.core.environment.eightpuzzle.MisplacedTilleHeuristicFunction2;
import aima.core.search.framework.GraphSearch;
import aima.core.search.framework.Problem;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.informed.AStarSearch;
import aima.core.search.uninformed.*;
import aima.core.util.math.Biseccion;

/**
 * @author Patricia Briones Yus, 735576
 * 
 */


public class EightPuzzlePract2<T> {
	enum TipoBusqueda {
		BFS, IDS, A_1, A_2
	}
	
	static int MAX = 100, aux;
	static int nodos_generados[];

	public static void main(String[] args) {
		EightPuzzleBoard estInicial, estObjetivo;
		EightPuzzleGoalTest2 goal;
		Problem problem;
		int aux_nodos;
		
		imprimir_cabecera();
		
		/* 
		 * Se van a realizar 100 experimentos. Y en cada uno de ellos se van a crear 23 problemas
		 * con una profundidad de 2 a 24 respectivamente.
		 */
		for(int i=2; i<=24; i++) {
			nodos_generados = new int [] {0, 0, 0, 0};
			
			for(int j=0; j<MAX;) {
				
				estInicial = new EightPuzzleBoard(GenerateInitialEightPuzzleBoard.randomIni());
				estObjetivo = new EightPuzzleBoard(GenerateInitialEightPuzzleBoard.random(i, new EightPuzzleBoard(estInicial)));
				goal = new EightPuzzleGoalTest2();
				goal.setGoalState(new EightPuzzleBoard(estObjetivo));
				
				problem = new Problem(estInicial, EightPuzzleFunctionFactory
						.getActionsFunction(), EightPuzzleFunctionFactory
						.getResultFunction(), goal);
				//new EightPuzzleGoalTest()
				
				/* 
				 * La función random que genera un estado objetivo teniendo en cuenta el estado inicial
				 * y una profundidad específica, no garantiza de que no haya una solución más corta, por 
				 * lo que habrá que comprobar si la profundidad obtenida es la correcta y esperada.
				 */
				aux_nodos = eightPuzzleSearch(TipoBusqueda.A_2, problem);
				
				if(aux == i) {
					nodos_generados[0] += eightPuzzleSearch(TipoBusqueda.BFS, problem);
					if(i<=10) nodos_generados[1] += eightPuzzleSearch(TipoBusqueda.IDS, problem);
					nodos_generados[2] += eightPuzzleSearch(TipoBusqueda.A_1, problem);
					nodos_generados[3] += aux_nodos;
					
					j++;
				}
			}
			
			imprimir_resultado(i);
		}
		
		// Se cierra la tabla
		for(int i=1; i<91; i++) System.out.print("-");
	}
	
	private static int eightPuzzleSearch(TipoBusqueda tipo, Problem problem) {
		try {
			
			Search search = null;
			
			switch (tipo) {
			case BFS:
				search = new BreadthFirstSearch(new GraphSearch());
				break;
			case IDS:
				search = new IterativeDeepeningSearch();
				break;
			case A_1:
				search = new AStarSearch(new GraphSearch(), new MisplacedTilleHeuristicFunction2());
				break;
			case A_2:
				search = new AStarSearch(new GraphSearch(), new ManhattanHeuristicFunction2());
				break;
			}
			
			SearchAgent agent = new SearchAgent(problem, search);
			
			if (agent.getInstrumentation().getProperty("pathCost")==null) aux = 0;
			else aux = (int)Float.parseFloat(agent.getInstrumentation().getProperty("pathCost"));
			
			if (agent.getInstrumentation().getProperty("nodesGenerated")==null) return 0;
			else return (int)Float.parseFloat(agent.getInstrumentation().getProperty("nodesGenerated"));
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
	
	public static void imprimir_cabecera() {
		/* CABECERA */
		for(int i=1; i<91; i++) System.out.print("-"); System.out.println();
		System.out.format("||    || %20s%17s || %19s%19s||\n","Nodos Generados", " ", "b*", " ");
		for(int i=1; i<91; i++) System.out.print("-"); System.out.println();
		System.out.format("|| %3s|| %6s  | %6s  | %6s  | %6s  |", "d", "BFS", "IDS", "A*h(1)", "A*h(2)");
		System.out.format("| %6s  | %6s  | %6s  | %6s  ||\n", "BFS", "IDS", "A*h(1)", "A*h(2)");
		for(int i=1; i<91; i++) System.out.print("-"); System.out.println();
		for(int i=1; i<91; i++) System.out.print("-"); System.out.println();
	}
	
	public static void imprimir_resultado(int i) {
		Biseccion bf;
		double factor_ram;
		
		/* LINEA HORIZONTAL RESULTADOS */
		
		System.out.format("|| %3s||", i);
		for(int j=0; j<4; j++) {
			if(j == 1 && i > 10) System.out.format(" %6s  |", "---");
			else System.out.format(" %6s  |", nodos_generados[j]/MAX);
		}
		
		System.out.print("|");
		
		for(int j=0; j<4; j++) {
			
			if(j == 1 && i > 10) {
				System.out.format(" %6s  |", "---");
			}
			else {
				bf = new Biseccion(); bf.setDepth(i);
				bf.setGeneratedNodes(nodos_generados[j]/MAX);
				
				factor_ram = bf.metodoDeBiseccion(1.000000000000001, 4, 1E-12);
				
				System.out.format("   %.2f  |", factor_ram);
			}
		}
		
		System.out.println("|");
	}

}