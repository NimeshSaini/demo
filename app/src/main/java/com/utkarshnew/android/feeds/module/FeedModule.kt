package com.utkarshnew.android.feeds.module

import com.appsquadz.mvvmwithretrofit.repository.Repository
import com.utkarshnew.android.Room.UtkashRoom
import com.utkarshnew.android.Utils.MakeMyExam
import com.utkarshnew.android.Utils.Network.APIInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FeedModule {


    @Provides
    @Singleton
    fun repositroyInstance(apiInterface: APIInterface): Repository {
        return Repository(apiInterface)
    }

    @Provides
    @Singleton
    fun apiInterfaceInstance(): APIInterface {
        return MakeMyExam.getRetrofitInstance().create(APIInterface::class.java)
    }

    @Provides
    @Singleton
    fun roomDbInsatance(): UtkashRoom {
        return UtkashRoom.getAppDatabase(MakeMyExam.getAppContext())
    }


}