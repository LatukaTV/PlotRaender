package de.latukatv.plotraender.utils;

import com.github.intellectualsites.plotsquared.api.PlotAPI;
import org.bukkit.Bukkit;

import com.github.intellectualsites.plotsquared.api.PlotAPI;

import de.latukatv.plotraender.PlotRaender;

public class PlotAPIHandler {

    private PlotAPI api;

    public PlotAPIHandler() {
        if (Bukkit.getPluginManager().getPlugin("PlotSquared") == null) {
            Bukkit.getLogger().warning("Konnte PlotSquared nicht auf deinem Server finden");
            Bukkit.getPluginManager().disablePlugin(PlotRaender.getInstance());
            return;
        }

        api = new PlotAPI();
    }

    public PlotAPI getApi() {
        return api;
    }

}
