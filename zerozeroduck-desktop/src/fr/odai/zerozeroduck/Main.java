package fr.odai.zerozeroduck;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "zerozeroduck";
		cfg.useGL20 = false;
		cfg.width = 1920;
		cfg.height = 1080;
		cfg.resizable = false;
		
		new LwjglApplication(new ZeroZeroDuck(), cfg);
	}
}
