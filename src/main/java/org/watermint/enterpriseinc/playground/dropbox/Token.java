package org.watermint.enterpriseinc.playground.dropbox;

import java.util.Properties;

public class Token {
    public static String getAccessToken(String placeHolder) {
        String dbxToken = System.getProperty("dropboxAccessToken");
        if (dbxToken != null && !dbxToken.isBlank()) {
            return dbxToken;
        }
        Properties props = System.getProperties();
        return placeHolder;
    }
}
