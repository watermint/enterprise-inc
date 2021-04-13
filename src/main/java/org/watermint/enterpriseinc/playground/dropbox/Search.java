package org.watermint.enterpriseinc.playground.dropbox;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.*;

import java.util.List;

public class Search {
    private static final String ACCESS_TOKEN = "PASTE_YOUR_TOKEN_HERE";
    private static final String SEARCH_PATH = "/Test";
    private static final String SEARCH_QUERY = "Dropbox";

    public static void processSearchResult(SearchMatchV2 match) {
        System.out.println(match.toString());
    }

    public static void main(String[] args) throws DbxException {
        DbxRequestConfig config = DbxRequestConfig.newBuilder("search").build();
        DbxClientV2 client = new DbxClientV2(config, ACCESS_TOKEN);

        SearchOptions opts = SearchOptions.newBuilder()
                .withPath(SEARCH_PATH)
                .withFileCategories(List.of(FileCategory.FOLDER)).build();
        SearchV2Result searchV2Result = client.files().searchV2Builder(SEARCH_QUERY).withOptions(opts).start();

        for (SearchMatchV2 match : searchV2Result.getMatches()) {
            processSearchResult(match);
        }

        while (searchV2Result.getHasMore()) {
            String cursor = searchV2Result.getCursor();
            searchV2Result = client.files().searchContinueV2(cursor);

            for (SearchMatchV2 match : searchV2Result.getMatches()) {
                processSearchResult(match);
            }
        }
    }
}
