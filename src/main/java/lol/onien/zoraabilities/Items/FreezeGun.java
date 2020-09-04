package lol.onien.zoraabilities.Items;

import lol.onien.zoraabilities.C;
import lol.onien.zoraabilities.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EnderPearl;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class FreezeGun implements Listener {
    Main plugin = Main.getPlugin(Main.class);
    private HashMap<Player, Long> cooldown = new HashMap<Player, Long>();

    @EventHandler
    public void onBuild(BlockBreakEvent e){
        if(e instanceof Player){
            if(e.getPlayer().getItemInHand().isSimilar(freezegun())){
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e){
        if(e.getPlayer().getItemInHand().isSimilar(freezegun())){
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
            e.setCancelled(true);
            cooldown.put(e.getPlayer(), (System.currentTimeMillis() + (cooldownfreezegun() * 1000)));
            e.getPlayer().launchProjectile(Snowball.class);
            Bukkit.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    cooldown.remove((e.getPlayer()));
                }
            }, (20 * (cooldownfreezegun())));
        }
    }

    @EventHandler
    public void snowballHit(EntityDamageByEntityEvent e) {
        if(e.getEntity() instanceof Player) {
            if(e.getDamager() instanceof Snowball) {
                Snowball snowball = (Snowball) e.getDamager();
                if(snowball.getShooter() instanceof Player) {
                    Player player = (Player) snowball.getShooter();
                    if(player.getItemInHand().isSimilar(freezegun())) {
                        if (plugin.checkRadius(e.getEntity().getLocation(), plugin.radius(), 0, 0)) {
                            player.sendMessage(C.color(plugin.spawn()));
                            e.setCancelled(true);
                            return;
                        }
                        if(player.getLocation().distance(e.getEntity().getLocation()) <= snowRadius()) {
                            ((Player) e.getEntity()).getPlayer().addPotionEffect(
                                    new PotionEffect(PotionEffectType.SLOW, (time() * 20), slownesslevel() - 1));
                            player.sendMessage(C.color(plugin.getConfig().getString("freezegun.shooter-hit-player").replaceAll("%player%", ((Player) e.getEntity()).getDisplayName())));
                            ((Player) e.getEntity()).getPlayer().sendMessage(C.color(plugin.getConfig().getString("freezegun.victim-got-hit").replaceAll("%player%", player.getDisplayName())));
                        } else {
                            player.sendMessage(C.color(plugin.getConfig().getString("freezegun.too-far")));
                            cooldown.remove(player);
                        }
                    }
                }
            }
        }
    }

    int snowRadius() {
        return plugin.getConfig().getInt("freezegun.radius");
    }

    ItemStack freezegun() {
        String name = plugin.getConfig().getString("freezegun.name");
        List<String> lore = plugin.getConfig().getStringList("freezegun.lore");
        ItemStack sb = createItem(Material.DIAMOND_HOE, name, lore);
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


    int cooldownfreezegun() {
        return plugin.getConfig().getInt("freezegun.cooldown");
    }

    int time() {
        return plugin.getConfig().getInt("freezegun.effect-time");
    }

    int slownesslevel() {
        return plugin.getConfig().getInt("freezegun.effect-amplifier");
    }
}
