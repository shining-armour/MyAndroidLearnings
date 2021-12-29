package com.example.affirmations

import android.content.Context
import com.example.affirmations.adapter.AffirmationAdapter
import com.example.affirmations.model.Affirmation
import org.junit.Test

import org.junit.Assert.*
import org.mockito.Mockito.mock

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    /**
     * Context is the context of the current state of the app, but remember that unit tests run on the JVM and not on an actual device, so there is no Context.
     * The mock method allows us to create a "mocked" instance of a Context.
     * It doesn't have any real functionality, but it can be used to test methods that require a context.
     */
    private val context = mock(Context::class.java)


    /**
     * The goal of this test is to make sure that the size of the adapter is the size of the list that was passed to the adapter.
     */
    @Test
    fun adapter_size(){
        val data = listOf(
            Affirmation(R.string.affirmation1, R.drawable.image1),
            Affirmation(R.string.affirmation2, R.drawable.image2)
        )
        val adapter = AffirmationAdapter(context,data)

        // assertEquals() method and compare the values of the expected list size with the actual adapter size. Otherwise throws the given error message
        assertEquals("AffirmationAdapter is not the correct size", data.size,adapter.itemCount)
    }


}