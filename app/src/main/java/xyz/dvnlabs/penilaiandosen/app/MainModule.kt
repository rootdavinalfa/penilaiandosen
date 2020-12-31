/*
 * Copyright (c) 2020.
 * Davin Alfarizky Putra Basudewa , dbasudewa@gmail.com
 * Educational References Only
 */

package xyz.dvnlabs.penilaiandosen.app

import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.component.KoinApiExtension
import org.koin.dsl.module
import xyz.dvnlabs.penilaiandosen.data.DataImporter
import xyz.dvnlabs.penilaiandosen.data.MainDatabase
import xyz.dvnlabs.penilaiandosen.ui.vm.DataViewModel
import xyz.dvnlabs.penilaiandosen.utils.AssetParser
import xyz.dvnlabs.penilaiandosen.utils.Preferences

@OptIn(KoinApiExtension::class)
val MainModule = module {
    single {
        MainDatabase.getDatabase(androidContext())
    }
    single { AssetParser(androidContext()) }
    single {
        DataImporter(androidContext())
    }
    single { Preferences(androidContext()) }
    viewModel {
        DataViewModel(androidApplication())
    }
}