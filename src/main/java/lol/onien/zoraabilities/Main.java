package lol.onien.zoraabilities;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import lol.onien.zoraabilities.Items.*;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin {

	@Getter
	private AbilityItems islicense;
    private StormbreakerAxe islicensea;

    @Getter
    private static Main instance;
	public void onEnable() {
        instance = this;
        final PluginManager pm = Bukkit.getPluginManager();
		saveDefaultConfig();
		String key = this.getConfig().getString("license-key");
		if(key.contains("|")||key.contains("-")||key.contains("HWID")||key.contains("Username")||
				key.equalsIgnoreCase("|")||key.equalsIgnoreCase("-")
				||key.equalsIgnoreCase("Username")||key.equalsIgnoreCase("HWID")||
				key.contains(" ")||key.equalsIgnoreCase(" ")||key.equalsIgnoreCase("")||key == null){
			this.getLogger().info("This plugin was not successfully licenced.");
			Bukkit.getPluginManager().disablePlugin(this);
			Bukkit.getServer().shutdown();
			return;
		}
        if (!getDescription().getName().equals("ZoraAbilities")) {
            try {
                Bukkit.getPluginManager().disablePlugin(this);
            } catch (Exception e) {
            }
            return;
        }
        islicense = new AbilityItems();
        islicense.isLicence(this);
        pm.registerEvents((Listener)new RottenEgg(), (Plugin)this);
        pm.registerEvents((Listener)new StormbreakerAxe(), (Plugin)this);
        pm.registerEvents((Listener)new CooldownBow(), (Plugin)this);
		pm.registerEvents((Listener)new BedBombing(), (Plugin)this);
		pm.registerEvents((Listener)new Rocket(), (Plugin)this);
		pm.registerEvents((Listener)new StopDamageListener(), (Plugin)this);
        pm.registerEvents((Listener)new GrapplingGun(), (Plugin)this);
		pm.registerEvents((Listener)new StickyWeb(), (Plugin)this);
		pm.registerEvents((Listener)new Switcheroo(), (Plugin)this);
		pm.registerEvents((Listener)new FreezeGun(), (Plugin)this);
        pm.registerEvents((Listener)new PumpkinReaper(), (Plugin)this);
		pm.registerEvents((Listener)new KitePotion(), (Plugin)this);
        islicensea = new StormbreakerAxe();
        islicensea.isLicence(this);
		getServer().getPluginCommand("abilityitems").setExecutor(new AbilityItems());
	}
	
	public int radius() {
		return getConfig().getInt("radius");
	}
	
	int snowRadius() {
		return getConfig().getInt("switcheroo.radius");
	}
	
	int eggRadius() {
		return getConfig().getInt("rotten-egg.radius");
	}
	
	public boolean checkRadius(Location loc, int radius, int x, int z) {
		radius = radius + 1;
		int cornerx = x + radius;
		int cornerz = z + radius;
		int cornerx1 = x - radius;
		int cornerz1 = z - radius;		
		if(loc.getBlockX() < cornerx && loc.getBlockX() > cornerx1) {
			if(loc.getBlockZ() < cornerz && loc.getBlockZ() > cornerz1) {
				return true;
			}
		}
		return false;
	}
	
	public String replaceRadius(String string) {
		if(string.contains("%eggradius%")) {
			string = string.replace("%eggradius%", "" + eggRadius());
		} else if(string.contains("%snowradius%")) {
			string = string.replace("%snowradius%", "" + snowRadius());
		}
		return color(string);
	}
	
	public String replaceTime(double time) {
		String string = getConfig().getString("cooldown");
		if(string.contains("[time]")) {
			DecimalFormat df = new DecimalFormat("#.#");      
			time = Double.valueOf(df.format(time));
			string = string.replace("[time]", "" + time);
		}
		return string;
	}
	
	public String replacePlayer(String string, String name) {
		if(string.contains("%player%")) {
			string = string.replace("%player%", name);
		}
		return string;
	}
	
	public String spawn() {
		return color(getConfig().getString("within-radius"));
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
