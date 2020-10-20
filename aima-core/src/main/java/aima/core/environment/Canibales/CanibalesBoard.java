package aima.core.environment.Canibales;

import java.util.Arrays;

import aima.core.agent.Action;
import aima.core.agent.impl.DynamicAction;

/**
 * @author Patricia Briones Yus, 735576
 */

public class CanibalesBoard {
	public enum Posicion {
		IZQ, DER
	}

	public static Action M1C = new DynamicAction("M1C");

	public static Action M2C = new DynamicAction("M2C");

	public static Action M1M = new DynamicAction("M1M");

	public static Action M2M = new DynamicAction("M2M");
	
	public static Action M1M1C = new DynamicAction("M1M1C");

	private int[] state;

	//
	// PUBLIC METHODS
	//

	public CanibalesBoard() {
		/*
		 * Misioneros (M), caníbales (C), posición barca (B), izquierda (i-0), derecha (d-1)
		 * Representación del estado: Mi, Ci, B, Md, Cd
		 */
		state = new int[] { 3, 3, Posicion.IZQ.ordinal(), 0, 0 };
	}

	public CanibalesBoard(int[] state) {
		this.state = new int[state.length];
		System.arraycopy(state, 0, this.state, 0, state.length);
	}

	public CanibalesBoard(CanibalesBoard copyBoard) {
		this(copyBoard.getState());
	}

	public int[] getState() {
		return state;
	}

	public void mover1C() {
		actualizarEstado(0,-1,0,1);
	}

	public void mover2C() {
		actualizarEstado(0,-2,0,2);
	}

	public void mover1M() {
		actualizarEstado(-1,0,1,0);
	}

	public void mover2M() {
		actualizarEstado(-2,0,2,0);
	}
	
	public void mover1M1C() {
		actualizarEstado(-1,-1,1,1);
	}

	public boolean canMove(Action where) {
		boolean retVal = true;
		int Mi = getMi(),
			Ci = getCi(),
			Md = getMd(),
			Cd = getCd();
		
		if (where.equals(M1C))
			retVal = (Ci >= 1 && (Md >= Cd+1 || Md == 0));
		else if (where.equals(M2C))
			retVal = (Ci >= 2 && (Md >= Cd+2 || Md == 0));
		else if (where.equals(M1M))
			retVal = (Mi >= 1 && (Mi-1 >= Ci || Mi-1 == 0) && Md+1 >= Cd);
		else if (where.equals(M2M))
			retVal = (Mi >= 2 && (Mi-2 >= Ci || Mi-2 == 0) && Md+2 >= Cd);
		else if (where.equals(M1M1C))
			retVal = (Mi >= 1 && Ci >= 1 && Md >= Cd);
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
		CanibalesBoard aBoard = (CanibalesBoard) o;

		if (!Arrays.equals(state, aBoard.state))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		int result = 17;
		for (int i = 0; i < 5; i++) {
			int position = this.getPositionOf(i);
			result = 37 * result + position;
		}
		return result;
	}

	@Override
	public String toString() {
		String retVal = "RIBERA-IZQ\s";
		
		for(int i=0; i<5; i++) {
			
			for(int j=2; j>=0; j--) {
				if(state[i]-j <= 0) retVal += "\s\s";
				else {
					if(i == 0 || i == 3) retVal += "M\s";
					else retVal += "C\s";
				}
			}
			
			if(i==1) { 
				i++;
				if(getBoatPosition() == Posicion.IZQ) retVal += "BOTE --RIO--\s\s\s\s\s\s";
				else retVal += "\s\s\s\s\s--RIO-- BOTE\s";
			}
		}
		
		retVal += " RIBERA-DCH";
		
		return retVal;
	}

	//
	// PRIVATE METHODS
	//
	
	/*
	 * Para obtener los datos de los caníbales y misioneros según la posición de
	 * la barca, se ha decidido hacer efecto espejo.
	 */
	private int getMi() {
		if(getBoatPosition() == Posicion.IZQ) return state[0];
		else return state[3];
	}
	
	private int getCi() {
		if(getBoatPosition() == Posicion.IZQ) return state[1];
		else return state[4];
	}
	
	private int getMd() {
		if(getBoatPosition() == Posicion.IZQ) return state[3];
		else return state[0];
	}
	
	private int getCd() {
		if(getBoatPosition() == Posicion.IZQ) return state[4];
		else return state[1];
	}

	private Posicion getBoatPosition() {
		return Posicion.values()[state[2]];
	}

	private int getPositionOf(int val) {
		int retVal = -1;
		for (int i = 0; i < 5; i++) {
			if (state[i] == val) {
				retVal = i;
			}
		}
		return retVal;
	}

	private void cambiarBarca() {
		state[2] = Math.abs(state[2]-1);
	}
	
	private void actualizarEstado(int Mi, int Ci, int Md, int Cd) {
		
		if(getBoatPosition() == Posicion.IZQ) {
			state[0] += Mi;
			state[1] += Ci;
			state[3] += Md;
			state[4] += Cd;
		}
		else {
			state[0] += Md;
			state[1] += Cd;
			state[3] += Mi;
			state[4] += Ci;
		}
		cambiarBarca();
		//System.out.println(state[2]);
	}
}