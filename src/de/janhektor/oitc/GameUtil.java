package de.janhektor.oitc;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GameUtil {
	
	private static Random rnd;
	private static InfoLayout layout;
	
	static {
		GameUtil.rnd = new Random();
		GameUtil.layout = new InfoLayout("OITC");
	}

	public static void startGame(Main plugin) {
		Bukkit.broadcastMessage(GameUtil.layout.prefix + GameUtil.layout.clSec + "Das Spiel hat begonnen!");
		Bukkit.broadcastMessage(GameUtil.layout.prefix + GameUtil.layout.clSec + "Viel Glueck!");
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			p.getInventory().clear();
			p.setLevel(0);
			p.setExp(0.0F);
			p.setHealth(20);
			p.setFoodLevel(20);
			p.setFireTicks(0);
			p.getInventory().setItem(8, getArrow());
			p.getInventory().setItem(4, getRedstone(plugin.getMaxLives()));
			p.getInventory().setItem(0, getBow());
			p.teleport(plugin.getSpawnPoints().get(rnd.nextInt(plugin.getSpawnPoints().size())));
			plugin.getLives().put(p.getName(), plugin.getMaxLives());
		}
		
		plugin.ingame = true;
	}
	
	public static void endMapVoting(Main plugin, String winMap, int votes) {
		Bukkit.broadcastMessage(GameUtil.layout.prefix + GameUtil.layout.clPos + "Das Map-Voting ist beendet!");
		Bukkit.broadcastMessage(GameUtil.layout.prefix
				+ GameUtil.layout.clPos + "Die Map "
				+ GameUtil.layout.clSec + winMap
				+ GameUtil.layout.clPos + " hat mit "
				+ GameUtil.layout.clSec + votes
				+ GameUtil.layout.clPos + " Votes gewonnen!");
	}
	
	public static Random getRnd() {
		return GameUtil.rnd;
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
	
	public static ItemStack getRedstone(int amount) {
		ItemStack redstone = new ItemStack(Material.REDSTONE);
		ItemMeta redstoneMeta = redstone.getItemMeta();
		redstoneMeta.setDisplayName("§3Du hast " + amount + " Leben");
		redstone.setItemMeta(redstoneMeta);
		redstone.setAmount(amount);
		return redstone;
	}
}
