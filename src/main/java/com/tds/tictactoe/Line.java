package com.tds.tictactoe;

import java.util.ArrayList;
import java.util.Collection;

public class Line {

	private Square[] squares = new Square[3];
	
	public Line( Square square0, Square square1, Square square2 ) {
		if( square0 == null || square1 == null || square2 == null ) {
			throw new IllegalArgumentException( "A square in a line cannot be null" );
		}
		
		squares[0] = square0;
		squares[1] = square1;
		squares[2] = square2;
	}
	
	public boolean contains( Square square ) {
		boolean containsSquare = false;
		for( Square squareInLine : squares ) {
			if( square == squareInLine ) {
				containsSquare = true;
			}
		}
		return containsSquare;
	}
	
	public Collection<Square> findAvailableSquares() {
		Collection<Square> availableSquares = new ArrayList<Square>();		
		for( Square square : squares ) {
			if( square.isAvailable() ) {
				availableSquares.add( square );
			}
		}
		return availableSquares;
	}

	public int countSquaresContaining( String label ) {
		int counter = 0;
		for( Square square : squares ) {
			if( square.getLabel().equals( label ) ) {
				counter++;
			}
		}
		return counter;
	}
	
	public boolean hasAvailableCornerOn( Board board ) {
		boolean hasAvailableCorner = false;
		for( Square square : squares ) {
			if( square.isAvailable() && board.isCorner( square ) ) {
				hasAvailableCorner = true;
			}
		}
		return hasAvailableCorner;
	}
	
	public boolean hasAvalailableSquare() {
		boolean hasAvailableSquare = false;
		for( Square square : squares ) {
			if( square.isAvailable() ) {
				hasAvailableSquare = true;
			}
		}
		return hasAvailableSquare;
	}
	
	public Square findAvailableCornerSquareOn( Board board ) {
		Square corner = null;
		for( Square square : squares ) {
			if( square.isAvailable() && board.isCorner( square ) ) {
				corner = square;
			}
		}
		return corner;
	}
	
	public Square findAvailableMiddleSquareOn( Board board ) {
		Square middle = null;
		for( Square square : squares ) {
			if( square.isAvailable() && board.isCorner( square ) == false ) {
				middle = square;
			}
		}
		return middle;
	}

}
