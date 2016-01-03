package org.c4k3.Events;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Team;

/**
 * This class handles the /eventset command
 * 
 * /eventset is used to set information about the event, such as spawn location.
 */
public class EventSetCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}

		/* If non-op */
		if (player != null && !player.isOp()) {
			player.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
			return true;
		}

		String sWorld = null;
		String sX = null;
		String sY = null;
		String sZ = null;
		String sTeam = null;

		for (String arg : args) {

			if (arg.toLowerCase().startsWith("world=")) {
				sWorld = arg.substring(6);

			} else if (arg.toLowerCase().startsWith("x=")) {
				sX = arg.substring(2);

			} else if (arg.toLowerCase().startsWith("y=")) {
				sY = arg.substring(2);

			} else if (arg.toLowerCase().startsWith("z=")) {
				sZ = arg.substring(2);

			} else if (arg.toLowerCase().startsWith("team=")) {
				sTeam = arg.substring(5);

			} else {
				/* An invalid argument was passed. */
				String message = "You tried to use an invalid argument with /eventset."
					+ "\nThe argument in question was: " + arg
					+ "\nThe correct syntax is: /eventset [x=] [y=] [z=] [world=] [team=]"
					+ "\nExample: /eventset x=26 y=75 z=-154 world=events";
				if (player == null) {
					Events.instance.getLogger().info(message);
				} else {
					player.sendMessage(ChatColor.RED + message);
				}

				return true;
			}

		}

		Location location = new Location(null, 0, 0, 0);

		/* If any of the location arguments have been passed, we try to create a Bukkit.Location out of them */
		if (sWorld != null || sX != null || sY != null || sZ != null) {
			try {
				World world = Events.instance.getServer().getWorld(sWorld);

				Block block = world.getBlockAt(Integer.parseInt(sX), Integer.parseInt(sY), Integer.parseInt(sZ) );

				location = block.getLocation();

				/* Adjust x and z coordinates so the player spawns in the center of the block */
				location.setX(location.getX() + 0.5);
				location.setZ(location.getZ() + 0.5);

			} catch (Exception e) {
				if (player != null)
					player.sendMessage(ChatColor.RED +  "Incorrect location arguments for /eventset\n" +
							"Proper syntax is: " + ChatColor.AQUA + "/eventset [x=<x>] [y=<y>] [z=<z>] [world=<world>]" +
							"\n" + ChatColor.RED + "If no arguments are given, your current position is used.");

				Events.instance.getLogger().info("Error setting custom location with /eventset: " + e);
				return true;
			}

		} else {
			/* If none of the arguments are passed, the sending player's location is used */
			if (player != null) {
				location = player.getLocation();
				/* But if the sending player is console, they have no location */
			} else {
				Events.instance.getLogger().info("As console you must set a custom location with /eventset\n"
						+ "Proper syntax is /eventset [x=<x>] [y=<y>] [z=<z>] [world=<world>]");
				return true;
			}

		}

		/* The given location is not the event's start location
		 * but the spawn location of a team */
		if (sTeam != null) {

			/* Check that the team exists */
			Team team = Events.instance.getServer().getScoreboardManager().getMainScoreboard().getTeam(sTeam);
			if (team == null) {
				String message = "No such scoreboard team. Type /scoreboard teams list"
					+ "\nfor a list of scoreboard teams.";
				if (player == null) {
					Events.instance.getLogger().info(message);
				} else {
					player.sendMessage(ChatColor.RED + message);
				}
				return true;
			}

			Event.setTeamSpawn(sTeam, location);

			String message = "Location of " + sTeam + " team's spawn location has been set.";
			if (player == null) {
				Events.instance.getLogger().info(message);
			} else {
				player.sendMessage(ChatColor.AQUA + message);
			}

			/* The given location is the event's start location */
		} else if (sTeam == null) {
			Event.setStartLocation(location);
			Event.setIsComplete(true);
			String message = "The event spawn has been set to the specified location.";
			if (player == null) {
				Events.instance.getLogger().info(message);
			} else {
				player.sendMessage(ChatColor.AQUA + message);
			}
		}

		return true;
	}

}

