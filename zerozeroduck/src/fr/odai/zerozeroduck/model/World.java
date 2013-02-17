package fr.odai.zerozeroduck.model;

import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import fr.odai.zerozeroduck.controller.MainController;
import fr.odai.zerozeroduck.model.Trap.State;


public class World {
	/** Bump per minutes */
	public static final int BPM = 85;
	
	public enum State {
		WAVE_IN_PROGRESS, // Not available yet, waiting for RELOAD_TIME seconds
		WAITING
	}
	
	TextureAtlas atlas;
		
	/** Traps **/
	Array<Trap> traps = new Array<Trap>();
	/** Startpoints for the units **/
	Array<Vector2> startpoints = new Array<Vector2>();
	/** Units in this world **/
	Array<Unit> units = new Array<Unit>();
	
	Array<Patate> wavePatates = new Array<Patate>();
	
	private Array<Float> floor_pos = new Array<Float>();

	/** Our player controlled hero **/
	Duck duck;
	
	
	State state = State.WAITING;
	
	static final int moyPatatesByWave = 3;
	static final int patateByWaveDelta = 1; 
	static final float waveWaitDelta = 2;
	static final float waveWaitDuration = 3;
	static final float inWaveWaitDuration = 0.5f;
	static final float inWaveWaitDelta = 0.2f;
	
	int poolPatates = 20;
	float waveWaitEnd = 3;
	float inWaveWaitEnd = 0.5f;
	float stateTime = 0;
	float inWaveTime = 5;
	
	int score = 0;

	// Getters -----------
	public Duck getDuck() {
		return duck;
	}
	public Array<Trap> getTraps() {
		return traps;
	}
	public Array<Unit> getUnits() {
		return units;
	}
	public int getScore() {
		return score;
	}	
	public void setScore(int score){
		this.score=score;
	}
	public int getTotalPool(){
		return poolPatates;
	}
	// --------------------

	public TextureAtlas getAtlas() {
		return atlas;
	}
	public Array<Float> getFloorPos() {
		return floor_pos;
	}
	public void setFloorPos(Array<Float> floor_pos) {
		this.floor_pos = floor_pos;
	}
	public World() {
		createDemoWorld();
	}
	
	public void setState(State state) {
		this.state = state;
		stateTime = 0;
	}

	private void createDemoWorld() {
		atlas = new TextureAtlas(Gdx.files.internal("images/textures.pack"));

		Pixmap floor_pixmap = new Pixmap(Gdx.files.internal("images/Stage0-floor.png"));
		int width = floor_pixmap.getWidth();
		setFloorPos(new Array<Float>(width));
		for(int i = 0; i < width; i++) {
			int j = 0;
			Color color = new Color();
			for(; j < floor_pixmap.getHeight(); j++) {
				Color.rgba8888ToColor(color, floor_pixmap.getPixel(i, j));
				if(color.r > 0.8f && color.g > 0.8f && color.b > 0.8f) {
					break;
				}
			}
			getFloorPos().add(7.f - j / (float) floor_pixmap.getHeight() * 7.f);
		}
		duck = new Duck(new Vector2(8.5f, getFloorHeight(8.5f) - 0.4f), this);

		Bruleur sb = new Bruleur(new Vector2(2.f, getFloorHeight(2)));
		sb.setAssociatedKey(MainController.Keys.TRAP_S);
		traps.add(sb);
		
		startpoints.add(new Vector2(-2, getFloorHeight(0)));
	}
	
	public float getFloorHeight(float x) {
		int index = Math.round(x / 10 * getFloorPos().size);
		if(index >= getFloorPos().size)
			return getFloorPos().get(getFloorPos().size - 1);
		else if(index < 0)
			return getFloorPos().get(0);
		return getFloorPos().get(index);
	}
	
	public void update(float delta) {
		stateTime += delta;
		inWaveTime += delta;

		for(Unit unit: units) {
			if(unit.position.x > 10 || unit.position.x < -2) {
				units.removeValue(unit, true);
			}
		}
		
		if(state == State.WAITING && stateTime > waveWaitEnd){
			if(poolPatates > 0){
				setState(State.WAVE_IN_PROGRESS);
				int nbPatates = moyPatatesByWave;
				nbPatates += Math.random()*2*patateByWaveDelta - patateByWaveDelta;
				nbPatates = Math.min(nbPatates, poolPatates);
				poolPatates -= nbPatates;
				for (int i = 0; i < nbPatates; i++) {
					wavePatates.add(new Patate(startpoints.get(0).cpy(),this,atlas));
				}
			}
		}
		
		if(state == State.WAVE_IN_PROGRESS && inWaveTime > inWaveWaitEnd){
			if(wavePatates.size != 0){
				units.add(wavePatates.pop());
				inWaveWaitEnd = inWaveWaitDuration;
				inWaveWaitEnd += Math.random()*2*inWaveWaitDelta - inWaveWaitDelta;
				inWaveTime = 0;
			}else {
				setState(State.WAITING);
				waveWaitEnd = waveWaitDuration;
				waveWaitEnd += Math.random()*2*waveWaitDelta - waveWaitDelta;
			}
		}
	}
}
