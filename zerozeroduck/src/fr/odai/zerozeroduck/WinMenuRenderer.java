package fr.odai.zerozeroduck;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

public class WinMenuRenderer {
	private OrthographicCamera cam;
	private Rectangle buttonReload = new Rectangle(3f, 3f, 4, 1.5f);
	private Rectangle buttonNext = new Rectangle(3.5f, 1f, 3, 1.5f);
	
	private static final float CAMERA_WIDTH = 10f;
	private static final float CAMERA_HEIGHT = 7f;

	private SpriteBatch spriteBatch;

	/* Nos textures */
	private TextureRegion boutonReloadTexture;
	private TextureRegion boutonNextTexture;
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

	public WinMenuRenderer() {
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
		TextureAtlas atlas = new TextureAtlas(
				Gdx.files.internal("images/textures.pack"));
		boutonReloadTexture = atlas.findRegion("PlayAgain");
		boutonNextTexture = atlas.findRegion("Next");
		backgroundTexture = atlas.findRegion("Win");
	}

	private void drawBouton() {
		spriteBatch.draw(boutonReloadTexture, buttonReload.x * ppuX, buttonReload.y * ppuY, buttonReload.width * ppuX, buttonReload.height * ppuY);
		spriteBatch.draw(boutonNextTexture, buttonNext.x * ppuX, buttonNext.y * ppuY, buttonNext.width * ppuX, buttonNext.height * ppuY);
	}
	
	private void drawBackground() {
		spriteBatch.draw(backgroundTexture, 0.f, 0.f, 10.f * ppuX, 7.f * ppuY);
	}
	
	public boolean clickReload(int x, int y){
		return buttonReload.contains(x/ppuX, 7-(y/ppuY));
	}
	public boolean clickNext(int x, int y){
		return buttonNext.contains(x/ppuX, 7-(y/ppuY));
	}
	
	
}
