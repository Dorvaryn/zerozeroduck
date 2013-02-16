package fr.odai.zerozeroduck.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


public class World {
	/** The blocks making up the world **/
	Array<Block> blocks = new Array<Block>();
	/** The trap **/
	Array<Trap> traps = new Array<Trap>();
	/** The startpoints for the units **/
	Array<Vector2> startpoints = new Array<Vector2>();
	/** The units in this world **/
	Array<Patate> patates = new Array<Patate>();

	/** Our player controlled hero **/
	Duck duck;
	

	// Getters -----------
	public Array<Block> getBlocks() {
		return blocks;
	}
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

	public World() {
		createDemoWorld();
	}

	private void createDemoWorld() {
		duck = new Duck(new Vector2(9, 1));
		Patate patate = new Patate(new Vector2(2,1));
		patates.add(patate);

		Trap trap = new Trap(5f,new Vector2(2,1),100);
		traps.add(trap);

		for (int i = 0; i < 10; i++) { 			 			
			blocks.add(new Block(new Vector2(i, 0))); 			 			
			blocks.add(new Block(new Vector2(i, 7)));
		}
	}
}
