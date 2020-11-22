package aima.gui.demo.search;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.environment.nqueens.AttackingPairsHeuristic;
import aima.core.environment.nqueens.NQueensBoard;
import aima.core.environment.nqueens.NQueensFitnessFunction;
import aima.core.environment.nqueens.NQueensFunctionFactory;
import aima.core.environment.nqueens.NQueensGoalTest;
import aima.core.search.framework.GraphSearch;
import aima.core.search.framework.Problem;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.TreeSearch;
import aima.core.search.local.GeneticAlgorithm;
import aima.core.search.local.HillClimbingSearch;
import aima.core.search.local.Individual;
import aima.core.search.local.Scheduler;
import aima.core.search.local.SimulatedAnnealingSearch;
import aima.core.search.uninformed.BreadthFirstSearch;
import aima.core.search.uninformed.DepthFirstSearch;
import aima.core.search.uninformed.DepthLimitedSearch;
import aima.core.search.uninformed.IterativeDeepeningSearch;
import aima.core.util.datastructure.XYLocation;

/**
 * @author Patricia Briones Yus, 735576
 * 
 */

public class NQueensPract3 {

	private static final int _boardSize = 8;
	
	public static void main(String[] args) {

		newNQueensDemo();
	}

	private static void newNQueensDemo() {

		/*
		nQueensWithDepthFirstSearch();
		nQueensWithBreadthFirstSearch();
		nQueensWithRecursiveDLS();
		nQueensWithIterativeDeepeningSearch();
		nQueensSimulatedAnnealingSearch();
		nQueensHillClimbingSearch();
		nQueensGeneticAlgorithmSearch();
		*/
		
		// Tarea 1
		nQueensHillClimbingSearch_Statistics(10000);
		// Tarea 2
		nQueensRandomRestartHillClimbing();
		
		// Tarea 3
		int T[] = {400,600,800,1000,1200,1800,2000,2200};
		int k[] = {10, 30, 50};
		double delta[] = {0.01, 0.05, 0.1};
		
		for(int i=0; i<delta.length; i++) {
			for(int j=0; j<k.length; j++) {
				for(int m=0; m<T.length; m++) {
					nQueensSimulatedAnnealing_Statistics(1000, T[m], k[j], delta[i]);					
				}
			}
		}
		
		// Tarea 4
		nQueensHillSimulatedAnnealingRestart(2200,10,0.1);
		
		// Tarea 5
		for(int i=2; i<80; i+=12) {
			for(double j=0.1; j<0.9; j+=0.15) {			
				nQueensGeneticAlgorithmSearch(i, j);
			}
		}
		
	}

