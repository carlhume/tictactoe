package com.tds.tictactoe;

public class AI {

	public Square selectSquareOn( Board board ) {
		return board.findFirstEmptySquare();
	}
	
}
