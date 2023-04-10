package com.example.weatherapplication.home.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.weatherapplication.FakeRepository
import com.example.weatherapplication.alert.TimeDate
import com.example.weatherapplication.alert.viewmodel.AlertViewModel
import com.example.weatherapplication.alert.viewmodel.MainDispatchersRule
import com.example.weatherapplication.getOrAwaitValue
import com.example.weatherapplication.model.Current
import com.example.weatherapplication.model.Daily
import com.example.weatherapplication.model.Data
import com.example.weatherapplication.model.Minutely
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith



@RunWith(AndroidJUnit4::class)

class HomeViewModelTest{
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatchersRule = MainDispatchersRule()
    lateinit var viewModel : HomeViewModel
    lateinit var repo : FakeRepository
    lateinit var data: Data
    lateinit var current: Current
    lateinit var minutely: List<Minutely>
    lateinit var current1:List<Current>
    lateinit var daily: List<Daily>
    @Before
    fun setUp(){
        current = Current(0L , null , null , 0.0 ,0.0 , 0L , 0L ,0.0 , 0.0 , 0L , 0L , 0.0 ,
        0L,0.0 , listOf() , null , null )
        minutely = listOf()
        current1 = listOf()
        daily = listOf()
        data=  Data(33.0 , 33.0 , "egypt" , 30L ,current ,minutely ,current1 ,daily)
        repo = FakeRepository()
        viewModel = HomeViewModel(repo)

    }
    @Test
    fun `getData oneData returnOneDataAdded` ()= runBlockingTest{
        viewModel.getData(data.lat , data.lon , "metric" , "en")
        var result=viewModel.mydata.getOrAwaitValue {  }
        assertThat(result, CoreMatchers.not(CoreMatchers.nullValue()))
    }
}