package com.ratushny.modulotech.data.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.ratushny.modulotech.data.database.Database
import com.ratushny.modulotech.data.database.mapper.convertToDatabaseEntity
import com.ratushny.modulotech.domain.model.device.Device.Heater
import com.ratushny.modulotech.domain.model.device.DeviceMode
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DeviceRepositoryTest {

    private val context = ApplicationProvider.getApplicationContext<Context>()

    private val db: Database by lazy {
        Room.inMemoryDatabaseBuilder(
            context,
            Database::class.java
        ).build()
    }

    private val deviceDao by lazy { db.deviceDao() }

    private val repository: DeviceRepository by lazy { DeviceRepository(deviceDao) }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun loadDevices_insertToEmpty_returnNotEmpty() {
        val heater = Heater(
            id = 0,
            deviceName = "test",
            mode = DeviceMode.OFF,
            temperature = 13f
        )
        runBlocking {
            MatcherAssert.assertThat(
                repository.loadDevices(),
                CoreMatchers.equalTo(emptyList())
            )
            deviceDao.insert(heater.convertToDatabaseEntity())
            MatcherAssert.assertThat(repository.loadDevices(), CoreMatchers.equalTo(listOf(heater)))
        }
    }

    @Test
    fun updateDevice_listOfHeater_listOfUpdatedHeater() {
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
    fun deleteDevice_listOfHeater_removeHeaterFromList() {
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