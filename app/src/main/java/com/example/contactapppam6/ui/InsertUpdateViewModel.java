package com.example.contactapppam6.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.contactapppam6.database.ContactModel;
import com.example.contactapppam6.repository.ContactRepository;

public class InsertUpdateViewModel extends AndroidViewModel {
    private ContactRepository repository;

    public InsertUpdateViewModel(@NonNull Application application){
        super(application);
        repository = new ContactRepository(application);
    }

    public void insert(ContactModel contactModel){
        repository.insert(contactModel);
    }

    public void update(ContactModel contactModel){
        repository.update(contactModel);
    }
    public void delete(ContactModel contactModel){
        repository.delete(contactModel);
    }
}
