package aima.gui.demo.search;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

import aima.core.agent.Action;
import aima.core.environment.eightpuzzle.EightPuzzleBoard;
import aima.core.environment.eightpuzzle.EightPuzzleFunctionFactory;
import aima.core.environment.eightpuzzle.EightPuzzleGoalTest;
import aima.core.search.framework.GraphSearch;
import aima.core.search.framework.Problem;
import aima.core.search.framework.QueueSearch;
import aima.core.search.framework.ResultFunction;
import aima.core.search.framework.Search;
import aima.core.search.framework.SearchAgent;
import aima.core.search.framework.TreeSearch;
import aima.core.search.uninformed.*;

/**
 * @author Patricia Briones Yus, 735576
 * 
 */



public class EightPuzzlePract1<T> {
	enum TipoBusqueda {
		BFS, DFS, DLS, IDS, UCS
	}
	
	static EightPuzzleBoard boardWithThreeMoveSolution = new EightPuzzleBoard(
			new int[] { 1, 2, 5, 3, 4, 0, 6, 7, 8 });;

	static EightPuzzleBoard random1 = new EightPuzzleBoard(new int[] { 1, 4, 2,
			7, 5, 8, 3, 0, 6 });

	static EightPuzzleBoard extreme = new EightPuzzleBoard(new int[] { 0, 8, 7,
			6, 5, 4, 3, 2, 1 });

	public static void main(String[] args) {
		System.out.format("%10s|%11s|%10s|%10s|%10s|%10s\n", "Problema", "Profundidad", "Expand", 
				"Q.Size", "MaxQS", "tiempo");
		/* -------------------------------------------- SOLUCION A 3 PASOS - boardWithThreeMoveSolution */
		
		/* Búsqueda en anchura: GRAFO - ARBOL */
		eightPuzzleSearch(TipoBusqueda.BFS, new GraphSearch(), boardWithThreeMoveSolution, "BFS-G-3", null);
		eightPuzzleSearch(TipoBusqueda.BFS, new TreeSearch(), boardWithThreeMoveSolution, "BFS-T-3", null);
		
		/* Búsqueda en profundidad: GRAFO - ARBOL */
		eightPuzzleSearch(TipoBusqueda.DFS, new GraphSearch(), boardWithThreeMoveSolution, "DFS-G-3", null);
		eightPuzzleSearch(TipoBusqueda.DFS, new TreeSearch(), boardWithThreeMoveSolution, "DFS-T-3", "(1)");
		
		/* Búsqueda en profundidad limitada */
		eightPuzzleSearch(TipoBusqueda.DLS, 9, boardWithThreeMoveSolution, "DLS-9-3", null);
		eightPuzzleSearch(TipoBusqueda.DLS, 3, boardWithThreeMoveSolution, "DLS-3-3", null);
		
		/* Búsqueda en profundidad iterativa */
		eightPuzzleSearch(TipoBusqueda.IDS, null, boardWithThreeMoveSolution, "IDS-3", null);
		
		/* Búsqueda con coste uniforme: GRAFO - ARBOL */
		eightPuzzleSearch(TipoBusqueda.UCS, new GraphSearch(), boardWithThreeMoveSolution, "UCS-G-3", null);
		eightPuzzleSearch(TipoBusqueda.UCS, new TreeSearch(), boardWithThreeMoveSolution, "UCS-T-3", null);
		
		/* -------------------------------------------- SOLUCION A 9 PASOS - random1 */
		
		/* Búsqueda en anchura: GRAFO - ARBOL */
		eightPuzzleSearch(TipoBusqueda.BFS, new GraphSearch(), random1, "BFS-G-9", null);
		eightPuzzleSearch(TipoBusqueda.BFS, new TreeSearch(), random1, "BFS-T-9", null);
		
		/* Búsqueda en profundidad: GRAFO - ARBOL */
		eightPuzzleSearch(TipoBusqueda.DFS, new GraphSearch(), random1, "DFS-G-9", null);
		eightPuzzleSearch(TipoBusqueda.DFS, new TreeSearch(), random1, "DFS-T-9", "(1)");
		
		/* Búsqueda en profundidad limitada */
		eightPuzzleSearch(TipoBusqueda.DLS, 9, random1, "DLS-9-9", null);
		eightPuzzleSearch(TipoBusqueda.DLS, 3, random1, "DLS-3-9", null);
		
		/* Búsqueda en profundidad iterativa */
		eightPuzzleSearch(TipoBusqueda.IDS, null, random1, "IDS-9", null);
		
		/* Búsqueda con coste uniforme: GRAFO - ARBOL */
		eightPuzzleSearch(TipoBusqueda.UCS, new GraphSearch(), random1, "UCS-G-9", null);
		eightPuzzleSearch(TipoBusqueda.UCS, new TreeSearch(), random1, "UCS-T-9", null);
		
		/* -------------------------------------------- SOLUCION A 30 PASOS - extreme */
		
		/* Búsqueda en anchura: GRAFO - ARBOL */
		eightPuzzleSearch(TipoBusqueda.BFS, new GraphSearch(), extreme, "BFS-G-30", null);
		eightPuzzleSearch(TipoBusqueda.BFS, new TreeSearch(), extreme, "BFS-T-30", "(2)");
		
		/* Búsqueda en profundidad: GRAFO - ARBOL */
		eightPuzzleSearch(TipoBusqueda.DFS, new GraphSearch(), extreme, "DFS-G-30", null);
		eightPuzzleSearch(TipoBusqueda.DFS, new TreeSearch(), extreme, "DFS-T-30", "(1)");
		
		/* Búsqueda en profundidad limitada */
		eightPuzzleSearch(TipoBusqueda.DLS, 30, extreme, "DLS-30-30", "(1)");
		eightPuzzleSearch(TipoBusqueda.DLS, 9, extreme, "DLS-9-30", null);
		eightPuzzleSearch(TipoBusqueda.DLS, 3, extreme, "DLS-3-30", null);
		
		/* Búsqueda en profundidad iterativa */
		eightPuzzleSearch(TipoBusqueda.IDS, null, extreme, "IDS-30", "(1)");
		
		/* Búsqueda con coste uniforme: GRAFO - ARBOL */
		eightPuzzleSearch(TipoBusqueda.UCS, new GraphSearch(), extreme, "UCS-G-30", null);
		eightPuzzleSearch(TipoBusqueda.UCS, new TreeSearch(), extreme, "UCS-T-30", "(1)");
	}
	
