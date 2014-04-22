package com.aurilux.xar.handlers;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;

import net.minecraft.network.INetworkManager;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ChatMessageComponent;

import com.aurilux.xar.lib.XAR_Ref;

import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.Player;

public class XARUpdateHandler implements IConnectionHandler {
    private final String MESSAGE_PREFACE = "[\u00A75" + XAR_Ref.MOD_NAME + "\u00A7r] A new version of " + XAR_Ref.MOD_NAME + " is available.";
    
    private String updateThread = null;

	@Override
	public void playerLoggedIn(Player player, NetHandler netHandler, INetworkManager manager) {
		if (isUpdateAvailable()) {
			netHandler.getPlayer().sendChatToPlayer(ChatMessageComponent.createFromText(MESSAGE_PREFACE + " Please go to " + updateThread + " to download the update."));
		}
	}
	
	private boolean isUpdateAvailable() {
		try {
			HttpsURLConnection conn = (HttpsURLConnection) new URL(XAR_Ref.VERSION_FILE).openConnection();
			InputStream versionFile = conn.getInputStream();
			Properties versionProperties = new Properties();
			versionProperties.loadFromXML(versionFile);
			String currentVersion = versionProperties.getProperty(Loader.instance().getMCVersionString());
			updateThread = versionProperties.getProperty("thread");
			return (currentVersion != null && updateThread != null && !currentVersion.equals(XAR_Ref.MOD_VERSION));
		}
		catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}
	
	//UNUSED INHERITED METHODS//////////////////////////////////////////////////////////////////////////////////////
	@Override
	public String connectionReceived(NetLoginHandler netHandler, INetworkManager manager) {
		return null;
	}

	@Override
	public void connectionOpened(NetHandler netClientHandler, String server, int port, INetworkManager manager) {
	}

	@Override
	public void connectionOpened(NetHandler netClientHandler, MinecraftServer server, INetworkManager manager) {
	}

	@Override
	public void connectionClosed(INetworkManager manager) {
	}

	@Override
	public void clientLoggedIn(NetHandler clientHandler, INetworkManager manager, Packet1Login login) {
	}
}