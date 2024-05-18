package com.example.contactapppam6.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.contactapppam6.R;
import com.example.contactapppam6.database.ContactModel;
import com.example.contactapppam6.databinding.ActivityInsertUpdateBinding;
import com.example.contactapppam6.repository.ViewModelFactory;

public class InsertUpdateActivity extends AppCompatActivity {

    public static final String EXTRA_CONTACT = "extra_contact";

    private boolean isModified = false;
    private ContactModel contactModel;
    private ActivityInsertUpdateBinding binding;
    private InsertUpdateViewModel insertUpdateViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityInsertUpdateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        insertUpdateViewModel = obtainViewModel(InsertUpdateActivity.this);

        contactModel = getIntent().getParcelableExtra(EXTRA_CONTACT);
        if (contactModel != null) {
            isModified = true;
        } else {
            contactModel = new ContactModel();
        }


        String actionBarTitle, btnTitle;
        if (isModified) {
            actionBarTitle = "Ubah";
            btnTitle = "Perbarui";

            binding.etName.setText(contactModel.getName());
            binding.etNumber.setText(contactModel.getNumber());
            binding.etGroup.setText(contactModel.getGroup());
            binding.etInstagram.setText(contactModel.getInstagram());
        }else {
            actionBarTitle = "Tambah";
            btnTitle = "Simpan";
        }

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(actionBarTitle);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        binding.btnSubmit.setOnClickListener(v-> {
            String name = binding.etName.getText().toString();
            String number = binding.etNumber.getText().toString();
            String instagram = binding.etInstagram.getText().toString();
            String group = binding.etGroup.getText().toString();

            if (name.isEmpty() || number.isEmpty() || instagram.isEmpty() || group.isEmpty()){
                Toast.makeText(this, "Please fill in the entire form", Toast.LENGTH_SHORT).show();
            } else {
                contactModel.setName(name);
                contactModel.setNumber(number);
                contactModel.setGroup(group);
                contactModel.setInstagram(instagram);
                Intent intent = new Intent();
                intent.putExtra(EXTRA_CONTACT, contactModel);
                if (isModified) {
                    insertUpdateViewModel.update(contactModel);
                    Toast.makeText(this, "Contact has been edited", Toast.LENGTH_SHORT).show();
                } else {
                    insertUpdateViewModel.insert(contactModel);
                    Toast.makeText(this, "Contact has been added", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });

        binding.btnClear.setOnClickListener(v -> clearData());
    }

    @NonNull
    private static InsertUpdateViewModel obtainViewModel(AppCompatActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());
        return new ViewModelProvider(activity, (ViewModelProvider.Factory) factory).get(InsertUpdateViewModel.class);
    }
    private void clearData(){
        binding.etName.setText("");
        binding.etNumber.setText("");
        binding.etInstagram.setText("");
        binding.etGroup.setText("");
    }
}