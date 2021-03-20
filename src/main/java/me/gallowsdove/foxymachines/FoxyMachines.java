package me.gallowsdove.foxymachines;

import java.io.File;
import java.io.IOException;

import javax.annotation.Nonnull;

import org.bukkit.plugin.java.JavaPlugin;

import io.github.mooy1.infinitylib.command.CommandManager;
import io.github.mooy1.infinitylib.core.PluginUtils;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import lombok.SneakyThrows;
import me.gallowsdove.foxymachines.abstracts.CustomBoss;
import me.gallowsdove.foxymachines.commands.QuestCommand;
import me.gallowsdove.foxymachines.commands.SacrificialAltarCommand;
import me.gallowsdove.foxymachines.commands.SummonCommand;
import me.gallowsdove.foxymachines.implementation.machines.ForcefieldDome;
import me.gallowsdove.foxymachines.implementation.tools.BerryBushTrimmer;
import me.gallowsdove.foxymachines.listeners.ArmorListener;
import me.gallowsdove.foxymachines.listeners.BerryBushListener;
import me.gallowsdove.foxymachines.listeners.BoostedRailListener;
import me.gallowsdove.foxymachines.listeners.BowListener;
import me.gallowsdove.foxymachines.listeners.ChunkLoaderListener;
import me.gallowsdove.foxymachines.listeners.ForcefieldListener;
import me.gallowsdove.foxymachines.listeners.PoseidonsFishingRodListener;
import me.gallowsdove.foxymachines.listeners.RemoteControllerListener;
import me.gallowsdove.foxymachines.listeners.SacrificialAltarListener;
import me.gallowsdove.foxymachines.listeners.SwordListener;
import me.gallowsdove.foxymachines.tickers.MobTicker;
import me.gallowsdove.foxymachines.tickers.QuestTicker;

public class FoxyMachines extends JavaPlugin implements SlimefunAddon {
    private static FoxyMachines instance;

    public String folderPath;

    @SneakyThrows
    @Override
    public void onEnable() {
        instance = this;

        PluginUtils.setup("foxymachines", this, "GallowsDove/FoxyMachines/master", getFile());

        CommandManager.setup("foxymachines", "foxymachines.admin", "/fm, /foxy",
                new SacrificialAltarCommand(), new QuestCommand(), new SummonCommand());

        PluginUtils.registerListener(new ChunkLoaderListener());
        PluginUtils.registerListener(new BoostedRailListener());
        PluginUtils.registerListener(new BerryBushListener());
        PluginUtils.registerListener(new ForcefieldListener());
        PluginUtils.registerListener(new RemoteControllerListener());
        PluginUtils.registerListener(new SacrificialAltarListener());
        PluginUtils.registerListener(new SwordListener());
        PluginUtils.registerListener(new PoseidonsFishingRodListener());
        PluginUtils.registerListener(new ArmorListener());
        PluginUtils.registerListener(new BowListener());

        ItemSetup.INSTANCE.init();
        ResearchSetup.INSTANCE.init();

        this.folderPath = getDataFolder().getAbsolutePath() + File.separator + "data-storage" + File.separator;
        try {
			BerryBushTrimmer.loadTrimmedBlocks();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			ForcefieldDome.loadDomeLocations();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        PluginUtils.runSync(() -> ForcefieldDome.INSTANCE.setupDomes());
        PluginUtils.scheduleRepeatingSync(new QuestTicker(), 10, 240);
        PluginUtils.scheduleRepeatingSync(new MobTicker(), 2);
    }

    @SneakyThrows
    @Override
    public void onDisable() {
        try {
			BerryBushTrimmer.saveTrimmedBlocks();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			ForcefieldDome.saveDomeLocations();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        CustomBoss.removeBossBars();
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
