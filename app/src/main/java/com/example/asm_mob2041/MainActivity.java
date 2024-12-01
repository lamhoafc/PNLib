package com.example.asm_mob2041;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import com.google.android.material.navigation.NavigationView;
import com.example.asm_mob2041.dao.SachDAO;
import com.example.asm_mob2041.dao.ThuThuDAO;
import com.example.asm_mob2041.fragment.AddUserFragment;
import com.example.asm_mob2041.fragment.QLLoaiSachFragment;
import com.example.asm_mob2041.fragment.QLPhieuMuonFragment;
import com.example.asm_mob2041.fragment.QLSachFragment;
import com.example.asm_mob2041.fragment.QLThanhVienFragment;
import com.example.asm_mob2041.fragment.ThongKeDoanhThuFragment;
import com.example.asm_mob2041.fragment.ThongKeTop10Fragment;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


//        SachDAO dao = new SachDAO(this);
//        dao.getDSDauSach();

        Toolbar toolbar = findViewById(R.id.toolBar);
        FrameLayout frameLayout = findViewById(R.id.frameLayout);
        NavigationView navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerlayout);
        View headerLayout = navigationView.getHeaderView(0);
        TextView txtTen = headerLayout.findViewById(R.id.txtTen);

        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        if (savedInstanceState == null) {
            toolbar.setTitle("Quản lý phiếu mượn");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frameLayout, new QLPhieuMuonFragment())
                    .commit();
        }

        SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN", MODE_PRIVATE);
        String matt = sharedPreferences.getString("matt", ""); // Get the username
        ThuThuDAO thuThuDAO = new ThuThuDAO(this);
        String loaiTK = thuThuDAO.getLoaiTaiKhoan(matt);
        switch (loaiTK) {
            case "admin":
                // Show admin views
                Menu menuAdmin = navigationView.getMenu();
                menuAdmin.findItem(R.id.mDoanhThu).setVisible(true);
                menuAdmin.findItem(R.id.mTop10).setVisible(true);
                menuAdmin.findItem(R.id.navAddUser).setVisible(true);
                break;
            case "Thuthu":
                // Hide admin views
                Menu menuThuthu = navigationView.getMenu();
                menuThuthu.findItem(R.id.navAddUser).setVisible(false);
                menuThuthu.findItem(R.id.mDoanhThu).setVisible(false);
                menuThuthu.findItem(R.id.mTop10).setVisible(false);
                break;
            default:
                break;
        }
        String hoten = sharedPreferences.getString("hoten", "");
        txtTen.setText("Xin chào, " + hoten);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                if (item.getItemId() == R.id.mQLPhieuMuon) {
                    fragment = new QLPhieuMuonFragment();
                } else if (item.getItemId() == R.id.mQLLoaiSach) {
                    fragment = new QLLoaiSachFragment();
                } else if (item.getItemId() == R.id.mThoat) {
                    Intent intent = new Intent(MainActivity.this, DangNhapActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    return true;
                } else if (item.getItemId() == R.id.mDoiMatKhau) {
                    showDialogDoiMatKhau();
                } else if (item.getItemId() == R.id.mTop10) {
                    fragment = new ThongKeTop10Fragment();
                } else if (item.getItemId() == R.id.mDoanhThu) {
                    fragment = new ThongKeDoanhThuFragment();
                } else if (item.getItemId() == R.id.mQLThanhVien) {
                    fragment = new QLThanhVienFragment();
                } else if (item.getItemId() == R.id.mQLSach) {
                    fragment = new QLSachFragment();
                } else if (item.getItemId() == R.id.navAddUser) {
                    fragment = new AddUserFragment();
                } else {
                    fragment = new QLPhieuMuonFragment();
                }
                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragment).commit();

                }
                drawerLayout.close();
                return true;
            }
        });





    }

    private void showDialogDoiMatKhau() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_doimatkhau, null);
        EditText edtPassOld = view.findViewById(R.id.edtPassOld);
        EditText edtNewPass = view.findViewById(R.id.edtNewPass);
        EditText edtReNewPass = view.findViewById(R.id.edtReNewPass);

        builder.setView(view);

        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.setNegativeButton("Cập nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String oldPass = edtPassOld.getText().toString();
                String newPass = edtNewPass.getText().toString();
                String reNewPass = edtReNewPass.getText().toString();
                if (newPass.equals(reNewPass)) {
                    SharedPreferences sharedPreferences = getSharedPreferences("THONGTIN", MODE_PRIVATE);
                    String matt = sharedPreferences.getString("matt", "");
                    ThuThuDAO thuThuDAO = new ThuThuDAO(MainActivity.this);
                    boolean check = thuThuDAO.capNhatMatKhau(matt, oldPass, newPass);
                    if (check) {
                        Toast.makeText(MainActivity.this, "Cập nhật mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, DangNhapActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    } else {
                        Toast.makeText(MainActivity.this, "Mật khẩu cũ không trùng khớp", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Mật khẩu không trùng nhau", Toast.LENGTH_SHORT).show();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }
}