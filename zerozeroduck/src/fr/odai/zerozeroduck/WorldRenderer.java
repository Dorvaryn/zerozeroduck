package fr.odai.zerozeroduck;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Array;

import fr.odai.zerozeroduck.model.Duck;
import fr.odai.zerozeroduck.model.Patate;
import fr.odai.zerozeroduck.model.Patate.State;
import fr.odai.zerozeroduck.model.Trap;
import fr.odai.zerozeroduck.model.World;

public class WorldRenderer {
	private World world;
	private OrthographicCamera cam;

	private static final float CAMERA_WIDTH = 10f;
	private static final float CAMERA_HEIGHT = 7f;

	boolean debug = false;

	private SpriteBatch spriteBatch;

	/* Nos textures */
	private TextureRegion backgroundTexture;
	private TextureRegion duckTexture;
	private TextureRegion trapTexture;

	private TextureRegion patateTexture;
	private TextureRegion patateFrame;

	/* Nos particules */
	ParticleEffectPool smokeEffectPool;
	ParticleEffect smokeEffect;
	Array<PooledEffect> effects;
	
	/* Musique */
	Music musicLoop;
	Music musicStart;

	private static final float PATATE_RUNNING_FRAME_DURATION = 60f / World.BPM / 8;
	private Animation walkRightPatate;

	/** for debug rendering **/
	ShapeRenderer debugRenderer = new ShapeRenderer();

	private float ppuX; // pixels per unit on the X axis
	private float ppuY; // pixels per unit on the Y axis

	private int width;
	private int height;

	public void setSize(int w, int h) {
		this.width = w;
		this.height = h;
		ppuX = (float) width / CAMERA_WIDTH;
		ppuY = (float) height / CAMERA_HEIGHT;
	}

	public WorldRenderer(World world, boolean debug) {
		this.world = world;
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
		this.cam.update();
		this.debug = debug;
		spriteBatch = new SpriteBatch();

		smokeEffect = new ParticleEffect();
		smokeEffect.load(Gdx.files.internal("particle/smoke.p"),
				Gdx.files.internal("particle"));
		smokeEffectPool = new ParticleEffectPool(smokeEffect, 1, 2);
		effects = new Array<PooledEffect>();

		//sounds
		//Sound sound = Gdx.audio.newSound(Gdx.files.internal("data/mysound.mp3"));
		//the ambiance music
		musicStart = Gdx.audio.newMusic(Gdx.files.internal("musics/duckIntro01.ogg"));
		musicStart.setLooping(false);
		musicLoop = Gdx.audio.newMusic(Gdx.files.internal("musics/duckBoucle01.ogg"));
		musicLoop.setLooping(true);
		
		musicStart.play();
		
		loadTextures();
	}

	public void render() {
		if(!musicStart.isPlaying() && !musicLoop.isPlaying()){
			musicLoop.play();
		}
		
		spriteBatch.begin();
		drawBackground();
		drawTrap();
		drawDuck();
		drawPatates();
		drawScore();

		// Update and draw effects:
		for (int i = 0; i < effects.size; i++) {
			PooledEffect effect = effects.get(i);
			if (effect.isComplete()) {
				effects.removeIndex(i);
				i--;
			} else
				effect.draw(spriteBatch, Gdx.graphics.getDeltaTime());
		}

		spriteBatch.end();

		// draw bounding boxes in debug mode
		if (debug)
			drawDebug();
	}
	
	public void stopMusic(){
		musicStart.stop();
		musicLoop.stop();
	}

	private void drawBackground() {
		spriteBatch.draw(backgroundTexture, 0.f, 0.f, 10.f * ppuX, 7.f * ppuY);
	}

	private void loadTextures() {
		TextureAtlas atlas = new TextureAtlas(
				Gdx.files.internal("images/textures.pack"));
		patateTexture = atlas.findRegion("Patate1");
		TextureRegion[] walkRightFrames = new TextureRegion[4];
		for (int i = 0; i <= 3; i++) {
			walkRightFrames[i] = atlas.findRegion("Patate" + (i + 1));
		}
		walkRightPatate = new Animation(PATATE_RUNNING_FRAME_DURATION,
				walkRightFrames);
		duckTexture = atlas.findRegion("block");
		trapTexture = atlas.findRegion("trap");
		backgroundTexture = atlas.findRegion("Stage0");
	}

