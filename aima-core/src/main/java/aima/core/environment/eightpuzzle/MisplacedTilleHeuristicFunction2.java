package aima.core.environment.eightpuzzle;

import aima.core.search.framework.HeuristicFunction;

/**
 * @author Patricia Briones Yus, 735576
 * 
 */
public class MisplacedTilleHeuristicFunction2 implements HeuristicFunction {

	public double h(Object state) {
		EightPuzzleBoard board = (EightPuzzleBoard) state;
		EightPuzzleBoard goalboard = EightPuzzleGoalTest2.getGoalState();
		return getNumberOfMisplacedTiles(board, goalboard);
	}

	private int getNumberOfMisplacedTiles(EightPuzzleBoard board, EightPuzzleBoard goalboard) {
		int numberOfMisplacedTiles = 0;
		for(int i=0; i<=8; i++) {
			if (!(board.getLocationOf(i).equals(goalboard.getLocationOf(i)))) {
				numberOfMisplacedTiles++;
			}
		}
		
		// Subtract the gap position from the # of misplaced tiles
		// as its not actually a tile (see issue 73).
		if (numberOfMisplacedTiles > 0) {
			numberOfMisplacedTiles--;
		}
		return numberOfMisplacedTiles;
	}
}