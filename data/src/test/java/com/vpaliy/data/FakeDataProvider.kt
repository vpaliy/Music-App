package com.vpaliy.data

import com.vpaliy.domain.model.Playlist
import com.vpaliy.domain.model.Track
import com.vpaliy.domain.model.User
import java.util.Arrays
import java.util.LinkedList

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
    val FAKE_COUNT = 1
    val FAKE_BOOLEAN = true

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
        playlist.tracks = buildTrack(10)
        return playlist
    }

    private fun fakeGenres(): List<String> {
        return Arrays.asList("Rock", "Indie", "Pop", "Pop Rock", "Alternative", "Rap", "Others")
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

    fun buildPlaylist(count: Int): List<Playlist> {
        var count = count
        val list = LinkedList<Playlist>()
        while (count >= 0) {
            list.add(buildPlaylist())
            count--
        }
        return list
    }

    fun buildUser(count: Int): List<User> {
        var count = count
        val list = LinkedList<User>()
        while (count >= 0) {
            list.add(buildUser())
            count--
        }
        return list
    }

    fun buildTrack(count: Int): List<Track> {
        var count = count
        val list = LinkedList<Track>()
        while (count >= 0) {
            list.add(buildTrack())
            count--
        }
        return list
    }
}
