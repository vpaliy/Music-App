package com.vpaliy.melophile.ui.search;

public class MoreEvent {

  public final SearchResult<?> result;

  public MoreEvent(SearchResult<?> result) {
    this.result = result;
  }

  public static MoreEvent requestMore(SearchResult<?> result) {
    return new MoreEvent(result);
  }
}
