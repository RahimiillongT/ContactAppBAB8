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
import com.example.contactapppam6.databinding.ActivityDetailContactBinding;

public class DetailContactActivity extends AppCompatActivity {

    private ActivityDetailContactBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityDetailContactBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getIntent().getExtras() != null){
            Bundle bundle = getIntent().getExtras();
            String getCName = bundle.getString("cname");
            String getCNumber = bundle.getString("cnumber");
            String getCInstagram = bundle.getString("cinstagram");
            String getCGroup = bundle.getString("cgroup");

            binding.tvName.setText(getCName);
            binding.tvNumber.setText(getCNumber);
            binding.tvInstagram.setText(getCInstagram);
            binding.tvGroup.setText(getCGroup);

            binding.btnCall.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + getCNumber));
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, 1);
                    return;
                }
                startActivity(intent);
            });

            binding.btnMessage.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("sms:" + getCNumber));
                startActivity(intent);
            });

            binding.layoutWhatsapp.setOnClickListener(v -> {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setPackage("com.whatsapp");
                String url = "https://api.whatsapp.com/send?phone=" + getCNumber;
                sendIntent.setData(Uri.parse(url));
                startActivity(sendIntent);
            });

            binding.btnInstagram.setOnClickListener(v -> {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/" + getCInstagram)));
            });
        }

        binding.backLink.setOnClickListener(v -> finish());
    }
}