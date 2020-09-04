package lol.onien.zoraabilities.Items;

import lol.onien.zoraabilities.C;
import lol.onien.zoraabilities.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PumpkinReaper implements Listener {
    Main plugin = Main.getPlugin(Main.class);

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            Player damager = (Player) e.getDamager();
            ItemStack hand = damager.getItemInHand();
            Player p = (Player) e.getEntity();

            if (damager.getItemInHand() != null) {
                if (damager.getItemInHand().isSimilar(pumpkinreaper())) {
                    if (plugin.checkRadius(damager.getLocation(), plugin.radius(), 0, 0)) {
                        damager.sendMessage(C.color(plugin.spawn()));
                        e.setCancelled(true);
                        return;
                    }
                    e.setDamage(plugin.getConfig().getDouble("pumpkinreaper.damage-multiplier"));
                    Random rnd = new Random();
                    int i = rnd.nextInt(plugin.getConfig().getInt("pumpkinreaper.chance"));
                    if (i == 1) {
                        if(((Player) e.getEntity()).getPlayer().getInventory().getHelmet()!=null){
                            if(((Player) e.getEntity()).getPlayer().getInventory().getHelmet().getType()==Material.PUMPKIN){
                                return;
                            }
                            damager.sendMessage(C.color(plugin.getConfig().getString("pumpkinreaper.damager-swapped-message")
                                    .replaceAll("%victim%",((Player) e.getEntity()).getDisplayName())
                                    .replaceAll("%damager%", damager.getDisplayName())));
                            ((Player) e.getEntity()).sendMessage(C.color(plugin.getConfig().getString("pumpkinreaper.victim-swapped-message")
                                    .replaceAll("%victim%",((Player) e.getEntity()).getDisplayName())
                                    .replaceAll("%damager%", damager.getDisplayName())));
                            ItemStack sb = ((Player) e.getEntity()).getPlayer().getInventory().getHelmet();
                            ((Player) e.getEntity()).getPlayer().getInventory().addItem(sb);
                            ((Player) e.getEntity()).getPlayer().getInventory().setHelmet(new ItemStack(Material.PUMPKIN));
                            ((Player) e.getEntity()).getPlayer().updateInventory();
                            e.setDamage(plugin.getConfig().getDouble("pumpkinreaper.damage-effect-multiplier"));
                            e.getEntity().getWorld().playSound(e.getEntity().getLocation(), Sound.GHAST_SCREAM, 0.5f, 1.8f);
                        }

                    }
                }
            }
        }
    }

    ItemStack pumpkinreaper() {
        String name = plugin.getConfig().getString("pumpkinreaper.name");
        List<String> lore = plugin.getConfig().getStringList("pumpkinreaper.lore");
        ItemStack sb = createItem(Material.IRON_SWORD, name, lore);
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
}
