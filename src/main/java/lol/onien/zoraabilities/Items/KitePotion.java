package lol.onien.zoraabilities.Items;

import lol.onien.zoraabilities.Main;
import lol.onien.zoraabilities.C;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KitePotion implements Listener {
    Main plugin = Main.getPlugin(Main.class);
    private HashMap<Player, Long> cooldown = new HashMap<Player, Long>();

    @EventHandler
    public void onPotionConsume(final PlayerItemConsumeEvent e) {
        if(e.getPlayer().getItemInHand()!=null){
            if(e.getPlayer().getItemInHand().isSimilar(kitePotion())){
                if(cooldown.containsKey(e.getPlayer())) {
                    double time = (cooldown.get(e.getPlayer()) - System.currentTimeMillis()) / 1000.0;
                    e.getPlayer().sendMessage(color(plugin.replaceTime(time)));
                    e.setCancelled(true);
                    return;
                }
                cooldown.put(e.getPlayer(), (System.currentTimeMillis() + (cooldownkitepotion() * 1000)));
                e.setCancelled(true);
                int newAmount = e.getPlayer().getItemInHand().getAmount() - 1;
                if(newAmount == 0)
                    e.getPlayer().setItemInHand(new ItemStack(Material.AIR, 1));
                else
                    e.getPlayer().getItemInHand().setAmount(newAmount);
                e.getPlayer().updateInventory();
                e.getPlayer().sendMessage(color(messagea()));

                Integer durationtime = plugin.getConfig().getInt("kitepotion.duration");
                Integer amplifieramount = plugin.getConfig().getInt("kitepotion.amplifier");
                e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, durationtime * 20, amplifieramount, false));
                Bukkit.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        cooldown.remove(e.getPlayer());
                    }
                }, (20 * (cooldownkitepotion())));
            }
        }
    }

//    @EventHandler
//    public void onPotionConsume(final PlayerInteractEvent e) {
//        e.getPlayer().sendMessage("a");
//        if(e.getPlayer().getItemInHand() !=null){
//            e.getPlayer().sendMessage("b");
//            if(e.getPlayer().getItemInHand().isSimilar(kitePotion())){
//                e.getPlayer().sendMessage("c");
//                if (plugin.checkRadius(e.getPlayer().getLocation(), plugin.radius(), 0, 0)) {
//                    e.getPlayer().sendMessage(plugin.spawn());
//                    e.setCancelled(true);
//                    e.getPlayer().sendMessage("d");
//                    return;
//                }
//                if(cooldown.containsKey(e.getPlayer())) {
//                    double time = (cooldown.get(e.getPlayer()) - System.currentTimeMillis()) / 1000.0;
//                    e.getPlayer().sendMessage(color(plugin.replaceTime(time)));
//                    e.setCancelled(true);
//                    e.getPlayer().sendMessage("e");
//                    return;
//                }
//                e.getPlayer().sendMessage("f");
//                cooldown.put(e.getPlayer(), (System.currentTimeMillis() + (cooldownkitepotion() * 1000)));
//                e.setCancelled(true);
//                int newAmount = e.getPlayer().getItemInHand().getAmount() - 1;
//                if(newAmount == 0)
//                    e.getPlayer().setItemInHand(new ItemStack(Material.AIR, 1));
//                else
//                    e.getPlayer().getItemInHand().setAmount(newAmount);
//                e.getPlayer().updateInventory();
//                e.getPlayer().sendMessage(color(messagea()));
//
//                Integer durationtime = plugin.getConfig().getInt("kitepotion.duration");
//                Integer amplifieramount = plugin.getConfig().getInt("kitepotion.amplifier");
//                e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.SPEED, durationtime * 20, amplifieramount, false));
//                Bukkit.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
//                    @Override
//                    public void run() {
//                        cooldown.remove(e.getPlayer());
//                    }
//                }, (20 * (cooldownkitepotion())));
//            }
//        }
//    }
    String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    ItemStack kitePotion() {
        String name = plugin.getConfig().getString("kitepotion.name");
        List<String> lore = plugin.getConfig().getStringList("kitepotion.lore");
        ItemStack sb = createItem(Material.POTION, name, lore);
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
        return color(plugin.getConfig().getString("kitepotion.message"));
    }
    int cooldownkitepotion() {
        return plugin.getConfig().getInt("kitepotion.cooldown");
    }
}
