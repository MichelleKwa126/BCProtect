package com.github.catageek.BCProtect.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.github.catageek.BCProtect.BCProtect;
import com.github.catageek.ByteCart.Event.UpdaterPassRouterEvent;
import com.github.catageek.ByteCart.Event.UpdaterPassStationEvent;

public final class UpdaterListener implements Listener {
	
	private UpdaterMoveListener updatermovelistener;

	@EventHandler
	public void onUpdaterPassRouter(UpdaterPassRouterEvent event) {
		if (updatermovelistener == null) {
			updatermovelistener = new UpdaterMoveListener();
			Bukkit.getServer().getPluginManager().registerEvents(updatermovelistener, BCProtect.myPlugin);
		}
		BCProtect.getRegionBuilder().onPassRouter(event.getIc().getBlock().getLocation(BCProtect.location),
				event.getFrom(), event.getTo(), event.getIc().getName(), event.getUpdaterLevel());
	}

	@EventHandler
	public void onUpdaterPassStation(UpdaterPassStationEvent event) {
		BCProtect.getRegionBuilder().onPassStation(event.getIc().getBlock().getLocation(BCProtect.location),
				event.getIc().getCardinal().getOppositeFace(), event.getIc().getName(), event.getUpdaterLevel());
	}
	
}
