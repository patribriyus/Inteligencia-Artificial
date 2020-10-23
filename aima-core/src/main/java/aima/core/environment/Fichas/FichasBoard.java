package aima.core.environment.Fichas;

import aima.core.agent.Action;
import aima.core.agent.impl.DynamicAction;

/**
 * @author Patricia Briones Yus, 735576
 */
public class FichasBoard {

	public static Action M1I = new DynamicAction("M1I");

	public static Action M2I = new DynamicAction("M2I");
	
	public static Action M3I = new DynamicAction("M3I");

	public static Action M1D = new DynamicAction("M1D");

	public static Action M2D = new DynamicAction("M2D");
	
	public static Action M3D = new DynamicAction("M3D");

	private int[] state;

	//
	// PUBLIC METHODS
	//

	public FichasBoard() {
		/* Ficha V=1, B=2, espacio=0 */
		state = new int[] { 1, 1, 1, 0, 2, 2, 2 };
	}

	public FichasBoard(int[] state) {
		this.state = new int[state.length];
		System.arraycopy(state, 0, this.state, 0, state.length);
	}

	public FichasBoard(FichasBoard copyBoard) {
		this(copyBoard.getState());
	}

	public int[] getState() {
		return state;
	}

	public void moveGap(int i) {
		int posGap = getGapPosition();
		state[posGap] = state[posGap+i];
		state[posGap+i] = 0; // Se establece el hueco
	}

	public boolean canMoveGap(Action where) {
		boolean retVal = true;
		int posGap = getGapPosition();
		if (where.equals(M1I))
			retVal = (posGap >= 1);
		else if (where.equals(M2I))
			retVal = (posGap >= 2);
		else if (where.equals(M3I))
			retVal = (posGap >= 3);
		else if (where.equals(M1D))
			retVal = (posGap < state.length-1);
		else if (where.equals(M2D))
			retVal = (posGap < state.length-2);
		else if (where.equals(M3D))
			retVal = (posGap < state.length-3);
		return retVal;
	}

	@Override
	public boolean equals(Object o) {

		if (this == o) {
			return true;
		}
		if ((o == null) || (this.getClass() != o.getClass())) {
			return false;
		}
		FichasBoard aBoard = (FichasBoard) o;

		for (int i = 0; i < 7; i++) {
			if (this.getValOf(i) != aBoard.getValOf(i)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		for (int i = 0; i < 7; i++) {
			int position = this.getPositionOf(i);
			result = 37 * result + position;
		}
		return result;
	}

	@Override
	public String toString() {
		String retVal = "+---+---+---+---+---+---+---+\n";
		retVal += "|";
		for(int i=0; i<7; i++) {
			if(state[i] == 1) retVal += " V ";
			else if(state[i] == 2) retVal += " B ";
			else retVal += "\s\s\s";
			retVal += "|";
		}
		retVal += "\n+---+---+---+---+---+---+---+";
		
		return retVal;
	}

	//
	// PRIVATE METHODS
	//

	private int getGapPosition() {
		return getPositionOf(0);
	}

	private int getPositionOf(int val) {
		int retVal = -1;
		for (int i = 0; i < 7; i++) {
			if (state[i] == val) {
				retVal = i;
			}
		}
		return retVal;
	}
	
	private int getValOf(int val) {
		return state[val];
	}
}