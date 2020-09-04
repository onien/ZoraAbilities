package lol.onien.zoraabilities.Items;

import lol.onien.zoraabilities.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GrapplingGun implements Listener {

    Main plugin = Main.getPlugin(Main.class);
    private HashMap<Player, Long> cooldown = new HashMap<Player, Long>();
    private Player player;
    private Location bobberLocation;

    @EventHandler
    public void onGrappleDamage(EntityDamageEvent e){
        if (e.getEntityType() != EntityType.PLAYER) {
            return;
        }
        Player p = (Player) e.getEntity();
        if(e.getCause().equals(EntityDamageEvent.DamageCause.FALL)){
            if(p.getItemInHand().isSimilar(grapplinggun())){
                e.setDamage(plugin.getConfig().getDouble("grapplinghook.damage"));
            }
        }
    }

    @EventHandler
    public void onGrapple(PlayerFishEvent e){
        if(e.getPlayer().getItemInHand().isSimilar(grapplinggun())){
            Player player = e.getPlayer();
            if(cooldown.containsKey(e.getPlayer())) {
                double time = (cooldown.get(e.getPlayer()) - System.currentTimeMillis()) / 1000.0;
                e.getPlayer().sendMessage(color(plugin.replaceTime(time)));
                e.setCancelled(true);
                return;
            }
            if (e.getState() == PlayerFishEvent.State.FISHING) {
                return;
            }
            final Entity bobber = (Entity)e.getHook();
            if (bobber == null) {
                return;
            }
            if (e.getCaught() != null) {
                e.getHook().remove();
                return;
            }
            final Location bobberLocation = bobber.getLocation();
            if (player.getFireTicks() <= 0 && (this.isValidForGrapple(bobberLocation.getBlock()) || this.isValidForGrapple(bobberLocation.getBlock().getRelative(BlockFace.UP)) || this.isValidForGrapple(bobberLocation.getBlock().getRelative(BlockFace.DOWN)))) {
                final Location playerLoc = player.getLocation();
                cooldown.put(e.getPlayer(), (System.currentTimeMillis() + (cooldowngrapplegun() * 1000)));
                player.getWorld().playSound(player.getPlayer().getLocation(), Sound.ZOMBIE_INFECT, 0.5f, 1.8f);
                e.getPlayer().setVelocity(e.getPlayer().getEyeLocation().getDirection().multiply(plugin.getConfig().getDouble("grapplinghook.distance")));
                e.setExpToDrop(0);
                e.getHook().remove();
                if (e.getCaught() != null && !(e.getCaught() instanceof LivingEntity)) {
                    e.getCaught().remove();
                }
                Bukkit.getServer().getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        cooldown.remove(e.getPlayer());
                    }
                }, (20 * (cooldowngrapplegun())));
            }
        }
    }

    ItemStack grapplinggun() {
        String name = plugin.getConfig().getString("grapplinghook.name");
        List<String> lore = plugin.getConfig().getStringList("grapplinghook.lore");
        ItemStack sb = createItem(Material.FISHING_ROD, name, lore);
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

    int cooldowngrapplegun() {
        return plugin.getConfig().getInt("grapplinghook.cooldown");
    }

    private boolean isValidForGrapple(final Block block) {
        return block.getTypeId() != 0 && block.getType() != Material.STATIONARY_WATER && block.getType() != Material.WATER;
    }

    public Location getBobberLocation() {
        return this.bobberLocation;
    }

    public void setBobberLocation(final Location bobberLocation) {
        this.bobberLocation = bobberLocation;
    }

    private Vector getVectorForPoints(final Location l1, final Location l2) {
        final double g = -0.08;
        final double t;
        final double d = t = l2.distance(l1);
        final double vX = (1.0 + 0.07 * t) * (l2.getX() - l1.getX()) / t;
        final double vY = (1.0 + 0.03 * t) * (l2.getY() - l1.getY()) / t - 0.5 * g * t;
        final double vZ = (1.0 + 0.07 * t) * (l2.getZ() - l1.getZ()) / t;
        return new Vector(vX, vY, vZ);
    }

}
