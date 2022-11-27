package com.example.mobiledatausage.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.mobiledatausage.repository.FakeRepository
import com.example.mobiledatausage.ui.list.ListViewModel
import com.example.mobiledatausage.utils.FakeDispatcherProvider
import com.example.mobiledatausage.utils.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class ListViewModelTest {
    private lateinit var viewModel: ListViewModel

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testGetMobileDataUsageSuccess() = runTest{
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        viewModel = ListViewModel(FakeRepository(), FakeDispatcherProvider(testDispatcher, testDispatcher, testDispatcher, testDispatcher))
        viewModel.getMobileDataUsage()
        val value = viewModel.mutableAnnualLiveData.getOrAwaitValue()
        assertThat(value).isNotEmpty()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun testGetMobileDataUsageError() = runTest{
        val fakeRepository = FakeRepository()
        fakeRepository.shouldReturnNetworkError = true
        val testDispatcher = UnconfinedTestDispatcher(testScheduler)
        viewModel = ListViewModel(fakeRepository, FakeDispatcherProvider(testDispatcher, testDispatcher, testDispatcher, testDispatcher))
        viewModel.getMobileDataUsage()
        val value = viewModel.mutableAnnualLiveData.getOrAwaitValue()
        assertThat(value).isEmpty()
    }
}