package aima.core.environment.Canibales;

import aima.core.environment.Canibales.CanibalesBoard.Posicion;
import aima.core.search.framework.GoalTest;

/**
 * @author Patricia Briones Yus, 735576
 * 
 */

public class CanibalesGoalTest implements GoalTest {
	CanibalesBoard goal = new CanibalesBoard(new int[] { 0, 0, Posicion.DER.ordinal(), 3, 3 });

	public boolean isGoalState(Object state) {
		CanibalesBoard board = (CanibalesBoard) state;
		return board.equals(goal);
	}
}