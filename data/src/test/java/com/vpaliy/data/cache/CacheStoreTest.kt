package com.vpaliy.data.cache

import com.vpaliy.data.FakeDataProvider
import com.vpaliy.domain.model.Playlist

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.util.concurrent.ConcurrentMap
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.given
import org.mockito.Mockito.verify

@RunWith(MockitoJUnitRunner::class)
class CacheStoreTest {

  @Mock
  private val mockMap: ConcurrentMap<String, Playlist>? = null

  @InjectMocks
  private val cacheStore: CacheStore<String, Playlist>? = null

  @Mock
  private val playlist: Playlist? = null

  @Before
  fun setUp() {
    given<Playlist>(mockMap!![any<String>(String::class.java!!)]).willReturn(playlist)
    given(mockMap.size).willReturn(1)
    cacheStore!!.put(FakeDataProvider.FAKE_ID, playlist)
  }

  @Test
  fun returnsStream() {
    val single = cacheStore!!.getStream(FakeDataProvider.FAKE_ID)
    assertNotNull(single)
    verify<ConcurrentMap<String, Playlist>>(mockMap)[FakeDataProvider.FAKE_ID]
  }

  @Test
  fun returnsCacheSize() {
    assertEquals(cacheStore!!.size(), 1)
    verify<ConcurrentMap<String, Playlist>>(mockMap).size
  }
}
