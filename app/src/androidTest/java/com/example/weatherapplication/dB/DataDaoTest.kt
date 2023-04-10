package com.example.weatherapplication.dB

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.runtime.collectAsState
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.example.weatherapplication.alert.TimeDate
import com.example.weatherapplication.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert
import org.hamcrest.Matchers
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class DataDaoTest {
    @get:Rule
    val instance = InstantTaskExecutorRule()
    private lateinit var database :DataDatabase
    lateinit var firstdate:TimeDate
    lateinit var seconddate:TimeDate
    lateinit var thirddate:TimeDate

    fun generateDummyData(){
        firstdate= TimeDate("12", "12" , "12" , "12")
        seconddate= TimeDate("22", "22" , "22" , "22")
        thirddate= TimeDate("32", "32" , "32" , "32")

    }
    @Before
    fun createDataBase(){
        generateDummyData()
        database=
            Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),DataDatabase::class.java).build()
    }

    @After
    fun closeDataBase(){
        database.close()
    }

    @Test
    fun getAllData_insertThreeAlerts_getListOfThreeAlerts() = runBlockingTest{

        database.getDataDao().insertDateTime(firstdate)
        database.getDataDao().insertDateTime(seconddate)
        database.getDataDao().insertDateTime(thirddate)
        val loaded = database.getDataDao().getDate().getOrAwaitValue{  }

        MatcherAssert.assertThat(loaded, Matchers.`is`(listOf(firstdate , seconddate , thirddate)))


    }

    @Test
    fun insertDateAndTime_InsertAlert_getListOfOneAlert() = runBlockingTest{
        database.getDataDao().insertDateTime(firstdate)
        val loaded = database.getDataDao().getDate().getOrAwaitValue {  }
        MatcherAssert.assertThat(loaded, Matchers.`is`(listOf(firstdate)))
    }

    @Test
    fun deleteDateAndTime_deleteOneAlert_getListOfAllAlertsExceptTheDeeleted() = runBlockingTest{
        database.getDataDao().insertDateTime(firstdate)
        database.getDataDao().insertDateTime(seconddate)
        database.getDataDao().insertDateTime(thirddate)
        database.getDataDao().deleteDateTime(firstdate)
        val loaded = database.getDataDao().getDate().getOrAwaitValue {  }
        MatcherAssert.assertThat(loaded, Matchers.`is`(listOf(seconddate, thirddate)))
    }
}
