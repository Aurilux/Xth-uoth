package com.aurilux.xar.handlers;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;

import net.minecraft.server.MinecraftServer;
import com.aurilux.xar.lib.XARModInfo;

import cpw.mods.fml.common.Loader;

public class XARUpdateHandler {
	//FIXME Make this work. Most likely done with Netty Packets
    private final String MESSAGE_PREFACE = "[\u00A75" + XARModInfo.MOD_NAME + "\u00A7r] A new version of " + XARModInfo.MOD_NAME + " is available.";
    
    private String updateThread = null;

	/*@Override
	public void playerLoggedIn(Player player, NetHandler netHandler, INetworkManager manager) {
		if (isUpdateAvailable()) {
			netHandler.getPlayer().sendChatToPlayer(ChatMessageComponent.createFromText(MESSAGE_PREFACE + " Please go to " + updateThread + " to download the update."));
		}
	}*/
	
	private boolean isUpdateAvailable() {
		try {
			HttpsURLConnection conn = (HttpsURLConnection) new URL(XARModInfo.VERSION_FILE).openConnection();
			InputStream versionFile = conn.getInputStream();
			Properties versionProperties = new Properties();
			versionProperties.loadFromXML(versionFile);
			String currentVersion = versionProperties.getProperty(Loader.instance().getMCVersionString());
			updateThread = versionProperties.getProperty("thread");
			return (currentVersion != null && updateThread != null && !currentVersion.equals(XARModInfo.MOD_VERSION));
		}
		catch (Exception e) {
			System.out.println(e);
			return false;
		}
	}
}