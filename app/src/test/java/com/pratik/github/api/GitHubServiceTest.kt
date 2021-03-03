package com.pratik.github.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.pratik.github.data.remote.api.GitHupService
import com.pratik.github.data.remote.dto.Root
import junit.framework.Assert.assertNotNull
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class GitHubServiceTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: GitHupService

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GitHupService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun requestGitHubCommits() {
        runBlocking {
            enqueueResponse(emptyList())
            val resultResponse = service.getCommits("palantir", "blueprint", 1, 2).body()
            val request = mockWebServer.takeRequest()
            assertNotNull(resultResponse)
            assertNotNull(request)
        }
    }


    private fun enqueueResponse(data: List<Root>, headers: Map<String, String> = emptyMap()) {
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(
            mockResponse.setBody(
                data.toString()
            )
        )
    }
}