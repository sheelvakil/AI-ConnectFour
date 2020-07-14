package connectFour;

import java.util.Scanner;

public class Player {
	private String character;
	private Board board;
	Scanner scn = new Scanner(System.in);

	public Player(String token, Board b) {
		character = token;
		board = b;
	}
	
	public void playerTurn() {
		boolean validTurn = false;
		while (!validTurn) {
			System.out.println(getToken() + ": " + "Enter a column 0 to 6 that you want to play.");
			int col = scn.nextInt();
			validTurn = turn(col);
		}
	}
	

	public boolean turn(int col) {
		return board.turn(col, getToken());
	}


	public String getToken() {
		return character;
	}
	
	public boolean isWinner() {
		if (board.isWinner(getToken())) {
			System.out.println(getToken() + " won!");
			return true;
		}
		return false;
	}

	public boolean isTie() {
		if (board.isTie()) {
			System.out.println("The game has ended in the a tie.");
			return true;
		}
		return false;
	}
	
	

}
