package aima.core.environment.Fichas;

import java.util.LinkedHashSet;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.search.framework.ActionsFunction;
import aima.core.search.framework.ResultFunction;

/**
 * @author Patricia Briones Yus, 735576
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

			if (board.canMoveGap(FichasBoard.M1I)) {
				actions.add(FichasBoard.M1I);
			}
			if (board.canMoveGap(FichasBoard.M2I)) {
				actions.add(FichasBoard.M2I);
			}
			if (board.canMoveGap(FichasBoard.M3I)) {
				actions.add(FichasBoard.M3I);
			}
			if (board.canMoveGap(FichasBoard.M1D)) {
				actions.add(FichasBoard.M1D);
			}
			if (board.canMoveGap(FichasBoard.M2D)) {
				actions.add(FichasBoard.M2D);
			}
			if (board.canMoveGap(FichasBoard.M3D)) {
				actions.add(FichasBoard.M3D);
			}

			return actions;
		}
	}

	private static class EPResultFunction implements ResultFunction {
		public Object result(Object s, Action a) {
			FichasBoard board = (FichasBoard) s;

			if (FichasBoard.M1I.equals(a)
					&& board.canMoveGap(FichasBoard.M1I)) {
				FichasBoard newBoard = new FichasBoard(board);
				newBoard.moveGap(-1);
				return newBoard;
			} else if (FichasBoard.M2I.equals(a)
					&& board.canMoveGap(FichasBoard.M2I)) {
				FichasBoard newBoard = new FichasBoard(board);
				newBoard.moveGap(-2);
				return newBoard;
			} else if (FichasBoard.M3I.equals(a)
					&& board.canMoveGap(FichasBoard.M3I)) {
				FichasBoard newBoard = new FichasBoard(board);
				newBoard.moveGap(-3);
				return newBoard;
			} else if (FichasBoard.M1D.equals(a)
					&& board.canMoveGap(FichasBoard.M1D)) {
				FichasBoard newBoard = new FichasBoard(board);
				newBoard.moveGap(1);
				return newBoard;
			} else if (FichasBoard.M2D.equals(a)
					&& board.canMoveGap(FichasBoard.M2D)) {
				FichasBoard newBoard = new FichasBoard(board);
				newBoard.moveGap(2);
				return newBoard;
			} else if (FichasBoard.M3D.equals(a)
					&& board.canMoveGap(FichasBoard.M3D)) {
				FichasBoard newBoard = new FichasBoard(board);
				newBoard.moveGap(3);
				return newBoard;
			}

			// The Action is not understood or is a NoOp
			// the result will be the current state.
			return s;
		}
	}
}