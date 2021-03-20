package me.gallowsdove.foxymachines.commands;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.github.mooy1.infinitylib.command.AbstractCommand;

public class SacrificialAltarCommand extends AbstractCommand {
    public SacrificialAltarCommand() {
        super("altar", "Gives you a link to the Sacrificial Altar", false);
    }

    @Override
    public void onExecute(@Nonnull CommandSender commandSender, @Nonnull String[] strings) {
        if (!(commandSender instanceof Player) || strings.length != 1) {
            return;
        }

        Player p = (Player) commandSender;

        p.sendMessage(ChatColor.LIGHT_PURPLE + "https://youtu.be/KbwCCpzq3O0");
    }

    @Nonnull
    @Override
    public List<String> onTab(@Nonnull CommandSender commandSender, @Nonnull String[] strings) {
        return new ArrayList<String>() ;
    }
}
