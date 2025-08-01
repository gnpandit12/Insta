
package com.example.insta.model.data;


public class Item {

    private String fullName;
    private String id;
    private Boolean isPrivate;
    private Boolean isVerified;
    private Integer latestStoryTs;
    private String profilePicUrl;
    private String username;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getIsPrivate() {
        return isPrivate;
    }

    public void setIsPrivate(Boolean isPrivate) {
        this.isPrivate = isPrivate;
    }

    public Boolean getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Boolean isVerified) {
        this.isVerified = isVerified;
    }

    public Integer getLatestStoryTs() {
        return latestStoryTs;
    }

    public void setLatestStoryTs(Integer latestStoryTs) {
        this.latestStoryTs = latestStoryTs;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