	private void drawPatates() {
		Array<Patate> patates = world.getPatates();
		for (int i = 0; i < patates.size; i++) {
			Patate patate = patates.get(i);
			patateFrame = patateTexture;
			if (patate.getState().equals(State.WALKING)) {
				patateFrame = walkRightPatate.getKeyFrame(
						patate.getStateTime(), true);
			}
			if (patate.getIsVisible()) {
				spriteBatch.draw(patateFrame, patate.getPosition().x * ppuX,
						patate.getPosition().y * ppuY, patate.getBounds().width
								* ppuX, patate.getBounds().height * ppuY);
			}
			if (patate.getState() == State.DYING) {
				// Create effect:
				PooledEffect effect = smokeEffectPool.obtain();
				effect.setDuration(500);
				effect.setPosition(
						patate.getPosition().x * ppuX + patate.getBounds().x
								/ 2.f * ppuX, patate.getPosition().y * ppuY
								+ 0.05f * ppuY);
				effects.add(effect);
				patates.removeIndex(i);
				i--;
			}
		}
	}

	private void drawDuck() {
		Duck bob = world.getDuck();
		spriteBatch.draw(duckTexture, bob.getPosition().x * ppuX,
				bob.getPosition().y * ppuY, Duck.SIZE * ppuX, Duck.SIZE * ppuY);
	}

	private void drawTrap() {
		for (Trap trap : world.getTraps()) {
			spriteBatch.draw(trapTexture, trap.getPosition().x * ppuX,
					trap.getPosition().y * ppuY, Trap.SIZE * ppuX, Trap.SIZE
							* ppuY);
			
			/*if(trap.getState()==Trap.State.RELOADING){
				debugRenderer.setProjectionMatrix(cam.combined);
				debugRenderer.begin(ShapeType.FilledRectangle);
				debugRenderer.setColor(new Color(0.75f, 0.75f, 0.75f, 1));
				
				debugRenderer.filledRect(trap.getPosition().x, trap.getPosition().y-0.3f*ppuY, , size);
				debugRenderer.end();
				
			}*/
		
		}
	}

	private void drawScore() {
		LabelStyle text = new LabelStyle(new BitmapFont(), Color.WHITE);
		Label label = new Label("Score: " + world.getScore(), text);
		label.setPosition(9f * ppuX, 6.5f * ppuY);
		label.setAlignment(Align.center);
		label.draw(spriteBatch, 1);
	}

	private void drawDebug() {
		// render blocks
		debugRenderer.setProjectionMatrix(cam.combined);
		debugRenderer.begin(ShapeType.FilledRectangle);
		debugRenderer.setColor(new Color(1, 0, 0, 1));
		float size = 0.02f;
		for (float i = 0; i < 10; i += 0.05f) {
			float x1 = i;
			float y1 = world.getFloorHeight(i);
			debugRenderer.filledRect(x1 - size / 2, y1 - size / 2, size, size);
		}
		debugRenderer.end();
		debugRenderer.begin(ShapeType.Rectangle);
		for (Patate patate : world.getPatates()) {
			Rectangle rect = patate.getBounds();
			float x1 = patate.getPosition().x + rect.x;
			float y1 = patate.getPosition().y + rect.y;
			debugRenderer.setColor(new Color(1, 1, 0, 1));
			debugRenderer.rect(x1, y1, rect.width, rect.height);
		}
		for (Trap trap : world.getTraps()) {
			Rectangle bounds = trap.getBounds();
			Rectangle rect = new Rectangle(bounds.x - (bounds.width / 2),
					bounds.y - (bounds.height / 2), bounds.width * 2,
					bounds.height * 2);
			float x1 = rect.x;
			float y1 = rect.y;
			debugRenderer.setColor(new Color(1, 1, 0, 1));
			debugRenderer.rect(x1, y1, rect.width, rect.height);
		}
		// render Duck
		Duck duck = world.getDuck();
		Rectangle rect = duck.getBounds();
		float x1 = duck.getPosition().x + rect.x;
		float y1 = duck.getPosition().y + rect.y;
		debugRenderer.setColor(new Color(0, 1, 0, 1));
		debugRenderer.rect(x1, y1, rect.width, rect.height);
		debugRenderer.end();
	}

	public float convertScaleX(int x) {
		return x / ppuX;
	}

	public float convertScaleY(int y) {
		return y / ppuY;
	}
}
