package fr.odai.zerozeroduck.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import fr.odai.zerozeroduck.utils.Util;

public class SaltBarrel extends Trap{
	
	public static final float BOOM=1.f;
	
	static protected TextureRegion textureNormal = null;
	static protected TextureRegion textureBoom = null;
	static protected TextureRegion texturePshh = null;
	
	float range=1.5f;
	
	protected Sound psschSound = Gdx.audio.newSound(Gdx.files.internal("sfx/meche01.ogg"));
	protected Sound boomSound = Gdx.audio.newSound(Gdx.files.internal("sfx/explosion01.ogg"));
	
	boolean pshht=false;
	
	float timeSinceActivated=0;
	
	public SaltBarrel(Vector2 position, int level, TextureAtlas atlas){
		super(position, atlas);
		SaltBarrel.RELOAD_TIME = 1;
		SaltBarrel.HURTING_TIME = 0.5f;
		this.level = level;
		damage = 50;
		this.bounds.height = 1.f * (404f / 510f);
		this.bounds.width = 1.f;
		if(textureBoom == null){
			textureNormal = atlas.findRegion("salt-normal");
			texturePshh = atlas.findRegion("salt-pssch");
			textureBoom = atlas.findRegion("salt-boom");
		}
	}
	
	public float getRange() {
		return range;
	}

	public void setRange(float range) {
		this.range = range;
	}
	
	@Override
	public void activate() {
		fade=1.0f;
		if(state == State.READY){
			pshht = true;
			psschSound.play();
			timeSinceActivated = 0.f;
		}
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
		if(pshht){
			timeSinceActivated+=delta;
			texture = texturePshh;
		}
		else if(state==State.READY || state==State.DISABLED || state==State.RELOADING){
			texture = textureNormal;
		}
		if(timeSinceActivated>BOOM){
			pshht=false;
			psschSound.stop();
			boomSound.play();
			texture = textureBoom;
			setState(State.HURTING);
			timeSinceActivated=0.f;
		}
	}
	
	@Override
	public int damageWhenTrapped(Rectangle rect){
		Vector2 posA = new Vector2((float)position.x+(float)(bounds.width/2.f), (float)position.y+(float)(bounds.y/2.f));
		Vector2 posB = new Vector2((float)rect.x+(float)(rect.width/2.f), (float)rect.y+(float)(rect.height/2.f));
		if(Util.Distance(posA, posB)<=range){
			return -damage;
		}
		else return 0;
	}
	
	@Override
	public void draw(SpriteBatch sb, float ppuX, float ppuY, ShapeRenderer shr, OrthographicCamera cam){
		super.draw(sb, ppuX, ppuY, shr, cam);
	}
	
}
