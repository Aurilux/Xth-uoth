package com.aurilux.xar.handlers;

import cpw.mods.fml.common.registry.LanguageRegistry;

public class LocalizationHandler {
    //language constants
	private static final String LANG_DIR = "/assets/xthuoth/lang/";
	private static final String[] localeFiles = {"en_US.xml"};
	
	public static void load () {
		for (String langFile : localeFiles) {
			LanguageRegistry.instance().loadLocalization(LANG_DIR + langFile, getLocaleFromFileName(langFile), isXMLLanguageFile(langFile));
		}
	}
	
	//Localization Utils
	private static boolean isXMLLanguageFile(String filename) {
		return filename.endsWith(".xml");
	}
	
	private static String getLocaleFromFileName(String filename) {
		return filename.substring(filename.lastIndexOf("/")+1, filename.lastIndexOf("."));
	}
	
	/*private static String getLocalizationString(String key) {
		return LanguageRegistry.instance().getStringLocalization(key);
	}*/
}