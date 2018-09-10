package pl.com.c4m.github

import android.app.Application
import org.koin.android.ext.android.startKoin

class GithubApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(mainModule))
    }
}