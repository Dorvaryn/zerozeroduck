package fr.odai.zerozeroduck.sound;

import com.badlogic.gdx.Gdx;

import fr.odai.zerozeroduck.model.World;

abstract public class ConditionalSound {
	protected float stateTime;
	
	abstract public boolean shouldBeStarted(World world, float delta);
	
	public boolean shouldBeStopped(World world) {
		return false;
	}
	
	public void update(float delta, World world) {
		stateTime += delta;
	}

	abstract public void start();
	abstract public void stop();
	abstract public void dispose();
}
