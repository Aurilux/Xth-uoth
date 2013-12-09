package com.aurilux.xar.lib;

public class XAR_Ref {
	public static final String MOD_ID       = "@MOD_ID@";
	public static final String MOD_NAME     = "@MOD_NAME@";
	public static final String MOD_TITLE    = MOD_NAME + ": The Aberrant Realm";
	public static final String MOD_VERSION  = "@MOD_VERSION@";
	
	public static final String ASSETS_DIR   = "assets/" + MOD_ID + "/";
	
	public static final String VERSION_FILE = "https://raw.github.com/Aurilux/Xth-uoth/master/version.xml";
	
	public static final String CLIENT_PROXY = "com.aurilux.xar.proxy.ClientXARProxy";
	public static final String SERVER_PROXY = "com.aurilux.xar.proxy.CommonXARProxy";
}