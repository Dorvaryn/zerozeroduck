package fr.odai.zerozeroduck.model;

import com.badlogic.gdx.math.Vector2;

public class Trap {
	public enum State {
		RELOADING, ENABLED, READY, DISABLED
	}
	
	Vector2 position = new Vector2();
	State state;
	int damage;
	
	float range;

	public float getRange() {
		return range;
	}

	public void setRange(float range) {
		this.range = range;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Trap(float range, Vector2 position, int damage) {
		super();
		this.range = range;
		this.position = position;
		this.damage = damage;
	}
	
	
	
}
