package com.tds.tictactoe;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This AI is trying to tie the player;  winning is not the goal.
 * This could be implemented as a lookup table of "best moves" given board placement.
 * I would rather try something algorithmicly.
 * 
 * TODO:  Refactor to 1 AI class that chooses different strategies
 * 
 * @author carl
 *
 */
public class UnbeatableAI extends TicTacToe {

	public Square selectSquareOn( Board board ) {
		return findBlockingSquareOn( board );
	}
	
	// If the player picks anything but the center, the AI will pick the center
	private Square findBlockingSquareOn( Board board ) {
		Square blockingSquare = board.getCenterSquare();
		if( blockingSquare.isTaken() ) {
			Collection<Line> threats = board.findLinesContaining( "X" );
			removeLinesWithNoAvailableSquares( threats );
			Line threat = findGreatestThreatOnBoard( threats, board );
			blockingSquare = pickBestAvailableSquareOnBoardFromLine( board, threat );
		}
		return blockingSquare;
	}

	private void removeLinesWithNoAvailableSquares( Collection<Line> lines ) {
		Collection<Line> linesToRemove = new ArrayList<Line>();
		for( Line line : lines ) {
			if( line.hasAvalailableSquare() == false ) {
				linesToRemove.add( line );
			}
		}
		lines.removeAll( linesToRemove );
	}
	
	public Line findGreatestThreatOnBoard( Collection<Line> threats, Board board ) {
		Line greatestThreat = null;
		for( Line threat : threats ) {
			greatestThreat = anyThreatIsGreaterThanNoThreat( greatestThreat, threat );
			greatestThreat = theGreatestThreatHasMoreSquaresTakenByTheOtherPlayer( greatestThreat, threat );
			greatestThreat = preferThreatsWithAvailableCornerOnBoardWhenEachHasTheSameSquaresTakenByOtherPlayer( greatestThreat, threat, board );
		}
		return greatestThreat;
	}

	private Line theGreatestThreatHasMoreSquaresTakenByTheOtherPlayer( Line threat, Line anotherThreat ) {
		if( anotherThreat.countSquaresContaining( "X" ) > threat.countSquaresContaining( "X" ) ) {
			threat = anotherThreat;
		}
		return threat;
	}

	private Line anyThreatIsGreaterThanNoThreat( Line threat, Line anotherThreat ) {
		if( threat == null && anotherThreat != null ) {
			threat = anotherThreat;
		}
		return threat;
	}
	
	public Line preferThreatsWithAvailableCornerOnBoardWhenEachHasTheSameSquaresTakenByOtherPlayer( Line threat, Line anotherThreat, Board board ) {
		Line preferedThreat = threat;
		if( anotherThreat.countSquaresContaining( "X" ) == threat.countSquaresContaining( "X" ) ) {
			if( anotherThreat.hasAvailableCornerOn( board ) ) {
				preferedThreat = anotherThreat;
			}
		}
		return preferedThreat;
	}
	
	public Square pickBestAvailableSquareOnBoardFromLine( Board board, Line line ) {
		Square bestChoice = null;
		if( prefersCorner( board, line) ) {
			bestChoice = line.findAvailableCornerSquareOn( board );
		} else {
			bestChoice = line.findAvailableMiddleSquareOn( board );
		}
		return bestChoice;
	}
	
	private boolean prefersCorner( Board board, Line line ) {
		boolean preferCorner = true;
		if( line.hasAvailableCornerOn( board ) == false ||
				board.isConfiguredWithPreferMiddleEdgeCase() == true ) {
			preferCorner = false;
		}
		return preferCorner;
	}
}
