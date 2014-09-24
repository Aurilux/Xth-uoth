package com.aurilux.xar.world;

import net.minecraft.util.ChunkCoordinates;

public class PortalPositionXthuoth extends ChunkCoordinates {
	public long lastUpdateTime;
	final TeleporterXthuoth field_85088_e;
	public PortalPositionXthuoth(TeleporterXthuoth teleporter, int par2, int par3, int par4, long par5)
	{
		super(par2, par3, par4);
		this.field_85088_e = teleporter;
		this.lastUpdateTime = par5;
	}
}
