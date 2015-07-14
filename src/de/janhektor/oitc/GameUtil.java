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
		
		plugin.setIngame(true);
		plugin.getHandler().unregister(GameState.LOBBY.bundleName());
		plugin.getHandler().register(GameState.GAME.bundleName());
		
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
		bow = GameUtil.rename(bow, "§6Bogen");
		bow = new NBTHelper(bow).setBoolean("oitc.bow", true).modify();
		return bow;
	}
	
	public static ItemStack getArrow() {
		ItemStack arrow = new ItemStack(Material.ARROW);
		arrow = GameUtil.rename(arrow, "§6Pfeil");
		arrow = new NBTHelper(arrow).setBoolean("oitc.arrow", true).modify();
		return arrow;
	}
	
	public static ItemStack getRedstone(int amount) {
		ItemStack redstone = new ItemStack(Material.REDSTONE);
		redstone = GameUtil.rename(redstone, "§3Du hast " + amount + " Leben");
		redstone = new NBTHelper(redstone).setBoolean("oitc.redstone", true).modify();
		redstone.setAmount(amount);
		return redstone;
	}
	
	private static ItemStack rename(ItemStack item, String name) {
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		return item;
	}
}
