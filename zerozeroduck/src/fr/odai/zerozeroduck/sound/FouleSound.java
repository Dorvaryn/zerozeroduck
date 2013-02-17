package fr.odai.zerozeroduck.sound;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.Array;

import fr.odai.zerozeroduck.model.World;

public class FouleSound extends ConditionalSound {
	protected final static float TIME_BETWEEN = 15.f;
	protected final static int HOW_MUCH_UNITS = 6;
	protected final static Array<String> SOUND_PATHS = new Array<String>();
	static {
		SOUND_PATHS.add("sfx/foule01.ogg");
		SOUND_PATHS.add("sfx/foule02.ogg");
		SOUND_PATHS.add("sfx/foule03.ogg");
	}
	
	protected boolean alreadyPlayed = false;
	protected float stateTime = 0.f;
	
	private Music sound = null;
	
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
		if(sound == null || !sound.isPlaying()) {
			if(sound != null) sound.dispose();
			sound = Gdx.audio.newMusic(Gdx.files.internal(SOUND_PATHS.get((int) Math.floor(Math.random() * SOUND_PATHS.size))));
			alreadyPlayed = true;
			stateTime = 0f;
			sound.setLooping(false);
			sound.play();
		}
	}
	
	public void stop() {
		if(sound != null && sound.isPlaying()) {
			sound.stop();
		}
	}
	
	public void dispose() {
		if(sound != null) sound.dispose();
	}
}
