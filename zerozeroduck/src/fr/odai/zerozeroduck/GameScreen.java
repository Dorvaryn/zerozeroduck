package fr.odai.zerozeroduck;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;

import fr.odai.zerozeroduck.controller.MainController;
import fr.odai.zerozeroduck.model.Trap;
import fr.odai.zerozeroduck.model.World;

public class GameScreen implements Screen, InputProcessor {
	
	private ZeroZeroDuck game;
	private World world;
	private WorldRenderer renderer;
	private MainController controller;

	public GameScreen(ZeroZeroDuck game) {
		super();
		this.game = game;
	}
	
	public void gameOver(){
		renderer.stopMusic();
		game.setScreen(game.gameOverScreen);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		controller.update(delta);
		renderer.render();
	}

	@Override
	public void show() {
		world = new World();
		renderer = new WorldRenderer(world, false);
		controller = new MainController(world, this);
		Gdx.input.setInputProcessor(this);
	}
	
	@Override
	public void resize(int width, int height) {
		renderer.setSize(width, height);
	}
	

	@Override
	public void hide() {
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		renderer.stopMusic();
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public boolean keyDown(int keycode) {
		if (keycode == Keys.SPACE)
			controller.patatePressed();
		if (keycode == Keys.ENTER)
			controller.killallPressed();
		if (keycode == Keys.S)
			controller.trapPressed(MainController.Keys.TRAP_S);
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if (keycode == Keys.SPACE)
			controller.patateReleased();
		if (keycode == Keys.ENTER)
			controller.killallReleased();
		if (keycode == Keys.S)
			controller.trapReleased(MainController.Keys.TRAP_S);
		return false;
	}
	
	@Override
	public boolean touchDown(int x, int y, int pointer, int button) {
		for(Trap trap : world.getTraps()){
			if(trap.click(renderer.convertScaleX(x),7-renderer.convertScaleY(y))){
				controller.trapPressed(trap.getAssociatedKey());
				return true;
			}
		}
		controller.patatePressed();
		return true;
	}

	@Override
	public boolean touchUp(int x, int y, int pointer, int button) {
		for(Trap trap : world.getTraps()){
			if(trap.click(renderer.convertScaleX(x),7-renderer.convertScaleY(y))){
				controller.trapReleased(trap.getAssociatedKey());
				return true;
			}
		}
		controller.patateReleased();
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
