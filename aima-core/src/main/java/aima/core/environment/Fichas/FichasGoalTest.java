package aima.core.environment.Fichas;

import aima.core.search.framework.GoalTest;

/**
 * @author Ravi Mohan
 * 
 */
public class FichasGoalTest implements GoalTest {
	FichasBoard goal = new FichasBoard(new int[] { 0, 1, 2, 3, 4, 5,
			6, 7, 8 });

	public boolean isGoalState(Object state) {
		FichasBoard board = (FichasBoard) state;
		return board.equals(goal);
	}
}