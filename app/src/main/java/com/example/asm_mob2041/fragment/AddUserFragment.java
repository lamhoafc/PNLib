package com.example.asm_mob2041.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;
import com.example.asm_mob2041.R;
import com.example.asm_mob2041.dao.ThuThuDAO;

public class AddUserFragment extends Fragment {
    TextInputEditText edtUsername, edtPassword, edtConfirmPassword, edtDisplayName;
    Button btnAddUser;
    ThuThuDAO thuThuDAO;

    public AddUserFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_user, container, false);

        edtUsername = view.findViewById(R.id.edtUsername);
        edtPassword = view.findViewById(R.id.edtPassword);
        edtConfirmPassword = view.findViewById(R.id.edtConfirmPassword);
        edtDisplayName = view.findViewById(R.id.edtDisplayName);
        btnAddUser = view.findViewById(R.id.btnAddUser);
        thuThuDAO = new ThuThuDAO(getContext()); // Initialize ThuThuDAO here


        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUsername.getText().toString();
                String password = edtPassword.getText().toString();

                String confirmPassword = edtConfirmPassword.getText().toString();
                String displayName = edtDisplayName.getText().toString();

                if (validate() > 0) {
                    if (thuThuDAO.themTaiKhoanThuThu(username, displayName, password)) {
                        Toast.makeText(getContext(), "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                        edtUsername.setText("");
                        edtPassword.setText("");
                        edtConfirmPassword.setText("");
                        edtDisplayName.setText("");
                    } else {
                        Toast.makeText(getContext(), "Tạo tài khoản thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        // ... your existing code for btnHuy ...

        return view;
    }

    private int validate() {
        int check = 1;
        if (edtUsername.getText().length() == 0 || edtDisplayName.getText().length() == 0 ||
                edtPassword.getText().length() == 0 || edtConfirmPassword.getText().length() == 0) {
            Toast.makeText(getContext(), "Không được để trống", Toast.LENGTH_SHORT).show();
            check = -1;
        } else {
            String pass = edtPassword.getText().toString();
            String passRepeat = edtConfirmPassword.getText().toString();
            if (!pass.equals(passRepeat)) {
                Toast.makeText(getContext(), "Mật khẩu lặp lại không đúng", Toast.LENGTH_SHORT).show();
                check = -1;
            }
        }
        return check;
    }
}