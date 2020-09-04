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
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class StormbreakerAxe implements Listener {
    Main plugin = Main.getPlugin(Main.class);
    private HashMap<Player, Long> cooldown = new HashMap<Player, Long>();

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e){
        if(e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            Player damager = (Player) e.getDamager();
            ItemStack hand = damager.getItemInHand();
            Player p = (Player) e.getEntity();
            if(hand !=null) {
                if (hand.isSimilar(stormaxe())) {
                    if (plugin.checkRadius(damager.getLocation(), plugin.radius(), 0, 0)) {
                        damager.sendMessage(C.color(plugin.spawn()));
                        e.setCancelled(true);
                        return;
                    }
                    if (cooldown.containsKey(damager)) {
                        double time = (cooldown.get(damager) - System.currentTimeMillis()) / 1000.0;
                        damager.sendMessage(color(plugin.replaceTime(time)));
                        e.setCancelled(true);
                        return;
                    }
                    cooldown.put(damager, (System.currentTimeMillis() + (cooldownstormaxe() * 1000)));
                    e.setDamage(plugin.getConfig().getDouble("stormbreaker.damage"));
                    p.getWorld().strikeLightningEffect(p.getLocation());
                    p.getWorld().strikeLightning(e.getEntity().getLocation());
                    Bukkit.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
                        @Override
                        public void run() {
                            cooldown.remove(damager);
                        }
                    }, (20 * (cooldownstormaxe())));
                }
            }
        }
    }

    ItemStack stormaxe() {
        String name = plugin.getConfig().getString("stormbreaker.name");
        List<String> lore = plugin.getConfig().getStringList("stormbreaker.lore");
        ItemStack sb = createItem(Material.DIAMOND_AXE, name, lore);
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

    public void isLicence(Main plugin) {
        String key = plugin.getConfig().getString("license-key");
        if(key.contains("|")||key.contains("-")||key.contains("HWID")||key.contains("Username")||
                key.equalsIgnoreCase("|")||key.equalsIgnoreCase("-")
                ||key.equalsIgnoreCase("Username")||key.equalsIgnoreCase("HWID")||
                key.contains(" ")||key.equalsIgnoreCase(" ")||key.equalsIgnoreCase("")||key == null){
            Bukkit.getPluginManager().disablePlugin(plugin);
            Bukkit.getServer().shutdown();
            return;
        }
        try{
            String url = "https://pastebin.com/raw/yuQ62C9B";
            URLConnection openConnection = new URL(url).openConnection();
            openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
            @SuppressWarnings("resource")
            Scanner scan = new Scanner((new InputStreamReader(openConnection.getInputStream())));
            while(scan.hasNextLine()){
                String firstline = scan.nextLine();
                if(firstline.contains("ZoraAbilities")){
                    String customer = scan.nextLine();
                    if(customer.contains(" "+key+" ")){
                        return;
                    }
                    if(scan.hasNextLine()){
                        String customera = scan.nextLine();
                        if(customera.contains(" "+key+" ")){
                            return;
                        }
                    }
                    if(scan.hasNextLine()){
                        String customerb = scan.nextLine();
                        if(customerb.contains(" "+key+" ")){
                            return;
                        }
                    }
                    if(scan.hasNextLine()){
                        String customera = scan.nextLine();
                        if(customera.contains(" "+key+" ")){
                            return;
                        }
                    }
                    if(scan.hasNextLine()){
                        String customera = scan.nextLine();
                        if(customera.contains(" "+key+" ")){
                            return;
                        }
                    }
                    if(scan.hasNextLine()){
                        String customera = scan.nextLine();
                        if(customera.contains(" "+key+" ")){
                            return;
                        }
                    }
                    if(scan.hasNextLine()){
                        String customera = scan.nextLine();
                        if(customera.contains(" "+key+" ")){
                            return;
                        }
                    }
                    if(scan.hasNextLine()){
                        String customera = scan.nextLine();
                        if(customera.contains(" "+key+" ")){
                            return;
                        }
                    }
                    if(scan.hasNextLine()){
                        String customera = scan.nextLine();
                        if(customera.contains(" "+key+" ")){
                            return;
                        }
                    }
                    if(scan.hasNextLine()){
                        String customera = scan.nextLine();
                        if(customera.contains(" "+key+" ")){
                            return;
                        }
                    }
                    if(scan.hasNextLine()){
                        String customera = scan.nextLine();
                        if(customera.contains(" "+key+" ")){
                            return;
                        }
                    }
                    if(scan.hasNextLine()){
                        String customera = scan.nextLine();
                        if(customera.contains(" "+key+" ")){
                            return;
                        }
                    }
                    if(scan.hasNextLine()){
                        String customera = scan.nextLine();
                        if(customera.contains(" "+key+" ")){
                            return;
                        }
                    }
                    if(scan.hasNextLine()){
                        String customera = scan.nextLine();
                        if(customera.contains(" "+key+" ")){
                            return;
                        }
                    }
                    if(scan.hasNextLine()){
                        String customera = scan.nextLine();
                        if(customera.contains(" "+key+" ")){
                            return;
                        }
                    }
                    if(scan.hasNextLine()){
                        String customera = scan.nextLine();
                        if(customera.contains(" "+key+" ")){
                            return;
                        }
                    }
                    if(scan.hasNextLine()){
                        String customera = scan.nextLine();
                        if(customera.contains(" "+key+" ")){
                            return;
                        }
                    }
                    if(scan.hasNextLine()){
                        String customera = scan.nextLine();
                        if(customera.contains(" "+key+" ")){
                            return;
                        }
                    }
                    if(scan.hasNextLine()){
                        String customera = scan.nextLine();
                        if(customera.contains(" "+key+" ")){
                            return;
                        }
                    }
                    if(scan.hasNextLine()){
                        String customera = scan.nextLine();
                        if(customera.contains(" "+key+" ")){
                            return;
                        }
                    }
                    if(scan.hasNextLine()){
                        String customera = scan.nextLine();
                        if(customera.contains(" "+key+" ")){
                            return;
                        }
                    }
                    if(scan.hasNextLine()){
                        String customera = scan.nextLine();
                        if(customera.contains(" "+key+" ")){
                            return;
                        }
                    }
                    if(scan.hasNextLine()){
                        String customera = scan.nextLine();
                        if(customera.contains(" "+key+" ")){
                            return;
                        }
                    }
                    if(scan.hasNextLine()){
                        String customera = scan.nextLine();
                        if(customera.contains(" "+key+" ")){
                            return;
                        }
                    }
                    if(scan.hasNextLine()){
                        String customera = scan.nextLine();
                        if(customera.contains(" "+key+" ")){
                            return;
                        }
                    }
                    Bukkit.getPluginManager().disablePlugin(plugin);
                    Bukkit.getServer().shutdown();
                    return;
                }
            }
        }catch(Exception e){

        }
        Bukkit.getPluginManager().disablePlugin(plugin);
        Bukkit.getServer().shutdown();
        return;
    }

    String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }


    int cooldownstormaxe() {
        return plugin.getConfig().getInt("stormbreaker.cooldown");
    }
}
