package com.vpaliy.data.source.local.handler;

import java.util.LinkedList;
import java.util.List;

public class Query {

  private String selection;
  private List<String> args;

  public static Query start() {
    return new Query();
  }

  public Query setSelection(String selection) {
    this.selection = selection;
    return this;
  }

  public Query addArgument(String arg) {
    if (args == null) {
      args = new LinkedList<>();
    }
    args.add(arg);
    return this;
  }

  public Query setArgs(List<String> args) {
    this.args = args;
    return this;
  }

  public String[] args() {
    if (args != null) {
      return args.toArray(new String[args.size()]);
    }
    return null;
  }

  public String selection() {
    return selection;
  }
}
