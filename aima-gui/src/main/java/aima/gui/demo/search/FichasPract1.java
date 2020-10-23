package aima.gui.demo.search;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.List;

import aima.core.agent.Action;
import aima.core.environment.Fichas.*;
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

public class FichasPract1<T> {
	enum TipoBusqueda {
		BFS, DFS, DLS, IDS, UCS
	}
	
	static FichasBoard initialState = new FichasBoard();
	
	public static void main(String[] args) {
		
		/* Búsqueda en anchura: GRAFO - ARBOL */
		fichasSearch(TipoBusqueda.BFS, new GraphSearch(), initialState, "BFS-G", null);
		fichasSearch(TipoBusqueda.BFS, new TreeSearch(), initialState, "BFS-T", null);
		
		/* Búsqueda en profundidad: GRAFO - ARBOL */
		fichasSearch(TipoBusqueda.DFS, new GraphSearch(), initialState, "DFS-G", null);
		fichasSearch(TipoBusqueda.DFS, new TreeSearch(), initialState, "DFS-T", "(1)");
		
		/* Búsqueda en profundidad limitada */
		fichasSearch(TipoBusqueda.DLS, 10, initialState, "DLS-10", null);
		fichasSearch(TipoBusqueda.DLS, 9, initialState, "DLS-9", null);
		fichasSearch(TipoBusqueda.DLS, 3, initialState, "DLS-3", null);
		
		/* Búsqueda en profundidad iterativa */
		fichasSearch(TipoBusqueda.IDS, null, initialState, "IDS", null);
		
		/* Búsqueda con coste uniforme: GRAFO - ARBOL */
		fichasSearch(TipoBusqueda.UCS, new GraphSearch(), initialState, "UCS-G", null);
		fichasSearch(TipoBusqueda.UCS, new TreeSearch(), initialState, "UCS-T", null);
	}
	
	private static <T> void fichasSearch(TipoBusqueda tipo, T data,
			FichasBoard estInicial, String metodo, String error) {
		try {
			Problem problem = new Problem(estInicial, FichasFunctionFactory
					.getActionsFunction(), FichasFunctionFactory
					.getResultFunction(), new FichasGoalTest());
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
				System.out.println("Fichas " + metodo + " -->");
				System.out.println("ERROR. NO SOLUCION");
				System.out.println();
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
		
		System.out.println("Fichas " + metodo + " -->");
		System.out.println("pathCost: " + depth);
		System.out.println("nodesExpanded: " + expandedNodes);
		System.out.println("queueSize: " + queueSize);
		System.out.println("maxQueueSize: " + maxQueueSize);
		System.out.println("Tiempo: " + tiempo + "mls");
		System.out.println();
	}
	
	/* Escribe en un fichero .txt la lista de acciones cuando se encuentra la solución */
	public static void executeActions(List<Action> actions, Problem problem, String metodo) {
		FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
        	// El fichero se crea en la carpeta principal del proyecto
        	File directorio = new File("../Fichas/");
        	if(!directorio.exists()) directorio.mkdirs();
            fichero = new FileWriter("../Fichas/" + metodo + ".txt");
            pw = new PrintWriter(fichero);

            Object initialState = problem.getInitialState();
    		ResultFunction resultFunction = problem.getResultFunction();
    		
    		Object state = initialState;
    		pw.println("SOLUCIÓN:");
    		
    		pw.printf("%20s\n%15s\n", "ESTADO INICIAL", state);
    		//System.out.printf("%20s\n%15s\n", "ESTADO INICIAL", state);
    		
    		for (Action action : actions) {
    			state = resultFunction.result(state, action);
    			pw.printf("%20s\n%15s\n", action.toString(), state);
    			//System.out.printf("%20s\n%15s\n", action.toString(), state);
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