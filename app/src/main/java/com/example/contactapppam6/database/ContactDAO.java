package com.example.contactapppam6.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert (ContactModel contactModel);

    @Update
    void update (ContactModel contactModel);

    @Delete
    void delete (ContactModel contactModel);

    @Query("SELECT * from contacts ORDER BY id ASC")
    LiveData<List<ContactModel>> getAllContacts();


}
