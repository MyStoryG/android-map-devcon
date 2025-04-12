package devcon.map

import com.google.gson.Gson
import devcon.map.data.repository.SearchRepository
import devcon.map.feature.SearchViewModel
import devcon.map.model.KakaoMapSearchResponse
import devcon.map.model.Keyword
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import java.io.File

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var fakeRepository: SearchRepository
    private lateinit var viewModel: SearchViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        fakeRepository = mockk {
            coEvery { fetchSearchKeyword() } returns emptyList()
        }
        viewModel = SearchViewModel(fakeRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `검색어를 추가 후 UI 상태를 업데이트한다`() = runTest(testDispatcher) {
        val keywordToAdd = Keyword(word = "Cafe")
        val updatedKeywords = listOf(Keyword(word = "Park"), Keyword(word = "Cafe"))
        coEvery { fakeRepository.searchKeyword(keywordToAdd) } returns updatedKeywords

        viewModel.searchKeyword(keywordToAdd)
        advanceUntilIdle()

        assertEquals(updatedKeywords, viewModel.uiState.value.keywords)
    }

    @Test
    fun `검색어를 삭제 후 UI 상태를 업데이트한다`() = runTest(testDispatcher) {
        val keywordToDelete = Keyword(word = "Park")
        val updatedKeywords = listOf(Keyword(word = "Cafe"))
        coEvery { fakeRepository.deleteKeyword(keywordToDelete) } returns updatedKeywords

        viewModel.deleteKeyword(keywordToDelete)
        advanceUntilIdle()

        assertEquals(updatedKeywords, viewModel.uiState.value.keywords)
    }

    @Test
    fun `검색어를 KakaoMap API에서 가져와 UI 상태를 업데이트한다`() = runTest(testDispatcher) {
        val json = File("src/test/assets/KakaoMapSearchResponse.json").readText()
        val body = Gson().fromJson(json, KakaoMapSearchResponse::class.java)
        val response = Response.success(200, body)
        coEvery { fakeRepository.getSearchKeyword(1, 15, "Cafe") } returns response

        viewModel.getSearchKeyword(1, 15, "Cafe")
        advanceUntilIdle()

        assertTrue(response.isSuccessful)
        assertEquals(body.documents.first().placeName, viewModel.uiState.value.places.first().name)
    }
}
