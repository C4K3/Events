package org.c4k3.Events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/** Class is responsible for listening to player deaths
 * and making sure no xp is dropped in the events
 */
public class PlayerDeath implements Listener {

	@EventHandler(priority = EventPriority.NORMAL,ignoreCancelled=true)
	public void onPlayerDeath(PlayerDeathEvent event) {

		if ( Event.isPlayerActive(event.getEntity().getName()) ) {

			event.setDroppedExp(0);

		}

	}

}
