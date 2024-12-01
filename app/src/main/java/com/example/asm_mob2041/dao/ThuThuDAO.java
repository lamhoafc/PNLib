package com.example.asm_mob2041.dao;

import static android.content.Context.MODE_PRIVATE;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asm_mob2041.database.DbHelper;

public class ThuThuDAO {
    DbHelper dbHelper;
    SharedPreferences sharedPreferences;

    public ThuThuDAO(Context context) {

        dbHelper = new DbHelper(context);
        sharedPreferences = context.getSharedPreferences("THONGTIN", MODE_PRIVATE);

    }

    public String getLoaiTaiKhoan(String matt) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String loaiTaiKhoan = "";
        Cursor cursor = db.rawQuery("SELECT loaitaikhoan FROM THUTHU WHERE matt = ?", new String[]{matt});
        if (cursor.moveToFirst()) {
            loaiTaiKhoan = cursor.getString(cursor.getColumnIndex("loaitaikhoan"));
        }
        cursor.close();
        return loaiTaiKhoan;
    }

    //Đăng nhập
    public boolean checkDangNhap(String matt, String matkhau) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM THUTHU WHERE matt = ? AND matkhau = ?", new String[]{matt, matkhau});
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("matt", cursor.getString(0));
            editor.putString("hoten", cursor.getString(1));
            editor.putString("matkhau", cursor.getString(2));
            editor.putString("loaitaikhoa", cursor.getString(3));

            editor.commit();
            return true;
        } else {
            return false;
        }
    }

    public boolean capNhatMatKhau(String username, String oldPass, String newPass) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM THUTHU WHERE matt = ? AND matkhau = ?", new String[]{username, oldPass});
        if (cursor.getCount() > 0) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("matkhau", newPass);
            long check = sqLiteDatabase.update("THUTHU", contentValues, "matt = ?", new String[]{username});
            if (check == -1) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    public boolean themTaiKhoanThuThu(String maTT, String hoTenTT, String matKhau) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("matt", maTT);
        values.put("hoten", hoTenTT);
        values.put("matkhau", matKhau);
        values.put("loaitaikhoan", "Thuthu"); // Hoặc giá trị mặc định khác cho loaitaikhoa

        long result = db.insert("THUTHU", null, values);
        return result != -1; // Trả về true nếu thêm thành công
    }
}
