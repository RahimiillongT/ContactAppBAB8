package com.example.contactapppam6.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {ContactModel.class}, version = 1)
public abstract class ContactDB extends RoomDatabase {
    public abstract ContactDAO contactDAO();
    public static volatile ContactDB INSTANCE;

    public static ContactDB getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (ContactDB.class){
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ContactDB.class, "contact_db").build();
            }
        }
        return INSTANCE;
    }
}
