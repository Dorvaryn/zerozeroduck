package fr.odai.zerozeroduck.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;

import fr.odai.zerozeroduck.model.World;

public class OGMSound extends ConditionalSound {
	protected final static float TIME_BETWEEN = 20.f;
	protected final static int HOW_MUCH_UNITS = 6;
	
	protected boolean alreadyPlayed = false;
	protected float stateTime = 0.f;
	
	private Music sound = Gdx.audio.newMusic(Gdx.files.internal("sfx/ogm01.ogg"));
	
	public boolean shouldBeStarted(World world, float delta) {
		return (!alreadyPlayed || stateTime > TIME_BETWEEN) && world.getPatates().size >= HOW_MUCH_UNITS; 
	}
	
	public boolean shouldBeStopped(World world) {
		return false;
	}
	
	public void update(float delta, World world) {
		stateTime += delta;
	}

	public void start() {
		if(!sound.isPlaying()) {
			alreadyPlayed = true;
			stateTime = 0;
			sound.setLooping(false);
			sound.play();
		}
	}
	
	public void stop() {
		if(sound.isPlaying()) {
			sound.stop();
		}
	}
	
	public void dispose() {
		sound.dispose();
	}
}
