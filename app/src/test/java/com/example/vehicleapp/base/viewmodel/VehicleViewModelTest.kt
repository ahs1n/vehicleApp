package com.example.vehicleapp.base.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.vehicleapp.MainCoroutinesRule
import com.example.vehicleapp.base.repository.ResponseStatusCallbacks
import com.example.vehicleapp.base.viewmodel.usecases.VehicleUseCaseRemote
import com.example.vehicleapp.base.viewmodel.usecases.UserUseCase
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/**
 * @author AliAzazAlam on 4/21/2021.
 */
@RunWith(JUnit4::class)
class VehicleViewModelTest {

    // Subject under test
    private lateinit var viewModel: VehicleViewModel

    @ExperimentalCoroutinesApi
    @get:Rule
    var coroutinesRule = MainCoroutinesRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @MockK
    lateinit var userUseCase: UserUseCase

    @MockK
    lateinit var vehicleUseCaseRemote: VehicleUseCaseRemote

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `test getAllImagesFromRemoteServer() returns list of images`() = runBlocking {
        // Given
        /*val imagesListObserver =
            mockk<Observer<ResponseStatusCallbacks<FetchDataModel>>>(relaxed = true)*/

        // When
/*        coEvery { userUseCase.invoke(any(), any()) }
            .returns(flowOf(MockTestUtil.createImages()))

        // Invoke
        viewModel = ImageViewModel(userUseCase, imageSearchUseCase)
        viewModel.imagesResponse.observeForever(imagesListObserver)

        // Then
        coVerify(exactly = 1) { userUseCase.invoke() }
        verify { imagesListObserver.onChanged(match { it.data != null }) }
        verify { imagesListObserver.onChanged(match { it.data?.imagesInfo?.size == 1 }) }*/

    }

    @Test
    fun `test getAllImagesFromRemoteServer() returns zero list of images`() = runBlocking {
        // Given
        /*val imagesListObserver =
            mockk<Observer<ResponseStatusCallbacks<FetchDataModel>>>(relaxed = true)*/

        // When
/*        coEvery { userUseCase.invoke(any(),any()) }.returns(flowOf(MockTestUtil.createZeroImage()))

        // Invoke
        viewModel = ImageViewModel(userUseCase, imageSearchUseCase)
        viewModel.imagesResponse.observeForever(imagesListObserver)

        // Then
        coVerify(exactly = 1) { userUseCase.invoke() }
        verify { imagesListObserver.onChanged(match { it.data != null }) }
        verify { imagesListObserver.onChanged(match { it.data?.imagesInfo?.isNullOrEmpty() ?: true }) }*/

    }

    @After
    fun tearDown() {
    }
}