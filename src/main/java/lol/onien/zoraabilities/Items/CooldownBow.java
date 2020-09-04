package lol.onien.zoraabilities.Items;

import lol.onien.zoraabilities.Main;
import lol.onien.zoraabilities.C;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CooldownBow implements Listener {
    Main plugin = Main.getPlugin(Main.class);
    private HashMap<Player, Long> cooldown = new HashMap<Player, Long>();

    @EventHandler
    public void onInteract(EntityShootBowEvent e){
        if(e.getEntity() instanceof Player){
            if(((Player) e.getEntity()).getItemInHand()!=null){
                if(((Player) e.getEntity()).getItemInHand().isSimilar(cooldownbow())){
                    Boolean infinityenabled = plugin.getConfig().getBoolean("cooldownbow.infinity-enabled");
                    if(infinityenabled){
                        Bukkit.getServer().getScheduler().runTaskLater(plugin, new Runnable() {

                            @Override
                            public void run() {
                                ((Player) e.getEntity()).getPlayer().getInventory().addItem(new ItemStack(Material.ARROW));
                            }
                        }, (20));
                    }
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player))
            return;
        if (e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
            Projectile proj = (Projectile) e.getDamager();
            if (!(proj instanceof Arrow))
                return;
            Arrow arrow = (Arrow) proj;
            Player damager = (Player) arrow.getShooter();
            if(!(arrow.getShooter() instanceof Player)){
                return;
            }
            if (damager.getItemInHand() != null) {
                if(damager.getItemInHand().isSimilar(cooldownbow())){
                    if (plugin.checkRadius(e.getEntity().getLocation(), plugin.radius(), 0, 0)
                            || plugin.checkRadius(e.getDamager().getLocation(), plugin.radius(), 0, 0)) {
                        damager.sendMessage(plugin.spawn());
                        e.setCancelled(true);
                        return;
                    }
                    if (cooldown.containsKey(damager)) {
                        double time = (cooldown.get(damager) - System.currentTimeMillis()) / 1000.0;
                        damager.sendMessage(color(plugin.replaceTime(time)));
                        return;
                    }
                    cooldown.put(damager, (System.currentTimeMillis() + (cooldowncooldownbow() * 1000)));
                    String command = plugin.getConfig().getString("cooldownbow.cooldown-command").replace("%player%", ((Player) e.getEntity()).getDisplayName());
                    damager.sendMessage(C.color(plugin.getConfig().getString("cooldownbow.message").replaceAll("%player%", ((Player) e.getEntity()).getDisplayName())));
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), command);
                    Bukkit.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
                        @Override
                        public void run() {
                            cooldown.remove(damager);
                        }
                    }, (20 * (cooldowncooldownbow())));
                }
            }
        }
    }
    ItemStack cooldownbow() {
        String name = plugin.getConfig().getString("cooldownbow.name");
        List<String> lore = plugin.getConfig().getStringList("cooldownbow.lore");
        ItemStack sb = createItem(Material.BOW, name, lore);
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


    int cooldowncooldownbow() {
        return plugin.getConfig().getInt("cooldownbow.cooldown");
    }
}
