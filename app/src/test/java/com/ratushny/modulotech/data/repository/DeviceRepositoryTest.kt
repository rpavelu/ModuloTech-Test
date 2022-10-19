package com.ratushny.modulotech.data.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ratushny.modulotech.data.database.Database
import com.ratushny.modulotech.data.database.mapper.convertToDatabaseEntity
import com.ratushny.modulotech.domain.model.device.Device
import com.ratushny.modulotech.domain.model.device.DeviceMode
import com.ratushny.modulotech.domain.model.device.Heater
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DeviceRepositoryTest {

    private lateinit var db: Database
    private lateinit var repository: DeviceRepository
    private val deviceDao by lazy { db.deviceDao() }

    @Before
    fun init() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            Database::class.java
        ).build()
        repository = DeviceRepository(deviceDao)
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun loadDevices() {
        val heater = Heater(
            id = 0,
            deviceName = "test",
            mode = DeviceMode.OFF,
            temperature = 13f
        )
        runBlocking {
            MatcherAssert.assertThat(
                repository.loadDevices(),
                CoreMatchers.equalTo(emptyList<Device>())
            )
            deviceDao.insert(heater.convertToDatabaseEntity())
            MatcherAssert.assertThat(repository.loadDevices(), CoreMatchers.equalTo(listOf(heater)))
        }
    }

    @Test
    fun updateDevice() {
        val heater = Heater(
            id = 0,
            deviceName = "test",
            mode = DeviceMode.OFF,
            temperature = 13f
        )
        val changedDevice = heater.copy(temperature = 14f, deviceName = "updated_device")
        runBlocking {
            deviceDao.deleteAll()
            deviceDao.insert(heater.convertToDatabaseEntity())
            MatcherAssert.assertThat(repository.loadDevices(), CoreMatchers.equalTo(listOf(heater)))
            repository.updateDevice(changedDevice)
            MatcherAssert.assertThat(
                repository.loadDevices(),
                CoreMatchers.equalTo(listOf(changedDevice))
            )
        }
    }

    @Test
    fun deleteDevice() {
        val heater = Heater(
            id = 0,
            deviceName = "test",
            mode = DeviceMode.OFF,
            temperature = 13f
        )
        runBlocking {
            deviceDao.deleteAll()
            deviceDao.insert(heater.convertToDatabaseEntity())
            MatcherAssert.assertThat(repository.loadDevices(), CoreMatchers.equalTo(listOf(heater)))
            repository.deleteDevice(heater)
            MatcherAssert.assertThat(repository.loadDevices(), CoreMatchers.equalTo(emptyList()))
        }
    }
}