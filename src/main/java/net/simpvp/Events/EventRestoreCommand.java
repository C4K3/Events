package net.simpvp.Events;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Base64;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;

/**
 * Adds the /eventrestore command
 *
 * Restores items based on the player items logged when a player runs the /event
 * command, should be directly copy-pastable from this output. The first argument
 * however must be the name of the player to receive the items. This command can
 * only be run from console.
 *
 * For info about the format of the items, see EventCommand.java
 */
public class EventRestoreCommand implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (sender instanceof Player) {
			sender.sendMessage(ChatColor.RED + "You do not have permission to use this command.");
			return true;
		}

		if (args.length != 3) {
			Events.instance.getLogger().info("You must provide exactly 3 arguments.");
			return true;
		}

		String sPlayer = args[0];
		String sLevel = args[1];
		String sInventory = args[2];

		@SuppressWarnings("deprecation") /* Only used on currently online players */
		Player player = Events.instance.getServer().getPlayerExact(sPlayer);
		if (player == null) {
			Events.instance.getLogger().info(String.format("No such player found: %s", sPlayer));
			return true;
		}

		try {
			ByteArrayInputStream bytearray = new ByteArrayInputStream(sInventory.getBytes());
			InputStream base64 = Base64.getDecoder().wrap(bytearray);

			BukkitObjectInputStream bukkitstream = new BukkitObjectInputStream(base64);

			int len = bukkitstream.readInt();
			ArrayList<ItemStack> inventory = new ArrayList<>();

			for (int i = 0; i < len; i++) {
				ItemStack item = (ItemStack) bukkitstream.readObject();
				inventory.add(item);
			}

			for (ItemStack item : inventory) {
				player.getWorld().dropItem(player.getLocation(), item);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		return true;
	}

}
