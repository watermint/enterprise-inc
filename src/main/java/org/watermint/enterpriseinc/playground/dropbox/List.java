package org.watermint.enterpriseinc.playground.dropbox;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;

public class List {
    private static final String ACCESS_TOKEN = Token.getAccessToken("PASTE_YOUR_TOKEN_HERE");
    private static final String LIST_PATH = "";

    private static void processEntry(Metadata metadata) {
        System.out.println(metadata);
    }

    public static void main(String[] args) throws DbxException {
        DbxRequestConfig config = DbxRequestConfig.newBuilder("list").build();
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);

        ListFolderResult result = client.files().listFolderBuilder(LIST_PATH).withRecursive(true).start();
        result.getEntries().stream().forEach(List::processEntry);

        while (result.getHasMore()) {
            String cursor = result.getCursor();
            result = client.files().listFolderContinue(cursor);
            result.getEntries().stream().forEach(List::processEntry);
        }
    }
}
