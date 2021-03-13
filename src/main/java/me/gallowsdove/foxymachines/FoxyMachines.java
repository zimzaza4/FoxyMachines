package me.gallowsdove.foxymachines;

import java.io.File;
import java.io.IOException;

import javax.annotation.Nonnull;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import io.github.mooy1.infinitylib.command.CommandManager;
import io.github.mooy1.infinitylib.core.PluginUtils;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import lombok.SneakyThrows;
import me.gallowsdove.foxymachines.commands.QuestCommand;
import me.gallowsdove.foxymachines.commands.SacrificialAltarCommand;
import me.gallowsdove.foxymachines.implementation.machines.ForcefieldDome;
import me.gallowsdove.foxymachines.listeners.ArmorListener;
import me.gallowsdove.foxymachines.listeners.BerryBushListener;
import me.gallowsdove.foxymachines.listeners.BoostedRailListener;
import me.gallowsdove.foxymachines.listeners.ChunkLoaderListener;
import me.gallowsdove.foxymachines.listeners.ForcefieldListener;
import me.gallowsdove.foxymachines.listeners.PoseidonsFishingRodListener;
import me.gallowsdove.foxymachines.listeners.RemoteControllerListener;
import me.gallowsdove.foxymachines.listeners.SacrificialAltarListener;
import me.gallowsdove.foxymachines.listeners.SwordListener;
import me.gallowsdove.foxymachines.tickers.QuestTicker;

public class FoxyMachines extends JavaPlugin implements SlimefunAddon {
    private static FoxyMachines instance;

    public String folderPath;

    @SneakyThrows
    @Override
    public void onEnable() {
        instance = this;

        PluginUtils.setup("foxymachines", this, "GallowsDove/FoxyMachines/master", getFile());

        CommandManager.setup("foxymachines", "foxymachines.info", "/fm, /foxy",
                new SacrificialAltarCommand(), new QuestCommand());

        getServer().getPluginManager().registerEvents(new ChunkLoaderListener(), this);
        getServer().getPluginManager().registerEvents(new BoostedRailListener(), this);
        getServer().getPluginManager().registerEvents(new BerryBushListener(), this);
        getServer().getPluginManager().registerEvents(new ForcefieldListener(), this);
        getServer().getPluginManager().registerEvents(new RemoteControllerListener(), this);
        getServer().getPluginManager().registerEvents(new SacrificialAltarListener(), this);
        getServer().getPluginManager().registerEvents(new SwordListener(), this);
        getServer().getPluginManager().registerEvents(new PoseidonsFishingRodListener(), this);
        getServer().getPluginManager().registerEvents(new ArmorListener(), this);

        ItemSetup.INSTANCE.init();
        ResearchSetup.INSTANCE.init();

        this.folderPath = getDataFolder().getAbsolutePath() + File.separator + "data-storage" + File.separator;
        try {
			ForcefieldDome.loadDomeLocations();
		} catch (IOException e) {
			e.printStackTrace();
		}
        Bukkit.getScheduler().runTask(this, () -> ForcefieldDome.INSTANCE.setupDomes());
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new QuestTicker(), 10, 120);
    }

    @SneakyThrows
    @Override
    public void onDisable() {
        try {
			ForcefieldDome.saveDomeLocations();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @Nonnull
    @Override
    public String getBugTrackerURL() {
        return "https://github.com/GallowsDove/FoxyMachines/issues";
    }

    @Nonnull
    @Override
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    @Nonnull
    public static FoxyMachines getInstance() {
        return instance;
    }
}
