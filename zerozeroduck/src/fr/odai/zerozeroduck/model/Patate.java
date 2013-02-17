package fr.odai.zerozeroduck.model;


import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Patate extends Unit{

	public enum State {
		IDLE, WALKING, DYING
	}	
	
	State		state = State.WALKING;

	public Patate(Vector2 position, World world) {
		super(position, world);
		Patate.ANIM_PERIOD = 60f / (float)World.BPM / 2f;
		Patate.SPEED = 2f;
		Patate.JUMP_VELOCITY = 1f;
		Patate.SIZE = 0.5f;
		Patate.COEF_H = 1.5f;
		this.velocity = new Vector2(1, 0);
		this.bouciness = new Vector2(0, 0.2f);
		this.hp = 100;
		this.damage = 100;
		this.score = 100;
		this.bounds.height = SIZE*COEF_H;
		this.bounds.width = SIZE*COEF_W;
	}
	
	@Override
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
		else if(state != State.DYING) isVisible=true;
		
		if(state==State.DYING) {
			hp=0;
			isVisible=false;
		}
		
		if(animTime > ANIM_PERIOD) animTime -= ANIM_PERIOD;
		
		if(state == State.WALKING){
			position.add(velocity.tmp().mul(delta));
			position.y = world.getFloorHeight(position.x);
		}
		
		Rectangle positionnedBounds = this.getPositionnedBounds();		
		for(Trap trap : world.getTraps()){
			if(trap.getState()==Trap.State.HURTING && invincibilityTime <= 0){
				int hpModifier = trap.damageWhenTrapped(positionnedBounds);
				if(hpModifier<0){
					invincibilityTime=1.0f;
					hp+=hpModifier;
					if(hp<=0){
						setState(State.DYING);
						world.setScore(world.getScore()+score);
					}
				}
			}
		}
	}


	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
		stateTime = 0;
	}
}
