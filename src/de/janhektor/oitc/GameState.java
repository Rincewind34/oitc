package de.janhektor.oitc;

public enum GameState {
	
	LOBBY("bundle.lobby"),
	GAME("bundle.game"),
	END("bundle.end");
	
	private String bundleName;
	
	private GameState(String bundleName) {
		this.bundleName = bundleName;
	}
	
	public String bundleName() {
		return this.bundleName;
	}
	
}
