package org.c4k3.Events;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * This class handles the /eventlist command
 * 
 * Very simply just prints a list of all players currently playing an
 * event to any admin or console.
 */
public class EventListCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Player player = null;
		if (sender instanceof Player){
			player = (Player) sender;
		}

		/* If sender is not an admin */
		if ( player != null && !player.isOp() ) {
			sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
			return true;
		}

		Set<String> eventPlayers = Event.getActivePlayers();

		String message;

		/* Only players should get the fancy colors, a console should just get a plain message. */
		if ( player != null ) {
			message = ChatColor.GOLD + "There are " + ChatColor.AQUA + eventPlayers.size() + ChatColor.GOLD +  " players currently in the events: " + ChatColor.AQUA;	
		} else {
			message = "There are " + eventPlayers.size() + " players currently in the events: ";
		}

		/* If there aren't any eventPlayers, don't post a blank empty line */
		if ( eventPlayers.size() > 0 ) message += "\n" + eventPlayers;

		if ( player != null ) player.sendMessage(message);
		else Events.instance.getLogger().info(message);

		return true;

	}

}
