package lol.onien.zoraabilities.Items;

import lol.onien.zoraabilities.C;
import lol.onien.zoraabilities.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Rocket implements Listener {
    private HashMap<Player, Long> cooldown = new HashMap<Player, Long>();

    Main plugin = Main.getPlugin(Main.class);

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamagea(final EntityDamageEvent e) {
        if (e.getEntityType() != EntityType.PLAYER) {
            return;
        }
        Player p = (Player) e.getEntity();
        if(e.getCause().equals(EntityDamageEvent.DamageCause.FALL)){
            if(p.hasMetadata("takeNoFallDamageRocket")) {
                p.removeMetadata("takeNoFallDamageRocket", Main.getInstance());
                e.setCancelled(true);
            }
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerCooldownInteract(PlayerInteractEvent e){
        if(e.getPlayer().getItemInHand() !=null){
            if(e.getPlayer().getItemInHand().isSimilar(rocket())){
                if (plugin.checkRadius(e.getPlayer().getLocation(), plugin.radius(), 0, 0)) {
                    e.getPlayer().sendMessage(C.color(plugin.spawn()));
                    e.setCancelled(true);
                    return;
                }
                if(cooldown.containsKey(e.getPlayer())) {
                    double time = (cooldown.get(e.getPlayer()) - System.currentTimeMillis()) / 1000.0;
                    e.getPlayer().sendMessage(color(plugin.replaceTime(time)));
                    e.setCancelled(true);
                    return;
                }
                cooldown.put(e.getPlayer(), (System.currentTimeMillis() + (cooldownRocket() * 1000)));
                e.getPlayer().sendMessage(C.color(messageb()));
                e.getPlayer().getInventory().remove(rocket());
                e.getPlayer().setVelocity(e.getPlayer().getEyeLocation().getDirection().multiply(plugin.getConfig().getDouble("rocket.launch-distance")));
                int newAmount = e.getItem().getAmount() - 1;
                if(newAmount == 0)
                    e.getPlayer().setItemInHand(new ItemStack(Material.AIR, 1));
                else
                    e.getItem().setAmount(newAmount);
                e.getPlayer().updateInventory();
                e.getPlayer().setMetadata("takeNoFallDamageRocket", new FixedMetadataValue(Main.getInstance(), e.getPlayer().getName()));
                Bukkit.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        cooldown.remove(e.getPlayer());
                    }
                }, (20 * (cooldownRocket())));
            }
        }
    }

    ItemStack rocket() {
        String name = plugin.getConfig().getString("rocket.name");
        List<String> lore = plugin.getConfig().getStringList("rocket.lore");
        ItemStack sb = createItem(Material.FIREWORK, name, lore);
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
    String messageb() {
        return color(plugin.getConfig().getString("rocket.message"));
    }

    String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    int cooldownRocket() {
        return plugin.getConfig().getInt("rocket.cooldown");
    }
}
