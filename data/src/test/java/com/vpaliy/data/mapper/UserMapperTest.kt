package com.vpaliy.data.mapper

import com.vpaliy.data.FakeDataProvider
import com.vpaliy.domain.model.User
import com.vpaliy.soundcloud.model.UserEntity
import junit.framework.Assert.assertEquals
import org.powermock.api.mockito.PowerMockito
import org.powermock.modules.junit4.PowerMockRunner
import org.mockito.Matchers.anyString
import org.mockito.Mockito.times
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.core.classloader.annotations.PrepareForTest

@RunWith(PowerMockRunner::class)
@PrepareForTest(MapperUtils::class)
class UserMapperTest {

  private val mapper = UserMapper()

  @Test
  fun mapsFakeToReal() {
    PowerMockito.mockStatic(MapperUtils::class.java)
    val userEntity = FakeDataProvider.buildUserEntity()
    val userReal = mapper.map(userEntity)
    compare(userReal, userEntity)
    PowerMockito.verifyStatic(times(4))
    MapperUtils.convertToInt(anyString())
  }

  private fun compare(real: User?, fake: UserEntity?) {
    if (real == null || fake == null) {
      assertEquals(real, fake)
      return
    }
    assertEquals(real.avatarUrl, fake.avatar_url)
    assertEquals(real.description, fake.description)
    assertEquals(real.fullName, fake.full_name)
    assertEquals(real.id, fake.id)
    assertEquals(real.nickName, fake.username)
  }

  @Test
  fun mapsRealToFake() {
    val real = FakeDataProvider.buildUser()
    val fake = mapper.reverse(real)
    compare(real, fake)
  }
}
