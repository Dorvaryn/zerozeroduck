package fr.odai.zerozeroduck.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import fr.odai.zerozeroduck.controller.MainController.Keys;
import fr.odai.zerozeroduck.utils.Util;

public class Trap {
	public enum State {
		RELOADING, // Not available yet, waiting for RELOAD_TIME seconds
		HURTING, // Hurting enemies
		READY, // Ready to be enabled
		DISABLED // Disabled
	}
	
	public static final float SIZE = 0.5f; // half a unit
	public static final float RELOAD_TIME = 2;
	public static final float HURTING_TIME = 2;
	
	Vector2 position = new Vector2();
	State state = State.READY;
	Rectangle bounds = new Rectangle();
	int damage;
	Keys associatedKey;
	
	float range;

	private float stateTime;

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
		this.bounds.x = position.x;
		this.bounds.y = position.y;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
		stateTime = 0;
	}
	
	public Keys getAssociatedKey() {
		return associatedKey;
	}

	public void setAssociatedKey(Keys associatedKey) {
		this.associatedKey = associatedKey;
	}
	
	public boolean click(float x, float y){
		Rectangle rect = new Rectangle(bounds.x-(bounds.width/2), bounds.y-(bounds.height/2), bounds.width*2, bounds.height*2);
		return rect.contains(x,y);
	}

	public Trap(float range, Vector2 position, int damage) {
		super();
		this.range = range;
		this.position = position;
		this.damage = damage;
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
		this.bounds.x = position.x;
		this.bounds.y = position.y;
		this.associatedKey = Keys.UNDEFINED;
	}

	public void activate() {
		if(state == State.READY)
			setState(State.HURTING);
	}
	
	public void update(float delta) {
		stateTime += delta;
		
		if(state == State.HURTING && stateTime > HURTING_TIME)
			setState(State.RELOADING);
		
		if(state == State.RELOADING && stateTime > RELOAD_TIME)
			setState(State.READY);
	}
	
	public int damageWhenTrapped(Rectangle rect){
		Vector2 posA = new Vector2((float)position.x+(float)(SIZE/2.f), (float)position.y+(float)(SIZE/2.f));
		Vector2 posB = new Vector2((float)rect.x+(float)(rect.width/2.f), (float)rect.y+(float)(rect.height/2.f));
		if(Util.Distance(posA, posB)<=range){
			return -damage;
		}
		else return 0;
	}

	public Rectangle getBounds() {
		return bounds;
	}
}
