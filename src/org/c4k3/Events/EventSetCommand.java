package org.c4k3.Events;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * This class handles the /eventset command
 * 
 * /eventset is used to set information about the event, such as spawn location.
 */
public class EventSetCommand implements CommandExecutor {

	/** This class handles the /eventset command. */
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Player player = null;
		if (sender instanceof Player){
			player = (Player) sender;
		}

		/* If non-op */
		if ( player != null && !player.isOp() ) {
			player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
			return true;
		}

		String sWorld = null;
		String sX = null;
		String sY = null;
		String sZ = null;

		for ( String arg : args ) {

			arg = arg.toLowerCase(); // So that startsWith becomes case-insensitive

			if ( arg.startsWith("world=") )
				/* Sanity checking isn't really necessary here. Here we just store the data,
				 * and then try to parse it into a location later on. If it's invalid, we'll know then. */
				sWorld = arg.substring(6);

			else if ( arg.startsWith("x=") )
				sX = arg.substring(2);

			else if ( arg.startsWith("y=") )
				sY = arg.substring(2);

			else if ( arg.startsWith("z=") )
				sZ = arg.substring(2);

		}

		Location startLocation = new Location(null, 0, 0, 0);

		/* If any of the location arguments have been passed, we try to create a Bukkit.Location out of them */
		if ( sWorld != null || sX != null || sY != null || sZ != null ) {
			try {

				World world = Events.instance.getServer().getWorld(sWorld);
				
				Block block = world.getBlockAt(Integer.parseInt(sX), Integer.parseInt(sY), Integer.parseInt(sZ) );
				
				startLocation = block.getLocation();
				
				/* Adjust x and z coordinates so the player spawns in the center of the block */
				startLocation.setX(startLocation.getX() + 0.5);
				startLocation.setZ(startLocation.getZ() + 0.5);

			} catch(Exception e) {
				if ( player != null ) player.sendMessage(ChatColor.RED +  "Incorrect location arguments for /eventset\n" +
						"Proper syntax is: " + ChatColor.AQUA + "/eventset [x=<x>] [y=<y>] [z=<z>] [world=<world>]" +
						"\n" + ChatColor.RED + "If no arguments are given, your current position is used.");
				Events.instance.getLogger().info("Error setting custom location with /eventset: " + e);
				return true;
			}

		} else {
			/* If none of the arguments are passed, the sending player's location is used */
			if ( player != null ) { 
				startLocation = player.getLocation();
				player.sendMessage(ChatColor.AQUA + "The event spawn has been set to your current location.");
			}

			/* But if the sending player is console, they have no location */
			else {
				Events.instance.getLogger().info("As console you must set a custom location with /eventset\n"
						+ "Proper syntax is /eventset [x=<x>] [y=<y>] [z=<z>] [world=<world>]");
				return true;
			}

		}

		/* Now everything should be in order */
		Event.setStartLocation(startLocation);
		Event.setIsComplete(true);

		return true;

	}

}
