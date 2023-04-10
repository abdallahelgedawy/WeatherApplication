package com.example.weatherapplication.model

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.weatherapplication.FakeRemoteSource
import com.example.weatherapplication.LocalSourceTest
import com.example.weatherapplication.alert.viewmodel.MainDispatchersRule
import com.example.weatherapplication.dB.LocalSource
import com.example.weatherapplication.getOrAwaitValue
import com.example.weatherapplication.network.RemoteSource
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.not
import org.hamcrest.CoreMatchers.nullValue
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RepositoryTest{
    lateinit var data: Data
    lateinit var current: Current
    lateinit var minutely: List<Minutely>
    lateinit var current1:List<Current>
    lateinit var daily: List<Daily>
    @get:Rule
    val rule = InstantTaskExecutorRule()
    @get:Rule
    val mainDispatchersRule = MainDispatchersRule()
    lateinit var localSource: LocalSource
    lateinit var remoteSource: RemoteSource
    lateinit var repo:RepositoryInterface
lateinit var mylocation : Location


    @Before
    fun setUp(){
        current = Current(0L , null , null , 0.0 ,0.0 , 0L , 0L ,0.0 , 0.0 , 0L , 0L , 0.0 ,
            0L,0.0 , listOf() , null , null )
        minutely = listOf()
        current1 = listOf()
        daily = listOf()
        data=  Data(33.0 , 33.0 , "egypt" , 30L ,current ,minutely ,current1 ,daily)
        mylocation = Location(33.0 , 33.0)
        remoteSource= FakeRemoteSource()
        localSource=LocalSourceTest()
        repo=Repository.getInstance(remoteSource,localSource)

    }

    @Test
    fun `getCurrentWeather inputLatLonUnitsLang currentWeather`()= runBlockingTest{
        val expectedResultWeather=data
        val flowData= repo.getData(10.00,10.00,"en","metric").getOrAwaitValue {  }
        assertEquals(expectedResultWeather,flowData)

    }

    @Test
    fun `addCurrentLocationToDataBase addMycurrentLocation RetrieveTheSameLocation`()= runBlockingTest {
        val addedLocation=mylocation
        repo.insertData(addedLocation)
        var myStoredLocation=repo.getstoredData().collect {  }
        assertThat(myStoredLocation,not(nullValue()))

    }

}
