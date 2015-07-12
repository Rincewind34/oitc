package de.janhektor.oitc.game.countdown.abstracts;

public interface CountdownListener {

	public abstract void onStart();
	
	public abstract void onTick(int seconds);

	public abstract void onEnd();
}
