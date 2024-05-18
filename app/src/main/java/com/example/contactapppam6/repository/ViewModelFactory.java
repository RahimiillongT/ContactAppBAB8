package com.example.contactapppam6.repository;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.contactapppam6.ui.ContactViewModel;
import com.example.contactapppam6.ui.InsertUpdateViewModel;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private static volatile ViewModelFactory INSTANCE;
    private Application mApplication;

    public ViewModelFactory(Application application){
        mApplication = application;
    }

    public static ViewModelFactory getInstance(Application application) {
        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                INSTANCE = new ViewModelFactory(application);
            }
        }
        return INSTANCE;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass){
        if (modelClass.isAssignableFrom(ContactViewModel.class)){
            return (T) new ContactViewModel(mApplication);
        } else if (modelClass.isAssignableFrom(InsertUpdateViewModel.class)) {
            return (T) new InsertUpdateViewModel(mApplication);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }

}
