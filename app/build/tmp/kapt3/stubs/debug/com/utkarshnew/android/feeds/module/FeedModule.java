package com.utkarshnew.android.feeds.module;

import java.lang.System;

@dagger.hilt.InstallIn(value = {dagger.hilt.components.SingletonComponent.class})
@kotlin.Metadata(mv = {1, 6, 0}, k = 1, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0003\u001a\u00020\u0004H\u0007J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0004H\u0007J\b\u0010\b\u001a\u00020\tH\u0007\u00a8\u0006\n"}, d2 = {"Lcom/utkarshnew/android/feeds/module/FeedModule;", "", "()V", "apiInterfaceInstance", "Lcom/utkarshnew/android/Utils/Network/APIInterface;", "repositroyInstance", "Lcom/appsquadz/mvvmwithretrofit/repository/Repository;", "apiInterface", "roomDbInsatance", "Lcom/utkarshnew/android/Room/UtkashRoom;", "app_debug"})
@dagger.Module()
public final class FeedModule {
    
    public FeedModule() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Singleton()
    @dagger.Provides()
    public final com.appsquadz.mvvmwithretrofit.repository.Repository repositroyInstance(@org.jetbrains.annotations.NotNull()
    com.utkarshnew.android.Utils.Network.APIInterface apiInterface) {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Singleton()
    @dagger.Provides()
    public final com.utkarshnew.android.Utils.Network.APIInterface apiInterfaceInstance() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    @javax.inject.Singleton()
    @dagger.Provides()
    public final com.utkarshnew.android.Room.UtkashRoom roomDbInsatance() {
        return null;
    }
}