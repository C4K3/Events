package net.simpvp.Events;

import java.util.List;
import java.util.UUID;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Events extends JavaPlugin implements Listener {

	public static Events instance;
	public static boolean enabled = false;
	static List<?> banned_players;

	public Events() {
		instance = this;
	}

	@Override
	public void onEnable(){
		this.saveDefaultConfig();
		banned_players = Events.instance.getConfig().getStringList("banned_players").stream().map(UUID::fromString).collect(java.util.stream.Collectors.toList());
		getServer().getPluginManager().registerEvents(new PlayerDeath(), this);
		getServer().getPluginManager().registerEvents(new PlayerRespawn(), this);
		getServer().getPluginManager().registerEvents(new PlayerQuit(), this);
		getServer().getPluginManager().registerEvents(new PluginDisable(), this);
		getCommand("ea").setExecutor(new EaCommand());
		getCommand("event").setExecutor(new EventCommand());
		getCommand("eventlist").setExecutor(new EventListCommand());
		getCommand("eventset").setExecutor(new EventSetCommand());
		getCommand("quitevent").setExecutor(new QuitEventCommand());
		getCommand("endevent").setExecutor(new EndEventCommand());
		getCommand("eventrestore").setExecutor(new EventRestoreCommand());
		enabled = true;
	}

	@Override
	public void onDisable(){
		enabled = false;
	}

}

