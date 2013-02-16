package fr.odai.zerozeroduck.model;


import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Single;

public class Patate {

	public enum State {
		IDLE, WALKING, DYING
	}
	
	float invincibilityTime=0;
	
	static final float SPEED = 2f;	// unit per second
	static final float JUMP_VELOCITY = 1f;
	public static final float SIZE = 0.5f; // half a unit
	
	static final float ANIM_PERIOD = 60f / (float)World.BPM / 2f;
			
	float       stateTime = 0;
	float 		animTime = 0;
	Vector2 	position = new Vector2();
	Vector2     velocity = new Vector2(1, 0);
	Vector2     bouciness = new Vector2(0, 0.2f);
	Rectangle 	bounds = new Rectangle();
	State		state = State.WALKING;
	boolean		facingLeft = true;
	boolean 	isVisible = true;
	World world;
	
	int hp=100;

	public Patate(Vector2 position, World world) {
		this.position = position;
		this.bounds.height = SIZE*1.5f;
		this.bounds.width = SIZE;
		this.world = world;
	}
	
	public boolean getIsVisible(){
		return isVisible;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public Vector2 getPosition() {
		return position.cpy().add(bouciness.tmp().mul(Math.abs((float) Math.sin((animTime / ANIM_PERIOD * Math.PI)))));
	}
	
	public State getState() {
		return state;
	}

	public float getStateTime() {
		return stateTime;
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
	
	public Rectangle getPositionnedBounds(){
		return new Rectangle(position.x, position.y, this.bounds.width, this.bounds.height);
	}
	
	public void update(float delta) {	
		stateTime += delta;
		invincibilityTime -= delta;
		
		animTime += delta;
		if(((animTime < 0.25f*(float)ANIM_PERIOD) || 
				(animTime > 0.5f*(float)ANIM_PERIOD 
				&& animTime <= 0.75f*(float)ANIM_PERIOD) )
				&& invincibilityTime>0 ){
			isVisible=false;
		}
		else isVisible=true;
		
		if(animTime > ANIM_PERIOD) animTime -= ANIM_PERIOD;
		
		if(state == State.WALKING){
			position.add(velocity.tmp().mul(delta));
		}
		
		Rectangle positionnedBounds = this.getPositionnedBounds();		
		for(Trap trap : world.getTraps()){
			if(trap.getState()==Trap.State.HURTING && invincibilityTime <= 0){
				int hpModifier = trap.damageWhenTrapped(positionnedBounds);
				if(hpModifier<0){
					invincibilityTime=1.0f;
					hp+=hpModifier;
				}
				if(hp<=0){
					setState(State.DYING);
				}
			}
		}
	}
}
