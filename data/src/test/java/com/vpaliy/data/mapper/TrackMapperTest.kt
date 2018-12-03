package com.vpaliy.data.mapper

import com.vpaliy.data.FakeDataProvider
import com.vpaliy.domain.model.Track
import com.vpaliy.domain.model.User
import com.vpaliy.soundcloud.model.MiniUserEntity
import com.vpaliy.soundcloud.model.TrackEntity
import com.vpaliy.soundcloud.model.UserEntity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import junit.framework.Assert.assertEquals
import org.mockito.ArgumentMatchers
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class TrackMapperTest {

  @Mock
  lateinit var userMapper: Mapper<User, MiniUserEntity>

  @InjectMocks
  lateinit var mapper: TrackMapper

  val realUser = User()
  val fakeUser = MiniUserEntity()

  @Before
  fun setUp() {
    `when`(userMapper.map(any(MiniUserEntity::class.java))).thenReturn(realUser)
    `when`(userMapper.reverse(any(User::class.java))).thenReturn(fakeUser)
  }

  @Test
  fun mapsFakeToReal() {
    val fake = FakeDataProvider.buildTrackEntity()
    assertEqual(mapper.map(fake), fake)
    verify(userMapper).map(any(MiniUserEntity::class.java))
  }

  @Test
  fun mapsRealToFake() {
    val real = FakeDataProvider.buildTrack()
    assertEqual(real, mapper.reverse(real))
    verify(userMapper).reverse(any(User::class.java))
  }

  @Test
  fun testsNullInput() {
    val fake: TrackEntity? = null
    val real: Track? = null
    assertEqual(mapper.map(fake), mapper.reverse(real))
  }

  private fun assertEqual(real: Track?, fake: TrackEntity?) {
    if (real == null || fake == null) {
      assertEquals(real, fake)
      return
    }
    assertEquals(real.id, fake.id)
    assertEquals(real.artworkUrl, fake.artwork_url)
    assertEquals(real.duration, fake.duration)
    assertEquals(real.releaseDate, fake.release)
    assertEquals(MapperUtils.convertFromStream(real.streamUrl), fake.stream_url)
    assertEquals(real.tags, MapperUtils.splitString(fake.tags_list))
    assertEquals(real.title, fake.title)
  }
}