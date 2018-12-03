package com.vpaliy.data

import com.vpaliy.domain.model.Playlist
import com.vpaliy.domain.model.Track
import com.vpaliy.domain.model.User
import com.vpaliy.soundcloud.model.MiniUserEntity
import com.vpaliy.soundcloud.model.PlaylistEntity
import com.vpaliy.soundcloud.model.TrackEntity
import com.vpaliy.soundcloud.model.UserEntity
import java.util.Arrays

object FakeDataProvider {

  val FAKE_ID = "fake_id"
  val FAKE_ART = "fake_art"
  val FAKE_TITLE = "fake_title"
  val FAKE_DESCRIPTION = "fake_description"
  val FAKE_DURATION = "fake_duration"
  val FAKE_RELEASE_DATE = "fake_release_date"
  val FAKE_STREAM_URL = "fake_stream_url"
  val FAKE_ARTIST = "fake_artist"
  val FAKE_NAME = "fake_name"
  val FAKE_FULLNAME = "fake_fullname"
  val FAKE_GENRES = "Rock,Indie,Pop,Pop Rock,Alternative,Rap,Others"
  val FAKE_COUNT = 1
  val FAKE_BOOLEAN = true

  private fun fakeGenres(): List<String> {
    return Arrays.asList("Rock", "Indie", "Pop", "Alternative", "Rap", "Others")
  }

  fun buildPlaylist(): Playlist {
    val playlist = Playlist()
    playlist.artUrl = FAKE_ART
    playlist.description = FAKE_DESCRIPTION
    playlist.duration = FAKE_DURATION
    playlist.id = FAKE_ID
    playlist.releaseDate = FAKE_RELEASE_DATE
    playlist.title = FAKE_TITLE
    playlist.trackCount = FAKE_COUNT
    playlist.genres = fakeGenres()
    playlist.tags = fakeGenres()
    playlist.user = buildUser()
    playlist.tracks = buildList(10, this::buildTrack)
    return playlist
  }

  fun buildPlaylistEntity(): PlaylistEntity {
    val playlist = PlaylistEntity()
    playlist.artwork_url = FAKE_ART
    playlist.description = FAKE_DESCRIPTION
    playlist.duration = FAKE_DURATION
    playlist.id = FAKE_ID
    playlist.release = FAKE_RELEASE_DATE
    playlist.title = FAKE_TITLE
    playlist.track_count = FAKE_COUNT.toString()
    playlist.genre = FAKE_GENRES
    playlist.tag_list = FAKE_GENRES
    playlist.user = buildMiniUser()
    playlist.tracks = buildList(10, this::buildTrackEntity)
    return playlist
  }

  fun buildMiniUser(): MiniUserEntity {
    val miniUser = MiniUserEntity()
    miniUser.avatar_url = FAKE_ART
    miniUser.id = FAKE_ID
    miniUser.username = FAKE_NAME
    return miniUser
  }

  fun buildUser(): User {
    val user = User()
    user.avatarUrl = FAKE_ART
    user.description = FAKE_DESCRIPTION
    user.isFollowed = FAKE_BOOLEAN
    user.fullName = FAKE_FULLNAME
    user.nickName = FAKE_NAME
    user.id = FAKE_ID
    user.followersCount = FAKE_COUNT
    user.followingCount = FAKE_COUNT
    user.likedTracksCount = FAKE_COUNT
    user.playlistsCount = FAKE_COUNT
    user.tracksCount = FAKE_COUNT
    return user
  }

  fun buildUserEntity(): UserEntity {
    val userEntity = UserEntity()
    userEntity.avatar_url = FAKE_ART
    userEntity.description = FAKE_DESCRIPTION
    userEntity.full_name = FAKE_FULLNAME
    userEntity.username = FAKE_NAME
    userEntity.id = FAKE_ID
    userEntity.followers_count = FAKE_COUNT.toString()
    userEntity.followings_count = FAKE_COUNT.toString()
    userEntity.playlist_count = FAKE_COUNT.toString()
    userEntity.track_count = FAKE_COUNT.toString()
    return userEntity
  }


  fun buildTrack(): Track {
    val track = Track()
    track.id = FAKE_ID
    track.streamUrl = FAKE_STREAM_URL
    track.user = buildUser()
    track.artworkUrl = FAKE_ART
    track.duration = FAKE_DURATION
    track.tags = fakeGenres()
    track.releaseDate = FAKE_RELEASE_DATE
    track.title = FAKE_TITLE
    track.artist = FAKE_ARTIST
    track.isLiked = FAKE_BOOLEAN
    return track
  }


  fun buildTrackEntity(): TrackEntity {
    val trackEntity = TrackEntity()
    trackEntity.id = FAKE_ID
    trackEntity.stream_url = FAKE_STREAM_URL
    trackEntity.artwork_url = FAKE_ART
    trackEntity.user = buildMiniUser()
    trackEntity.duration = FAKE_DURATION
    trackEntity.tags_list = FAKE_GENRES
    trackEntity.release = FAKE_RELEASE_DATE
    trackEntity.title = FAKE_TITLE
    trackEntity.is_streamable = true
    return trackEntity
  }

  fun <T> buildList(count: Int, create: () -> T): List<T> {
    val list = arrayListOf<T>()
    for (index in 0..count) {
      list.add(create())
    }
    return list
  }
}
