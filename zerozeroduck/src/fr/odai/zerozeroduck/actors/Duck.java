package fr.odai.zerozeroduck.actors;


import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Duck {

	public enum State {
		IDLE, WALKING, JUMPING, DYING
	}

	static final float SPEED = 2f;	// unit per second
	static final float JUMP_VELOCITY = 1f;
	static final float SIZE = 0.5f; // half a unit

	Vector2 	position = new Vector2();
	Rectangle 	bounds = new Rectangle();
	State		state = State.IDLE;
	boolean		facingLeft = true;

	public Duck(Vector2 position) {
		this.position = position;
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public boolean isFacingLeft() {
		return facingLeft;
	}

	public void setFacingLeft(boolean facingLeft) {
		this.facingLeft = facingLeft;
	}
	
}
