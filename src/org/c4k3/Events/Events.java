package org.c4k3.Events;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Events extends JavaPlugin implements Listener {

	public static Events instance;

	public Events() {
		instance = this;
	}

	@Override
	public void onEnable(){
		getServer().getPluginManager().registerEvents(new PlayerDeath(), this);
		getServer().getPluginManager().registerEvents(new PlayerRespawn(), this);
		getServer().getPluginManager().registerEvents(new PlayerQuit(), this);
		getCommand("ea").setExecutor(new EaCommand());
		getCommand("event").setExecutor(new EventCommand());
		getCommand("eventlist").setExecutor(new EventListCommand());
		getCommand("eventset").setExecutor(new EventSetCommand());
		getCommand("quitevent").setExecutor(new QuitEventCommand());
		getCommand("endevent").setExecutor(new EndEventCommand());
	}

	@Override
	public void onDisable(){

	}

}
