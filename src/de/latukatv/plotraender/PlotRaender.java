package de.latukatv.plotraender;

import java.util.HashMap;

import de.latukatv.plotraender.handler.CommandHandler;
import de.latukatv.plotraender.handler.EventHandlers;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import de.latukatv.plotraender.utils.Configs;
import de.latukatv.plotraender.utils.PlotAPIHandler;
import de.latukatv.plotraender.utils.Utils;

public class PlotRaender extends JavaPlugin {

    private static PlotRaender instance;

    private String packet = Bukkit.getServer().getClass().getPackage().getName();
    private String version = packet.substring(packet.lastIndexOf('.') + 1);

    private PlotAPIHandler plotAPIHandler;

    private HashMap<String, Inventory> inventories = new HashMap<>();

    private Configs raenderConfig;
    private Configs commandBlacklistConfig;

    public void onEnable() {
        instance = this;

        plotAPIHandler = new PlotAPIHandler();

        raenderConfig = new Configs(instance, "raender.yml", true);
        commandBlacklistConfig = new Configs(instance, "commandblacklist.yml", true);

        getCommand("pr").setExecutor(new CommandHandler());

        getServer().getPluginManager().registerEvents(new EventHandlers(), instance);

        for (String key : raenderConfig.get().getConfigurationSection("").getKeys(false)) {
            Inventory inv = Bukkit.createInventory(null, 9 * 6, "§8Rand » §6" + key);
            System.out.println(key + " includes: " + (raenderConfig.get().isSet(key + ".includes") ? raenderConfig.get().getStringList(key + ".includes") : "Not Set"));
            if (raenderConfig.get().isSet(key + ".includes")) {
                for (String include : raenderConfig.get().getStringList(key + ".includes")) {
                    if (!raenderConfig.get().contains(include)) {
                        System.out.println(include + " wird übersprungen da es nicht exestiert");
                        continue;
                    }
                    for (String itemKey : raenderConfig.get().getConfigurationSection(include + ".raender").getKeys(false)) {
                        String materialString = raenderConfig.get().getString(include + ".raender." + itemKey + ".material");
                        if (materialString != null) {
//                            System.out.println(key + " " + materialString);
                            Material material = Material.getMaterial(materialString);
                            if (material != null) {
//                                System.out.println(key + " material not null " + material);
                                inv.addItem(Utils.createItem(material, 1, raenderConfig.get().getInt(include + ".raender." + itemKey + ".damage"), Utils.format(itemKey)));
                            }
                        }
                    }
                }
            }
            for (String itemKey : raenderConfig.get().getConfigurationSection(key + ".raender").getKeys(false)) {
                String materialString = raenderConfig.get().getString(key + ".raender." + itemKey + ".material");
                if (materialString != null) {
//                    System.out.println(key + " " + materialString);
                    Material material = Material.getMaterial(materialString);
                    if (material != null) {
//                        System.out.println(key + " material not null " + material);
                        inv.addItem(Utils.createItem(material, 1, raenderConfig.get().getInt(key + ".raender." + itemKey + ".damage"), Utils.format(itemKey)));
                    }
                }
            }
            inventories.put(key, inv);
        }
    }

    public void onDisable() {
    }

    public static PlotRaender getInstance() {
        return instance;
    }

    public String getVersion() {
        return version;
    }

    public HashMap<String, Inventory> getInventories() {
        return inventories;
    }

    public PlotAPIHandler getPlotAPIHandler() {
        return plotAPIHandler;
    }

    public Configs getRaenderConfig() {
        return raenderConfig;
    }

    public Configs getCommandBlacklistConfig() {
        return commandBlacklistConfig;
    }
}
