package net.simpvp.Events;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/** Class is responsible for listening to player deaths
 * and making sure no xp is dropped in the events
 * and that players don't keep any set gamemodes
 */
public class PlayerDeath implements Listener {

	@EventHandler(priority = EventPriority.NORMAL,ignoreCancelled=true)
	public void onPlayerDeath(PlayerDeathEvent event) {

		if (Event.isPlayerActive(event.getEntity().getUniqueId()))
			event.setDroppedExp(0);

		Player player = event.getEntity();

		if (!player.isOp() && player.getGameMode() != GameMode.SURVIVAL)
			player.setGameMode(GameMode.SURVIVAL);
	}

}

