package com.tds.tictactoe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.lwjgl.util.Point;

/**
 * Board Coordinates
 * 
 *    2, 0   |   2, 1   |   2, 2
 *    ---------------------------
 *    1, 0   |   1, 1   |   1, 2
 *    --------------------------
 *    0, 0   |   0, 1   |   0, 2
 *    
 */

public class Board {

	private static final int LINE_WIDTH = 5;
	
	private Square [][] squares;	
	private Point origin;
	private int lenghtOfSquareSide;
	
	private Line [] lines;
	
	/**
	 * Create a board with 0 squares starting from the origin point
	 * 
	 * TODO:  Separate logical components from UI
	 * 
	 */
	public Board() {
		this( new Point( 0, 0 ), 0 );
	}
	
	public Board( Point origin, int length ) {
		this.squares = new Square[3][3];
		this.origin = origin;
		
		// TODO: Add unit tests for calculation
		lenghtOfSquareSide = (length - (2 * LINE_WIDTH)) / 3;
		
		initializeSquares();
		initializeLines();
	}

	private void initializeSquares() {
		squares[0][0] = new Square( determineOriginForSquareAt( 0, 0 ), lenghtOfSquareSide );
		squares[0][1] = new Square( determineOriginForSquareAt( 0, 1 ), lenghtOfSquareSide );
		squares[0][2] = new Square( determineOriginForSquareAt( 0, 2 ), lenghtOfSquareSide );
		
		squares[1][0] = new Square( determineOriginForSquareAt( 1, 0 ), lenghtOfSquareSide );
		squares[1][1] = new Square( determineOriginForSquareAt( 1, 1 ), lenghtOfSquareSide );
		squares[1][2] = new Square( determineOriginForSquareAt( 1, 2 ), lenghtOfSquareSide );
		
		squares[2][0] = new Square( determineOriginForSquareAt( 2, 0 ), lenghtOfSquareSide );
		squares[2][1] = new Square( determineOriginForSquareAt( 2, 1 ), lenghtOfSquareSide );
		squares[2][2] = new Square( determineOriginForSquareAt( 2, 2 ), lenghtOfSquareSide );
	}

	private void initializeLines() {
		lines = new Line[8];
		lines[0] = new Line( squares[0][0], squares[0][1], squares[0][2] );
		lines[1] = new Line( squares[1][0], squares[1][1], squares[1][2] );
		lines[2] = new Line( squares[2][0], squares[2][1], squares[2][2] );
		lines[3] = new Line( squares[0][0], squares[1][0], squares[2][0] );
		lines[4] = new Line( squares[0][1], squares[1][1], squares[2][1] );
		lines[5] = new Line( squares[0][2], squares[1][2], squares[2][2] );
		lines[6] = new Line( squares[0][0], squares[1][1], squares[2][2] );
		lines[7] = new Line( squares[2][0], squares[1][1], squares[0][2] );
	}
	
	// TODO: Add unit tests
	private Point determineOriginForSquareAt( int arrayX, int arrayY ) {
		int originXModifier = ( lenghtOfSquareSide + LINE_WIDTH ) * arrayX;
		int originYModifier = ( lenghtOfSquareSide + LINE_WIDTH ) * arrayY;
		return new Point( origin.getX() + originXModifier, origin.getY() + originYModifier );
	}
	
	public void render() {
		for( Square [] column : squares ) {
			for( Square square : column ) {
				square.render();
			}
		}
	}
	
	// >> cnh >> Math can be used to select the right square with all the information the board has, but the naive approach will work given how late it is ...
	public Square findSquareAt( Point point ) {
		Square foundSquare = null;
		for( Square [] column : squares ) {
			for( Square square : column ) {
				if( square.areaContains( point ) ) {
					foundSquare = square;
					break;
				}
			}
		}
		return foundSquare;
	}
	
	public Square findSquareAt( int logicalX, int logicalY ) {
		return squares[logicalX][logicalY];
	}
	
