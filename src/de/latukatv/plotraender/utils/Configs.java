package de.latukatv.plotraender.utils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.Channels;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import com.google.common.io.Files;

import de.latukatv.plotraender.PlotRaender;

public class Configs {

    private FileConfiguration config = null;
    private File file = null;

    private String filename;
    private String subfolder;

    private boolean inPlugin;

    private Plugin plugin;

    public Configs(Plugin plugin, String filename, boolean internalFile, String... subfolder) {
        this.plugin = plugin;
        this.filename = filename;
        this.subfolder = subfolder.length > 0 ? "plugins/" + plugin.getName() + "/" + subfolder[0] : "plugins/" + plugin.getName();
        if (inPlugin = internalFile) {
            saveInternalFile(filename, this.subfolder);
        }
        get().options().copyDefaults(true);
    }

    public FileConfiguration get() {
        if (config == null) {
            reload();
        }
        return config;
    }

    public void save() {
        try {
            config.save(file);
        } catch (Exception e) {
            plugin.getLogger().log(Level.WARNING, "Failed to save File " + file.getName(), e);
        }
    }

    public void reload() {
        file = new File(subfolder, filename);
        config = YamlConfiguration.loadConfiguration(file);
        if (inPlugin) {
            InputStream dataStream = plugin.getResource(filename);
            if (dataStream != null) {
                config.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(dataStream)));
            }
        }
    }

    public File getFile() {
        if (file == null) {
            reload();
        }
        return file;
    }
    
    public static File saveInternalFile(String filename, String... sub) {
        try {
            String currentDir = System.getProperty("user.dir");
            String subfolder = sub.length > 0 ? currentDir + "/" + sub[0] : currentDir;
            File outFile = new File(subfolder, filename);
            Files.createParentDirs(outFile);
            if (!outFile.exists()) {
                InputStream fileInputStream = PlotRaender.getInstance().getResource(filename);
                FileOutputStream fileOutputStream = new FileOutputStream(outFile);
                fileOutputStream.getChannel().transferFrom(Channels.newChannel(fileInputStream), 0, Integer.MAX_VALUE);
                fileOutputStream.close();
                fileInputStream.close();
            }
            return outFile;
        } catch (IOException e) {
            Bukkit.getLogger().log(Level.WARNING, "Failed to create File " + filename, e);
        }
        return null;
    }

}
