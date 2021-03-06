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

public class Carrot extends Unit {

	public enum State {
		IDLE, WALKING, DYING, DISAPEARING, DISAPEARD, APPEARING
	}

	private static final float DISAPEAR_FRAME_DURATION = 60f / World.BPM / 8;
	private static final float ALEA_DURATION = 0.5f;
	private static final float DISAPEARED_DURATION = 2;
	private static final float DISAPEAR_PERIOD = DISAPEAR_FRAME_DURATION*4;
	private boolean canDisapear = true;

	State state = State.WALKING;
	static Animation disapear = null;
	static Animation 	walkRight = null;
	static TextureRegion textureFrame;
	static TextureRegion textureBase;
	float aleaTime = 50;

	public Carrot(Vector2 position, int level, World world, TextureAtlas atlas) {
		super(position, world, atlas);
		Carrot.ANIM_PERIOD = 60f / (float) World.BPM / 1.5f;
		Carrot.SIZE = 0.5f;
		Carrot.COEF_H = 1.5f;
		this.velocity = new Vector2(1.2f, 0);
		this.bouciness = new Vector2(0, 0.12f);
		this.level = level;
		this.hp = 80;
		this.damage = 150;
		this.score = 150;
		this.bounds.height = SIZE * COEF_H;
		this.bounds.width = SIZE * COEF_W;
	}

	@Override
	protected void loadTextures(TextureAtlas atlas) {

		Carrot.RUNNING_FRAME_DURATION = 60f / World.BPM / 6;

		if(walkRight == null){
			TextureRegion[] walkRightFrames = new TextureRegion[3];
			for (int i = 0; i <= 2; i++) {
				walkRightFrames[i] = atlas.findRegion("Karot" + (i + 1));
			}
			textureBase = walkRightFrames[0];
			walkRight = new Animation(Carrot.RUNNING_FRAME_DURATION, walkRightFrames);
		}
		if(disapear == null){
			TextureRegion[] disapearFrames = new TextureRegion[4];
			for (int i = 0; i <= 3; i++) {
				disapearFrames[i] = atlas.findRegion("Karot-Disp" + (i + 1));
			}
			disapear = new Animation(Carrot.DISAPEAR_FRAME_DURATION, disapearFrames);
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

		if (canDisapear && state == State.WALKING && aleaTime > ALEA_DURATION) {
			aleaTime = 0;
			if (Math.random() * 6 > 5) {
				setState(State.DISAPEARING);
				disapear.setPlayMode(Animation.NORMAL);
				canDisapear = false;
			}
		}

		if (state == State.DISAPEARING && stateTime > DISAPEAR_PERIOD) {
			setState(State.DISAPEARD);
		}

		if (state == State.DISAPEARD && stateTime > DISAPEARED_DURATION) {
			setState(State.APPEARING);
			disapear.setPlayMode(Animation.REVERSED);
		}
		
		if (state == State.APPEARING && stateTime > DISAPEAR_PERIOD) {
			setState(State.WALKING);
		}

		if (animTime > ANIM_PERIOD)
			animTime -= ANIM_PERIOD;

		if (state == State.WALKING || state == State.DISAPEARD || state == State.DISAPEARING || state == State.APPEARING) {
			position.add(velocity.tmp().mul(delta));
			position.y = world.getFloorHeight(position.x, level);
			bounds.y=position.y;
		}

		if (state != State.DISAPEARD) {
			Rectangle positionnedBounds = this.getPositionnedBounds();
			for (Trap trap : world.getTraps()) {
				if (trap.getState() == Trap.State.HURTING
						&& invincibilityTime <= 0) {
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
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
		stateTime = 0;
	}

	@Override
	public void draw(SpriteBatch spriteBatch, Array<PooledEffect> effects,
			ShapeRenderer shr, float ppuX, float ppuY) {
		textureFrame = textureBase;
		if(!toBeRemoved){
			if (state.equals(State.WALKING)) {
				textureFrame = walkRight.getKeyFrame(this.getStateTime(), true);
			}
			if (state.equals(State.DISAPEARING) || state.equals(State.APPEARING)) {
				textureFrame = disapear.getKeyFrame(this.getStateTime(), true);
			}
			if (isVisible && state != State.DISAPEARD) {
				spriteBatch.draw(textureFrame, getPosition().x * ppuX,
						getPosition().y * ppuY, bounds.width * ppuX, bounds.height
								* ppuY);
			}
		}
	}

	@Override
	public void kill(){
		super.kill();
		setState(State.DYING);
	}

}
