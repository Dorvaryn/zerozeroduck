package fr.odai.zerozeroduck;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool.PooledEffect;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.utils.Array;

import fr.odai.zerozeroduck.model.Patate;
import fr.odai.zerozeroduck.model.World;
import fr.odai.zerozeroduck.model.Patate.State;

public class EndMenuRenderer {
	private OrthographicCamera cam;
	private Rectangle button = new Rectangle(3.5f, 2, 3.5f, 1.5f);

	private static final float CAMERA_WIDTH = 10f;
	private static final float CAMERA_HEIGHT = 7f;

	private class BigPatate {
		public static final float SIZE = 3; 
		static final float COEF_H = 1.5f;

		protected static final float RUNNING_FRAME_DURATION = 60f / World.BPM / 8;

		float stateTime = 0;
		Vector2 position;
		Rectangle bounds = new Rectangle();
		TextureRegion textureFrame;
		Animation walkRight;

		public BigPatate(Vector2 position, TextureAtlas atlas) {
			this.position = position;
			this.bounds.height = SIZE*COEF_H;
			this.bounds.width = SIZE;
			TextureRegion[] walkRightFrames = new TextureRegion[4];
			for (int i = 0; i <= 3; i++) {
				walkRightFrames[i] = atlas.findRegion("Patate" + (i + 1));
			}
			walkRight = new Animation(BigPatate.RUNNING_FRAME_DURATION,
					walkRightFrames);
		}

		public void update(float delta) {
			stateTime += delta;
		}

		public void draw(SpriteBatch spriteBatch, float ppuX, float ppuY) {
			textureFrame = walkRight.getKeyFrame(stateTime, true);
			spriteBatch.draw(textureFrame, position.x * ppuX, position.y * ppuY, bounds.width * ppuX,	bounds.height * ppuY);
		}

	}
	
	private class BigCarrot {
		public static final float SIZE = 3; 
		static final float COEF_H = 1.5f;

		protected static final float RUNNING_FRAME_DURATION = 60f / World.BPM / 6;

		float stateTime = 0;
		Vector2 position;
		Rectangle bounds = new Rectangle();
		TextureRegion textureFrame;
		Animation walkRight;

		public BigCarrot(Vector2 position, TextureAtlas atlas) {
			this.position = position;
			this.bounds.height = SIZE*COEF_H;
			this.bounds.width = SIZE;
			TextureRegion[] walkRightFrames = new TextureRegion[3];
			for (int i = 0; i <= 2; i++) {
				walkRightFrames[i] = atlas.findRegion("Karot" + (i + 1));
			}
			walkRight = new Animation(BigPatate.RUNNING_FRAME_DURATION,
					walkRightFrames);
		}

		public void update(float delta) {
			stateTime += delta;
		}

		public void draw(SpriteBatch spriteBatch, float ppuX, float ppuY) {
			textureFrame = walkRight.getKeyFrame(stateTime, true);
			spriteBatch.draw(textureFrame, position.x * ppuX, position.y * ppuY, bounds.width * ppuX,	bounds.height * ppuY);
		}

	}
	
	public void update(float delta){
		patate.update(delta);
		carrot.update(delta);
	}
	
	private BigPatate patate = new BigPatate(new Vector2(0.2f, 0.2f),new TextureAtlas(Gdx.files.internal("images/textures.pack")));
	private BigCarrot carrot = new BigCarrot(new Vector2(6.5f, 0.2f),new TextureAtlas(Gdx.files.internal("images/textures.pack")));
	
	private SpriteBatch spriteBatch;

	/* Nos textures */
	private TextureRegion boutonReloadTexture;
	private TextureRegion backgroundTexture;

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

	public EndMenuRenderer() {
		this.cam = new OrthographicCamera(CAMERA_WIDTH, CAMERA_HEIGHT);
		this.cam.position.set(CAMERA_WIDTH / 2f, CAMERA_HEIGHT / 2f, 0);
		this.cam.update();
		spriteBatch = new SpriteBatch();
		loadTextures();
	}

	public void render() {
		spriteBatch.begin();
		drawBackground();
		drawBouton();
		drawUnits();
		spriteBatch.end();
	}

	private void loadTextures() {
		TextureAtlas atlas = new TextureAtlas(
				Gdx.files.internal("images/textures.pack"));
		boutonReloadTexture = atlas.findRegion("Retry");
		backgroundTexture = atlas.findRegion("Lose");
	}

	private void drawBouton() {
		spriteBatch.draw(boutonReloadTexture, button.x * ppuX, button.y * ppuY,
				button.width * ppuX, button.height * ppuY);
	}

	private void drawUnits() {
		patate.draw(spriteBatch, ppuX, ppuY);
		carrot.draw(spriteBatch, ppuX, ppuY);
	}

	public boolean click(int x, int y) {
		return button.contains(x / ppuX, 7 - (y / ppuY));
	}

	private void drawBackground() {
		spriteBatch.draw(backgroundTexture, 0.f, 0.f, 10.f * ppuX, 7.f * ppuY);
	}

}
