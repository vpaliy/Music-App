package com.vpaliy.domain.model;


public class Comment {

  private String time;
  private User user;
  private String id;
  private String uri;
  private String body;

  public String getBody() {
    return body;
  }

  public String getId() {
    return id;
  }

  public String getTime() {
    return time;
  }

  public String getUri() {
    return uri;
  }

  public User getUser() {
    return user;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public void setId(String id) {
    this.id = id;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public void setUser(User user) {
    this.user = user;
  }
}

