package com.example.contactapppam6.ui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.Manifest;

import com.example.contactapppam6.R;
import com.example.contactapppam6.database.ContactModel;
import com.example.contactapppam6.databinding.ActivityDetailContactBinding;

public class DetailContactActivity extends AppCompatActivity {

    ActivityDetailContactBinding binding;
    public static final String EXTRA_CONTACT = "extra_contact";
    ContactModel contactModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityDetailContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getIntent().getExtras() != null){
            Bundle bundle = getIntent().getExtras();
            contactModel = bundle.getParcelable(EXTRA_CONTACT);

            if (contactModel != null){
                binding.tvName.setText(contactModel.getName());
                binding.tvNumber.setText(contactModel.getNumber());
                binding.tvInstagram.setText(contactModel.getInstagram());
                binding.tvGroup.setText(contactModel.getGroup());
            }

            binding.btnCall.setOnClickListener(v -> {
                if (contactModel != null){
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + contactModel.getNumber()));
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                        return;
                    }
                    startActivity(intent);
                }
            });

            binding.btnMessage.setOnClickListener(v -> {
                if (contactModel != null) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse("sms:" + contactModel.getNumber()));
                    startActivity(intent);
                }
            });

            binding.layoutWhatsapp.setOnClickListener(v -> {
                if (contactModel != null) {
                    Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                    sendIntent.setPackage("com.whatsapp");
                    String url = "https://api.whatsapp.com/send?phone=" + contactModel.getNumber();
                    sendIntent.setData(Uri.parse(url));
                    startActivity(sendIntent);
                }
            });

            binding.btnInstagram.setOnClickListener(v -> {
                if (contactModel != null) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/" + contactModel.getInstagram())));
                }
            });
        }

        binding.backLink.setOnClickListener(v -> finish());
    }
}