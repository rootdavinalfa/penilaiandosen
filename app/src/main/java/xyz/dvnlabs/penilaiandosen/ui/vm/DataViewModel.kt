/*
 * Copyright (c) 2020.
 * Davin Alfarizky Putra Basudewa , dbasudewa@gmail.com
 * Educational References Only
 */

package xyz.dvnlabs.penilaiandosen.ui.vm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf
import xyz.dvnlabs.penilaiandosen.data.MainDatabase
import xyz.dvnlabs.penilaiandosen.data.view.UserCourseDosenView

@KoinApiExtension
class DataViewModel(application: Application) : AndroidViewModel(application), KoinComponent {

    private val mainDatabase: MainDatabase by inject { parametersOf(application) }

    suspend fun getUserCourse(username: String): LiveData<List<UserCourseDosenView>> {
        return flow {
            emit(
                mainDatabase.userCourseDAO()
                    .getUserCourseDosenByUsername(username)
            )
        }.asLiveData()

    }

}