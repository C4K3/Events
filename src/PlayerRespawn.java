package net.simpvp.Events;

import java.util.UUID;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scoreboard.Team;
import org.bukkit.inventory.ItemStack;

/** Monitors people respawning
 * If somebody participating in the event respawns, it teleports them home
 * and gives them back previous items and health, etc */
public class PlayerRespawn implements Listener {

	static public HashMap<UUID , ItemStack[]> items = new HashMap<UUID , ItemStack[]>();
	private static EventPlayer eventPlayer;

	@EventHandler(priority = EventPriority.NORMAL,ignoreCancelled=true)
	public void onPlayerRespawn(PlayerRespawnEvent event) {
		
		UUID uuid = event.getPlayer().getUniqueId();

		/* If player is not participating in the event */
		if (!Event.isPlayerActive(uuid))
			return;

		eventPlayer = Event.getEventPlayer(uuid);
		Player player = event.getPlayer();
		Location teamSpawn = null;
		Team team = player.getScoreboard().getPlayerTeam(player);

		if (team != null)
			teamSpawn = Event.getTeamSpawn(team.getName());

		/* If isQuitting is true, or if the player is not in a team with a registered team spawn, then send player home */
		if (eventPlayer.getIsQuitting() || teamSpawn == null) {
			event.setRespawnLocation(eventPlayer.getLocation());
			eventPlayer.sendHome(player);
		} else {
			/* else we send the player to their team's spawn location */
			event.setRespawnLocation(teamSpawn);
			player.sendMessage(ChatColor.AQUA + "Respawning at your team's spawn."
					+ "\nTo leave the event, type " + ChatColor.GOLD + "/quitevent");
			/* If keepInv is true then their inventory will be set to the contents*/
			
			if (Event.getKeepMyInventory() == true) {
				
				if(items.containsKey(uuid)){
					player.getInventory().clear();
					ItemStack[] myItems = items.get(uuid);
					player.getInventory().setContents(myItems);
						
	            }
					
					items.remove(uuid);
						
				}
			}
		}
	}

