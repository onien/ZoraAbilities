package lol.onien.zoraabilities.Items;

import lol.onien.zoraabilities.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BedBombing implements Listener {
    Main plugin = Main.getPlugin(Main.class);
    private HashMap<Player, Long> cooldown = new HashMap<Player, Long>();

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBedPlace(BlockPlaceEvent e) {
        Block block = e.getBlockPlaced();
        if(e.getPlayer().getItemInHand() !=null) {
            if (e.getPlayer().getItemInHand().isSimilar(bedbomb())) {
                if (plugin.checkRadius(e.getPlayer().getLocation(), plugin.radius(), 0, 0)) {
                    e.getPlayer().sendMessage(plugin.spawn());
                    e.setCancelled(true);
                    return;
                }
                if(cooldown.containsKey(e.getPlayer())) {
                    double time = (cooldown.get(e.getPlayer()) - System.currentTimeMillis()) / 1000.0;
                    e.getPlayer().sendMessage(color(plugin.replaceTime(time)));
                    e.setCancelled(true);
                    return;
                }
                cooldown.put(e.getPlayer(), (System.currentTimeMillis() + (cooldownbedbomb() * 1000)));
                e.setCancelled(true);
                int newAmount = e.getItemInHand().getAmount() - 1;
                if(newAmount == 0)
                    e.getPlayer().setItemInHand(new ItemStack(Material.AIR, 1));
                else
                    e.getItemInHand().setAmount(newAmount);
                e.getPlayer().updateInventory();
                e.getPlayer().sendMessage(color(messagea()));
                Player player = e.getPlayer();
                Fireball fireball = player.getWorld().spawn(block.getLocation(), Fireball.class);
                fireball.setYield(plugin.getConfig().getInt("bedbomb.damage"));
                fireball.setDirection(new Vector(0,-0.3,0));
                Bukkit.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        cooldown.remove(e.getPlayer());
                    }
                }, (20 * (cooldownbedbomb())));
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onCreeperExplosion(final EntityExplodeEvent event) {
        if(event.getEntity() instanceof Fireball){
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent event){
        if (event.getCause() == BlockIgniteEvent.IgniteCause.FIREBALL){
            event.setCancelled(true);
        }
        if(event.getCause().equals(BlockIgniteEvent.IgniteCause.FIREBALL)){
            event.setCancelled(true);
        }
        if(event.getCause()== BlockIgniteEvent.IgniteCause.EXPLOSION){
            event.setCancelled(true);
        }
    }

    String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    ItemStack bedbomb() {
        String name = plugin.getConfig().getString("bedbomb.name");
        List<String> lore = plugin.getConfig().getStringList("bedbomb.lore");
        ItemStack sb = createItem(Material.BED, name, lore);
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

    String messagea() {
        return color(plugin.getConfig().getString("bedbomb.message"));
    }
    int cooldownbedbomb() {
        return plugin.getConfig().getInt("bedbomb.cooldown");
    }
}
