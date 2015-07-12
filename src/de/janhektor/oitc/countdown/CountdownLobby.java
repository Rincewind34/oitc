package de.janhektor.oitc.countdown;

import org.bukkit.Bukkit;

import de.janhektor.oitc.GameUtil;
import de.janhektor.oitc.Main;
import de.janhektor.oitc.game.countdown.abstracts.AbstractCountdown;
import de.janhektor.oitc.game.countdown.abstracts.Countdown;
import de.janhektor.oitc.game.countdown.abstracts.CountdownListener;

public class CountdownLobby extends AbstractCountdown implements CountdownListener {
	
	private static Countdown instance;
	
	static {
		CountdownLobby.instance = new CountdownLobby();
	}
	
	public static Countdown getInstance() {
		return CountdownLobby.instance;
	}

	private CountdownLobby() {
		super(Main.getInstance(), Main.getInstance().lobbyTime);
		super.addHandler(this);
	}

	@Override
	public void onAnnounceTime() {
		Bukkit.broadcastMessage(super.plugin.prefix + "§aDas Spiel startet in " + super.secondsLeft + " "
				+ (super.secondsLeft == 1 ? "Sekunde" : "Sekunden"));
		
		if (super.secondsLeft <= 10 && super.plugin.mapVoting.isRunning()) {
			super.plugin.mapVoting.endVoting();
		}
	}

	@Override
	public boolean isAnnounceTime(int seconds) {
		return AbstractCountdown.checkAnnounceTime(seconds);
	}

	@Override
	public void onStart() {
		
	}

	@Override
	public void onTick(int seconds) {
		
	}

	@Override
	public void onEnd() {
		if (Bukkit.getOnlinePlayers().size() >= super.plugin.minPlayers) {
			GameUtil.startGame(super.plugin);
		} else {
			super.start();
		}
	}

}
