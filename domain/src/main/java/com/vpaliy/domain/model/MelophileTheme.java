package com.vpaliy.domain.model;

import java.util.Arrays;
import java.util.List;

public class MelophileTheme {
  private String theme;
  private List<String> tags;

  public MelophileTheme() {
  }

  public MelophileTheme(String theme, List<String> tags) {
    this.theme = theme;
    this.tags = tags;
  }

  public List<String> getTags() {
    return tags;
  }

  public String getTheme() {
    return theme;
  }

  public static MelophileTheme create(String theme, String... tags) {
    return new MelophileTheme(theme, Arrays.asList(tags));
  }
}
