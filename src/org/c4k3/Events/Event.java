package org.c4k3.Events;

import java.util.HashMap;
import java.util.Set;

import org.bukkit.Location;

/**
 * This class stores data about the current event.
 */
public class Event {

	private static Boolean isActive = new Boolean("false");
	private static Boolean isComplete = new Boolean("false");
	private static Location startLocation;
	private static HashMap<String, EventPlayer> eventPlayers = new HashMap<String, EventPlayer>();

	/**
	 * Gets whether the event is active or not.
	 * @return Whether event is active.
	 */
	public static Boolean getIsActive() {
		return isActive;
	}

	/**
	 * Sets whether the event is active or not.
	 * @param isActive Whether event is active.
	 */
	public static void setIsActive(Boolean isActive) {
		Event.isActive = isActive;
	}

	/**
	 * Whether the event is complete.
	 * 
	 * As a general rule of thumb, this is true if there is a startLocation.
	 * But if there are spawn locations etc. then they have to all be set for this to be true.
	 * @return If event is completely set.
	 */
	public static Boolean getIsComplete() {
		return isComplete;
	}

	/**
	 * Whether the event is complete.
	 * @param isComplete If event is completely set.
	 */
	public static void setIsComplete(Boolean isComplete) {
		Event.isComplete = isComplete;
	}

	/**
	 * Set's the starting spawn location of the event.
	 * 
	 * All players joining the event will be teleported to this location.
	 * @param location Event's spawn location.
	 */
	public static void setStartLocation(Location location) {
		startLocation = location;
	}

	/**
	 * Returns the spawn location of the event.
	 * @return Event's spawn location.
	 */
	public static Location getStartLocation() {
		return startLocation;
	}

	/**
	 * Saves an EventPlayer object in the eventPlayers HashMap.
	 * 
	 * For later retrieval when the player is to be sent home.
	 * @param name Name of the player.
	 * @param eventPlayer EventPlayer object of the player.
	 */
	public static void saveEventPlayer(String name, EventPlayer eventPlayer) {
		eventPlayers.put(name, eventPlayer);
	}

	/**
	 * Gets a stored EventPlayer object.
	 * @param name Name of the player.
	 * @return EventPlayer object of the player.
	 */
	public static EventPlayer getEventPlayer(String name) {
		return eventPlayers.get(name);
	}

	/**
	 * Deletes a stored EventPlayer object.
	 * @param name Name of the player.
	 */
	public static void removeEventPlayer(String name) {
		eventPlayers.remove(name);
	}

	/**
	 * Gets whether the player is currently playing an event.
	 * @param name Name of the player.
	 * @return Whether player is in an event.
	 */
	public static Boolean isPlayerActive(String name) {		
		return eventPlayers.containsKey(name);
	}

	/**
	 * Get a set of the names of all players currently playing an event.
	 * @return Name of all players currently playing an event.
	 */
	public static Set<String> getActivePlayers() {
		return eventPlayers.keySet();
	}

}
