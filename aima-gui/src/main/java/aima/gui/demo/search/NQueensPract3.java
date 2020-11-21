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
		/*
		nQueensHillClimbingSearch_Statistics(10000);
		nQueensRandomRestartHillClimbing();
		
		int T[] = {400,600,800,1000,1200,1800,2000,2200};
		int k[] = {10, 30, 50};
		double delta[] = {0.01, 0.05, 0.1};
		
		for(int i=0; i<delta.length; i++) {
			for(int j=0; j<k.length; j++) {
				for(int m=0; m<T.length; m++) {
					nQueensSimulatedAnnealing_Statistics(1000, T[m], k[j], delta[i]);					
				}
			}
		}*/
		
		
		
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
		System.out.format("Parámetros Scheduler: Scheduler (%d, %d, %.4f)\n", limit, k, delta);
		try {
			Problem problem = null;
			int numExitos = 0, numFallos = 0,
				costeExitos = 0, costeFallos = 0, j;
			// Contendrá todos los estados iniciales únicos (sin repetidos)
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
				
				/* Se comprueba si el último estado donde se ha encontrado el máximo 
				 local es igual al objetivo */
				if(estObjetivo.isGoalState(search.getLastSearchState())) {
					numExitos++; costeExitos += agent.getActions().size();
				}
				else {
					numFallos++; costeFallos += agent.getActions().size();
				}
			}
			
			// Estadísticas
			System.out.format("Fallos: %.2f%n", numFallos*100.0 / numExperiments);
			System.out.format("Coste medio fallo: %.2f%n", (double)costeFallos / numFallos);
			System.out.format("Éxitos: %.2f%n", numExitos*100.0 / numExperiments);
			System.out.format("Coste medio éxitos: %.2f%n", (double)costeExitos / numExitos);
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
			// Contendrá todos los estados iniciales únicos (sin repetidos)
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
				
				/* Se comprueba si el último estado donde se ha encontrado el máximo 
				 local es igual al objetivo */
				if(estObjetivo.isGoalState(search.getLastSearchState())) {
					numExitos++; costeExitos += agent.getActions().size();
				}
				else {
					numFallos++; costeFallos += agent.getActions().size();
				}
			}
			
			// Estadísticas
			System.out.format("Fallos: %.2f%n", numFallos*100.0 / numExperiments);
			System.out.format("Coste medio fallo: %.2f%n", (double)costeFallos / numFallos);
			System.out.format("Éxitos: %.2f%n", numExitos*100.0 / numExperiments);
			System.out.format("Coste medio éxitos: %.2f%n", (double)costeExitos / numExitos);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static NQueensBoard NQueensBoardAleatorio() {
		NQueensBoard board = new NQueensBoard(_boardSize);
		
		for(int i=0; i<_boardSize; i++) {
			// Establecerá las reinas de izquierda a derecha
			board.addQueenAt(new XYLocation(i, (new Random()).nextInt(_boardSize)));
		}
		
		return board;
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
				
				/* Se comprueba si el último estado donde se ha encontrado el máximo 
				 local es igual al objetivo */
				esObjetivo = estObjetivo.isGoalState(search.getLastSearchState());
				if(!esObjetivo) {
					numFallos++; costeFallos += agent.getActions().size();
				}
				else costeExito = agent.getActions().size();
			} while(!esObjetivo);			
			
			
			// Solución
			System.out.println("Search Outcome=" + search.getOutcome());
			System.out.println("Final State=\n" + search.getLastSearchState());
			
			// Número de reintentos
			System.out.format("Número de intentos: %d%n", numIntentos);
			
			// Estadísticas
			System.out.format("Fallos: %.2f%n", numFallos*100.0 / numIntentos);
			System.out.format("Coste medio fallo: %.2f%n", (double)costeFallos / numFallos);
			System.out.format("Éxito: %.2f%n", 100.0 / numIntentos);
			System.out.format("Coste éxito: %.2f%n", (double)costeExito);
			//System.out.format("Coste medio éxito: %.2f%n", ); // ???

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void nQueensGeneticAlgorithmSearch() {
		System.out.println("\nNQueensDemo GeneticAlgorithm  -->");
		try {
			NQueensFitnessFunction fitnessFunction = new NQueensFitnessFunction();
			// Generate an initial population
			Set<Individual<Integer>> population = new HashSet<Individual<Integer>>();
			for (int i = 0; i < 50; i++) {
				population.add(fitnessFunction
						.generateRandomIndividual(_boardSize));
			}

			GeneticAlgorithm<Integer> ga = new GeneticAlgorithm<Integer>(
					_boardSize,
					fitnessFunction.getFiniteAlphabetForBoardOfSize(_boardSize),
					0.15);

			// Run for a set amount of time
			Individual<Integer> bestIndividual = ga.geneticAlgorithm(
					population, fitnessFunction, fitnessFunction, 1000L);

			System.out.println("Max Time (1 second) Best Individual=\n"
					+ fitnessFunction.getBoardForIndividual(bestIndividual));
			System.out.println("Board Size      = " + _boardSize);
			System.out.println("# Board Layouts = "
					+ (new BigDecimal(_boardSize)).pow(_boardSize));
			System.out.println("Fitness         = "
					+ fitnessFunction.getValue(bestIndividual));
			System.out.println("Is Goal         = "
					+ fitnessFunction.isGoalState(bestIndividual));
			System.out.println("Population Size = " + ga.getPopulationSize());
			System.out.println("Itertions       = " + ga.getIterations());
			System.out.println("Took            = "
					+ ga.getTimeInMilliseconds() + "ms.");

			// Run till goal is achieved
			bestIndividual = ga.geneticAlgorithm(population, fitnessFunction,
					fitnessFunction, 0L);

			System.out.println("");
			System.out.println("Goal Test Best Individual=\n"
					+ fitnessFunction.getBoardForIndividual(bestIndividual));
			System.out.println("Board Size      = " + _boardSize);
			System.out.println("# Board Layouts = "
					+ (new BigDecimal(_boardSize)).pow(_boardSize));
			System.out.println("Fitness         = "
					+ fitnessFunction.getValue(bestIndividual));
			System.out.println("Is Goal         = "
					+ fitnessFunction.isGoalState(bestIndividual));
			System.out.println("Population Size = " + ga.getPopulationSize());
			System.out.println("Itertions       = " + ga.getIterations());
			System.out.println("Took            = "
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