package aima.core.environment.Canibales;

import java.util.LinkedHashSet;
import java.util.Set;

import aima.core.agent.Action;
import aima.core.search.framework.ActionsFunction;
import aima.core.search.framework.ResultFunction;

/**
 * @author Patricia Briones Yus, 735576
 */
public class CanibalesFunctionFactory {
	private static ActionsFunction _actionsFunction = null;
	private static ResultFunction _resultFunction = null;

	public static ActionsFunction getActionsFunction() {
		if (null == _actionsFunction) {
			_actionsFunction = new CActionsFunction();
		}
		return _actionsFunction;
	}

	public static ResultFunction getResultFunction() {
		if (null == _resultFunction) {
			_resultFunction = new CResultFunction();
		}
		return _resultFunction;
	}

	private static class CActionsFunction implements ActionsFunction {
		public Set<Action> actions(Object state) {
			CanibalesBoard board = (CanibalesBoard) state;

			Set<Action> actions = new LinkedHashSet<Action>();

			if (board.canMove(CanibalesBoard.M1C)) {
				actions.add(CanibalesBoard.M1C);
			}
			if (board.canMove(CanibalesBoard.M2C)) {
				actions.add(CanibalesBoard.M2C);
			}
			if (board.canMove(CanibalesBoard.M1M)) {
				actions.add(CanibalesBoard.M1M);
			}
			if (board.canMove(CanibalesBoard.M2M)) {
				actions.add(CanibalesBoard.M2M);
			}
			if (board.canMove(CanibalesBoard.M1M1C)) {
				actions.add(CanibalesBoard.M1M1C);
			}

			return actions;
		}
	}

	private static class CResultFunction implements ResultFunction {
		public Object result(Object s, Action a) {
			CanibalesBoard board = (CanibalesBoard) s;

			if (CanibalesBoard.M1C.equals(a)
					&& board.canMove(CanibalesBoard.M1C)) {
				CanibalesBoard newBoard = new CanibalesBoard(board);
				newBoard.mover1C();
				return newBoard;
			} else if (CanibalesBoard.M2C.equals(a)
					&& board.canMove(CanibalesBoard.M2C)) {
				CanibalesBoard newBoard = new CanibalesBoard(board);
				newBoard.mover2C();
				return newBoard;
			} else if (CanibalesBoard.M1M.equals(a)
					&& board.canMove(CanibalesBoard.M1M)) {
				CanibalesBoard newBoard = new CanibalesBoard(board);
				newBoard.mover1M();
				return newBoard;
			} else if (CanibalesBoard.M2M.equals(a)
					&& board.canMove(CanibalesBoard.M2M)) {
				CanibalesBoard newBoard = new CanibalesBoard(board);
				newBoard.mover2M();
				return newBoard;
			} else if (CanibalesBoard.M1M1C.equals(a)
					&& board.canMove(CanibalesBoard.M1M1C)) {
				CanibalesBoard newBoard = new CanibalesBoard(board);
				newBoard.mover1M1C();
				return newBoard;
			}

			// The Action is not understood or is a NoOp
			// the result will be the current state.
			return s;
		}
	}
}