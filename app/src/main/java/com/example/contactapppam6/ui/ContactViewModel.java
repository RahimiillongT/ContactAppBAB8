package com.example.contactapppam6.ui;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.contactapppam6.database.ContactModel;
import com.example.contactapppam6.repository.ContactRepository;

import java.util.List;

public class ContactViewModel extends AndroidViewModel {
    private ContactRepository repository;
    private LiveData<List<ContactModel>> allContacts;

    public ContactViewModel(@NonNull Application application){
        super(application);
        repository = new ContactRepository(application);
        allContacts = repository.getAllContacts();
    }

    public LiveData<List<ContactModel>> getAllContacts(){
        return repository.getAllContacts();
    }


}
