package com.vpaliy.data.mapper

import com.vpaliy.data.FakeDataProvider
import com.vpaliy.domain.model.User
import com.vpaliy.soundcloud.model.UserEntity
import org.mockito.junit.MockitoJUnitRunner
import junit.framework.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(MockitoJUnitRunner::class)
class UserMapperTest {

    private val mapper = UserMapper()

    @Test
    fun mapsFakeToReal() {
        val userEntity=FakeDataProvider.buildUserEntity()
        val userReal=mapper.map(userEntity)
        compare(userReal,userEntity)
    }

    private fun compare(real: User?, fake:UserEntity?){
        if(real==null || fake==null){
            assertEquals(real,fake)
            return
        }
        assertEquals(real.avatarUrl,fake.avatar_url)
        assertEquals(real.description,fake.description)
        assertEquals(real.fullName,fake.full_name)
        assertEquals(real.id,fake.id)
        assertEquals(real.nickName,fake.username)
    }

    @Test
    fun mapsRealToFake(){
        val real=FakeDataProvider.buildUser()
        val fake=mapper.reverse(real)
        compare(real,fake)
    }
}
