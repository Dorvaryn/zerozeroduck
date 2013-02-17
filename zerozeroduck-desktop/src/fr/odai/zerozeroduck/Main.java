package fr.odai.zerozeroduck;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "zerozeroduck";
		cfg.useGL20 = false;
		cfg.width = (int) (1000 * 0.8);
		cfg.height = (int) (700 * 0.8);
		cfg.resizable = false;
		
		new LwjglApplication(new ZeroZeroDuck(), cfg);
	}
}
