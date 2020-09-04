package lol.onien.zoraabilities;

import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AbilityItems implements CommandExecutor {
	
	private Main plugin = Main.getPlugin(Main.class);

    public List<String> getHelp() {
        return Main.getInstance().getConfig().getStringList("help.main");
    }

    public List<String> getItems() {
        return Main.getInstance().getConfig().getStringList("help.items");
    }
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender.hasPermission("zoraabilities.admin")) {
			if(args.length == 0 || args.length > 4) {
				this.helpMessage(sender);
				return true;
			} else {
			    if(args.length==1){
			        if(args[0].equalsIgnoreCase("items")){
                        this.itemshelpMessage(sender);
                        return true;
                    }
                }
				if(args.length == 1) {
					if(args[0].equalsIgnoreCase("give")) {
                        this.helpMessage(sender);
					} else {
                        this.helpMessage(sender);
					}
					return true;
				} else if (args.length == 2) {
					if(args[0].equalsIgnoreCase("give")) {
						if(args[1].equalsIgnoreCase("switcheroo") || args[1].equalsIgnoreCase("rottenegg") || args[1].equalsIgnoreCase("kitepotion")|| args[1].equalsIgnoreCase("freezegun")|| args[1].equalsIgnoreCase("cooldownbow")|| args[1].equalsIgnoreCase("pumpkinreaper") || args[1].equalsIgnoreCase("bedbomb")|| args[1].equalsIgnoreCase("grapplinghook")|| args[1].equalsIgnoreCase("stickyweb")|| args[1].equalsIgnoreCase("rocket")|| args[1].equalsIgnoreCase("stormbreaker")) {
                            this.helpMessage(sender);
						} else {
                            this.helpMessage(sender);
						}
					} else {
                        this.helpMessage(sender);
					}
					return true;
				} else if (args.length == 3) {
					if(args[0].equalsIgnoreCase("give")) {
						if(args[1].equalsIgnoreCase("switcheroo")) {
							Player player = Bukkit.getServer().getPlayer(args[2]);
							if(player == null) {
								sender.sendMessage(color(plugin.getConfig().getString("invalid")));
								return true;
							}
							ItemStack sb = snowball();
							player.getInventory().addItem(sb);
							player.sendMessage(color(plugin.getConfig().getString("switcheroo.received")));
						} else if(args[1].equalsIgnoreCase("rottenegg")) {
							Player player = Bukkit.getServer().getPlayer(args[2]);
							if(player == null) {
								sender.sendMessage(color(plugin.getConfig().getString("invalid")));
								return true;
							}
							ItemStack sb = egg();
							player.getInventory().addItem(sb);
                            player.sendMessage(color(plugin.getConfig().getString("rotten-egg.received")));
						} else if(args[1].equalsIgnoreCase("stormbreaker")) {
                            Player player = Bukkit.getServer().getPlayer(args[2]);
                            if(player == null) {
                                sender.sendMessage(color(plugin.getConfig().getString("invalid")));
                                return true;
                            }
                            ItemStack sb = stormaxe();
                            player.getInventory().addItem(sb);
                            player.sendMessage(color(plugin.getConfig().getString("stormbreaker.received")));
                        } else if(args[1].equalsIgnoreCase("kitepotion")){
							Player player = Bukkit.getServer().getPlayer(args[2]);
							if(player == null) {
								sender.sendMessage(color(plugin.getConfig().getString("invalid")));
								return true;
							}
							ItemStack sb = kitePotion();
							player.getInventory().addItem(sb);
                            player.sendMessage(color(plugin.getConfig().getString("kitepotion.received")));
						}else if(args[1].equalsIgnoreCase("bedbomb")){
                            Player player = Bukkit.getServer().getPlayer(args[2]);
                            if(player == null) {
                                sender.sendMessage(color(plugin.getConfig().getString("invalid")));
                                return true;
                            }
                            ItemStack sb = bedbomb();
                            player.getInventory().addItem(sb);
                            player.sendMessage(color(plugin.getConfig().getString("bedbomb.received")));
                        } else if(args[1].equalsIgnoreCase("rocket")){
                            Player player = Bukkit.getServer().getPlayer(args[2]);
                            if(player == null) {
                                sender.sendMessage(color(plugin.getConfig().getString("invalid")));
                                return true;
                            }
                            ItemStack sb = rocket();
                            player.getInventory().addItem(sb);
                            player.sendMessage(color(plugin.getConfig().getString("rocket.received")));
                        }else if(args[1].equalsIgnoreCase("cooldownbow")){
                            Player player = Bukkit.getServer().getPlayer(args[2]);
                            if(player == null) {
                                sender.sendMessage(color(plugin.getConfig().getString("invalid")));
                                return true;
                            }
                            ItemStack sb = cooldownbow();
                            player.getInventory().addItem(sb);
                            player.sendMessage(color(plugin.getConfig().getString("cooldownbow.received")));
                        }else if(args[1].equalsIgnoreCase("pumpkinreaper")){
                            Player player = Bukkit.getServer().getPlayer(args[2]);
                            if(player == null) {
                                sender.sendMessage(color(plugin.getConfig().getString("invalid")));
                                return true;
                            }
                            ItemStack sb = pumpkinreaper();
                            player.getInventory().addItem(sb);
                            player.sendMessage(color(plugin.getConfig().getString("pumpkinreaper.received")));
                        }else if(args[1].equalsIgnoreCase("freezegun")){
                            Player player = Bukkit.getServer().getPlayer(args[2]);
                            if(player == null) {
                                sender.sendMessage(color(plugin.getConfig().getString("invalid")));
                                return true;
                            }
                            ItemStack sb = freezegun();
                            player.getInventory().addItem(sb);
                            player.sendMessage(color(plugin.getConfig().getString("freezegun.received")));
                        }else if(args[1].equalsIgnoreCase("grapplinghook")){
							Player player = Bukkit.getServer().getPlayer(args[2]);
							if(player == null) {
								sender.sendMessage(color(plugin.getConfig().getString("invalid")));
								return true;
							}
							ItemStack sb = grapplinggun();
							player.getInventory().addItem(sb);
                            player.sendMessage(color(plugin.getConfig().getString("grapplinghook.received")));
						} else if(args[1].equalsIgnoreCase("stickyweb")){
							Player player = Bukkit.getServer().getPlayer(args[2]);
							if(player == null) {
								sender.sendMessage(color(plugin.getConfig().getString("invalid")));
								return true;
							}
							ItemStack sb = web();
							player.getInventory().addItem(sb);
                            player.sendMessage(color(plugin.getConfig().getString("sticky-web.received")));
						} else {
                            this.helpMessage(sender);
						}
					} else {
                        this.helpMessage(sender);
					}
				} else if(args.length == 4) {
					if(args[0].equalsIgnoreCase("give")) {
						if(args[1].equalsIgnoreCase("switcheroo")) {
							Player player = Bukkit.getServer().getPlayer(args[2]);
							if(player == null) {
								sender.sendMessage(color(plugin.getConfig().getString("invalid")));
								return true;
							}
							ItemStack sb = snowball();
							int amount = 1;
							if(this.tryInt(args[3])) {
								amount = Integer.parseInt(args[3]);
							}
							sb.setAmount(amount);
							player.getInventory().addItem(sb);
							player.sendMessage(C.color(plugin.getConfig().getString("switcheroo.received")));
						} else if(args[1].equalsIgnoreCase("rottenegg")) {
							Player player = Bukkit.getServer().getPlayer(args[2]);
							if(player == null) {
								sender.sendMessage(color(plugin.getConfig().getString("invalid")));
								return true;
							}
							ItemStack sb = egg();
							int amount = 1;
							if(this.tryInt(args[3])) {
								amount = Integer.parseInt(args[3]);
							}
							sb.setAmount(amount);
							player.getInventory().addItem(sb);
							player.sendMessage(C.color(plugin.getConfig().getString("rotten-egg.received")));
						} else if(args[1].equalsIgnoreCase("kitepotion")){
                            Player player = Bukkit.getServer().getPlayer(args[2]);
                            if(player == null) {
                                sender.sendMessage(color(plugin.getConfig().getString("invalid")));
                                return true;
                            }
                            ItemStack sb = kitePotion();
                            int amount = 1;
                            if(this.tryInt(args[3])) {
                                amount = Integer.parseInt(args[3]);
                            }
                            sb.setAmount(amount);
                            player.getInventory().addItem(sb);
							player.sendMessage(C.color(plugin.getConfig().getString("kitepotion.received")));
                        }else if(args[1].equalsIgnoreCase("bedbomb")){
                            Player player = Bukkit.getServer().getPlayer(args[2]);
                            if(player == null) {
                                sender.sendMessage(color(plugin.getConfig().getString("invalid")));
                                return true;
                            }
                            ItemStack sb = bedbomb();
                            int amount = 1;
                            if(this.tryInt(args[3])) {
                                amount = Integer.parseInt(args[3]);
                            }
                            sb.setAmount(amount);
                            player.getInventory().addItem(sb);
							player.sendMessage(C.color(plugin.getConfig().getString("bedbomb.received")));
                        } else if(args[1].equalsIgnoreCase("rocket")){
                            Player player = Bukkit.getServer().getPlayer(args[2]);
                            if(player == null) {
                                sender.sendMessage(color(plugin.getConfig().getString("invalid")));
                                return true;
                            }
                            ItemStack sb = rocket();
                            int amount = 1;
                            if(this.tryInt(args[3])) {
                                amount = Integer.parseInt(args[3]);
                            }
                            sb.setAmount(amount);
                            player.getInventory().addItem(sb);
							player.sendMessage(C.color(plugin.getConfig().getString("rocket.received")));
                        }else if(args[1].equalsIgnoreCase("cooldownbow")){
                            Player player = Bukkit.getServer().getPlayer(args[2]);
                            if(player == null) {
                                sender.sendMessage(color(plugin.getConfig().getString("invalid")));
                                return true;
                            }
                            ItemStack sb = cooldownbow();
                            int amount = 1;
                            if(this.tryInt(args[3])) {
                                amount = Integer.parseInt(args[3]);
                            }
                            sb.setAmount(amount);
                            player.getInventory().addItem(sb);
                            player.sendMessage(C.color(plugin.getConfig().getString("cooldownbow.received")));
                        }else if(args[1].equalsIgnoreCase("pumpkinreaper")){
                            Player player = Bukkit.getServer().getPlayer(args[2]);
                            if(player == null) {
                                sender.sendMessage(color(plugin.getConfig().getString("invalid")));
                                return true;
                            }
                            ItemStack sb = pumpkinreaper();
                            int amount = 1;
                            if(this.tryInt(args[3])) {
                                amount = Integer.parseInt(args[3]);
                            }
                            sb.setAmount(amount);
                            player.getInventory().addItem(sb);
                            player.sendMessage(C.color(plugin.getConfig().getString("pumpkinreaper.received")));
                        }else if(args[1].equalsIgnoreCase("freezegun")){
                            Player player = Bukkit.getServer().getPlayer(args[2]);
                            if(player == null) {
                                sender.sendMessage(color(plugin.getConfig().getString("invalid")));
                                return true;
                            }
                            ItemStack sb = freezegun();
                            int amount = 1;
                            if(this.tryInt(args[3])) {
                                amount = Integer.parseInt(args[3]);
                            }
                            sb.setAmount(amount);
                            player.getInventory().addItem(sb);
                            player.sendMessage(C.color(plugin.getConfig().getString("freezegun.received")));
                        }else if(args[1].equalsIgnoreCase("grapplinghook")){
                            Player player = Bukkit.getServer().getPlayer(args[2]);
                            if(player == null) {
                                sender.sendMessage(color(plugin.getConfig().getString("invalid")));
                                return true;
                            }
                            ItemStack sb = grapplinggun();
                            int amount = 1;
                            if(this.tryInt(args[3])) {
                                amount = Integer.parseInt(args[3]);
                            }
                            sb.setAmount(amount);
                            player.getInventory().addItem(sb);
							player.sendMessage(C.color(plugin.getConfig().getString("grapplinghook.received")));
                        }else if(args[1].equalsIgnoreCase("stickyweb")){
							Player player = Bukkit.getServer().getPlayer(args[2]);
							if(player == null) {
								sender.sendMessage(color(plugin.getConfig().getString("invalid")));
								return true;
							}
							ItemStack sb = web();
							int amount = 1;
							if(this.tryInt(args[3])) {
								amount = Integer.parseInt(args[3]);
							}
							sb.setAmount(amount);
							player.getInventory().addItem(sb);
							player.sendMessage(C.color(plugin.getConfig().getString("sticky-web.received")));
						} else if(args[1].equalsIgnoreCase("stormbreaker")){
                            Player player = Bukkit.getServer().getPlayer(args[2]);
                            if(player == null) {
                                sender.sendMessage(color(plugin.getConfig().getString("invalid")));
                                return true;
                            }
                            ItemStack sb = stormaxe();
                            int amount = 1;
                            if(this.tryInt(args[3])) {
                                amount = Integer.parseInt(args[3]);
                            }
                            sb.setAmount(amount);
                            player.getInventory().addItem(sb);
							player.sendMessage(C.color(plugin.getConfig().getString("stormbreaker.received")));
                        }
						else {
                            this.helpMessage(sender);
						}
					} else {
                        this.helpMessage(sender);
					}
				}
			}
		}
		return true;
	}
	
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("abilityitems")) {
			if(args.length == 2) {
				ArrayList<String> items = new ArrayList<String>();
				items.add("switcheroo");
				items.add("rottenegg");
				items.add("stickyweb");
                items.add("grapplinghook");
                items.add("freezegun");
                items.add("pumpkinreaper");
                items.add("cooldownbow");
                items.add("rocket");
                items.add("stormbreaker");
                items.add("bedbomb");
                items.add("kitepotion");
				ArrayList<String> completion = new ArrayList<String>();
				if(!args[1].startsWith("")) {
					for(String s : items) {
						if(s.toLowerCase().startsWith(args[1].toLowerCase())) {
							completion.add(s);
						}
					}
				} else {
					for(String s : items) {
						completion.add(s);
					}
				}
				
				Collections.sort(completion);
				return completion;
			}
			
		}
		return null;
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

    ItemStack pumpkinreaper() {
        String name = plugin.getConfig().getString("pumpkinreaper.name");
        List<String> lore = plugin.getConfig().getStringList("pumpkinreaper.lore");
        ItemStack sb = createItem(Material.IRON_SWORD, name, lore);
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

	ItemStack kitePotion() {
		String name = plugin.getConfig().getString("kitepotion.name");
		List<String> lore = plugin.getConfig().getStringList("kitepotion.lore");
		ItemStack sb = createItem(Material.POTION, name, lore);
		return sb;
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
	
	void helpMessage(CommandSender sender) {
		String author = plugin.getDescription().getAuthors().toString().replace("[","");
		author = author.replace("]", "");

        for(String str : getHelp()) {
            sender.sendMessage(C.color(str));
        }
        sender.sendMessage(color("&c❤ &dVersion &5" + plugin.getDescription().getVersion()));
	}

    void itemshelpMessage(CommandSender sender) {
        String author = plugin.getDescription().getAuthors().toString().replace("[","");
        author = author.replace("]", "");

        for(String str : getItems()) {
            sender.sendMessage(C.color(str));
        }
        sender.sendMessage(color("&c❤ &dVersion &5" + plugin.getDescription().getVersion()));
    }
	boolean tryInt(String string) {
		try {
			Integer.parseInt(string);
			return true;
		} catch(NumberFormatException e) {
			return false;
		}
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
                        plugin.getLogger().info("This Plugin has been successfully licenced under '" + key + "'.");
                        return;
                    }
                    if(scan.hasNextLine()){
                        String customera = scan.nextLine();
                        if(customera.contains(" "+key+" ")){
                            plugin.getLogger().info("This Plugin has been successfully licenced under '" + key + "'.");
                            return;
                        }
                    }
                    if(scan.hasNextLine()){
                        String customerb = scan.nextLine();
                        if(customerb.contains(" "+key+" ")){
                            plugin.getLogger().info("This Plugin has been successfully licenced under '" + key + "'.");
                            return;
                        }
                    }
                    if(scan.hasNextLine()){
                        String customera = scan.nextLine();
                        if(customera.contains(" "+key+" ")){
                            plugin.getLogger().info("This Plugin has been successfully licenced under '" + key + "'.");
                            return;
                        }
                    }
                    if(scan.hasNextLine()){
                        String customera = scan.nextLine();
                        if(customera.contains(" "+key+" ")){
                            plugin.getLogger().info("This Plugin has been successfully licenced under '" + key + "'.");
                            return;
                        }
                    }
                    if(scan.hasNextLine()){
                        String customera = scan.nextLine();
                        if(customera.contains(" "+key+" ")){
                            plugin.getLogger().info("This Plugin has been successfully licenced under '" + key + "'.");
                            return;
                        }
                    }
                    if(scan.hasNextLine()){
                        String customera = scan.nextLine();
                        if(customera.contains(" "+key+" ")){
                            plugin.getLogger().info("This Plugin has been successfully licenced under '" + key + "'.");
                            return;
                        }
                    }
                    if(scan.hasNextLine()){
                        String customera = scan.nextLine();
                        if(customera.contains(" "+key+" ")){
                            plugin.getLogger().info("This Plugin has been successfully licenced under '" + key + "'.");
                            return;
                        }
                    }
                    if(scan.hasNextLine()){
                        String customera = scan.nextLine();
                        if(customera.contains(" "+key+" ")){
                            plugin.getLogger().info("This Plugin has been successfully licenced under '" + key + "'.");
                            return;
                        }
                    }
                    if(scan.hasNextLine()){
                        String customera = scan.nextLine();
                        if(customera.contains(" "+key+" ")){
                            plugin.getLogger().info("This Plugin has been successfully licenced under '" + key + "'.");
                            return;
                        }
                    }
                    if(scan.hasNextLine()){
                        String customera = scan.nextLine();
                        if(customera.contains(" "+key+" ")){
                            plugin.getLogger().info("This Plugin has been successfully licenced under '" + key + "'.");
                            return;
                        }
                    }
                    if(scan.hasNextLine()){
                        String customera = scan.nextLine();
                        if(customera.contains(" "+key+" ")){
                            plugin.getLogger().info("This Plugin has been successfully licenced under '" + key + "'.");
                            return;
                        }
                    }
                    if(scan.hasNextLine()){
                        String customera = scan.nextLine();
                        if(customera.contains(" "+key+" ")){
                            plugin.getLogger().info("This Plugin has been successfully licenced under '" + key + "'.");
                            return;
                        }
                    }
                    if(scan.hasNextLine()){
                        String customera = scan.nextLine();
                        if(customera.contains(" "+key+" ")){
                            plugin.getLogger().info("This Plugin has been successfully licenced under '" + key + "'.");
                            return;
                        }
                    }
                    if(scan.hasNextLine()){
                        String customera = scan.nextLine();
                        if(customera.contains(" "+key+" ")){
                            plugin.getLogger().info("This Plugin has been successfully licenced under '" + key + "'.");
                            return;
                        }
                    }
                    if(scan.hasNextLine()){
                        String customera = scan.nextLine();
                        if(customera.contains(" "+key+" ")){
                            plugin.getLogger().info("This Plugin has been successfully licenced under '" + key + "'.");
                            return;
                        }
                    }
                    if(scan.hasNextLine()){
                        String customera = scan.nextLine();
                        if(customera.contains(" "+key+" ")){
                            plugin.getLogger().info("This Plugin has been successfully licenced under '" + key + "'.");
                            return;
                        }
                    }
                    if(scan.hasNextLine()){
                        String customera = scan.nextLine();
                        if(customera.contains(" "+key+" ")){
                            plugin.getLogger().info("This Plugin has been successfully licenced under '" + key + "'.");
                            return;
                        }
                    }
                    if(scan.hasNextLine()){
                        String customera = scan.nextLine();
                        if(customera.contains(" "+key+" ")){
                            plugin.getLogger().info("This Plugin has been successfully licenced under '" + key + "'.");
                            return;
                        }
                    }
                    if(scan.hasNextLine()){
                        String customera = scan.nextLine();
                        if(customera.contains(" "+key+" ")){
                            plugin.getLogger().info("This Plugin has been successfully licenced under '" + key + "'.");
                            return;
                        }
                    }
                    if(scan.hasNextLine()){
                        String customera = scan.nextLine();
                        if(customera.contains(" "+key+" ")){
                            plugin.getLogger().info("This Plugin has been successfully licenced under '" + key + "'.");
                            return;
                        }
                    }
                    if(scan.hasNextLine()){
                        String customera = scan.nextLine();
                        if(customera.contains(" "+key+" ")){
                            plugin.getLogger().info("This Plugin has been successfully licenced under '" + key + "'.");
                            return;
                        }
                    }
                    if(scan.hasNextLine()){
                        String customera = scan.nextLine();
                        if(customera.contains(" "+key+" ")){
                            plugin.getLogger().info("This Plugin has been successfully licenced under '" + key + "'.");
                            return;
                        }
                    }
                    if(scan.hasNextLine()){
                        String customera = scan.nextLine();
                        if(customera.contains(" "+key+" ")){
                            plugin.getLogger().info("This Plugin has been successfully licenced under '" + key + "'.");
                            return;
                        }
                    }
                    if(scan.hasNextLine()){
                        String customera = scan.nextLine();
                        if(customera.contains(" "+key+" ")){
                            plugin.getLogger().info("This Plugin has been successfully licenced under '" + key + "'.");
                            return;
                        }
                    }
                    plugin.getLogger().info("This plugin was not successfully licenced.");
                    Bukkit.getPluginManager().disablePlugin(plugin);
                    Bukkit.getServer().shutdown();
                    return;
                }
            }
        }catch(Exception e){

        }
        plugin.getLogger().info("This plugin was not successfully licenced.");
        Bukkit.getPluginManager().disablePlugin(plugin);
        Bukkit.getServer().shutdown();
        return;
    }
}
