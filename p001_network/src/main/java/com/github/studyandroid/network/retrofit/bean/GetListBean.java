package com.github.studyandroid.network.retrofit.bean;

import java.io.Serializable;

public class GetListBean implements Serializable {
    private String login;
    private String avatar_url;
    private String gravatar_id;
    private String url;
    private String html_url;
    private String followers_url;
    private String following_url;
    private String gists_url;
    private String starred_url;
    private String subscriptions_url;
    private String organizations_url;
    private String repos_url;
    private String events_url;
    private String received_events_url;
    private String type;
    private String name;
    private String company;
    private String blog;
    private String location;
    private String email;
    private String hireable;
    private String bio;
    private String created_at;
    private String updated_at;

    private int id;
    private int public_repos;
    private int public_gists;
    private int followers;
    private int following;

    private boolean site_admin;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getGravatar_id() {
        return gravatar_id;
    }

    public void setGravatar_id(String gravatar_id) {
        this.gravatar_id = gravatar_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getFollowers_url() {
        return followers_url;
    }

    public void setFollowers_url(String followers_url) {
        this.followers_url = followers_url;
    }

    public String getFollowing_url() {
        return following_url;
    }

    public void setFollowing_url(String following_url) {
        this.following_url = following_url;
    }

    public String getGists_url() {
        return gists_url;
    }

    public void setGists_url(String gists_url) {
        this.gists_url = gists_url;
    }

    public String getStarred_url() {
        return starred_url;
    }

    public void setStarred_url(String starred_url) {
        this.starred_url = starred_url;
    }

    public String getSubscriptions_url() {
        return subscriptions_url;
    }

    public void setSubscriptions_url(String subscriptions_url) {
        this.subscriptions_url = subscriptions_url;
    }

    public String getOrganizations_url() {
        return organizations_url;
    }

    public void setOrganizations_url(String organizations_url) {
        this.organizations_url = organizations_url;
    }

    public String getRepos_url() {
        return repos_url;
    }

    public void setRepos_url(String repos_url) {
        this.repos_url = repos_url;
    }

    public String getEvents_url() {
        return events_url;
    }

    public void setEvents_url(String events_url) {
        this.events_url = events_url;
    }

    public String getReceived_events_url() {
        return received_events_url;
    }

    public void setReceived_events_url(String received_events_url) {
        this.received_events_url = received_events_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getBlog() {
        return blog;
    }

    public void setBlog(String blog) {
        this.blog = blog;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHireable() {
        return hireable;
    }

    public void setHireable(String hireable) {
        this.hireable = hireable;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPublic_repos() {
        return public_repos;
    }

    public void setPublic_repos(int public_repos) {
        this.public_repos = public_repos;
    }

    public int getPublic_gists() {
        return public_gists;
    }

    public void setPublic_gists(int public_gists) {
        this.public_gists = public_gists;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFollowing() {
        return following;
    }

    public void setFollowing(int following) {
        this.following = following;
    }

    public boolean isSite_admin() {
        return site_admin;
    }

    public void setSite_admin(boolean site_admin) {
        this.site_admin = site_admin;
    }

    @Override
    public String toString() {
        return "GetListBean{" +
                "\n    login='" + login + '\'' +
                ",\n    avatar_url='" + avatar_url + '\'' +
                ",\n    gravatar_id='" + gravatar_id + '\'' +
                ",\n    url='" + url + '\'' +
                ",\n    html_url='" + html_url + '\'' +
                ",\n    followers_url='" + followers_url + '\'' +
                ",\n    following_url='" + following_url + '\'' +
                ",\n    gists_url='" + gists_url + '\'' +
                ",\n    starred_url='" + starred_url + '\'' +
                ",\n    subscriptions_url='" + subscriptions_url + '\'' +
                ",\n    organizations_url='" + organizations_url + '\'' +
                ",\n    repos_url='" + repos_url + '\'' +
                ",\n    events_url='" + events_url + '\'' +
                ",\n    received_events_url='" + received_events_url + '\'' +
                ",\n    type='" + type + '\'' +
                ",\n    name='" + name + '\'' +
                ",\n    company='" + company + '\'' +
                ",\n    blog='" + blog + '\'' +
                ",\n    location='" + location + '\'' +
                ",\n    email='" + email + '\'' +
                ",\n    hireable='" + hireable + '\'' +
                ",\n    bio='" + bio + '\'' +
                ",\n    created_at='" + created_at + '\'' +
                ",\n    updated_at='" + updated_at + '\'' +
                ",\n    id=" + id +
                ",\n    public_repos=" + public_repos +
                ",\n    public_gists=" + public_gists +
                ",\n    followers=" + followers +
                ",\n    following=" + following +
                ",\n    site_admin=" + site_admin +
                "\n" + '}';
    }
}
