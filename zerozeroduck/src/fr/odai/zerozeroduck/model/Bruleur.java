package fr.odai.zerozeroduck.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import fr.odai.zerozeroduck.model.Trap.State;
import fr.odai.zerozeroduck.utils.Util;

public class Bruleur extends Trap{
	
	/* Nos particules */
	ParticleEffectPool fireEffectPool;
	ParticleEffect fireEffect;
	Array<PooledEffect> effects;
	
	protected Sound flameSound = Gdx.audio.newSound(Gdx.files.internal("sfx/barillementproprelong01.ogg"));
	
	public Bruleur(Vector2 position){
		super(position);
		this.RELOAD_TIME = 2;
		this.HURTING_TIME = 1.5f;
		damage = 50;
		this.bounds.height = 1.25f*219.f/500.f;
		this.bounds.width = 1.25f*500.f/500.f;
		atlas = new TextureAtlas(Gdx.files.internal("images/textures.pack"));
		texture = atlas.findRegion("bruleur-off");
		
		fireEffect = new ParticleEffect();
		fireEffect.load(Gdx.files.internal("particle/fire.p"),
				Gdx.files.internal("particle"));
		fireEffectPool = new ParticleEffectPool(fireEffect, 1, 2);
		
		effects = new Array<PooledEffect>();
		
	}
	
	@Override
	public void activate() {
		if(state == State.READY){
			setState(State.HURTING);
			
			texture=atlas.findRegion("bruleur-on");
			
			// Create effect:
			PooledEffect effect = fireEffectPool.obtain();
			effect.setDuration(1500);
			effects.add(effect);
			
		    float pScale = 4.0f-1920.f/(float)Util.screenWidth;
		    
		    for(PooledEffect ef:effects){
			    float scaling = ef.getEmitters().get(0).getScale().getHighMax();
			    ef.getEmitters().get(0).getScale().setHigh(scaling * pScale);
	
			    scaling = ef.getEmitters().get(0).getScale().getLowMax();
			    ef.getEmitters().get(0).getScale().setLow(scaling * pScale);
	
			    scaling = ef.getEmitters().get(0).getVelocity().getHighMax();
			    ef.getEmitters().get(0).getVelocity().setHigh(scaling * pScale);
	
			    scaling = ef.getEmitters().get(0).getVelocity().getLowMax();
			    ef.getEmitters().get(0).getVelocity().setLow(scaling * pScale);
		    }
		}
	}
	
	@Override
	public void update(float delta) {
		if(state!=State.HURTING){
			texture=atlas.findRegion("bruleur-off");
		}
		super.update(delta);
	}
	
	@Override
	public int damageWhenTrapped(Rectangle rect){
		if(((rect.x + rect.width) > bounds.x) && ((rect.x) < bounds.x+bounds.width)){
			flameSound.play();
			return -damage;
		}
		else return 0;
	}
	
	@Override
	public void draw(SpriteBatch sb, float ppuX, float ppuY, ShapeRenderer shr, OrthographicCamera cam){
		super.draw(sb, ppuX, ppuY, shr, cam);
		if(state==State.HURTING){
			for(PooledEffect effect:effects){
				effect.setPosition((bounds.x+bounds.width/2)*ppuX, (bounds.y)*ppuY);
				if(!effect.isComplete()){
					effect.draw(sb, Gdx.graphics.getDeltaTime());
				}
			}
		}
	}
	
	
}
