package de.janhektor.oitc;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@SuppressWarnings("deprecation")
public class GameUtil {

	public static void startGame(Main plugin) {
		Bukkit.broadcastMessage(plugin.prefix + "§6Das Spiel hat begonnen!");
		Bukkit.broadcastMessage(plugin.prefix + "§6Viel Glück!");
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.getInventory().clear();
			p.setHealth(20);
			p.setFoodLevel(20);
			p.setFireTicks(0);
			p.getInventory().setItem(8, getArrow());
			p.getInventory().setItem(0, getBow());
		}
		plugin.ingame = true;
	}
	
	public static void endMapVoting(Main plugin, String winMap, int votes) {
		Bukkit.broadcastMessage(plugin.prefix + "§aDas Map-Voting ist beendet!");
		Bukkit.broadcastMessage(plugin.prefix + "§aDie Map §6" + winMap + "§a hat mit §6" + votes + "§a Votes gewonnen!");
	}
	
	public static ItemStack getBow() {
		ItemStack bow = new ItemStack(Material.BOW);
		ItemMeta bowMeta = bow.getItemMeta();
		bowMeta.setDisplayName("§6Bogen");
		bow.setItemMeta(bowMeta);
		return bow;
	}
	
	public static ItemStack getArrow() {
		ItemStack arrow = new ItemStack(Material.ARROW);
		ItemMeta arrowMeta = arrow.getItemMeta();
		arrowMeta.setDisplayName("§6Pfeil");
		arrow.setItemMeta(arrowMeta);
		return arrow;
	}
}
