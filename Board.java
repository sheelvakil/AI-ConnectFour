package connectFour;

public class Board {

	String[][] board; // previosuly it was "private static"

	public Board() {
		board = new String[7][7];
	}

	public void setBoard() {
		for (int j = 0; j < board[0].length; j++) {
			board[0][j] = "" + j;
		}
		for (int i = 1; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				board[i][j] = "~";
			}
		}
	}

	public void printBoard() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				System.out.print(board[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	public boolean turn(int col, String token) {
		if (col < 0 || col > 6) {
			System.out.println("Column chosen is out of bounds, please pick a column between 0 to 6.");
			return false;
		} else {
			int freeRow = getFreeRow(col);
			if (freeRow == -1) {
				System.out.println("Column is filled, please pick another column.");
				return false;
			} else {
				board[freeRow][col] = token;
				return true;
			}
		}
	}
	

	public int getFreeRow(int col) {
		for (int i = board.length - 1; i >= 1; i--) {
			if (board[i][col].equals("~")) {
				return i;
			}
		}
		return -1;
	}

	public boolean inBounds(int i, int j) {
		if (i < 1 || i >= board.length || j < 0 || j >= board[0].length) {
			return false;
		}
		return true;
	}

	public boolean isWinner(String token) {
		// check board if there is a connect four, for the given token.
		for (int i = 1; i < board.length; i++) {
			for (int j = 0; j < board[0].length; j++) {
				if (board[i][j].equals(token)) {
					if (checkConnectFour(i, j, token)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean checkConnectFour(int i, int j, String token) {
		int row = i; // saving original row value
		int col = j; // saving original column value

		// horizontal check
		int count = 0;
		while (inBounds(i, j) && board[i][j].equals(token)) { // go left
			count++;
			j--;
		}
		while (inBounds(i, j) && board[i][j].equals(token)) { // go right
			count++;
			j++;
		}
		if (count >= 4) {
			return true;
		}
		// vertical check
		count = 0;
		j = col; // resetting the column value
		i = row;
		while (inBounds(i, j) && board[i][j].equals(token)) { // go up
			count++;
			i--;
		}
		while (inBounds(i, j) && board[i][j].equals(token)) { // go down
			count++;
			i++;
		}
		if (count >= 4) {
			return true;
		}

		// left diagonal check
		count = 0;
		i = row; // resetting row value
		j = col;
		while (inBounds(i, j) && board[i][j].equals(token)) { // go upper left (both i and j --)
			i--;
			j--;
			count++;
		}
		i = row;
		j = col;
		count--;// prevent double count
		while (inBounds(i, j) && board[i][j].equals(token)) { // go lower right (both i and j ++)
			i++;
			j++;
			count++;
		}
		if (count >= 4) {
			return true;
		}

		// right diagonal check
		count = 0;
		i = row;
		j = col;
		while (inBounds(i, j) && board[i][j].equals(token)) { // go lower left, j--, i++
			i++;
			j--;
			count++;
		}
		i = row;
		j = col;
		count--;// = prevent double count
		while (inBounds(i, j) && board[i][j].equals(token)) { // go upper right, i--, j++
			i--;
			j++;
			count++;
		}
		if (count >= 4) {
			return true;
		}

		return false;

	}

	public boolean isTie() {
		for (int i = 1; i < board.length; i++) {
			for (int j = 1; j < board[i].length; j++) {
				if (board[i][j].equals("~")) {
					return false;
				}
			}
		}
		return true;
	}

	public int numOfFreeCol() {
		int count = 0;
		for (int j = 0; j < board.length; j++) {
			if (board[1][j].equals("~")) {
				count++;
			}
		}
		return count;
	}

}
