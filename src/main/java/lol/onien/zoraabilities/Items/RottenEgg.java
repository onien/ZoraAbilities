package lol.onien.zoraabilities.Items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import lol.onien.zoraabilities.C;
import lol.onien.zoraabilities.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Egg;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.md_5.bungee.api.ChatColor;

public class RottenEgg implements Listener {

	private Main plugin = Main.getPlugin(Main.class);
	private HashMap<Player, Long> cooldown = new HashMap<Player, Long>();
	private HashMap<Player, Egg> egg = new HashMap<Player, Egg>();
	private ArrayList<Player> thrown = new ArrayList<Player>();

	@EventHandler
	public void eggThrow(PlayerInteractEvent e) {
		final Player player = e.getPlayer();
		if (e.getItem() == null)
			return;
		if (!e.getItem().hasItemMeta())
			return;
		if (!e.getItem().getItemMeta().hasDisplayName())
			return;
		if (!e.getItem().getItemMeta().hasLore())
			return;
		if (e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK))
			return;
		if (e.getItem().getType().equals(Material.EGG)) {
			if (plugin.checkRadius(player.getLocation(), plugin.radius(), 0, 0)) {
				player.sendMessage(C.color(plugin.spawn()));
				e.setCancelled(true);
				return;
			}
			ItemStack egg = egg();
			egg.setAmount(e.getItem().getAmount());
			if (egg.isSimilar(e.getItem())) {
				if (cooldown.containsKey(player)) {
					double time = (cooldown.get(player) - System.currentTimeMillis()) / 1000.0;
					player.sendMessage(color(plugin.replaceTime(time)));
					e.setCancelled(true);
					return;
				}
				thrown.add(player);
				cooldown.put(player, (System.currentTimeMillis() + (cooldown() * 1000)));
				Bukkit.getServer().getScheduler().runTaskLater(plugin, new Runnable() {

					@Override
					public void run() {
						cooldown.remove(player);
					}

				}, (20 * (cooldown())));
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void eggLaunch(ProjectileLaunchEvent e) {
		if (e.getEntity() instanceof Egg) {
			Egg egg = (Egg) e.getEntity();
			Player shooter = (Player) e.getEntity().getShooter();
			if (thrown.contains(shooter)) {
				thrown.remove(shooter);
				this.egg.put(shooter, egg);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void eggLand(ProjectileHitEvent e) {
		if(e.getEntity() instanceof Egg) {
			final Egg eg = (Egg) e.getEntity();
			if(eg.getShooter() instanceof Player) {
				final Player player = (Player) eg.getShooter();
				Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {

					@Override
					public void run() {
						if(egg.containsKey(player)) {
							if(eg.equals(egg.get(player))) {
								egg.remove(player);
								cooldown.remove(player);
								player.getInventory().addItem(egg());
								player.sendMessage(color(plugin.getConfig().getString("rotten-egg.did-not-hit")));
							}
						}
					}
					
				}, 20);
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void eggHit(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player) {
			if (e.getDamager() instanceof Egg) {
				Egg egg = (Egg) e.getDamager();
				if (egg.getShooter() instanceof Player) {
					Player player = (Player) egg.getShooter();
					if (this.egg.containsKey(player)) {
						Egg stored = this.egg.get(player);
						if (egg.equals(stored)) {
							this.egg.remove(player);
							Player target = (Player) e.getEntity();
							if (plugin.checkRadius(player.getLocation(), plugin.radius(), 0, 0)) {
								String name = plugin.getConfig().getString("rotten-egg.name");
								List<String> lore = plugin.getConfig().getStringList("rotten-egg.lore");
								ItemStack egg1 = createItem(Material.EGG, name, lore);
								player.getInventory().addItem(egg1);
								cooldown.remove(player);
								return;
							}
							if (player.getLocation().distance(e.getEntity().getLocation()) <= eggRadius()) {
								target.addPotionEffect(
										new PotionEffect(PotionEffectType.WITHER, (time() * 20), slowLevel() - 1));
                                target.addPotionEffect(
                                        new PotionEffect(PotionEffectType.CONFUSION, (time() * 20), slowLevel() - 1));
								target.addPotionEffect(
										new PotionEffect(PotionEffectType.SLOW, (time() * 20), slowLevel() - 1));
								target.addPotionEffect(
										new PotionEffect(PotionEffectType.POISON, (time() * 20), poisonLevel() - 1));
								player.sendMessage(color(attack(target.getName())));
								target.sendMessage(color(hit(player.getName())));
							} else {
								String name = plugin.getConfig().getString("rotten-egg.name");
								List<String> lore = plugin.getConfig().getStringList("rotten-egg.lore");
								ItemStack egg1 = createItem(Material.EGG, name, lore);
								player.getInventory().addItem(egg1);
								cooldown.remove(player);
								player.sendMessage(C.color(plugin.replaceRadius(messageEgg())));
							}
						}
					}
				}
			}
		}
	}

	private int cooldown() {
		return plugin.getConfig().getInt("rotten-egg.cooldown");
	}

	private ItemStack egg() {
		String name = plugin.getConfig().getString("rotten-egg.name");
		List<String> lore = plugin.getConfig().getStringList("rotten-egg.lore");
		ItemStack egg = createItem(Material.EGG, name, lore);
		return egg;
	}

	private String messageEgg() {
		return plugin.getConfig().getString("rotten-egg.message");
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

	List<String> colorList(List<String> list) {
		ArrayList<String> newList = new ArrayList<String>();
		for (String s : list) {
			newList.add(color(s));
		}
		return newList;
	}

	String color(String text) {
		return ChatColor.translateAlternateColorCodes('&', text);
	}

	String attack(String name) {
		return plugin.replacePlayer(plugin.getConfig().getString("rotten-egg.attack"), name);
	}

	String hit(String name) {
		return plugin.replacePlayer(plugin.getConfig().getString("rotten-egg.hit"), name);
	}

	int eggRadius() {
		return plugin.getConfig().getInt("rotten-egg.radius");
	}

	int slowLevel() {
		return plugin.getConfig().getInt("rotten-egg.slowness");
	}

	int poisonLevel() {
		return plugin.getConfig().getInt("rotten-egg.poison");
	}

	int time() {
		return plugin.getConfig().getInt("rotten-egg.time");
	}

}
