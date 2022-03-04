package com.krachkovsky.it_anews

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.krachkovsky.it_anews.presentation.settings.manager.SharedPrefsManager
import com.krachkovsky.it_anews.presentation.settings.model.DayNightMode
import com.krachkovsky.it_anews.presentation.settings.model.LanguageMode
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(RobolectricTestRunner::class)
@Config(manifest= Config.NONE)
class ExampleUnitTest {

    @Test
    fun `test prefs`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val prefs = SharedPrefsManager(context)

        Assert.assertEquals(DayNightMode.SYSTEM, prefs.dayNightMode)
        Assert.assertEquals(LanguageMode.EN, prefs.languageMode)
    }

    @Test
    fun `test flow`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val prefs = SharedPrefsManager(context)

        runBlocking {
            launch {
                //Assert.assertEquals()
            }
        }
    }
}