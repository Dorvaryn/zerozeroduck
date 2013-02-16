package fr.odai.zerozeroduck.model;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.Pixmap;

import fr.odai.zerozeroduck.controller.MainController;


public class World {
	/** Bump per minutes */
	public static final int BPM = 85;
		
	/** Traps **/
	Array<Trap> traps = new Array<Trap>();
	/** Startpoints for the units **/
	Array<Vector2> startpoints = new Array<Vector2>();
	/** Units in this world **/
	Array<Patate> patates = new Array<Patate>();
	
	private Array<Integer> floor_pos = new Array<Integer>();

	/** Our player controlled hero **/
	Duck duck;	

	// Getters -----------
	public Duck getDuck() {
		return duck;
	}
	public Array<Trap> getTraps() {
		return traps;
	}
	public Array<Patate> getPatates() {
		return patates;
	}
	// --------------------

	public Array<Integer> getFloorPos() {
		return floor_pos;
	}
	public void setFloorPos(Array<Integer> floor_pos) {
		this.floor_pos = floor_pos;
	}
	public World() {
		createDemoWorld();
	}

	private void createDemoWorld() {
		duck = new Duck(new Vector2(9, 1));

		Trap trap = new Trap(5f,new Vector2(2,1), 100);
		trap.setAssociatedKey(MainController.Keys.TRAP_F);
		traps.add(trap);
		
		Pixmap floor_pixmap = new Pixmap(Gdx.files.internal("images/Stage0-floor.png"));
		int width = floor_pixmap.getWidth();
		setFloorPos(new Array<Integer>(width));
		for(int i = 0; i < width; i++) {
			int j = floor_pixmap.getHeight();
			for(; j > 0; j--) {
				if(floor_pixmap.getPixel(i, j) > 0) {
					break;
				}
			}
			getFloorPos().add(j);
		}
	}
	
	public float getFloorHeight(float x) {
		int index = Math.round(x / 10 * getFloorPos().size);
		return getFloorPos().get(index);
	}
	
	public void update(float delta) {
		// Auto-deleting when getting out of screen
		for(Patate patate: patates) {
			if(patate.position.x > 10 || patate.position.x < 0) {
				patates.removeValue(patate, true);
			}
		}
	}
}
