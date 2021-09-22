package ifeanyi.opara.treasureapplication.di

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ifeanyi.opara.treasureapplication.mainRepository.TreasureRepository
import ifeanyi.opara.treasureapplication.repositoryImplementation.TreasureRepositoryImplementation
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent :: class)
object AppModule {

    @Provides
    @Singleton
    fun provideRepository(application: Application) : TreasureRepository{
        return TreasureRepositoryImplementation(application)
    }

}