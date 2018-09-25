package de.latukatv.plotraender.utils;

import org.bukkit.Bukkit;

import com.intellectualcrafters.plot.api.PlotAPI;

import de.latukatv.plotraender.PlotRaender;

public class PlotAPIHandler {
	
	private PlotAPI api;
	
	public PlotAPIHandler() {
		if(Bukkit.getPluginManager().getPlugin("PlotSquared") == null) {
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
