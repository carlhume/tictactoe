package com.tds.tictactoe;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Point;

public class Square {

	private int length;
	private Point origin;
	
	private String label = "";
	
	public Square( Point origin, int length ) {
		this.origin = origin;
		this.length = length;
	}
	
	public void render() {
		renderSquare();
		renderLabel();
	}

	private void renderLabel() {
		if( label.equals( "X" ) ) {
			renderX();
		} else if( label.equals( "O" ) ) {
			renderO();
		}
	}
	
	private void renderX() {
		GL11.glColor3f( 1.0f, 0.0f, 0.0f );
		GL11.glLineWidth( 3.0f );
		
		GL11.glBegin( GL11.GL_LINE_STRIP );
		GL11.glVertex2f( origin.getX(), origin.getY() );
		GL11.glVertex2f( origin.getX() + length, origin.getY() + length );
		GL11.glEnd();
		
		GL11.glBegin( GL11.GL_LINE_STRIP );
		GL11.glVertex2f( origin.getX(), origin.getY() + length );
		GL11.glVertex2f( origin.getX() + length, origin.getY() );
		GL11.glEnd();
	}

	private void renderO() {
		GL11.glColor3f( 0.0f, 1.0f, 0.0f );
		GL11.glLineWidth( 3.0f );
		
		GL11.glBegin( GL11.GL_LINE_STRIP );
		for( int i=0; i<length/2; i++ ) {
			GL11.glVertex2f( origin.getX() + length / 2 - i, origin.getY() + i);
		}
		for( int i=0; i<length/2; i++ ) {
			GL11.glVertex2f( origin.getX() + i, origin.getY() + length / 2 + i);
		}
		for( int i=0; i<length/2; i++ ) {
			GL11.glVertex2f( origin.getX() + length / 2 + i, origin.getY() + length - i);
		}
		for( int i=0; i<length/2; i++ ) {
			GL11.glVertex2f( origin.getX() + length - i, origin.getY() + length / 2 - i);
		}
		GL11.glEnd();		
	}
	
	private void renderSquare() {
		GL11.glColor3f( 1.0f, 1.0f, 1.0f );
		GL11.glBegin( GL11.GL_QUADS );
		GL11.glVertex2f( origin.getX(), origin.getY() );
		GL11.glVertex2f( origin.getX() + length, origin.getY() );
		GL11.glVertex2f( origin.getX() + length, origin.getY() + length );
		GL11.glVertex2f( origin.getX(), origin.getY() + length );
		GL11.glEnd();
	}
	
	// TODO: Add unit tests
	public boolean areaContains( Point point ) {
		return point.getX() > origin.getX() && point.getX() < origin.getX() + length &&
				point.getY() > origin.getY() && point.getY() < origin.getY() + length;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public void setLabel( String value ) {
		this.label = value;
	}
	
	public boolean isTaken() {
		return this.label.isEmpty() == false;
	}
	
	public boolean isAvailable() {
		return this.label.isEmpty();
	}
}
