package net.simpvp.Events;

import java.util.HashMap;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

/** Class is responsible for listening to player deaths
 * and making sure no xp is dropped in the events
 * and that players don't keep any set gamemodes
 */
public class PlayerDeath implements Listener {

	@EventHandler(priority = EventPriority.NORMAL,ignoreCancelled=true)
	public void onPlayerDeath(PlayerDeathEvent event) {
		
		/** If player is in the event and if keepInv is true
		 * then item drops are disabled and their inventory is saved
		 * for later retrieval
		 */
		
		boolean keepInv = Event.getKeepMyInventory();
		if (Event.isPlayerActive(event.getEntity().getUniqueId())) {
			event.setDroppedExp(0);
		
 		if (keepInv == true) {
 			ItemStack[] content = event.getEntity().getInventory().getContents();
 			PlayerRespawn.items.put(event.getEntity(), content);
 			event.getEntity().getInventory().clear();
 			event.getDrops().clear();

 			}
 	
		Player player = event.getEntity();

		if (!player.isOp() && player.getGameMode() != GameMode.SURVIVAL)
			player.setGameMode(GameMode.SURVIVAL);
		}
	}
}


