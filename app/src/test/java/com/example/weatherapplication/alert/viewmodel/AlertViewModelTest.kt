package com.example.weatherapplication.alert.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.weatherapplication.FakeRepository
import com.example.weatherapplication.alert.TimeDate
import com.example.weatherapplication.alert.view.Alert
import com.example.weatherapplication.getOrAwaitValue
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

class AlertViewModelTest{
    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatchersRule = MainDispatchersRule()
    lateinit var viewModel : AlertViewModel
    lateinit var repo : FakeRepository
    lateinit var firstAlert:TimeDate
    lateinit var secondAlert:TimeDate
    fun generateDummyData(){
        firstAlert= TimeDate("20","20","20","20")
        secondAlert= TimeDate("50","50","50","50")
    }

    @Before
    fun setUp(){
        generateDummyData()
        repo = FakeRepository()
        viewModel = AlertViewModel(repo)

    }
    @Test
    fun `addToAlarm oneFavoriteAlert returnOneFavoriteAlert` ()= runBlockingTest{
         viewModel.addToAlarm(firstAlert)
        var result=viewModel.finaldateTime.observeForever {}
        assertThat(result,not(nullValue()))
    }
}
