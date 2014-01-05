package com.tds.tictactoe;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class GraphicsTest {

	private static final int ERROR_CREATING_DISPLAY = 100;
	
	public void testDisplay() {
		
		setupDisplay();		
		while( Display.isCloseRequested() == false ) {
			Display.update();
		}		
		Display.destroy();
		
	}

	private void setupDisplay() {
		try {
			Display.setDisplayMode( new DisplayMode( 1024, 768 ) );
			Display.create();
		} catch ( LWJGLException e ) {
			e.printStackTrace();
			System.exit( ERROR_CREATING_DISPLAY );
		}
	}
	
	public static void main( String [] args ) {
		GraphicsTest test = new GraphicsTest();
		test.testDisplay();
	}
}
