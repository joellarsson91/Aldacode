package game;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Board extends JPanel {

	// behöver ju inte vara faktiska squares i denna...
	private Square[][] squareMatrix = new Square[GameController.DIMENSION][GameController.DIMENSION];
	private int countFilledSquares = 0;
	public Board() {

		// göra detta i en annan konstruktor istÃ¤llet? vad Ã¤r bp?

		setLayout(new GridLayout(GameController.DIMENSION, GameController.DIMENSION));

		setUpSquares();

	}
	public void plusFilledSquares() {
		countFilledSquares++;
	}
	public void decreaseFilledSquares() {
		countFilledSquares--;
	}

	public boolean fullBoard() {
		if (countFilledSquares==GameController.DIMENSION*GameController.DIMENSION) {
			return true;
		
		}
		return false;
	}
	
	public void findCompMove() {

		int i;
		int responsiveValue;
		int value;
		int bestmove = 1;
		MoveInfo quickWinInfo;
		
		if(fullBoard()) {
			value = 0;
		} else if ((quickWinInfo = immediateCompWin()) !=null) {
			return quickWinInfo;
		}
		
		
	}
	
	public MoveInfo immediateCompWin() {
		
		for (int row = 0; row < squareMatrix.length; row++) {
			for (int column = 0; column < squareMatrix[row].length; column++) {
				
				checkFiveInARow(false, row, column);
			}
		}
		
	}
	
	
	
	public Square getSquare(int row, int column) {
		return squareMatrix[row][column];
	}

	public boolean checkFiveInARow(boolean isHumanMarker, int row, int column) {

		return checkVertical(isHumanMarker, row, column) || checkHorizontal(isHumanMarker, row, column)
				|| checkDiagonals(isHumanMarker, row, column);
	}


	
	private boolean checkVertical(boolean isHumanMarker, int row, int column) {

		Square tempSquare;

		int count = 0;

		for (int checkRow = row - 4; checkRow < row + 5; checkRow++) {

			// is within the board
			if (checkRow >= 0 && checkRow < GameController.DIMENSION) {
				
				tempSquare = squareMatrix[checkRow][column];

				if (tempSquare.isMarked() && tempSquare.isHumanMarker() == isHumanMarker) {
					count++;

					if (count >= 5) {
						return true;
					}
					// make the count 0 if encounter Square that is marked by opponent or is
					// unmarked
				} else
					count = 0;

			}

		}

		return false;
	}

	private boolean checkDiagonals(boolean isHumanMarker, int row, int column) {

		return checkDownToLeft(isHumanMarker, row, column) || checkDownToRight(isHumanMarker, row, column);

	}

	private boolean checkDownToLeft(boolean isHumanMarker, int row, int column) {

		Square tempSquare;

		int count = 0;

		int checkRow = row - 4;

		int checkColumn = column + 4;

		for (int i = 0; i < 9; i++) {

			if (isWithinBoard(checkRow, checkColumn)) {

				tempSquare = squareMatrix[checkRow][checkColumn];

				if (tempSquare.isMarked() && tempSquare.isHumanMarker()) {
					count++;

					if (count >= 5) {
						return true;
					}
				}

				else {
					count = 0;
				}

			}

			checkRow++;
			checkColumn--;

		}

		return false;

	}

	private boolean checkDownToRight(boolean isHumanMarker, int row, int column) {
		Square tempSquare;

		int count = 0;

		int checkRow = row - 4;

		int checkColumn = column - 4;
		
		for (int i = 0; i < 9; i++) {

			if (isWithinBoard(checkRow, checkColumn)) {
				tempSquare = squareMatrix[checkRow][checkColumn];

				if (tempSquare.isMarked() && tempSquare.isHumanMarker() == isHumanMarker) {
					count++;

					if (count >= 5) {
						return true;
					}
				} else {
					count = 0;
				}

			}

			checkRow++;
			checkColumn++;

		}

		return false;
	}

	private boolean isWithinBoard(int row, int column) {

		return row >= 0 && row < GameController.DIMENSION && column >= 0 && column < GameController.DIMENSION;

	}

	private boolean checkHorizontal(boolean isHumanMarker, int row, int column) {

		Square temp;

		int count = 0;

		for (int compareColumn = column - 4; compareColumn < column + 5; compareColumn++) {

			if (compareColumn >= 0 && compareColumn < squareMatrix[row].length) {

				temp = squareMatrix[row][compareColumn];

				if (temp.isMarked() && temp.isHumanMarker() == isHumanMarker) {

					count++;

					if (count >= 5) {
						return true;
					}

				} else {
					count = 0;
				}

			}

		}

		return false;
	}

	public void removeListenerFromSquares(MouseListener squareListener) {
		for (int row = 0; row < squareMatrix.length; row++) {
			for (int column = 0; column < squareMatrix[row].length; column++) {
				Square s = squareMatrix[row][column];
				s.removeMouseListener(squareListener);
			}
		}
	}

	public void addListenerToSquares(MouseListener squareListener) {
		for (int row = 0; row < squareMatrix.length; row++) {
			for (int column = 0; column < squareMatrix[row].length; column++) {
				Square s = squareMatrix[row][column];
				s.addMouseListener(squareListener);
			}
		}
	}

	private void setUpSquares() {

		for (int row = 0; row < squareMatrix.length; row++) {
			for (int column = 0; column < squareMatrix[row].length; column++) {
				Square s = new Square(row, column);
				squareMatrix[row][column] = s;
				add(s);

			}
		}
	}
}
