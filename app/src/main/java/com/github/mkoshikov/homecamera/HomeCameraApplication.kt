package com.github.mkoshikov.homecamera

import android.app.Application
import com.github.mkoshikov.homecamera.repository.`object`.Door
import com.github.mkoshikov.homecamera.model.MainViewModel
import com.github.mkoshikov.homecamera.model.main.CamerasViewModel
import com.github.mkoshikov.homecamera.model.main.DoorsViewModel
import com.github.mkoshikov.homecamera.network.Api
import com.github.mkoshikov.homecamera.repository.CameraRepository
import com.github.mkoshikov.homecamera.repository.DoorRepository
import com.github.mkoshikov.homecamera.repository.`object`.Camera
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val realmConfiguration = RealmConfiguration.create(setOf(Door::class, Camera::class))

fun provideRealm() = Realm.open(realmConfiguration)

fun provideKtor() = HttpClient(OkHttp) {
    followRedirects = true

    install(Logging) {
        logger = Logger.ANDROID
        level = LogLevel.ALL

    }

    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            encodeDefaults = true
        })
    }

    install(DefaultRequest) {
        url("http://cars.cprogroup.ru/api/rubetek/")
    }
}

val appModule = module {
    singleOf(::provideKtor)
    singleOf(::provideRealm)
    singleOf(::Api)

    viewModelOf(::MainViewModel)
    viewModelOf(::CamerasViewModel)
    viewModelOf(::DoorsViewModel)
}

val repositoryModule = module {
    factoryOf(::DoorRepository)
    factoryOf(::CameraRepository)
}

class HomeCameraApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@HomeCameraApplication)
            modules(appModule, repositoryModule)
        }
    }
}