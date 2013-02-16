package fr.odai.zerozeroduck.model;


import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Duck {

	public enum State {
		IDLE, WALKING, JUMPING, DYING, DEAD
	}

	public static final float DYING_TIME = 2;
	static final float SPEED = 2f;	// unit per second
	static final float JUMP_VELOCITY = 1f;
	public static final float SIZE = 0.5f; // half a unit

	Vector2 	position = new Vector2();
	Rectangle 	bounds = new Rectangle();
	State		state = State.IDLE;
	int 		life = 800;
	float       stateTime = 0;
	boolean		facingLeft = true;
	World 		world;

	public Duck(Vector2 position, World world) {
		this.position = position;
		this.bounds.height = SIZE;
		this.bounds.width = SIZE;
		this.world = world;
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
		stateTime = 0;
	}

	public boolean isFacingLeft() {
		return facingLeft;
	}

	public void setFacingLeft(boolean facingLeft) {
		this.facingLeft = facingLeft;
	}
	
	public Rectangle getPositionnedBounds(){
		return new Rectangle(position.x, position.y, this.bounds.width, this.bounds.height);
	}
	
	public void update(float delta){
		stateTime+=delta;
		Array<Patate> patates = world.getPatates();
		for(Patate patate:patates){
			int damage = patate.damageWhenFinish(this.getPositionnedBounds());
			if(damage<0){
				life+=damage;
				patate.setState(Patate.State.DYING);
				System.out.println(life);
			}
		}
		if(life<=0 && state==State.IDLE){
			setState(State.DYING);
			System.out.println("mourant");
		}
		if(state==State.DYING && stateTime > DYING_TIME){
			setState(State.DEAD);
			System.out.println("mort");
		}
	}
}
