package fr.odai.zerozeroduck;

import com.badlogic.gdx.Game;

public class ZeroZeroDuck extends Game {

	GameOverScreen gameOverScreen;
	GameScreen gameScreen;
	TitleScreen titlescreen;
	
	@Override
	public void create() {
		gameOverScreen = new GameOverScreen(this);
		gameScreen = new GameScreen(this);
		titlescreen = new TitleScreen(this);
		setScreen(titlescreen);
	}
}
