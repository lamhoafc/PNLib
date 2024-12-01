package com.example.asm_mob2041;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.example.asm_mob2041.dao.ThuThuDAO;

public class DangNhapActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dang_nhap);

        TextInputEditText edtUser = findViewById(R.id.edtUser);
        TextInputEditText edtPass = findViewById(R.id.edtPassword);
        AppCompatButton btnDangNhap = findViewById(R.id.btnDangNhap);

        ThuThuDAO thuThuDAO = new ThuThuDAO(this);

        btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = edtUser.getText().toString();
                String pass = edtPass.getText().toString();
                if (thuThuDAO.checkDangNhap(user, pass)) {
                    //lưu


                    startActivity(new Intent(DangNhapActivity.this, MainActivity.class));

                } else {
                    Toast.makeText(DangNhapActivity.this, "Username hoặc Password không đúng !", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}