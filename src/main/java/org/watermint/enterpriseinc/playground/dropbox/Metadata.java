package org.watermint.enterpriseinc.playground.dropbox;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.ListFolderResult;

public class Metadata {
    private static final String ACCESS_TOKEN = Token.getAccessToken("PASTE_YOUR_TOKEN_HERE");
    private static final String METADATA_PATH = "";

    private static void processEntry(com.dropbox.core.v2.files.Metadata metadata) {
        System.out.println(metadata);
    }

    public static void main(String[] args) throws DbxException {
        DbxRequestConfig config = DbxRequestConfig.newBuilder("list").build();
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);

        com.dropbox.core.v2.files.Metadata metadata = client.files().getMetadata(METADATA_PATH);
        processEntry(metadata);
    }
}
