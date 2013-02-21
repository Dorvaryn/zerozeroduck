package fr.odai.zerozeroduck.model;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public class Patate extends Unit {

	public enum State {
		IDLE, WALKING, DYING
	}

	State state = State.WALKING;
	static Animation 	walkRight = null;
	static TextureRegion textureFrame;
	static TextureRegion textureBase;

	public Patate(Vector2 position, int level, World world, TextureAtlas atlas) {
		super(position, world, atlas);
		Patate.ANIM_PERIOD = 60f / (float) World.BPM / 2f;
		Patate.SIZE = 0.5f;
		Patate.COEF_H = 1.5f;
		this.velocity = new Vector2(1f, 0);
		this.bouciness = new Vector2(0, 0.2f);
		this.level = level;
		this.hp = 100;
		this.damage = 100;
		this.score = 100;
		this.bounds.height = SIZE * COEF_H;
		this.bounds.width = SIZE * COEF_W;
	}
	
	@Override
	protected void loadTextures(TextureAtlas atlas) {		
		Patate.RUNNING_FRAME_DURATION = 60f / World.BPM / 8;
		if(walkRight == null){
			TextureRegion[] walkRightFrames = new TextureRegion[4];
			for (int i = 0; i <= 3; i++) {
				walkRightFrames[i] = atlas.findRegion("Patate" + (i + 1));
			}
			textureBase = walkRightFrames[0];
			walkRight = new Animation(Patate.RUNNING_FRAME_DURATION, walkRightFrames);
		}
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		if (((animTime < 0.25f * (float) ANIM_PERIOD) || (animTime > 0.5f * (float) ANIM_PERIOD && animTime <= 0.75f * (float) ANIM_PERIOD))
				&& invincibilityTime > 0) {
			isVisible = false;
		} else if (state != State.DYING)
			isVisible = true;

		if (state == State.DYING) {
			hp = 0;
			isVisible = false;
		}

		if (animTime > ANIM_PERIOD)
			animTime -= ANIM_PERIOD;

		if (state == State.WALKING) {
			position.add(velocity.tmp().mul(delta));
			position.y = world.getFloorHeight(position.x, level);
			bounds.y=position.y;
		}

		Rectangle positionnedBounds = this.getPositionnedBounds();
		for (Trap trap : world.getTraps()) {
			if (trap.getState() == Trap.State.HURTING && invincibilityTime <= 0) {
				int hpModifier = trap.damageWhenTrapped(positionnedBounds);
				if (hpModifier < 0) {
					invincibilityTime = 1.0f;
					hp += hpModifier;
					if (hp <= 0) {
						if(state!=State.DYING){
							world.setScore(world.getScore() + score);
							kill();
						}
					}
				}
			}
		}
	}

	@Override
	public void draw(SpriteBatch spriteBatch, Array<PooledEffect> effects, ShapeRenderer shr, float ppuX, float ppuY) {
		textureFrame = textureBase;
		if(!toBeRemoved){
			if (state.equals(State.WALKING)) {
				textureFrame = walkRight.getKeyFrame(this.getStateTime(), true);
			}
			if (isVisible) {
				spriteBatch.draw(textureFrame, getPosition().x * ppuX, getPosition().y * ppuY, bounds.width * ppuX,	bounds.height * ppuY);
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

	@Override
	public void kill() {
		super.kill();
		setState(State.DYING);
	}
}
