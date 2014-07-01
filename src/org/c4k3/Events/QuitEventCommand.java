package org.c4k3.Events;

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
		if (sender instanceof Player){
			player = (Player) sender;
		}

		/* If not a player */
		if ( player == null ) {
			Events.instance.getLogger().info("You must be a player to use this command.");
			return true;
		}

		String sPlayer = player.getName();

		/* If player is not participating in the event */
		if ( !Event.isPlayerActive(sPlayer) ) {
			return true;
		}

		EventPlayer eventPlayer = Event.getEventPlayer(sPlayer);

		eventPlayer.setIsQuitting(true);

		player.setHealth(0);

		return true;

	}

}