	private static void nQueensWithRecursiveDLS() {
		System.out.println("\nNQueensDemo recursive DLS -->");
		try {
			Problem problem = new Problem(new NQueensBoard(_boardSize),
					NQueensFunctionFactory.getIActionsFunction(),
					NQueensFunctionFactory.getResultFunction(),
					new NQueensGoalTest());
			Search search = new DepthLimitedSearch(_boardSize);
			SearchAgent agent = new SearchAgent(problem, search);
			printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static void nQueensWithBreadthFirstSearch() {
		try {
			System.out.println("\nNQueensDemo BFS -->");
			Problem problem = new Problem(new NQueensBoard(_boardSize),
					NQueensFunctionFactory.getIActionsFunction(),
					NQueensFunctionFactory.getResultFunction(),
					new NQueensGoalTest());
			Search search = new BreadthFirstSearch(new TreeSearch());
			SearchAgent agent2 = new SearchAgent(problem, search);
			printActions(agent2.getActions());
			printInstrumentation(agent2.getInstrumentation());
		} catch (Exception e1) {

			e1.printStackTrace();
		}
	}

	private static void nQueensWithDepthFirstSearch() {
		System.out.println("\nNQueensDemo DFS -->");
		try {
			Problem problem = new Problem(new NQueensBoard(_boardSize),
					NQueensFunctionFactory.getIActionsFunction(),
					NQueensFunctionFactory.getResultFunction(),
					new NQueensGoalTest());
			Search search = new DepthFirstSearch(new GraphSearch());
			SearchAgent agent = new SearchAgent(problem, search);
			printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void nQueensWithIterativeDeepeningSearch() {
		System.out.println("\nNQueensDemo Iterative DS  -->");
		try {
			Problem problem = new Problem(new NQueensBoard(_boardSize),
					NQueensFunctionFactory.getIActionsFunction(),
					NQueensFunctionFactory.getResultFunction(),
					new NQueensGoalTest());
			Search search = new IterativeDeepeningSearch();
			SearchAgent agent = new SearchAgent(problem, search);

			System.out.println();
			printActions(agent.getActions());
			printInstrumentation(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void nQueensSimulatedAnnealingSearch() {
		System.out.println("\nNQueensDemo Simulated Annealing  -->");
		try {
			Problem problem = new Problem(new NQueensBoard(_boardSize),
					NQueensFunctionFactory.getIActionsFunction(),
					NQueensFunctionFactory.getResultFunction(),
					new NQueensGoalTest());
			SimulatedAnnealingSearch search = new SimulatedAnnealingSearch(
					new AttackingPairsHeuristic());
			SearchAgent agent = new SearchAgent(problem, search);
			
			System.out.println();
			printActions(agent.getActions());
			System.out.println("Search Outcome=" + search.getOutcome());
			System.out.println("Final State=\n" + search.getLastSearchState());
			printInstrumentation(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void nQueensSimulatedAnnealing_Statistics(int numExperiments, int limit, int k, double delta) {
		System.out.println("\nNQueensDemo Simulated Annealing con " + numExperiments + " estados iniciales diferentes -->");
		System.out.format("Par�metros Scheduler: Scheduler (%d, %d, %.4f)\n", limit, k, delta);
		try {
			Problem problem = null;
			int numExitos = 0, numFallos = 0,
				costeExitos = 0, costeFallos = 0, j;
			// Contendr� todos los estados iniciales �nicos (sin repetidos)
			NQueensBoard estInicial[] = new NQueensBoard[numExperiments];
			NQueensGoalTest estObjetivo = new NQueensGoalTest();
			
			SimulatedAnnealingSearch search = new SimulatedAnnealingSearch(
					new AttackingPairsHeuristic(), new Scheduler(k, delta, limit));
			
			for(int i=0; i<numExperiments; i++) {
				
				j = 0;
				do {
					estInicial[i] = NQueensBoardAleatorio();
					
					if(estInicial[i].equals(estInicial[j])) j = 0;
					else j++;
				} while(j<i);
				
				problem = new Problem(estInicial[i],
						NQueensFunctionFactory.getCActionsFunction(),
						NQueensFunctionFactory.getResultFunction(),
						estObjetivo);				
				SearchAgent agent = new SearchAgent(problem, search);
				
				/* Se comprueba si el �ltimo estado donde se ha encontrado el m�ximo 
				 local es igual al objetivo */
				if(estObjetivo.isGoalState(search.getLastSearchState())) {
					numExitos++; costeExitos += agent.getActions().size();
				}
				else {
					numFallos++; costeFallos += agent.getActions().size();
				}
			}
			
			// Estad�sticas
			System.out.format("Fallos: %.2f%n", numFallos*100.0 / numExperiments);
			System.out.format("Coste medio fallo: %.2f%n", (double)costeFallos / numFallos);
			System.out.format("�xitos: %.2f%n", numExitos*100.0 / numExperiments);
			System.out.format("Coste medio �xitos: %.2f%n", (double)costeExitos / numExitos);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void nQueensHillSimulatedAnnealingRestart(int limit, int k, double delta) {
		System.out.println("\nNQueens Random RestartSimulated Annealing -->");
		try {
			Problem problem = null;
			int numFallos = 0, numIntentos = 0,
				costeExito = 0;
			boolean esObjetivo;

			NQueensBoard estInicial = null;
			NQueensGoalTest estObjetivo = new NQueensGoalTest();
			
			SimulatedAnnealingSearch search = new SimulatedAnnealingSearch(
					new AttackingPairsHeuristic(), new Scheduler(k, delta, limit));
			
			do {
				numIntentos++;
				
				estInicial = NQueensBoardAleatorio();
				
				problem = new Problem(estInicial,
						NQueensFunctionFactory.getCActionsFunction(),
						NQueensFunctionFactory.getResultFunction(),
						estObjetivo);				
				SearchAgent agent = new SearchAgent(problem, search);
				
				/* Se comprueba si el �ltimo estado donde se ha encontrado el m�ximo 
				 local es igual al objetivo */
				esObjetivo = estObjetivo.isGoalState(search.getLastSearchState());
				if(!esObjetivo) numFallos++;
				else costeExito = agent.getActions().size();
				
			} while(!esObjetivo);			
			
			
			// Soluci�n
			System.out.println("Search Outcome=" + search.getOutcome());
			System.out.println("Final State=\n" + search.getLastSearchState());
			
			// N�mero de reintentos
			System.out.format("N�mero de intentos: %d%n", numIntentos);
			
			// Estad�sticas
			System.out.format("Fallos: %.2f%n", numFallos*100.0 / numIntentos);
			System.out.format("Coste �xito: %.2f%n", (double)costeExito);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void nQueensHillClimbingSearch() {
		System.out.println("\nNQueensDemo HillClimbing  -->");
		try {
			Problem problem = new Problem(new NQueensBoard(_boardSize),
					NQueensFunctionFactory.getIActionsFunction(),
					NQueensFunctionFactory.getResultFunction(),
					new NQueensGoalTest());
			HillClimbingSearch search = new HillClimbingSearch(
					new AttackingPairsHeuristic());
			SearchAgent agent = new SearchAgent(problem, search);

			System.out.println();
			printActions(agent.getActions());
			System.out.println("Search Outcome=" + search.getOutcome());
			System.out.println("Final State=\n" + search.getLastSearchState());
			printInstrumentation(agent.getInstrumentation());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static void nQueensHillClimbingSearch_Statistics(int numExperiments) {
		System.out.println("\nNQueens HillClimbing con " + numExperiments + " estados iniciales diferentes  -->");
		try {
			Problem problem = null;
			int numExitos = 0, numFallos = 0,
				costeExitos = 0, costeFallos = 0, j;
			// Contendr� todos los estados iniciales �nicos (sin repetidos)
			NQueensBoard estInicial[] = new NQueensBoard[numExperiments];
			NQueensGoalTest estObjetivo = new NQueensGoalTest();
			
			HillClimbingSearch search = new HillClimbingSearch(
					new AttackingPairsHeuristic());
			
			for(int i=0; i<numExperiments; i++) {
				
				j = 0;
				do {
					estInicial[i] = NQueensBoardAleatorio();
					
					if(estInicial[i].equals(estInicial[j])) j = 0;
					else j++;
				} while(j<i);
				
				problem = new Problem(estInicial[i],
						NQueensFunctionFactory.getCActionsFunction(),
						NQueensFunctionFactory.getResultFunction(),
						estObjetivo);				
				SearchAgent agent = new SearchAgent(problem, search);
				
				/* Se comprueba si el �ltimo estado donde se ha encontrado el m�ximo 
				 local es igual al objetivo */
				if(estObjetivo.isGoalState(search.getLastSearchState())) {
					numExitos++; costeExitos += agent.getActions().size();
				}
				else {
					numFallos++; costeFallos += agent.getActions().size();
				}
			}
			
			// Estad�sticas
			System.out.format("Fallos: %.2f%n", numFallos*100.0 / numExperiments);
			System.out.format("Coste medio fallo: %.2f%n", (double)costeFallos / numFallos);
			System.out.format("�xitos: %.2f%n", numExitos*100.0 / numExperiments);
			System.out.format("Coste medio �xitos: %.2f%n", (double)costeExitos / numExitos);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	private static void nQueensRandomRestartHillClimbing() {
		System.out.println("\nNQueens Random Restart HillClimbing -->");
		try {
			Problem problem = null;
			int numFallos = 0, numIntentos = 0,
				costeFallos = 0, costeExito = 0;
			boolean esObjetivo;

			NQueensBoard estInicial = null;
			NQueensGoalTest estObjetivo = new NQueensGoalTest();
			
			HillClimbingSearch search = new HillClimbingSearch(
					new AttackingPairsHeuristic());
			
			do {
				numIntentos++;
				
				estInicial = NQueensBoardAleatorio();
				
				problem = new Problem(estInicial,
						NQueensFunctionFactory.getCActionsFunction(),
						NQueensFunctionFactory.getResultFunction(),
						estObjetivo);				
				SearchAgent agent = new SearchAgent(problem, search);
				
				/* Se comprueba si el �ltimo estado donde se ha encontrado el m�ximo 
				 local es igual al objetivo */
				esObjetivo = estObjetivo.isGoalState(search.getLastSearchState());
				if(!esObjetivo) {
					numFallos++; costeFallos += agent.getActions().size();
				}
				else costeExito = agent.getActions().size();
			} while(!esObjetivo);			
			
			
			// Soluci�n
			System.out.println("Search Outcome=" + search.getOutcome());
			System.out.println("Final State=\n" + search.getLastSearchState());
			
			// N�mero de reintentos
			System.out.format("N�mero de intentos: %d%n", numIntentos);
			
			// Estad�sticas
			System.out.format("Fallos: %.2f%n", numFallos*100.0 / numIntentos);
			System.out.format("Coste medio fallo: %.2f%n", (double)costeFallos / numFallos);
			System.out.format("�xito: %.2f%n", 100.0 / numIntentos);
			System.out.format("Coste �xito: %.2f%n", (double)costeExito);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static NQueensBoard NQueensBoardAleatorio() {
		NQueensBoard board = new NQueensBoard(_boardSize);
		
		for(int i=0; i<_boardSize; i++) {
			// Establecer� las reinas de izquierda a derecha
			board.addQueenAt(new XYLocation(i, (new Random()).nextInt(_boardSize)));
		}
		
		return board;
	}

	public static void nQueensGeneticAlgorithmSearch(int poblacion, double probMutacion) {
		System.out.println("\nNQueensDemo GeneticAlgorithm  -->");
		System.out.format("Par�metros iniciales:\tPoblaci�n: %d, Probabilidad mutaci�n: %.2f", poblacion, probMutacion);
		try {
			NQueensFitnessFunction fitnessFunction = new NQueensFitnessFunction();
			// Generate an initial population
			Set<Individual<Integer>> population = new HashSet<Individual<Integer>>();
			for (int i = 0; i < poblacion; i++) {
				population.add(fitnessFunction
						.generateRandomIndividual(_boardSize));
			}

			GeneticAlgorithm<Integer> ga = new GeneticAlgorithm<Integer>(
					_boardSize,
					fitnessFunction.getFiniteAlphabetForBoardOfSize(_boardSize),
					probMutacion);

			// Run till goal is achieved
			Individual<Integer> bestIndividual = ga.geneticAlgorithm(population, fitnessFunction,
					fitnessFunction, 0L);

			System.out.println("");
			System.out.println("Mejor individuo=\n"
					+ fitnessFunction.getBoardForIndividual(bestIndividual));
			System.out.println("Tama�o tablero      = " + _boardSize);
			System.out.println("Fitness             = "
					+ fitnessFunction.getValue(bestIndividual));
			System.out.println("Es objetivo         = "
					+ fitnessFunction.isGoalState(bestIndividual));
			System.out.println("Tama�o de poblaci�n = " + ga.getPopulationSize());
			System.out.println("Iteraciones         = " + ga.getIterations());
			System.out.println("Tiempo              = "
					+ ga.getTimeInMilliseconds() + "ms.");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void printInstrumentation(Properties properties) {
		Iterator<Object> keys = properties.keySet().iterator();
		while (keys.hasNext()) {
			String key = (String) keys.next();
			String property = properties.getProperty(key);
			System.out.println(key + " : " + property);
		}

	}

	private static void printActions(List<Action> actions) {
		for (int i = 0; i < actions.size(); i++) {
			String action = actions.get(i).toString();
			System.out.println(action);
		}
	}

}