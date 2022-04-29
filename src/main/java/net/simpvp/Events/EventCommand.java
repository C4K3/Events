package net.simpvp.Events;

import java.util.Base64;
import java.util.Collection;
import java.util.UUID;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scoreboard.Team;
import org.bukkit.util.io.BukkitObjectOutputStream;

/** This class is responsible for handling the /event command.
 *
 * Saves all the player's information.
 *  Teleports users to the set starting location of the event.
 *  And prepares them for the event. */
public class EventCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		}

		/* Command sender must be an ingame player */
		if (player == null) {
			Events.instance.getLogger().info("You must be a player to use this command.");
			return true;
		}

		/* Event must be active */
		if (!Event.getIsActive()) {
			player.sendMessage(ChatColor.RED + "Event is currently closed for new contestants.\n"
					+ "Please wait until the next event starts.");
			return true;
		}

		/* If the event is not setup yet */
		if (!Event.getIsComplete()) {
			sender.sendMessage(ChatColor.RED + "Woops. Looks like the event is set to active,\n" +
					"but I'm missing some information about the event.\n" +
					"Please tell the nice admin that they're missing something :)");
			return true;
		}

		/* If player quit an event less than 4 seconds ago */
		if (EventPlayer.playerLastJoinTime.containsKey(player.getUniqueId())) {
			if (System.currentTimeMillis() - EventPlayer.playerLastJoinTime.get(player.getUniqueId()) < 4 * 1000) {
				sender.sendMessage(ChatColor.RED + "Please wait a few seconds before joining.");
				return true;
			}
		}

		
		if (Events.banned_players.contains(player.getUniqueId())) {
			sender.sendMessage(ChatColor.RED + "You are banned from events.");
			return true;
		}

		if (player.isInsideVehicle()) {
			sender.sendMessage(ChatColor.RED + "You cannot join events while in a vehicle.");
			return true;
		}

		if (player.getLocation().getY() < 0.0) {
			sender.sendMessage(ChatColor.RED + "You cannot join events from your current location.");
			return true;
		}

		if (player.isOp()) {
			player.performCommand("rg bypass off");
		}

		UUID uuid = player.getUniqueId();
		/* Everything seems to be in order. Saving all player info for when the event is over */
		if (!Event.isPlayerActive(uuid)) {
			save_player_data(player);
		}

		/* Now we reset all their stuff and teleport them off */
		player.setFoodLevel(20);
		player.setLevel(0);
		player.setExp(0);
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		player.setHealth(player.getMaxHealth());
		for (PotionEffect potionEffect : player.getActivePotionEffects())
			player.removePotionEffect(potionEffect.getType());
		player.setGameMode(GameMode.SURVIVAL);
		player.teleport(Event.getStartLocation());
		/* Leave current team. Doesn't save it, but maybe it should? */
		Team team = player.getScoreboard().getPlayerTeam(player);
		if (team != null) {
			team.removePlayer(player);
		}

		player.sendMessage(ChatColor.AQUA + "Teleporting you to the event arena."
				+ "\nWhen you die, you will be teleported back with all your items and XP in order.");

		return true;

	}

	private void save_player_data(Player player) {
		String sPlayer = player.getName();
		Location playerLocation = player.getLocation();
		int playerFoodLevel = player.getFoodLevel();
		int playerLevel = player.getLevel();
		float playerXP = player.getExp();
		ItemStack[] armorContents = player.getInventory().getArmorContents();
		ItemStack[] inventoryContents = player.getInventory().getContents();
		Double playerHealth = player.getHealth();
		Collection<PotionEffect> potionEffects = player.getActivePotionEffects();
		GameMode gameMode = player.getGameMode();

		/* Save all this data */
		EventPlayer eventPlayer = new EventPlayer(player.getUniqueId(), sPlayer, playerLocation, playerFoodLevel, playerLevel, playerXP, armorContents, inventoryContents, playerHealth, potionEffects, gameMode, false);
		eventPlayer.save();

		/* Log it all, just in case */
		String inventoryString;
		try {
			ByteArrayOutputStream bytearray = new ByteArrayOutputStream();
			OutputStream base64 = Base64.getEncoder().wrap(bytearray);
			BukkitObjectOutputStream bukkitstream = new BukkitObjectOutputStream(base64);

			int len = 0;
			for (ItemStack itemStack : inventoryContents) {
				if (itemStack == null) {
					continue;
				}
				len += 1;
			}
			bukkitstream.writeInt(len);

			for (ItemStack itemStack : inventoryContents) {
				if (itemStack == null) {
					continue;
				}

				bukkitstream.writeObject(itemStack);
			}

			bukkitstream.close();
			base64.close();

			inventoryString = new String(bytearray.toByteArray());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		Events.instance.getLogger().info(String.format("%s is participating in event: %d %s", sPlayer, playerLevel, inventoryString));
	}

}
