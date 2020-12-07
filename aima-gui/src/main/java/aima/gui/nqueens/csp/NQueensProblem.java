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
                variables.add(new NQueensVariable("Reina en columna [" + j +"]",j));
           }
          return variables;
     }
     /**
      *
      * @param var variable del tablero
      * @return Dominio de la variable --> hay reina o no, 1 o 0
      */
     private static List<Integer> getReinasDomain(NQueensVariable var) {
          List<Integer> list = new ArrayList<Integer>();
          if (var.getValue() != 0) {
               list.add(new Integer(var.getValue()));
               return list;
          } else
               for (int i = 1; i <= 9; i++)
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
          initialize();

          // Define dominios de variables
          Domain domain;
          for (Variable var : getVariables()) {
               domain = new Domain(getReinasDomain((NQueensVariable) var));
               setDomain(var, domain);
          }
          //restricciones
          doConstraint();
     }
     /**
      * Inicializa las variables a partir de las celdas disponibles, que
      * tienen valor. Recorren las listas de variables del Sudoku y del
      * pack, y si tienen las mismas coordenadas, les da el valor que tiene.
      * @param pack
      */
     private void initialize() {
          List<Variable> alList = pack.getList();
          Domain domain;
          for (int i = 0; i < cells; i++) {
               NQueensVariable var1 = (NQueensVariable) variables.get(i);
               for (int j = 0; j < pack.getNumOfAvailable(); j++) {
                    NQueensVariable var2 = (NQueensVariable) alList.get(j);
                    if (var1.getX() == var2.getX() && var1.getY() == var2.getY()) {
                         var1.setValue(var2.getValue());
                    }
               }
          }
     }
     private void doConstraint() {
          int index,h,x,y;
          for (int i = 0; i < 9; i++)
               for (int j = 0; j < 9; j++) {
                     index = i * 9 + j;   // índice de la variable en un vector 0..80
                     for (int k=0; k<9; k++){   // recorro columnas de la  fila i
                         // Elementos de FILAS DISTINTOS
                         h = i*9 + k; 		   // En la misma fila
                         if (k != j ) {         // No es la propia variable
                              addConstraint(new NQueensConstraint(variables.get(index), variables.get(h)));
                         }
                         // Elementos de COLUMNAS DISTINTOS
                         h = k*9 + j;  			//recorro filas de la columna j
                         if (k != i) {          // No es la propia variable
                              addConstraint(new NQueensConstraint(variables.get(index), variables.get(h)));
                         }
                     }
                     // Elementos de CELDAS DISTINTOS

                     x = (i/3) * 3; 			// fila inicial de la celda  0, 3 o 6
                     y = (j / 3) * 3;       // columna inicial de la celda 0,3, 6
                     for (int k=x; k< x+3;k++)
                          for (int l=y; l< y+3;l++) {  // Recorro elementos celda
                               if (k!=i || l != j) {   // Que no sean la propia variable
                                    addConstraint(new NQueensConstraint(variables.get(index), variables.get(k*9 + l)));
                               }
                          }
               }
     }
}
