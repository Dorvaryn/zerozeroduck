package fr.odai.zerozeroduck.model;

import com.badlogic.gdx.math.Vector2;

public class SaltBarrel extends Trap{
	
	private float range=0.5f;
	private int damage=50;
	
	public SaltBarrel(Vector2 position, int damage){
		super(position, damage);
	}
}
