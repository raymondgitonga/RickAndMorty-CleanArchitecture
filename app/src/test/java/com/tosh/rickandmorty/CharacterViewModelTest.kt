package com.tosh.rickandmorty

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.tosh.rickandmorty.model.CharResult
import com.tosh.rickandmorty.model.CharacterModel
import com.tosh.rickandmorty.model.CharactersService
import com.tosh.rickandmorty.viewmodel.CharacterViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class CharacterViewModelTest {

    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var charactersService: CharactersService

    @InjectMocks
    var characterViewModel = CharacterViewModel()

    private var testSingle: Single<CharacterModel>? = null

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
    }

    @Before
    fun setupRxchedulers(){
        val immediate = object : Scheduler(){
            override fun scheduleDirect(run: Runnable?, delay: Long, unit: TimeUnit?): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }
            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
            }
        }

        RxJavaPlugins.setInitIoSchedulerHandler { immediate }
        RxJavaPlugins.setInitComputationSchedulerHandler { immediate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { immediate }
        RxJavaPlugins.setInitSingleSchedulerHandler { immediate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { immediate }
    }

    @Test
    fun getCharactersIsSuccessful(){
        val character = CharacterModel(
            listOf(CharResult("Male",1,
            "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            "Rick Sanchez","Human","Alive" )))


        testSingle = Single.just(character)

        Mockito.`when`(charactersService.getCharacters()).thenReturn(testSingle)

        characterViewModel.refresh()

        assertEquals(1, characterViewModel.characters.value!!.size)
        assertEquals(false, characterViewModel.charactersLoadError.value)
        assertEquals(false, characterViewModel.loading.value)
    }

    @Test
    fun getCharactersFails(){
        testSingle = Single.error(Throwable())

        Mockito.`when`(charactersService.getCharacters()).thenReturn(testSingle)

        characterViewModel.refresh()

        assertEquals(true, characterViewModel.charactersLoadError.value)
        assertEquals(false, characterViewModel.loading.value)

    }

}