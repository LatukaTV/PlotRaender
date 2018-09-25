package de.latukatv.plotraender.handler;

import com.intellectualcrafters.plot.api.PlotAPI;
import com.intellectualcrafters.plot.config.Configuration;
import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotBlock;
import de.latukatv.plotraender.PlotRaender;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class EventHandlers implements Listener {

    @EventHandler
    public void inventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        if (event.getInventory().getName().startsWith("§8Rand » §6")) {
            PlotAPI api = PlotRaender.getInstance().getPlotAPIHandler().getApi();
            Plot plot = api.getPlot(player.getLocation());
            event.setCancelled(true);
            if (plot != null) {
                if (plot.hasOwner() && plot.isOwner(player.getUniqueId())) {
                    if(event.getCurrentItem() != null && event.getCurrentItem().hasItemMeta()) {
                        ItemStack currentItem = event.getCurrentItem();
                        String id = currentItem.getType().getId() + ":" + currentItem.getDurability();
                        if (currentItem.getType() == Material.BARRIER)
                            id = "0:0";
                        if(currentItem.getType() == Material.getMaterial("REDSTONE_LAMP_OFF"))
                            id = "124:0";
                        PlotBlock[] plotBlocks = Configuration.BLOCKLIST.parseString(id);
                        updatePlotBorder(plot, plotBlocks);
                        player.sendMessage("§6Dein Rand wurde zu " + currentItem.getItemMeta().getDisplayName() + " §6geändert");
                        Sound.valueOf(PlotRaender.getInstance().getVersion().contains("v1_8") ? "LEVEL_UP" : "ENTITY_PLAYER_LEVELUP");
                    }
                    return;
                }
            }
            player.sendMessage("§cDu bist auf keinem Plot oder es gehört dir nicht");
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        String command = event.getMessage();

        List<String> blockedCommands = PlotRaender.getInstance().getCommandBlacklistConfig().get().getStringList("Blocked-Cmds");
        for (String uEingabe : blockedCommands) {
            if (command.toUpperCase().contains("/" + uEingabe.toUpperCase())) {
                event.getPlayer().sendMessage("§cDieser Command wurde vom Admin geblockt!");
                event.setCancelled(true);
                break;
            }
        }
    }

    private void updatePlotBorder(Plot plot, PlotBlock[] plotBlocks) {
        for (Plot connectedPlot : plot.getConnectedPlots()) {
            connectedPlot.setComponent("border", plotBlocks);
        }
    }

}
