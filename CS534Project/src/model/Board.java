package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Board {
	private static final int FIRST_CELL = 0;
	
	private Cell firstCell;
	private Cell lastCell;

	public Board(int numCells) {
		if (numCells < 1) {
			throw new IllegalArgumentException();
		}

		// create all symbols for board(static creation)
		List<Symbol> allSymbols = new ArrayList<Symbol>();
		// SEGMENT 1
		allSymbols.add(Symbol.BOTTLE);
		allSymbols.add(Symbol.PISTOL);
		allSymbols.add(Symbol.SKULL);
		allSymbols.add(Symbol.HAT);
		allSymbols.add(Symbol.SWORD);
		allSymbols.add(Symbol.KEYS);
		// SEGMENT 2
		allSymbols.add(Symbol.BOTTLE);
		allSymbols.add(Symbol.SWORD);
		allSymbols.add(Symbol.PISTOL);
		allSymbols.add(Symbol.HAT);
		allSymbols.add(Symbol.SKULL);
		allSymbols.add(Symbol.KEYS);
		// SEGMENT 3
		allSymbols.add(Symbol.HAT);
		allSymbols.add(Symbol.PISTOL);
		allSymbols.add(Symbol.BOTTLE);
		allSymbols.add(Symbol.SKULL);
		allSymbols.add(Symbol.SWORD);
		allSymbols.add(Symbol.KEYS);	
		// SEGMENT 4
		allSymbols.add(Symbol.KEYS);
		allSymbols.add(Symbol.PISTOL);
		allSymbols.add(Symbol.BOTTLE);
		allSymbols.add(Symbol.SWORD);
		allSymbols.add(Symbol.SKULL);
		allSymbols.add(Symbol.HAT);
		// SEGMENT 5
		allSymbols.add(Symbol.BOTTLE);
		allSymbols.add(Symbol.HAT);
		allSymbols.add(Symbol.KEYS);
		allSymbols.add(Symbol.SKULL);
		allSymbols.add(Symbol.SWORD);
		allSymbols.add(Symbol.PISTOL);
		// SEGMENT 6
		allSymbols.add(Symbol.BOTTLE);
		allSymbols.add(Symbol.PISTOL);
		allSymbols.add(Symbol.SWORD);
		allSymbols.add(Symbol.SKULL);
		allSymbols.add(Symbol.HAT);
		allSymbols.add(Symbol.KEYS);
		//
		firstCell = new Cell(FIRST_CELL, allSymbols.get(FIRST_CELL));
		lastCell = firstCell;

		for (int i = 1; i < numCells; i++) {
			Cell cell = new Cell(i, allSymbols.get(i));
			Cell tempLastCell = lastCell;
			lastCell.setNext(cell);
			lastCell = cell;
			lastCell.setPrevious(tempLastCell);
		}
		// Last cell points to itself to prevent going "out of bounds"
		lastCell.setNext(lastCell);
	}
/*	public Board(int numCells) {
		if (numCells < 1) {
			throw new IllegalArgumentException();
		}

		// create all symbols for board
		List<Symbol> allSymbols = new ArrayList<Symbol>();
		for (int i = 0; i< NUM_OF_SEGMENTS; i++) {
			List<Symbol> symbolsInASegment = Arrays.asList(Symbol.values());
			Collections.shuffle(symbolsInASegment);
			for (int j = 0; j< symbolsInASegment.size(); j++) {
				Symbol cellSymbol = symbolsInASegment.get(j);
				allSymbols.add(cellSymbol);
			}
		}
		
		firstCell = new Cell(FIRST_CELL, allSymbols.get(FIRST_CELL));
		lastCell = firstCell;

		for (int i = 1; i < numCells; i++) {
			Cell cell = new Cell(i, allSymbols.get(i));
			lastCell.setNext(cell);
			lastCell = cell;
		}
		// Last cell points to itself to prevent going "out of bounds"
		// This gives us the "RelaxedLandingRule" for free.
		lastCell.setNext(lastCell);
	}
*/
	public Cell getFirstCell() {
		return firstCell;
	}

	public Cell getLastCell() {
		return lastCell;
	}

	public int getNumCells() {
		return lastCell.getIndex() + 1;
	}
}
