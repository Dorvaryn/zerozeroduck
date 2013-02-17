package fr.odai.zerozeroduck.model;


import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Unit {

	
	float invincibilityTime=0;
	
	static float SPEED;	// unit per second
	static float JUMP_VELOCITY;
	public static float SIZE = 1; // half a unit
	static float COEF_H = 1;
	static float COEF_W = 1;
	
	static float ANIM_PERIOD;
			
	float       stateTime = 0;
	float 		animTime = 0;
	Vector2 	position;
	Vector2     velocity;
	Vector2     bouciness;
	Rectangle 	bounds = new Rectangle();
	boolean		facingLeft = true;
	boolean 	isVisible = true;	
	int 		hp;
	int 		damage;
	int 		score;
	
	World world;

	public Unit(Vector2 position, World world) {
		this.position = position;
		this.world = world;
	}
	
	public int getScore(){
		return score;
	}
	
	public Vector2 getPosition() {
		return position.cpy().add(bouciness.tmp().mul(Math.abs((float) Math.sin((animTime / ANIM_PERIOD * Math.PI)))));
	}
	
	public boolean getIsVisible(){
		return isVisible;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public float getStateTime() {
		return stateTime;
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
	
	public int damageWhenFinish(Rectangle rect){
		if(position.x>rect.x){
			return -damage;
		}
		else return 0;
	}
	
	abstract public void update(float delta);
}
