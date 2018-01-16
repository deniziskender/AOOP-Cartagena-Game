package model;

public class BoardBuilder {
	public static final int NUM_OF_CELLS = 36;
    public static Board getBoard() {
		System.out.println("---Board creation started.---");
        Board board = new Board(NUM_OF_CELLS);
		System.out.println("---Board creation ended.---");
        return board;
    }
}
