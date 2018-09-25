package de.latukatv.plotraender.handler;

import de.latukatv.plotraender.PlotRaender;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class CommandHandler implements CommandExecutor {

    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(commandSender instanceof Player) {
            Player player = (Player) commandSender;
            Inventory raenderInventory = null;
            for(String randInv : PlotRaender.getInstance().getInventories().keySet()) {
                if(player.hasPermission("plotRaender.use." + randInv)) {
                    raenderInventory = PlotRaender.getInstance().getInventories().get(randInv);
                }
            }
            if(raenderInventory == null) {
                player.sendMessage("§cDu hast nicht genug Rechte dazu");
                return true;
            } else {
                player.openInventory(raenderInventory);
                player.playSound(player.getLocation(), Sound.valueOf(PlotRaender.getInstance().getVersion().contains("v1_8") ? "CHEST_OPEN" : "BLOCK_CHEST_OPEN"), 1, 1);
            }
        }
        return true;
    }
}
