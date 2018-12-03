package com.vpaliy.data.mapper;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public abstract class Mapper<To, From> {

  public abstract To map(From from);

  public abstract From reverse(To to);

  public List<To> map(List<From> froms) {
    if (froms != null) {
      List<To> result = new ArrayList<>(froms.size());
      for (From from : froms) result.add(map(from));
      return result;
    }
    return null;
  }

  public List<From> reverse(List<To> tos) {
    if (tos != null) {
      List<From> result = new ArrayList<From>(tos.size());
      for (To to : tos) result.add(reverse(to));
      return result;
    }
    return null;
  }
}
