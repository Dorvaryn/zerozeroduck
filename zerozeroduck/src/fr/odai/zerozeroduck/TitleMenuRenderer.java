package fr.odai.zerozeroduck;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class TitleMenuRenderer {
	private OrthographicCamera cam;
	private Rectangle button = new Rectangle(1, 1.5f, 3, 1.5f);

	private static final float CAMERA_WIDTH = 10f;
	private static final float CAMERA_HEIGHT = 7f;

	TextureAtlas atlas;
	private SpriteBatch spriteBatch;

	/* Nos textures */
	private TextureRegion boutonStartTexture;
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

	public TitleMenuRenderer() {
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
		spriteBatch.end();
	}

	private void loadTextures() {
		atlas = new TextureAtlas(
				Gdx.files.internal("images/textures.pack"));
		boutonStartTexture = atlas.findRegion("BouttonStart");
		backgroundTexture = atlas.findRegion("Menu");
	}

	private void drawBouton() {
		spriteBatch.draw(boutonStartTexture, button.x * ppuX, button.y * ppuY,
				button.width * ppuX, button.height * ppuY);
	}

	private void drawBackground() {
		spriteBatch.draw(backgroundTexture, 0.f, 0.f, 10.f * ppuX, 7.f * ppuY);
	}

	public boolean click(int x, int y) {
		  return button.contains(x/ppuX, 7-(y/ppuY));
	}
	
	public void dispose(){
		atlas.dispose();
		spriteBatch.dispose();
		cam = null;
		backgroundTexture = null;
		boutonStartTexture = null;
		button = null;
	}

}
