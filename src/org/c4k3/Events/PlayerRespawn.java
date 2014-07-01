package org.c4k3.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

/** Monitors people respawning
 * If somebody participating in the event respawns, it teleports them home
 * and gives them back previous items and health, etc */
public class PlayerRespawn implements Listener {

	private static EventPlayer eventPlayer;

	@EventHandler(priority = EventPriority.NORMAL,ignoreCancelled=true)
	public void onPlayerRespawn(PlayerRespawnEvent event) {

		String sPlayer = event.getPlayer().getName();

		/* If player is not participating in the event */
		if ( !Event.isPlayerActive(sPlayer) ) {
			return;
		}

		eventPlayer = Event.getEventPlayer(sPlayer);

		/* If the player should be respawning inside the event */
		if ( !eventPlayer.getIsQuitting() ) return;

		event.setRespawnLocation(eventPlayer.getLocation());

		Player player = event.getPlayer();

		eventPlayer.sendHome(player, false);

	}

}
