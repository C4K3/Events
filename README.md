Events
=====

A Bukkit plugin that lets admins host events that players can join, without losing any of their regular equipment or stats.

Commands
-----
```
/event
```
- Join the current active event. Usable by all players.

```
/ea [true/false] [Name of event]
```
- Change the active state of the event (whether people can join or not.) If true/false is not specified, then it toggles it.
If name of event is specified, then it will announce that the event is beginning.

```
/eventset [world=] [x=] [y=] [z=] [team=]
```
- Used by admins to setup the event.
If no location arguments are specified (world, x, y, z) then it will set the spawn location of the event to where the admin currently is. Otherwise it will set it to the location defined by those 4 arguments.
If team is set, then the location is used as the spawn location of the assigned team. If team is not set, the location is set to be the event's start location.

```
/quitevent
```
- Let's a player leave the event. Used because in events with respawning the player cannot leave with /kill.

```
/endevent
```
- End's the event and sends all players home. Can be used by OPs and console.