package com.example.contactapppam6.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.contactapppam6.database.ContactDAO;
import com.example.contactapppam6.database.ContactDB;
import com.example.contactapppam6.database.ContactModel;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ContactRepository {
    private ContactDAO contactDAO;
    private LiveData<List<ContactModel>> allContacts;
    private ExecutorService executorService;

    public ContactRepository(Application application){
        ContactDB db = ContactDB.getDatabase(application);
        contactDAO = db.contactDAO();
        allContacts = contactDAO.getAllContacts();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<ContactModel>> getAllContacts(){
        return contactDAO.getAllContacts();
    }

    public void insert(final ContactModel contactModel){
        executorService.execute(()-> contactDAO.insert(contactModel));
    }

    public void delete(final ContactModel contactModel){
        executorService.execute(()-> contactDAO.delete(contactModel));
    }

    public void update(final ContactModel contactModel){
        executorService.execute(()-> contactDAO.update(contactModel));
    }

}
