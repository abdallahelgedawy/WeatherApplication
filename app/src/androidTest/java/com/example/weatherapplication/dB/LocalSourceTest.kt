package com.example.weatherapplication.dB

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.weatherapplication.getOrAwaitValue
import com.example.weatherapplication.model.Location
import com.example.weatherapplication.model.Repository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class LocalSourceTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    private lateinit var localDataSource: ConcreteLocalSource
    private lateinit var database: DataDatabase
    lateinit var firstWeather : Location
    lateinit var secondWeather : Location
    lateinit var thirdWeather : Location



    fun createDummyData(){
        firstWeather= Location(33.3 , 33.3)
        secondWeather= Location(35.6,35.6)
        thirdWeather= Location(35.6,35.6)

    }

    @Before
    fun setup() {
        createDummyData()
        var app: Application = ApplicationProvider.getApplicationContext()
        database = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),DataDatabase::class.java)
            .allowMainThreadQueries()
            .build()

        localDataSource = ConcreteLocalSource.getInstance(app)
    }
    @After
    fun cleanUp() {
        database.close()
    }

    @Test
    fun insertData_insertOneLocation_getListData() = runBlockingTest {
        localDataSource.insertData(firstWeather)
        val result = localDataSource.getstoredData().getOrAwaitValue {  }
        MatcherAssert.assertThat(result, CoreMatchers.not(CoreMatchers.nullValue()))
        Assertions.assertThat(result).contains(firstWeather)
    }

    @Test
    fun getMyDatan_insertThreeLocations_getListContainsAllLocations() = runBlockingTest {
        localDataSource.insertData(firstWeather)
        localDataSource.insertData(secondWeather)
        localDataSource.insertData(thirdWeather)
        val result = localDataSource.getstoredData().getOrAwaitValue {  }
        MatcherAssert.assertThat(result, CoreMatchers.not(CoreMatchers.nullValue()))

    }

    @Test
    fun deleteMyData_deleteOneLocation_getListofTheUndeletedLocation() = runBlockingTest {
        localDataSource.insertData(firstWeather)
        localDataSource.insertData(secondWeather)
        localDataSource.insertData(thirdWeather)
        localDataSource.deleteData(secondWeather)
        val result = localDataSource.getstoredData().getOrAwaitValue {  }


        MatcherAssert.assertThat(result, CoreMatchers.not(CoreMatchers.nullValue()))
        Assertions.assertThat(result).doesNotContain(secondWeather)

    }
}
