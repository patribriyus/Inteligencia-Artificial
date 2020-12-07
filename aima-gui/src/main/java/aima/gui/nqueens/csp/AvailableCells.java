package aima.gui.nqueens.csp;

import java.util.ArrayList;
import java.util.List;
import aima.core.search.csp.Variable;

/**
 * @author Patricia Briones Yus, 735576
 * 
 */

public class AvailableCells {
     private int numOfAvailable;
     private List<Variable> list;

     public AvailableCells(int num) {
          this.numOfAvailable = num;
          list = new ArrayList<Variable>(numOfAvailable);
     }

     public void insert(int j, int value)  {
          NQueensVariable variable = new NQueensVariable("Reina en columna [" + j +"]",j);
          list.add(variable);
     }

     public int getNumOfAvailable() {
          return numOfAvailable;
     }

     public List<Variable> getList() {
          return list;
     }
}
