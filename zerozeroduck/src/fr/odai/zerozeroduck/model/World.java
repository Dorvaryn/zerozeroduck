package fr.odai.zerozeroduck.model;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;


public class World {
	/** The blocks making up the world **/
	Array<Block> blocks = new Array<Block>();
	/** The startpoints for the units **/
	Array<Vector2> startpoints = new Array<Vector2>();
	/** The units in this world **/
	Array<Patate> patates = new Array<Patate>();

	/** Our player controlled hero **/
	Duck duck;
	
	Trap trap;

	// Getters -----------
	public Array<Block> getBlocks() {
		return blocks;
	}
	public Duck getDuck() {
		return duck;
	}
	public Trap getTrap() {
		return trap;
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

		trap = new Trap(5f,new Vector2(2,1),100);

		for (int i = 0; i < 10; i++) { 			 			
			blocks.add(new Block(new Vector2(i, 0))); 			 			
			blocks.add(new Block(new Vector2(i, 7)));
		}
	}
}
