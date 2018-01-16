package gui;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

import model.Board;
import model.BoardBuilder;
import model.Card;
import model.DeckBuilder;
import model.Game;

public class Server {
	private static final int NUM_OF_PLAYERS = 2;
	// server port number
	public final static int PORT = 3458;

	public static Stack<Command> commands = new Stack<Command>();
	public static ObjectOutputStream writer;
	
	public static void main(String args[]) throws Exception {  
		Scanner userInput = new Scanner(System.in);
		// create socket and bind to port
		ServerSocket sock = new ServerSocket(PORT);

		System.out.println("Server is waiting for client to connect.");
		Socket clientSocket = sock.accept();
		System.out.println("Client has connected.");

		writer = new ObjectOutputStream(clientSocket.getOutputStream());
		ObjectInputStream reader = new ObjectInputStream(clientSocket.getInputStream());
		
		System.out.println("...Cartagena game started(Server)...");
		Board board = BoardBuilder.getBoard();
		List<Card> cards = DeckBuilder.getShuffledCards();
		Game game = new Game(board, cards, NUM_OF_PLAYERS, true);

        GUI gui = new GUI(game, "board.png", 6, 6, true);
        Controller controller = new Controller(game, gui, true);
        controller.start();
        
		while(true) {
			// read msg from client       
			Command msg = (Command)reader.readObject();
			commands.push(msg);
			msg.executeOn(game, gui);
		}		
	}
}











