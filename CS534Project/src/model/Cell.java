package model;

import java.io.Serializable;

public class Cell implements Serializable{
	private static final long serialVersionUID = 1L;

	public static final int NUM_OF_MAX_PIRATE_IN_A_CELL = 3;

	private Symbol symbol;
	private int index;
	private int numOfPirates;

	private Cell previous;
	private Cell next;

	public Symbol getSymbol() {
		return symbol;
	}

	public void setSymbol(Symbol symbol) {
		this.symbol = symbol;
	}

	public Cell(int index, Symbol symbol) {
		this.index = index;
		this.symbol = symbol;
		System.out.println("Cell created with index: " + index + " and symbol: " + symbol + ".");
	}

	public Cell getPrevious() {
		return previous;
	}

	public void setPrevious(Cell previous) {
		this.previous = previous;
	}

	public Cell getNext() {
		return next;
	}

	public void setNext(Cell next) {
		this.next = next;
	}

	public int getIndex() {
		return index;
	}

	public int getNumOfPirates() {
		return numOfPirates;
	}

	public void setNumOfPirates(int numOfPirates) {
		this.numOfPirates = numOfPirates;
	}

	@Override
	public String toString() {
		return "Cell(" + (index + 1) + ")";
	}
}
