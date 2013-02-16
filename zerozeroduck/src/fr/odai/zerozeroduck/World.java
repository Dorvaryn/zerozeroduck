package fr.odai.zerozeroduck;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import fr.odai.zerozeroduck.actors.Duck;

public class World {
	/** The blocks making up the world **/
	Array blocks = new Array();
	/** Our player controlled hero **/
	Duck duck;

	// Getters -----------
	public Array getBlocks() {
		return blocks;
	}
	public Duck getDuck() {
		return duck;
	}
	// --------------------

	public World() {
		createDemoWorld();
	}

	private void createDemoWorld() {
		duck = new Duck(new Vector2(7, 2));

		for (int i = 0; i < 10; i++) { 			 			
			blocks.add(new Block(new Vector2(i, 0))); 			 			
			blocks.add(new Block(new Vector2(i, 7))); 			 			
			if (i > 2)
				blocks.add(new Block(new Vector2(i, 1)));
		}
		blocks.add(new Block(new Vector2(9, 2)));
		blocks.add(new Block(new Vector2(9, 3)));
		blocks.add(new Block(new Vector2(9, 4)));
		blocks.add(new Block(new Vector2(9, 5)));

		blocks.add(new Block(new Vector2(6, 3)));
		blocks.add(new Block(new Vector2(6, 4)));
		blocks.add(new Block(new Vector2(6, 5)));
	}
}