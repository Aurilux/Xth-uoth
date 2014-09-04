package com.aurilux.xar.lib;

public class XARModInfo {
	public static final String MOD_ID       = "xthuoth";
	public static final String MOD_NAME     = "Xth'uoth";
	public static final String MOD_TITLE    = MOD_NAME + ": The Aberrant Realm";
	public static final String MOD_VERSION  = "1.0.0";
    public static final String MOD_DEPEND   = "required-after:Thaumcraft";
	
	public static final String VERSION_FILE = "https://raw.githubusercontent.com/Aurilux/Xth-uoth/master/version.xml";
	
	public static final String CLIENT_PROXY = "com.aurilux.xar.proxy.ClientXARProxy";
	public static final String SERVER_PROXY = "com.aurilux.xar.proxy.CommonXARProxy";
    public static final String GUI_FACTORY  = "com.aurilux.xar.client.gui.config.XARGuiFactory";
}