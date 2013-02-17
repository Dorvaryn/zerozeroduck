package fr.odai.zerozeroduck.utils;

import java.util.ArrayList;

public class StageInfo {
	public class TrapsInfo {
		public int level;
		public float x;
		public String type;
		
		public TrapsInfo(int level, float x, String type) {
			super();
			this.level = level;
			this.x = x;
			this.type = type;
		}
	}
	
	public int number;
	public String map_path;
	
	public ArrayList<String> floor_paths;
	public ArrayList<Float> starting_points;
	
	public float duck_x;
	public int duck_level;
	
	public ArrayList<TrapsInfo> traps;
	
	public int poolPatates;
	public int moyPatatesByWave = 3;
	public int patatesByWaveDelta = 1;
	
	public int poolCarrots;
	public int moyCarrotsByWave = 0;
	public int carrotsByWaveDelta = 2;

	public StageInfo(int number, String map_path, ArrayList<String> floor_paths,
			ArrayList<Float> starting_points, float duck_x, int duck_level,
			int poolPatates, int moyPatatesByWave, int patatesByWaveDelta,
			int poolCarrots, int moyCarrotsByWave, int carrotsByWaveDelta) {
		super();
		this.number = number;
		this.map_path = map_path;
		this.floor_paths = floor_paths;
		this.starting_points = starting_points;
		this.duck_x = duck_x;
		this.duck_level = duck_level;
		this.traps = new ArrayList<TrapsInfo>();
		
		this.poolPatates = poolPatates;
		this.moyPatatesByWave = moyPatatesByWave;
		this.patatesByWaveDelta = patatesByWaveDelta;
		
		this.poolCarrots = poolCarrots;
		this.moyCarrotsByWave = moyCarrotsByWave;
		this.carrotsByWaveDelta = carrotsByWaveDelta;
	}
	
	public void addTrapInfo(int level, String type, float position) {
		this.traps.add(new TrapsInfo(level, position, type));
	}
}
