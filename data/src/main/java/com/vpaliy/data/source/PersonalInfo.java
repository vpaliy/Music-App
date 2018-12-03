package com.vpaliy.data.source;

import com.vpaliy.domain.model.Track;
import com.vpaliy.domain.model.User;
import com.vpaliy.soundcloud.SoundCloudService;
import com.vpaliy.soundcloud.model.TrackEntity;
import com.vpaliy.soundcloud.model.UserEntity;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import io.reactivex.schedulers.Schedulers;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PersonalInfo {

  private Set<String> likedIds;
  private Set<String> followedIds;

  @Inject
  public PersonalInfo(SoundCloudService service) {
    likedIds = new HashSet<>();
    followedIds = new HashSet<>();
    //  fetchFollowings(service);
    //  fetchTracks(service);
  }

  private void fetchFollowings(SoundCloudService service) {
    service.fetchMyFollowings()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .subscribe(page -> {
              if (page != null) {
                fetchFollowings(page.collection);
              }
            });
  }

  private void fetchFollowings(List<UserEntity> list) {
    if (list != null) {
      if (!list.isEmpty()) {
        for (UserEntity entity : list) {
          followedIds.add(entity.id);
        }
      }
    }
  }

  private void fetchTracks(SoundCloudService service) {
    service.fetchMyFavoriteTracks()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .subscribe(list -> {
              if (list != null) {
                if (!list.isEmpty()) {
                  for (TrackEntity entity : list) {
                    likedIds.add(entity.id);
                  }
                }
              }
            });
  }

  public boolean amFollowing(String id) {
    return followedIds.contains(id);
  }

  public boolean didLike(String id) {
    return likedIds.contains(id);
  }

  public User amFollowing(User user) {
    if (user != null) {
      user.setFollowed(followedIds.contains(user.getId()));
    }
    return user;
  }

  public List<User> amFollowing(List<User> userList) {
    if (userList != null) {
      for (User user : userList) {
        amFollowing(user);
      }
    }
    return userList;
  }

  public List<Track> didLike(List<Track> tracks) {
    if (tracks != null) {
      for (Track track : tracks) {
        didLike(track);
      }
    }
    return tracks;
  }

  public Track didLike(Track track) {
    if (track != null) {
      track.setLiked(likedIds.contains(track.getId()));
    }
    return track;
  }


  public void removeFollower(String id) {
    followedIds.remove(id);
  }

  public void removeLiked(String id) {
    likedIds.remove(id);
  }

  public void follow(String id) {
    followedIds.add(id);
  }

  public void like(String id) {
    likedIds.add(id);
  }
}