	private static <T> void eightPuzzleSearch(TipoBusqueda tipo, T data,
			EightPuzzleBoard estInicial, String metodo, String error) {
		try {
			Problem problem = new Problem(estInicial, EightPuzzleFunctionFactory
					.getActionsFunction(), EightPuzzleFunctionFactory
					.getResultFunction(), new EightPuzzleGoalTest());
			Search search = null;
			
			switch (tipo) {
			case BFS:
				search = new BreadthFirstSearch((QueueSearch)data);
				break;
			case DFS:
				search = new DepthFirstSearch((QueueSearch)data);
				break;
			case DLS:
				search = new DepthLimitedSearch((int)data);
				break;
			case IDS:
				search = new IterativeDeepeningSearch();
				break;
			case UCS:
				search = new UniformCostSearch((QueueSearch)data);
				break;
			}
			
			if(error == null) {
				long tiempo1 = System.currentTimeMillis();
				SearchAgent agent = new SearchAgent(problem, search);
				long tiempo2 = System.currentTimeMillis();
				
				metricasBusq(agent, metodo, tiempo2-tiempo1);
				executeActions(agent.getActions(), problem, metodo);
			}
			else {
				System.out.format("%10s|%11s|%10s|%10s|%10s|%10s\n", metodo, "---", "---",
						"---", "---", error);
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/* Muestra por pantalla las métricas de la búsqueda */
	private static void metricasBusq(SearchAgent agent, String metodo, long tiempo) {
		int depth, expandedNodes, queueSize, maxQueueSize;
		
		String pathcostM = agent.getInstrumentation().getProperty("pathCost");
	
		if (pathcostM!=null) depth = (int)Float.parseFloat(pathcostM);
		else depth = 0;
		
		if (agent.getInstrumentation().getProperty("nodesExpanded")==null) expandedNodes= 0;
		else expandedNodes =
				(int)Float.parseFloat(agent.getInstrumentation().getProperty("nodesExpanded"));
		
		if (agent.getInstrumentation().getProperty("queueSize")==null) queueSize=0;
		else queueSize = (int)Float.parseFloat(agent.getInstrumentation().getProperty("queueSize"));
		
		if (agent.getInstrumentation().getProperty("maxQueueSize")==null) maxQueueSize= 0;
		else maxQueueSize =
				(int)Float.parseFloat(agent.getInstrumentation().getProperty("maxQueueSize"));
		
		System.out.format("%10s|%11s|%10s|%10s|%10s|%10s\n", metodo, depth, expandedNodes,
				queueSize, maxQueueSize, tiempo);
	}
	
	/* Escribe en un fichero .txt la lista de acciones cuando se encuentra la solución */
	public static void executeActions(List<Action> actions, Problem problem, String metodo) {
		FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
        	// El fichero se crea en la carpeta principal del proyecto
        	File directorio = new File("../EightPuzzle/");
        	if(!directorio.exists()) directorio.mkdirs();
            fichero = new FileWriter("../EightPuzzle/" + metodo + ".txt");
            pw = new PrintWriter(fichero);

            Object initialState = problem.getInitialState();
    		ResultFunction resultFunction = problem.getResultFunction();
    		
    		Object state = initialState;
    		pw.println("INITIAL STATE");
    		pw.println(state);
    		
    		for (Action action : actions) {
    			pw.println(action.toString());
    			state = resultFunction.result(state, action);
    			pw.println(state);
    			pw.println("- - -");
    		}

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           try {
           // Nuevamente aprovechamos el finally para asegurarnos que se cierra el fichero
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
	}

}