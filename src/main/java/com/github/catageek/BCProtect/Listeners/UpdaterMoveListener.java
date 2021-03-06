package com.github.catageek.BCProtect.Listeners;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.github.catageek.ByteCartAPI.Event.UpdaterMoveEvent;


public class UpdaterMoveListener implements Listener {

	UpdaterMoveListener() {
	}


	@EventHandler
	public void onUpdaterMove(UpdaterMoveEvent event) {
		Location from = event.getEvent().getFrom();
		Location to = event.getEvent().getTo();
		UpdaterListener.getRegionBuilder(event.getEvent().getVehicle().getEntityId()).onMove(from, to, this.getDirection(from, to));
	}

	private BlockFace getDirection(Location from, Location to) {
		if (from.getBlockX() == to.getBlockX())
			if (from.getBlockZ() < to.getBlockZ())
				return BlockFace.SOUTH;
			else
				return BlockFace.NORTH;
		else
			if (from.getBlockX() < to.getBlockX())
				return BlockFace.EAST;
			else
				return BlockFace.WEST;
	}
}
