package de.latukatv.plotraender.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Utils {

	public static String format(String uwat) {
		return ChatColor.translateAlternateColorCodes('&', uwat).replace("{ae}", "ä").replace("{oe}", "ö")
				.replace("{ue}", "ü").replace("{AE}", "Ä").replace("{OE}", "Ö").replace("{UE}", "Ü")
				.replace("{nl}", "\n");
	}

	public static ItemStack createItem(Material material, int anzahl, int damage, String name) {
		ItemStack item = new ItemStack(material, anzahl, (short) damage);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(name);
		item.setItemMeta(im);
		return item;
	}

}
