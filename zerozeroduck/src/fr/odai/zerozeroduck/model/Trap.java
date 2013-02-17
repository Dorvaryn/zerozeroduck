package fr.odai.zerozeroduck.model;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
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
	
	protected TextureAtlas atlas;
	protected TextureRegion texture;
	protected TextureRegion keyTexture;
	
	public static float SIZE = 0.5f; // half a unit
	public static float RELOAD_TIME = 2;
	public static float HURTING_TIME = 0.5f;
	
	Vector2 position;
	State state;
	Rectangle bounds;
	int damage;
	Keys associatedKey;
	int level;

	protected float stateTime;
	
	public float getStateTime(){
		return stateTime;
	}
	
	public TextureRegion getTexture() {
		return texture;
	}

	public void setTexture(TextureRegion texture) {
		this.texture = texture;
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
		if(associatedKey==Keys.TRAP_F){
			keyTexture=atlas.findRegion("LettreF");
		}
		else if(associatedKey==Keys.TRAP_K){
			keyTexture=atlas.findRegion("LettreK");
		}
		else if(associatedKey==Keys.TRAP_H){
			keyTexture=atlas.findRegion("LettreH");
		}
		else if(associatedKey==Keys.TRAP_S){
			keyTexture=atlas.findRegion("LettreS");
		}
		this.associatedKey = associatedKey;
	}
	
	public boolean click(float x, float y){
		Rectangle rect = new Rectangle(getBounds().x-(getBounds().width/2), getBounds().y-(getBounds().height/2), getBounds().width*2, getBounds().height*2);
		return rect.contains(x,y);
	}

	public Trap(Vector2 position) {
		super();
		state = State.READY;
		bounds = new Rectangle();
		this.position = position;
		bounds.x=position.x;
		bounds.y=position.y;
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
		return 0;
	}

	public Rectangle getBounds() {
		return bounds;
	}
	
	
	
	public void draw(SpriteBatch sb, float ppuX, float ppuY, ShapeRenderer shr, OrthographicCamera cam){
		sb.draw(getTexture(), getPosition().x * ppuX,
				getPosition().y * ppuY, bounds.width * ppuX, bounds.height * ppuY);
		
		sb.draw(getTexture(), getPosition().x * ppuX, (float)(getPosition().y-0.7) * ppuY, 0.5f * ppuX, 0.5f * ppuY);
		sb.end();
		
		if(state==Trap.State.RELOADING){
			shr.setProjectionMatrix(cam.combined);
			shr.begin(ShapeType.FilledRectangle);
			shr.setColor(Color.LIGHT_GRAY);
			float rectWidth = (float)getBounds().width*(float)stateTime/(float)RELOAD_TIME;
			shr.filledRect(getPosition().x, (getPosition().y - 0.3f), rectWidth ,0.065f);
			shr.end();
			shr.begin(ShapeType.Rectangle);
			shr.setColor(Color.LIGHT_GRAY);
			shr.rect(getPosition().x ,(getPosition().y - 0.3f), getBounds().getWidth() ,0.065f);
			shr.end();
		}
		sb.begin();
		
	}
}
