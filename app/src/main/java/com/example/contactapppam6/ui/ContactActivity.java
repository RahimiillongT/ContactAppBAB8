package com.example.contactapppam6.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactapppam6.database.ContactModel;
import com.example.contactapppam6.R;
import com.example.contactapppam6.databinding.ActivityContactBinding;
import com.example.contactapppam6.repository.ViewModelFactory;

import java.util.ArrayList;

public class ContactActivity extends AppCompatActivity {

    private ActivityContactBinding binding;
    private ContactViewModel contactViewModel;
    private ContactAdapter contactAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ContactViewModel contactMainViewModel = obtainViewModel(ContactActivity.this);

        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        contactMainViewModel.getAllContacts().observe(this, contacts -> {
            if (contacts != null) {
                contactAdapter.setContacts(contacts);
            }
        });

        contactAdapter = new ContactAdapter(this);
        binding.recycleContact.setLayoutManager(new LinearLayoutManager(this));
        binding.recycleContact.setHasFixedSize(true);
        binding.recycleContact.setAdapter(contactAdapter);


        binding.tvOption.setOnClickListener(v ->{
            Intent intent = new Intent(this, InsertUpdateActivity.class);
            startActivity(intent);
        });
    }

    @NonNull
    private static ContactViewModel obtainViewModel(AppCompatActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity, (ViewModelProvider.Factory) factory).get(ContactViewModel.class);
    }

}