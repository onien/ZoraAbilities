package lol.onien.zoraabilities.Items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lol.onien.zoraabilities.Main;
import lol.onien.zoraabilities.C;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import net.md_5.bungee.api.ChatColor;

public class Switcheroo implements Listener {
	
	private Main plugin = Main.getPlugin(Main.class);
	private HashMap<Player, Long> cooldown = new HashMap<Player, Long>();
	private HashMap<Player, Snowball> snowball = new HashMap<Player, Snowball>();
	private ArrayList<Player> thrown = new ArrayList<Player>();
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void snowballLaunch(ProjectileLaunchEvent e) {
		if(e.getEntity() instanceof Snowball) {
			Snowball snowball = (Snowball) e.getEntity();
			Player shooter = (Player) snowball.getShooter();
			if(thrown.contains(shooter)) {
				thrown.remove(shooter);
				this.snowball.put(shooter, snowball);
			}
		}
	}
	
	@EventHandler
	public void snowballThrow(PlayerInteractEvent e) {
		final Player player = e.getPlayer();
		if(e.getItem() == null)
			return;
		if(!e.getItem().hasItemMeta())
			return;
		if(!e.getItem().getItemMeta().hasDisplayName())
			return;
		if(!e.getItem().getItemMeta().hasLore())
			return;
		if(e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK))
			return;
		if(e.getItem().getType().equals(Material.SNOW_BALL)) {
			if(plugin.checkRadius(player.getLocation(), plugin.radius(), 0, 0)) {
				player.sendMessage(C.color(plugin.spawn()));
				e.setCancelled(true);
				return;
			}
			ItemStack sb = snowball();
			sb.setAmount(e.getItem().getAmount());
			if(e.getItem().equals(sb)) {
				if(cooldown.containsKey(player)) {
					double time = (cooldown.get(player) - System.currentTimeMillis()) / 1000.0;
					player.sendMessage(color(plugin.replaceTime(time)));
					e.setCancelled(true);
					return;
				}
				thrown.add(player);
				cooldown.put(player, (System.currentTimeMillis() + (cooldownSnow() * 1000)));
				Bukkit.getServer().getScheduler().runTaskLater(plugin, new Runnable() {

					@Override
					public void run() {
						cooldown.remove(player);
					}
					
				}, (20 * (cooldownSnow())));
			}
			return;
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void snowballLand(ProjectileHitEvent e) {
		if(e.getEntity() instanceof Snowball) {
			final Snowball sb = (Snowball) e.getEntity();
			if(sb.getShooter() instanceof Player) {
				final Player player = (Player) sb.getShooter();
				Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {

					@Override
					public void run() {
						if(snowball.containsKey(player)) {
							if(sb.equals(snowball.get(player))) {
								snowball.remove(player);
								cooldown.remove(player);
								player.getInventory().addItem(snowball());
								player.sendMessage(color(plugin.getConfig().getString("switcheroo.did-not-hit")));
							}
						}
					}

				}, 20);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void snowballHit(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Player) {
			if(e.getDamager() instanceof Snowball) {
				Snowball snowball = (Snowball) e.getDamager();
				if(snowball.getShooter() instanceof Player) {
					Player player = (Player) snowball.getShooter();
					if(this.snowball.containsKey(player)) {
						Snowball stored = this.snowball.get(player);
						if(snowball.equals(stored)) {
							this.snowball.remove(player);
							Player target = (Player) e.getEntity();
							if(plugin.checkRadius(target.getLocation(), plugin.radius(), 0, 0)) {
								player.getInventory().addItem(snowball());
								cooldown.remove(player);
								return;
							}
							if(player.getLocation().distance(e.getEntity().getLocation()) <= snowRadius()) {
								Location loc = player.getLocation();
								Location loc2 = e.getEntity().getLocation();
								player.teleport(loc2);
								e.getEntity().teleport(loc);
								player.sendMessage(color(swap(target.getName())));
								target.sendMessage(color(swap(player.getName())));
							} else {
								player.getInventory().addItem(snowball());
								player.sendMessage(C.color(plugin.replaceRadius(messageSnow())));
								cooldown.remove(player);
							}
						}
					}
				}
			}
		}
	}
	
	int snowRadius() {
		return plugin.getConfig().getInt("switcheroo.radius");
	}
	
	String messageSnow() {
		return plugin.getConfig().getString("switcheroo.message");
	}
	
	String swap(String name) {
		return plugin.replacePlayer(plugin.getConfig().getString("switcheroo.switch"), name);
	}
	
	ItemStack snowball() {
		String name = plugin.getConfig().getString("switcheroo.name");
		List<String> lore = plugin.getConfig().getStringList("switcheroo.lore");
		ItemStack sb = createItem(Material.SNOW_BALL, name, lore);
		return sb;
	}
	
	ItemStack createItem(Material material, String name, List<String> lore) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(color(name));
		meta.setLore(colorList(lore));
		Boolean unbreakablea = plugin.getConfig().getBoolean("unbreaking-enchant");
		if(unbreakablea){
			meta.addEnchant(Enchantment.DURABILITY,10,true);
		}
		item.setItemMeta(meta);
		return item;
	}
	
	List<String> colorList(List<String> list){
		ArrayList<String> newList = new ArrayList<String>();
		for(String s : list) {
			newList.add(color(s));
		}
		return newList;
	}
	
	String color(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}
	
	int cooldownSnow() {
		return plugin.getConfig().getInt("switcheroo.cooldown");
	}
}
