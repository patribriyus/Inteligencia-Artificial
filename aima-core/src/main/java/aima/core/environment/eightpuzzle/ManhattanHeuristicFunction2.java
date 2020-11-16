package aima.core.environment.eightpuzzle;

import aima.core.search.framework.HeuristicFunction;
import aima.core.util.datastructure.XYLocation;

/**
 * @author Patricia Briones Yus, 735576
 * 
 */
public class ManhattanHeuristicFunction2 implements HeuristicFunction {

	public double h(Object state) {
		EightPuzzleBoard board = (EightPuzzleBoard) state;
		EightPuzzleBoard goalboard = EightPuzzleGoalTest2.getGoalState();
		int retVal = 0;
		for (int i = 1; i < 9; i++) {
			XYLocation loc = board.getLocationOf(i);
			XYLocation locgoal = goalboard.getLocationOf(i);
			retVal += evaluateManhattanDistanceOf(loc, locgoal);
		}
		return retVal;

	}

	public int evaluateManhattanDistanceOf(XYLocation loc, XYLocation locgoal) {
		int retVal = -1;
		int xpos = loc.getXCoOrdinate();
		int ypos = loc.getYCoOrdinate();
		int xposgoal = locgoal.getXCoOrdinate();
		int yposgoal = locgoal.getYCoOrdinate();

		retVal = Math.abs(xpos - xposgoal) + Math.abs(ypos - yposgoal);

		return retVal;
	}
}