package de.janhektor.oitc.listener;

import org.bukkit.event.entity.FoodLevelChangeEvent;

import de.janhektor.listener.bundle.DefaultListener;
import de.janhektor.listener.bundle.ListenerBundle;

public class ListenerFood extends DefaultListener {

	public ListenerFood() {
		super(ListenerFood.class, FoodLevelChangeEvent.getHandlerList());
	}

	@ListenerBundle(name = "bundle.all")
	private static void onFoodChange(FoodLevelChangeEvent e) {
		e.setCancelled(true);
	}
}
