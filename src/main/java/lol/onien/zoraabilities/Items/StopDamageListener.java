package lol.onien.zoraabilities.Items;

import lol.onien.zoraabilities.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class StopDamageListener implements Listener{
    private Main plugin = Main.getPlugin(Main.class);

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDurLoss(PlayerItemDamageEvent e) {
        Player player = e.getPlayer();
        if(e.getItem().isSimilar(web())||e.getItem().isSimilar(grapplinggun())
                ||e.getItem().isSimilar(snowball())||e.getItem().isSimilar(egg())
                ||e.getItem().isSimilar(freezegun())
                ||e.getItem().isSimilar(snowball())||e.getItem().isSimilar(pumpkinreaper())
                ||e.getItem().isSimilar(snowball())||e.getItem().isSimilar(cooldownbow())
                ||e.getItem().isSimilar(stormaxe())||e.getItem().isSimilar(rocket())
                ||e.getItem().isSimilar(bedbomb())){
//            e.setDamage(0);
            e.setCancelled(true);
        }
    }
    ItemStack web() {
        String name = plugin.getConfig().getString("sticky-web.name");
        List<String> lore = plugin.getConfig().getStringList("sticky-web.lore");
        ItemStack sb = createItem(Material.WEB, name, lore);
        return sb;
    }

    ItemStack freezegun() {
        String name = plugin.getConfig().getString("freezegun.name");
        List<String> lore = plugin.getConfig().getStringList("freezegun.lore");
        ItemStack sb = createItem(Material.DIAMOND_HOE, name, lore);
        return sb;
    }

    ItemStack pumpkinreaper() {
        String name = plugin.getConfig().getString("pumpkinreaper.name");
        List<String> lore = plugin.getConfig().getStringList("pumpkinreaper.lore");
        ItemStack sb = createItem(Material.IRON_SWORD, name, lore);
        return sb;
    }

    ItemStack cooldownbow() {
        String name = plugin.getConfig().getString("cooldownbow.name");
        List<String> lore = plugin.getConfig().getStringList("cooldownbow.lore");
        ItemStack sb = createItem(Material.BOW, name, lore);
        return sb;
    }

    ItemStack grapplinggun() {
        String name = plugin.getConfig().getString("grapplinghook.name");
        List<String> lore = plugin.getConfig().getStringList("grapplinghook.lore");
        ItemStack sb = createItem(Material.FISHING_ROD, name, lore);
        return sb;
    }

    ItemStack snowball() {
        String name = plugin.getConfig().getString("switcheroo.name");
        List<String> lore = plugin.getConfig().getStringList("switcheroo.lore");
        ItemStack sb = createItem(Material.SNOW_BALL, name, lore);
        return sb;
    }

    ItemStack egg() {
        String name = plugin.getConfig().getString("rotten-egg.name");
        List<String> lore = plugin.getConfig().getStringList("rotten-egg.lore");
        ItemStack sb = createItem(Material.EGG, name, lore);
        return sb;
    }

    ItemStack stormaxe() {
        String name = plugin.getConfig().getString("stormbreaker.name");
        List<String> lore = plugin.getConfig().getStringList("stormbreaker.lore");
        ItemStack sb = createItem(Material.DIAMOND_AXE, name, lore);
        return sb;
    }

    ItemStack rocket() {
        String name = plugin.getConfig().getString("rocket.name");
        List<String> lore = plugin.getConfig().getStringList("rocket.lore");
        ItemStack sb = createItem(Material.FIREWORK, name, lore);
        return sb;
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

    String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }
}