	public Collection<Line> findLinesContaining( String label ) {
		Collection<Line> linesContainingLabel = new ArrayList<Line>();
		Collection<Square> squaresWithLabel = findSquaresWithLabel( label );
		for( Line line : lines ) {
			for( Square square : squaresWithLabel ) {
				if( line.contains( square ) ) {
					linesContainingLabel.add( line );
					break;
				}
			}
		}
		return linesContainingLabel;
	}
	
	public boolean isCorner( Square square ) {
		return square == squares[0][0] ||
			   square == squares[2][0] ||
			   square == squares[0][2] ||
			   square == squares[2][2];
	}

	
	public Square getCenterSquare() {
		return squares[1][1];
	}
	
	public Square findFirstEmptySquare() {
		Square foundSquare = null;
		for( Square [] column : squares ) {
			for( Square square : column ) {
				if( square.getLabel().isEmpty() ) {
					return square;
				}
			}
		}
		return foundSquare;
	}
	
	public boolean isFull() {
		boolean isFull = true;
		for( Square [] column : squares ) {
			for( Square square : column ) {
				if( square.getLabel().isEmpty() ) {
					isFull = false;
					break;
				}
			}
		}
		return isFull;
	}
	
	public String findWinner() {
		String winner = findWinnerInColumn();
		if( winner.isEmpty() ) {
			winner = findWinnerInRow();
		}
		if( winner.isEmpty() ) {
			winner = findWinnerInDiagonal();
		}
		return winner;		
	}
	
	private String findWinnerInColumn() {
		String winner = findWinnerInColumn( 0 );
		if( winner.isEmpty() ) {
			winner = findWinnerInColumn( 1 );
		}
		if( winner.isEmpty() ) {
			winner = findWinnerInColumn( 2 );
		}
		return winner;
	}
	
	private String findWinnerInRow() {
		String winner = findWinnerInRow( 0 );
		if( winner.isEmpty() ) {
			winner = findWinnerInRow( 1 );
		}
		if( winner.isEmpty() ) {
			winner = findWinnerInRow( 2 );
		}
		return winner;
	}
	
	private String findWinnerInColumn( int column ) {
		String winner = "";
		if( squares[column][0].getLabel().equals( squares[column][1].getLabel() ) && 
				squares[column][0].getLabel().equals( squares[column][2].getLabel() ) ) {
			winner = squares[column][0].getLabel();
		}
		return winner;
	}
	
	private String findWinnerInRow( int row ) {
		String winner = "";
		if( squares[0][row].getLabel().equals( squares[1][row].getLabel() ) && 
				squares[0][row].getLabel().equals( squares[2][row].getLabel() ) ) {
			winner = squares[0][row].getLabel();
		}
		return winner;
	}

	private String findWinnerInDiagonal() {
		String winner = "";
		if( squares[0][0].getLabel().equals( squares[1][1].getLabel() ) && 
				squares[0][0].getLabel().equals( squares[2][2].getLabel() ) ) {
			winner = squares[0][0].getLabel();
		} else if(  squares[2][0].getLabel().equals( squares[1][1].getLabel() ) && 
				squares[2][0].getLabel().equals( squares[0][2].getLabel() ) ) {
			winner = squares[2][0].getLabel();
		}
		return winner;
	}
	
	public Set<Square> findSquaresWithLabel( String label ) {
		Set<Square> squaresWithLabel = new HashSet<Square>();
		for( Square [] column : squares ) {
			for( Square square : column ) {
				if( square.getLabel().equals( label ) ) {
					squaresWithLabel.add( square );
				}
			}
		}
		return squaresWithLabel;
	}
	
	// TODO: This method doesn't belong here...
	public boolean isConfiguredWithPreferMiddleEdgeCase() {
		boolean preferMiddle = false;
		if( squares[0][0].getLabel().equals( "X" ) && squares[2][2].getLabel().equals( "X" ) && countTakenSquares() == 3 ||
				squares[0][2].getLabel().equals( "X" ) && squares[2][0].getLabel().equals( "X" ) && countTakenSquares() == 3 ) {
			preferMiddle = true;
		}
		return preferMiddle;
	}
	
	public int countTakenSquares() {
		int counter = 0;
		for( Square [] column : squares ) {
			for( Square square : column ) {
				if( square.isTaken() ) {
					counter++;
				}
			}
		}
		return counter;
	}
}
