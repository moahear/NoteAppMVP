package com.gamil.moahear.noteappmvp.utils.di

import android.app.Activity
import com.gamil.moahear.noteappmvp.ui.main.MainContracts
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class ContractsViewModuleActivity {
    @Provides
    fun provideMainView(activity: Activity): MainContracts.View = activity as MainContracts.View
}
