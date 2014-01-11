package com.tds.tictactoe;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Point;

public class TicTacToe {

	private static final int ERROR_CREATING_DISPLAY = 100;

	private static final int LEFT_MOUSE_BUTTON = 0;
	
	private Board board;
	private UnbeatableAI ai;
	private boolean isAITurn = false;
	
	public void launch() {
		
		initialize();
		while( gameLoopIsRunning() ) {
			play();
		}		
		cleanUp();

	}

	private void play() {
		takeTurn();
		render();
		Display.update();
		checkForWinner();
		checkForGameOver();
	}

	private void takeTurn() {
		if( isAITurn ) {
			runAI();
		} else {
			pollInput();
		}
	}
	
	private void checkForGameOver() {
		if( board.isFull() ) {
			Sys.alert( "Game Over!", "It's a Tie!" );
			System.exit( 0 );
		}
	}

	private void checkForWinner() {
		String player = board.findWinner();
		if( player != null && player.isEmpty() == false ) {
			Sys.alert( "We have a winner!", player + " wins!" );
			System.exit( 0 );
		}
	}
	
	private void runAI() {
		if( isAITurn == true ) {
			Square square = ai.selectSquareOn( board );
			if( square != null ) {
				square.setLabel( "O" );
			}
			isAITurn = false;
		}
	}

	private void render() {
		clearBuffers();
		board.render();
	}

	private void clearBuffers() {
		GL11.glClear( GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT );
	}
	
	private void pollInput() {
		if( Mouse.isButtonDown( LEFT_MOUSE_BUTTON ) ) {
			Square selectedSquare = board.findSquareAt( new Point( Mouse.getX(), Mouse.getY() ) );
			if( selectedSquare != null && selectedSquare.getLabel().isEmpty() ) {
				selectedSquare.setLabel( "X" );
				isAITurn = true;
			}
		}
	}
	
	private boolean gameLoopIsRunning() {
		return Display.isCloseRequested() == false;
	}
	
	private void initialize() {
		initializeBoard();
		initializeAI();
		initializeDisplay();
		initializeOpenGL();
	}

	private void initializeAI() {
		this.ai = new UnbeatableAI();
	}
	
	private void initializeBoard() {
		Point origin = new Point( 100, 100 );
		int boardLength = 600;
		this.board = new Board( origin, boardLength );
	}
	
	private void initializeOpenGL() {
		GL11.glMatrixMode( GL11.GL_PROJECTION );
		GL11.glLoadIdentity();
		GL11.glOrtho( 0, 1024, 0, 768, 1, -1 );
		GL11.glMatrixMode( GL11.GL_MODELVIEW );
	}
	
	private void initializeDisplay() {
		try {
			Display.setDisplayMode( new DisplayMode( 1024, 768 ) );
			Display.create();
		} catch ( LWJGLException e ) {
			e.printStackTrace();
			System.exit( ERROR_CREATING_DISPLAY );
		}
	}
	
	private void cleanUp() {
		Display.destroy();
	}

	public static void main( String [] args ) {
		TicTacToe game = new TicTacToe();
		game.launch();
	}
	
}
