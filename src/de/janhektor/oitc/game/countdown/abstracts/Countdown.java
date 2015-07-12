package de.janhektor.oitc.game.countdown.abstracts;

public interface Countdown {

	public abstract void start();
	
	public abstract void stop();
	
	public abstract void broadcastChatMessage(String pattern, Object... replacements);
	
	public abstract void setTime(int seconds);
	
	public abstract void addHandler(CountdownListener l);
	
	public abstract void removeHandler(CountdownListener l);
	
	public abstract int getTime();
	
	public abstract boolean isRunning();
	
}
