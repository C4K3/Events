package org.c4k3.Events;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * This class handles the /quitevent command
 * 
 * Let's players leave the event anytime.
 */
public class QuitEventCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}

		/* If not a player */
		if (player == null) {
			Events.instance.getLogger().info("You must be a player to use this command.");
			return true;
		}

		UUID uuid = player.getUniqueId();

		/* If player is not participating in the event */
		if (!Event.isPlayerActive(uuid)) {
			player.sendMessage(ChatColor.RED + "You are not in any event. If you need help, try asking for an admin.");
			return true;
		}

		EventPlayer eventPlayer = Event.getEventPlayer(uuid);

		eventPlayer.setIsQuitting(true);

		player.setHealth(0);

		return true;
	}

}

