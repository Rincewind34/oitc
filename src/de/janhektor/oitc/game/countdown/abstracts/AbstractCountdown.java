package de.janhektor.oitc.game.countdown.abstracts;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

import de.janhektor.oitc.Main;

public abstract class AbstractCountdown implements Countdown {

	protected final int startSeconds;
	protected final Main plugin;
	
	protected BukkitTask task;
	protected List<CountdownListener> handlers;
	
	protected int secondsLeft;
	
	public AbstractCountdown(Main plugin, int secondsLeft) {
		this.plugin = plugin;
		this.secondsLeft = secondsLeft;
		this.startSeconds = secondsLeft;
		this.handlers = new ArrayList<>();
	}
	
	@Override
	public void start() {
		this.secondsLeft = this.startSeconds;
		
		for (CountdownListener listener : this.handlers) {
			listener.onStart();
		}
		
		this.task = Bukkit.getScheduler().runTaskTimer(this.plugin, () -> {
			
			if (this.secondsLeft <= 0) {
				this.stop();
				return;
			}
			
			if (this.isAnnounceTime(this.secondsLeft)) {
				this.onAnnounceTime();
			}
			
			for (CountdownListener listener : this.handlers) {
				listener.onTick(this.secondsLeft);
			}
			
			this.secondsLeft--;
			
			
		}, 0L, 20L);
	}

	@Override
	public void stop() {
		this.task.cancel();
		
		for (CountdownListener listener : this.handlers) {
			listener.onEnd();
		}
	}

	@Override
	public void broadcastChatMessage(String pattern, Object... replacements) {
		Bukkit.broadcastMessage(MessageFormat.format(pattern, replacements));
	}

	@Override
	public void setTime(int seconds) {
		this.secondsLeft = seconds;
	}

	@Override
	public void addHandler(CountdownListener listener) {
		this.handlers.add(listener);
	}

	@Override
	public void removeHandler(CountdownListener listener) {
		this.handlers.remove(listener);
	}

	@Override
	public int getTime() {
		return this.secondsLeft;
	}

	@Override
	public boolean isRunning() {
		return this.task != null;
	}
	
	public abstract void onAnnounceTime();
	
	public abstract boolean isAnnounceTime(int seconds);
	
	public static boolean checkAnnounceTime(int seconds) {
		return seconds % 120 == 0 || (seconds <= 60 && seconds % 15 == 0)
				|| seconds == 10 || seconds <= 5;
	}

}
