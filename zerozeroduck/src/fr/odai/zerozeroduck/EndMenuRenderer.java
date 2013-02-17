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

public class EndMenuRenderer {
	private OrthographicCamera cam;
	private Rectangle button = new Rectangle(4.5f, 3, 1, 1);
	
	private static final float CAMERA_WIDTH = 10f;
	private static final float CAMERA_HEIGHT = 7f;

	private SpriteBatch spriteBatch;

	/* Nos textures */
	private TextureRegion boutonReloadTexture;

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
		drawBouton();
		drawText();
		spriteBatch.end();
	}

	private void loadTextures() {
		TextureAtlas atlas = new TextureAtlas(
				Gdx.files.internal("images/textures.pack"));
		boutonReloadTexture = atlas.findRegion("reload");
	}

	private void drawBouton() {
		spriteBatch.draw(boutonReloadTexture, button.x * ppuX, button.y * ppuY, button.width * ppuX, button.height * ppuY);
	}
	
	public boolean click(int x, int y){
		return button.contains(x/ppuX, y/ppuY);
	}

	private void drawText() {
		LabelStyle text = new LabelStyle(new BitmapFont(), Color.WHITE);
		Label label = new Label("Game Over", text);
		label.setPosition(4.5f * ppuX, 4f * ppuY);
		label.setAlignment(Align.center);
		label.draw(spriteBatch, 1);
	}
	
	
}
