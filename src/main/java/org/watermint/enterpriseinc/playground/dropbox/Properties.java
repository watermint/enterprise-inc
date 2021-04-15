package org.watermint.enterpriseinc.playground.dropbox;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.DbxTeamClientV2;
import com.dropbox.core.v2.fileproperties.*;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.FolderMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;

import java.util.List;

public class Properties {
    private static final String ACCESS_TOKEN = Token.getAccessToken("PASTE_YOUR_TOKEN_HERE");
    private static final String clientId = "watermint-enterprise-inc-usecases-properties/1.0";

    public static void main(String[] args) throws DbxException {
        DbxRequestConfig config = DbxRequestConfig.newBuilder(clientId).withAutoRetryEnabled().build();
        DbxTeamClientV2 teamClient = new DbxTeamClientV2(config, ACCESS_TOKEN);

        // プロパティーテンプレートの作成
        List<PropertyFieldTemplate> propertyFieldTemplates = List.of(
                new PropertyFieldTemplate("フィールド名1", "フィールドの説明", PropertyType.STRING),
                new PropertyFieldTemplate("フィールド名2", "フィールドの説明", PropertyType.STRING)
        );
        AddTemplateResult templateResult = teamClient.fileProperties().templatesAddForTeam("テンプレート名", "テンプレートの説明", propertyFieldTemplates);


        // メンバーのファイルにテンプレートを用いてプロパティーを設定
        DbxClientV2 client = teamClient.asMember("チームメンバーID");
        List<PropertyGroup> propertyGroups = List.of(
                new PropertyGroup(templateResult.getTemplateId(), List.of(
                        new PropertyField("フィールド名1", "値1"),
                        new PropertyField("フィールド名2", "値2")
                ))
        );
        client.fileProperties().propertiesAdd("/path/to/file", propertyGroups);


        // テンプレート情報の取得
        ListFolderResult listResult = client.files().listFolderBuilder("/path/to/folder")
                .withIncludePropertyGroups(TemplateFilterBase.filterSome(List.of(
                        templateResult.getTemplateId()
                ))).start();

        for (Metadata entry : listResult.getEntries()) {
            if (entry instanceof FileMetadata) {
                FileMetadata file = (FileMetadata) entry;
                System.out.printf("Path[%s] Property[%s]", file.getPathDisplay(), file.getPropertyGroups());
            } else if (entry instanceof FolderMetadata) {
                FolderMetadata folder = (FolderMetadata) entry;
                System.out.printf("Path[%s] Property[%s]", folder.getPathDisplay(), folder.getPropertyGroups());
            }
        }
    }
}
