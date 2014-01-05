package com.tds.tictactoe;


import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

public class UnbeatableAITest {

	private UnbeatableAI ai;
	private Board board;
	
	@Before
	public void setUp() {
		ai = new UnbeatableAI();
		board = new Board();
	}
	
	@Test
	public void takeCenterSquareIfBoardIsEmpty() {
		assertSame( ai.selectSquareOn( board ), board.getCenterSquare() );
	}
	
	@Test
	public void takeCenterSquareIfAvailable() {
		board.findSquareAt( 2, 0 ).setLabel( "X" );
		assertSame( ai.selectSquareOn( board ), board.getCenterSquare() );
	}
	
	@Test
	public void firstMoveIfCenterIsTakenWillBeCorner() {
		board.getCenterSquare().setLabel( "X" );
		assertTrue( board.isCorner( ai.selectSquareOn( board ) ) );
	}
	
	@Test
	public void willBlockTwoInARow() {
		board.findSquareAt( 0, 0 ).setLabel( "X" );
		board.findSquareAt( 1, 1 ).setLabel( "X" );
		assertSame( board.findSquareAt( 2, 2 ), ai.selectSquareOn( board ) );
	}
	
	@Test
	public void willSelectAvailableSquareWithSidewaysTBoradSetup() {
		board.findSquareAt( 0, 0 ).setLabel( "X" );
		board.findSquareAt( 2, 0 ).setLabel( "X" );
		board.findSquareAt( 1, 2 ).setLabel( "X" );
		board.findSquareAt( 1, 1 ).setLabel( "O" );
		board.findSquareAt( 1, 0 ).setLabel( "O" );
		assertTrue( ai.selectSquareOn( board ).isAvailable() );
	}
	
	@Test
	public void willSelectAvailableSquareWithTwoCornersTakenByOtherPlayer() {
		board.findSquareAt( 0, 0 ).setLabel( "X" );
		board.findSquareAt( 2, 2 ).setLabel( "X" );
		board.findSquareAt( 1, 1 ).setLabel( "O" );
		assertTrue( ai.selectSquareOn( board ).isAvailable() );		
	}
	
	@Test
	public void willSelectAvailableSquareWithTopTwoLinesAndBottomRightCornerTaken() {
		board.findSquareAt( 0, 2 ).setLabel( "X" );
		board.findSquareAt( 1, 2 ).setLabel( "X" );
		board.findSquareAt( 2, 1 ).setLabel( "X" );
		board.findSquareAt( 0, 0 ).setLabel( "X" );
		board.findSquareAt( 2, 2 ).setLabel( "O" );
		board.findSquareAt( 1, 1 ).setLabel( "O" );
		board.findSquareAt( 0, 1 ).setLabel( "O" );
		assertTrue( ai.selectSquareOn( board ).isAvailable() );		
	}
	
	@Test
	public void willNotSelectCornerIfOtherPlayersFirstTwoMovesAreOppositeCorners() {
		board.findSquareAt( 0, 2 ).setLabel( "X" );
		board.findSquareAt( 2, 0 ).setLabel( "X" );
		board.findSquareAt( 1, 1 ).setLabel( "O" );
		assertFalse( board.isCorner( ai.selectSquareOn( board ) ) );		
	}
}
