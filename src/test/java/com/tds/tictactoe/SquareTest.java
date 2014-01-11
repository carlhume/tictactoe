package com.tds.tictactoe;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SquareTest {

	@Test
	public void emptySquareIsNotTaken() {
		Square square = new Square( null, 0 );
		assertFalse( square.isTaken() );
	}

	@Test
	public void isTakenIfLabelIsSet() {
		Square square = new Square( null, 0 );
		square.setLabel( "X" );
		assertTrue( square.isTaken() );
	}
}
