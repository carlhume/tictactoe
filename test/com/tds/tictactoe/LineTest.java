package com.tds.tictactoe;

import java.util.Collection;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class LineTest {

	@Test
	public void squaresInLineCannotBeNull() {
		try {
			new Line( null, null, null );
			fail( "Nulls are not allowed in Line" );
		} catch( IllegalArgumentException expectedException ) {
			// This is expected to be thrown
		}
	}
	
	@Test
	public void squareInLineIsContainedByLine() {
		Square square = new Square( null, 0 );
		Line line = new Line( square, new Square( null, 0 ), new Square( null, 0 ) );
		assertTrue( line.contains( square ) );
	}
	
	@Test
	public void squareNotInLineIsNotContainedByLine() {
		Square square = new Square( null, 0 );
		Line line = new Line( new Square( null, 0 ), new Square( null, 0 ), new Square( null, 0 ) );
		assertFalse( line.contains( square ) );
	}
	
	@Test
	public void ifNoSquaresAreTakenAllAreAvailable() {
		Square square1 = new Square( null, 0 );
		Square square2 = new Square( null, 0 );
		Square square3 = new Square( null, 0 );
		Line line = new Line( square1, square2, square3 );
		
		Collection<Square> availableSquares = line.findAvailableSquares();
		assertTrue( availableSquares.contains( square1 ) );
		assertTrue( availableSquares.contains( square2 ) );
		assertTrue( availableSquares.contains( square3 ) );		
	}
	
	@Test 
	public void ifSquareIsTakenItIsNotAvailable() {
		Square taken = new Square( null, 0 );
		taken.setLabel( "X" );
		Line line = new Line( new Square( null, 0 ), new Square( null, 0 ), taken );
		Collection<Square> availableSquares = line.findAvailableSquares();
		assertFalse( availableSquares.contains( taken ) );		
	}
	
	@Test
	public void ifAllSquaresAreAvailableNoSquaresContainX() {
		Line line = new Line( new Square( null, 0 ), new Square( null, 0 ), new Square( null, 0 ) );
		assertEquals( 0, line.countSquaresContaining( "X" ) );
	}
	
	@Test
	public void linesCanCountSquaresContainingALabel() {
		Square x = new Square( null, 0 );
		x.setLabel( "X" );
		Line line = new Line( x, x, new Square( null, 0 ) );
		assertEquals( 2, line.countSquaresContaining( "X" ) );
	}
}
