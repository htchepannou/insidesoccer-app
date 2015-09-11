package com.tchepannou.app.login.mapper;

import com.tchepannou.app.login.client.v1.TransactionInfo;
import com.tchepannou.app.login.client.v1.blog.AppPostCollectionResponse;
import com.tchepannou.blog.client.v1.AttachmentResponse;
import com.tchepannou.blog.client.v1.PostResponse;
import com.tchepannou.party.client.v1.PartyResponse;

import java.util.List;
import java.util.Map;

public class AppPostCollectionResponseMapper {
    //-- Attribute
    private List<PostResponse> posts;
    private Map<Long, PartyResponse> teams;
    private String assertUrlPrefix;
    private TransactionInfo transactionInfo;


    //-- Public
    public AppPostCollectionResponse map (){
        AppPostCollectionResponse result = new AppPostCollectionResponse (transactionInfo, posts, teams);
        for (PostResponse post : posts){
            for (AttachmentResponse attachment : post.getAttachments()){
                String url = attachment.getUrl();
                if (!url.startsWith("http://") && !url.startsWith("https://") && !url.startsWith("//")){
                    attachment.setUrl(assertUrlPrefix + url);
                }
            }
        }
        return result;
    }

    public AppPostCollectionResponseMapper withTransactionInfo (TransactionInfo transactionInfo){
        this.transactionInfo = transactionInfo;
        return this;
    }
    public AppPostCollectionResponseMapper withPosts(List<PostResponse> posts) {
        this.posts = posts;
        return this;
    }

    public AppPostCollectionResponseMapper withTeams(Map<Long, PartyResponse> teams) {
        this.teams = teams;
        return this;
    }

    public AppPostCollectionResponseMapper withAssertUrlPrefix(String assertUrlPrefix) {
        this.assertUrlPrefix = assertUrlPrefix;
        return this;
    }
}
