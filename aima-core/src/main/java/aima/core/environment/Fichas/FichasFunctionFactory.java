package aima.core.environment.Fichas;

import java.util.LinkedHashSet;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.search.framework.ActionsFunction;
import aima.core.search.framework.ResultFunction;

/**
 * @author Ravi Mohan
 * @author Ciaran O'Reilly
 */
public class FichasFunctionFactory {
	private static ActionsFunction _actionsFunction = null;
	private static ResultFunction _resultFunction = null;

	public static ActionsFunction getActionsFunction() {
		if (null == _actionsFunction) {
			_actionsFunction = new EPActionsFunction();
		}
		return _actionsFunction;
	}

	public static ResultFunction getResultFunction() {
		if (null == _resultFunction) {
			_resultFunction = new EPResultFunction();
		}
		return _resultFunction;
	}

	private static class EPActionsFunction implements ActionsFunction {
		public Set<Action> actions(Object state) {
			FichasBoard board = (FichasBoard) state;

			Set<Action> actions = new LinkedHashSet<Action>();

			if (board.canMoveGap(FichasBoard.UP)) {
				actions.add(FichasBoard.UP);
			}
			if (board.canMoveGap(FichasBoard.DOWN)) {
				actions.add(FichasBoard.DOWN);
			}
			if (board.canMoveGap(FichasBoard.LEFT)) {
				actions.add(FichasBoard.LEFT);
			}
			if (board.canMoveGap(FichasBoard.RIGHT)) {
				actions.add(FichasBoard.RIGHT);
			}

			return actions;
		}
	}

	private static class EPResultFunction implements ResultFunction {
		public Object result(Object s, Action a) {
			FichasBoard board = (FichasBoard) s;

			if (FichasBoard.UP.equals(a)
					&& board.canMoveGap(FichasBoard.UP)) {
				FichasBoard newBoard = new FichasBoard(board);
				newBoard.moveGapUp();
				return newBoard;
			} else if (FichasBoard.DOWN.equals(a)
					&& board.canMoveGap(FichasBoard.DOWN)) {
				FichasBoard newBoard = new FichasBoard(board);
				newBoard.moveGapDown();
				return newBoard;
			} else if (FichasBoard.LEFT.equals(a)
					&& board.canMoveGap(FichasBoard.LEFT)) {
				FichasBoard newBoard = new FichasBoard(board);
				newBoard.moveGapLeft();
				return newBoard;
			} else if (FichasBoard.RIGHT.equals(a)
					&& board.canMoveGap(FichasBoard.RIGHT)) {
				FichasBoard newBoard = new FichasBoard(board);
				newBoard.moveGapRight();
				return newBoard;
			}

			// The Action is not understood or is a NoOp
			// the result will be the current state.
			return s;
		}
	}
}