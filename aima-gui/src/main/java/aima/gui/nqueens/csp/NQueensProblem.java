package aima.gui.nqueens.csp;

import java.util.ArrayList;
import java.util.List;
import aima.core.search.csp.CSP;
import aima.core.search.csp.Domain;
import aima.core.search.csp.Variable;

/**
 * @author Patricia Briones Yus, 735576
 * 
 */

public class NQueensProblem extends CSP {
     private static final int cells = 64;
     private static List<Variable> variables = null;

     /**
      *
      * @return Devuelve la lista de variables del tablero de N reinas.
      *         Nombre: Reina en columna [j], y coordenadas j
      */
     private static List<Variable> collectVariables() {
          variables = new ArrayList<Variable>();
           for (int j = 0; j < 8; j++) {
                variables.add(new NQueensVariable("Reina en columna [" + j +"]", j));
           }
          return variables;
     }
     
     /**
      *
      * @param var variable del tablero
      * @return Dominio de la variable 0 al 7 (filas)
      */
     private static List<Integer> getReinasDomain(NQueensVariable var) {
          List<Integer> list = new ArrayList<Integer>();
          for (int i = 0; i < 8; i++)
            list.add(new Integer(i));
          return list;
     }

     /**
      * Define como un CSP. Define variables, sus dominios y restricciones.
      * @param pack
      */
     public NQueensProblem() {
    	  // Variables
          super(collectVariables());

          // Define dominios de variables
          Domain domain;
          for (Variable var : getVariables()) {
               domain = new Domain(getReinasDomain((NQueensVariable) var));
               setDomain(var, domain);
          }
          // Restricciones
          doConstraint();
     }
     
     private void doConstraint() {
    	 for (int i = 0; i < 8; i++) { // columna actual
	    	 for (int j = i+1; j < 8; j++) { // siguientes columnas
	    		 addConstraint(new NQueensConstraint(variables.get(i), variables.get(j)));
	    	 }
    	 }
     }
}
