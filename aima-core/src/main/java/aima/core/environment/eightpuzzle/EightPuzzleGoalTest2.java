package aima.core.environment.eightpuzzle;

import aima.core.search.framework.GoalTest;

/**
 * @author Ravi Mohan
 * 
 */
public class EightPuzzleGoalTest2 implements GoalTest {
	static EightPuzzleBoard goal;

	public boolean isGoalState(Object state) {
		EightPuzzleBoard board = (EightPuzzleBoard) state;
		return board.equals(goal);
	}
	
	// Recovering goalState
	
	public static EightPuzzleBoard getGoalState() {
		return goal;
	}
	
	// Change goalState
	
	public static void setGoalState(EightPuzzleBoard board) {
		goal = board;
	}
}