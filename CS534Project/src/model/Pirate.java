package model;

import java.io.Serializable;

public class Pirate implements Serializable {
	private int index;
	private Cell cell;
	private boolean isReachedToBoat;

	public Pirate(int index, Board board, boolean isReachedToBoat) {
		this.index = index;
		setCell(board.getFirstCell());
		System.out.println("Pirate is given with cell: " + board.getFirstCell().getIndex());
		isReachedToBoat = false;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public boolean isReachedToBoat() {
		return isReachedToBoat;
	}

	public void setReachedToBoat(boolean isReachedToBoat) {
		this.isReachedToBoat = isReachedToBoat;
	}

	public Cell getCell() {
		return cell;
	}

	public void setCell(Cell cell) {
		this.cell = cell;
		cell.setNumOfPirates(cell.getNumOfPirates() + 1);
	}

	public void moveForward(Card playedCard, int startPos, int lastPos) {
		Symbol currentCellSymbol = cell.getSymbol();
		Cell reachedCell = null;
		boolean isAnyEmptyCellFound = false;
		// bulundugum cell uzerindeki pirate sayisi azaltilir
		cell.setNumOfPirates(cell.getNumOfPirates() - 1);
		Cell nextCell = cell.getNext();
		for (int i = startPos + 1; i < lastPos; i++) {
			// eger oynamak istedigim sembol ve cell'in sembolu ayniysa
			if (playedCard.getFrontFace().getSymbol() == nextCell.getSymbol()) {
				// cell bossa
				if (nextCell.getNumOfPirates() == 0) {
					reachedCell = nextCell;
					isAnyEmptyCellFound = true;
					break;
				}
			}
			nextCell = nextCell.getNext();
		}
		if (!isAnyEmptyCellFound) {
			isReachedToBoat = true;
		} else {
			// pirate'in cell'ini degistir.
			// +1'ler sadece debug'i kolaylastirmak icin var.
			System.out.print("Pirate goes forward (" + (startPos + 1) + " , " + currentCellSymbol + " -> ");
			setCell(reachedCell);
			cell.setNumOfPirates(1);
			System.out.println((cell.getIndex() + 1) + " , " + cell.getSymbol() + ")");
		}
	}

	public boolean moveBackward() {
		Cell currentCell = cell;
		while (true) {
			currentCell = currentCell.getPrevious();
			if (currentCell != null) {
				if (currentCell.getNumOfPirates() != 0
						&& currentCell.getNumOfPirates() < Cell.NUM_OF_MAX_PIRATE_IN_A_CELL) {
					cell.setNumOfPirates(cell.getNumOfPirates() - 1);
					System.out.print(
							"Pirate goes backward (" + (cell.getIndex() + 1) + " , " + cell.getSymbol() + " -> ");
					System.out.println((currentCell.getIndex() + 1) + " , " + currentCell.getSymbol() + ")");
					setCell(currentCell);
					return true;
				}
			} else {
				return false;
			}
		}
	}
}
