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
/ea [true/false]
```
- Change the active state of the event (whether people can join or not.) If true/false is not specified, then it toggles it.

```
/eventset [world=] [x=] [y=] [z=] [name=]
```
- Used by admins to setup the event.
If no location arguments are specified (world, x, y, z) then it will set the spawn location of the event to where the admin currently is. Otherwise it will set it to the location defined by those 4 arguments.
If name is specified, then it will announce the starting of the event when the event is set to active.