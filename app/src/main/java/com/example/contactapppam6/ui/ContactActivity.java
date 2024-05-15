package com.example.contactapppam6.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.contactapppam6.database.ContactModel;
import com.example.contactapppam6.R;
import com.example.contactapppam6.databinding.ActivityContactBinding;

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

        contactAdapter = new ContactAdapter(this);
        binding.recycleContact.setLayoutManager(new LinearLayoutManager(this));
        binding.recycleContact.setAdapter(contactAdapter);

        contactViewModel = new ViewModelProvider(this).get(ContactViewModel.class);
        contactViewModel.getAllContacts().observe(this, contacts -> {
            contactAdapter.setContacts(contacts);
        });

        binding.tvOption.setOnClickListener(v ->{
            if (binding.recycleContact.getVisibility() == View.VISIBLE){
                binding.recycleContact.setVisibility(View.GONE);
                binding.layoutAdd.setVisibility(View.VISIBLE);
                clearData();
            } else {
                binding.recycleContact.setVisibility(View.VISIBLE);
                binding.layoutAdd.setVisibility(View.GONE);
            }
        });

        binding.btnClear.setOnClickListener(v -> clearData());

        binding.btnSubmit.setOnClickListener(v -> {
            String name = binding.etName.getText().toString();
            String number = binding.etNumber.getText().toString();
            String instagram = binding.etInstagram.getText().toString();
            String group = binding.etGroup.getText().toString();

            if (name.isEmpty() || number.isEmpty() || instagram.isEmpty() || group.isEmpty()){
                Toast.makeText(this, "Please fill in the entire form", Toast.LENGTH_SHORT).show();
            }  else {
                ContactModel contact = new ContactModel(name, number, group, instagram);
                contactViewModel.insert(contact);
                binding.recycleContact.setVisibility(View.VISIBLE);
                binding.layoutAdd.setVisibility(View.GONE);
            }
        });

        contactAdapter.setOnItemClickListener((contact, view) -> {
            contactViewModel.delete(contact);
            Toast.makeText(ContactActivity.this, "Contact deleted", Toast.LENGTH_SHORT).show();
        });


    }

    public void clearData(){
        binding.etName.setText("");
        binding.etNumber.setText("");
        binding.etInstagram.setText("");
        binding.etGroup.setText("");
    }
}