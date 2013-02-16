package fr.odai.zerozeroduck.model;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import fr.odai.zerozeroduck.utils.Util;

public class SaltBarrel extends Trap{
	
	private float range=0.5f;
	private int damage=50;
	
	public SaltBarrel(Vector2 position, int damage){
		super(position, damage);
	}
	
	public float getRange() {
		return range;
	}

	public void setRange(float range) {
		this.range = range;
	}
	
	@Override
	public int damageWhenTrapped(Rectangle rect){
		Vector2 posA = new Vector2((float)position.x+(float)(SIZE/2.f), (float)position.y+(float)(SIZE/2.f));
		Vector2 posB = new Vector2((float)rect.x+(float)(rect.width/2.f), (float)rect.y+(float)(rect.height/2.f));
		if(Util.Distance(posA, posB)<=range){
			return -damage;
		}
		else return 0;
	}
}
