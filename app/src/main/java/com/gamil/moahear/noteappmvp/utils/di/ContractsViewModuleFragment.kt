package com.gamil.moahear.noteappmvp.utils.di

import androidx.fragment.app.Fragment
import com.gamil.moahear.noteappmvp.ui.add.AddContracts
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
class ContractsViewModuleFragment {
    @Provides
    fun provideAddView(fragment: Fragment): AddContracts.View = fragment as AddContracts.View
}