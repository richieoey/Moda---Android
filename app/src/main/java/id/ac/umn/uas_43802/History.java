package id.ac.umn.uas_43802;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import id.ac.umn.uas_43802.databinding.ActivityHistoryBinding;
import id.ac.umn.uas_43802.databinding.ActivitySignUpBinding;

public class History extends AppCompatActivity {

    ActivityHistoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.backbutton.setOnClickListener(view -> History.super.onBackPressed() );

    }
}