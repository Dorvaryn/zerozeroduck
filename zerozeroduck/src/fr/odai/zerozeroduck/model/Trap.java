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
	
	TextureRegion texture;
	TextureRegion keyTexture;
	static TextureRegion textureF = null;
	static TextureRegion textureK = null;
	static TextureRegion textureH = null;
	static TextureRegion textureS = null;
	
	public static float SIZE = 0.5f; // half a unit
	public static float RELOAD_TIME = 2;
	public static float HURTING_TIME = 0.5f;
	
	Vector2 position;
	State state;
	Rectangle bounds;
	int damage;
	Keys associatedKey;
	int level;
	float fade;

	protected float stateTime;
	
	public float getStateTime(){
		return stateTime;
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
			keyTexture=textureF;
		}
		else if(associatedKey==Keys.TRAP_K){
			keyTexture=textureK;
		}
		else if(associatedKey==Keys.TRAP_H){
			keyTexture=textureH;
		}
		else if(associatedKey==Keys.TRAP_S){
			keyTexture=textureS;
		}
		this.associatedKey = associatedKey;
	}
	
	public boolean click(float x, float y){
		Rectangle rect = new Rectangle(getBounds().x-(getBounds().width/2), getBounds().y-(getBounds().height/2), getBounds().width*2, getBounds().height*2);
		return rect.contains(x,y);
	}

	public Trap(Vector2 position, TextureAtlas atlas) {
		super();
		fade=0.2f;
		state = State.READY;
		bounds = new Rectangle();
		this.position = position;
		bounds.x=position.x;
		bounds.y=position.y;
		this.associatedKey = Keys.UNDEFINED;
		if(textureF == null){
			textureF = atlas.findRegion("LettreF");
			textureK = atlas.findRegion("LettreK");
			textureH = atlas.findRegion("LettreH");
			textureS = atlas.findRegion("LettreS");
		}
	}

	public void activate() {
		fade=1.f;
		System.out.println(fade);
		if(state == State.READY){
			setState(State.HURTING);
		}
	}
	
	public void update(float delta) {
		stateTime += delta;
		
		if(fade>0.2f){
			fade-=delta;
		}
		if(fade<=0.2f){
			fade=0.2f;
		}
		
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
	
	public void dispose(){
		texture = null;
	}
	
	
	public void draw(SpriteBatch sb, float ppuX, float ppuY, ShapeRenderer shr, OrthographicCamera cam){
		sb.draw(texture, getPosition().x * ppuX,
				getPosition().y * ppuY, bounds.width * ppuX, bounds.height * ppuY);
		sb.setColor(1, 1, 1, fade);
		//sb.draw(keyTexture, (getPosition().x+0.3f) * ppuX, (float)(getPosition().y-0.9) * ppuY, 0.5f * ppuX, 0.5f * ppuY );
		sb.setColor(1, 1, 1, 1);
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
