package fr.odai.zerozeroduck.utils;

import com.badlogic.gdx.tools.imagepacker.TexturePacker2;

public class TextureSetup {

	public static void main(String[] args) {
		TexturePacker2.process("../zerozeroduck-android/assets/images/",
				"../zerozeroduck-android/assets/images/", "textures.pack");
	}
}
