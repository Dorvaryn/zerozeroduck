package fr.odai.zerozeroduck.utils;

import com.badlogic.gdx.math.Vector2;

public class Util {
	public static float Distance(Vector2 a, Vector2 b){
		return (float)Math.sqrt(((a.x-b.x)*(a.x-b.x))+((a.y-b.y)*(a.y-b.y)));
	}
	
	public static float screenHeight;
	public static float screenWidth;
}
