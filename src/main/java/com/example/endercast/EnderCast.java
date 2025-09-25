package com.example.endercast;

import java.io.File;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bstats.bukkit.Metrics;

import org.jetbrains.annotations.NotNull;
import net.kyori.adventure.text.Component;

public class EnderCast extends JavaPlugin implements Listener {
    private FileConfiguration messagesConfig;

    private final int pluginID = 27358; // bstats plugin ID

    @Override
    public void onEnable() {
        saveDefaultMessagesConfig();

        messagesConfig = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "messages.yml"));

        Metrics metrics = new Metrics(this, pluginID); // Start metrics for bstats


        setupCommands();
    }













    // COMMAND HANDLING
    private void setupCommands() {
        this.getCommand("endercast").setExecutor(this);
        this.getCommand("broadcast").setExecutor(this);
        this.getCommand("ec").setExecutor(this);
        this.getCommand("bc").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        try {
            String msg = messagesConfig.getString("template").replace("{message}", args[0]);
            Component componentMsg = Component.text(msg);
            Bukkit.getServer().broadcast(componentMsg);
        } catch (Exception e) {
            getLogger().severe(e.getMessage());
            return false;
        }
        return true;
    }













    // CONFIG HANDLING
    public void saveDefaultMessagesConfig() {
        if (!new File(getDataFolder(), "messages.yml").exists()) {
            this.saveResource("messages.yml", false);
        }
    }

}
