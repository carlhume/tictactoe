package com.tds.tictactoe;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

public class BoardTest {

	private Board emptyBoard;
	
	@Before
	public void setUp() {
		this.emptyBoard = new Board();
	}
	
	@Test
	public void newBoardIsNotFull() {
		assertFalse( emptyBoard.isFull() );
	}
	
	@Test
	public void fullBoardIsFull() {
		Board fullBoard = createFullBoard();
		assertTrue( fullBoard.isFull() );
	}

	@Test
	public void firstEmptySquareOnAnEmptyBoardIsTheOrigin() {
		assertSame( emptyBoard.findSquareAt( 0, 0 ), emptyBoard.findFirstEmptySquare() );
	}
	
	@Test
	public void centerSquareIsAt11() {
		assertSame( emptyBoard.findSquareAt( 1, 1 ), emptyBoard.getCenterSquare() );
	}
	
	@Test
	public void isCornerIdentifiesCornerSquares() {
		assertTrue( emptyBoard.isCorner( emptyBoard.findSquareAt( 0, 0 ) ) );
		assertTrue( emptyBoard.isCorner( emptyBoard.findSquareAt( 0, 2 ) ) );
		assertTrue( emptyBoard.isCorner( emptyBoard.findSquareAt( 2, 0 ) ) );
		assertTrue( emptyBoard.isCorner( emptyBoard.findSquareAt( 2, 2 ) ) );
		assertFalse( emptyBoard.isCorner( emptyBoard.getCenterSquare() ) );
		assertFalse( emptyBoard.isCorner( emptyBoard.findSquareAt( 1, 0 ) ) );
	}
	
	@Test
	public void thereAreFourLinesContainingTheCenterSquare() {
		emptyBoard.getCenterSquare().setLabel( "X" );
		assertEquals( 4, emptyBoard.findLinesContaining( "X" ).size() );
	}
	
	@Test
	public void testAllLinesContainXWhenSetWithXInTheseFourPositions() {
		emptyBoard.findSquareAt( 0, 0 ).setLabel( "X" );
		emptyBoard.findSquareAt( 0, 2 ).setLabel( "X" );
		emptyBoard.findSquareAt( 1, 2 ).setLabel( "X" );
		emptyBoard.findSquareAt( 2, 1 ).setLabel( "X" );
		assertEquals( 8, emptyBoard.findLinesContaining( "X" ).size() );
	}
	
	private Board createFullBoard() {
		Board fullBoard = new Board();
		fullBoard.findSquareAt( 0, 0 ).setLabel( "X" );
		fullBoard.findSquareAt( 0, 1 ).setLabel( "X" );
		fullBoard.findSquareAt( 0, 2 ).setLabel( "X" );
		fullBoard.findSquareAt( 1, 0 ).setLabel( "X" );
		fullBoard.findSquareAt( 1, 1 ).setLabel( "X" );
		fullBoard.findSquareAt( 1, 2 ).setLabel( "X" );
		fullBoard.findSquareAt( 2, 0 ).setLabel( "X" );
		fullBoard.findSquareAt( 2, 1 ).setLabel( "X" );
		fullBoard.findSquareAt( 2, 2 ).setLabel( "X" );
		return fullBoard;
	}
	
}
