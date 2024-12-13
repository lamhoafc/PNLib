package com.example.asm_mob2041.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.asm_mob2041.database.DbHelper;
import com.example.asm_mob2041.model.Sach;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ThongKeDAO {
    DbHelper dbHelper;
    public ThongKeDAO(Context context){
        dbHelper = new DbHelper(context);
    }
        public ArrayList<Sach> getTop10(){
        ArrayList<Sach> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT pm.masach, sc.tensach, COUNT(pm.masach) FROM PHIEUMUON pm, SACH sc WHERE pm.masach = sc.masach GROUP BY pm.masach, sc.tensach ORDER BY COUNT(pm.masach) DESC LIMIT 10", null);
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                list.add(new Sach(cursor.getInt(0), cursor.getString(1), cursor.getInt(2)));
            }while (cursor.moveToNext());
        }
        return list;
    }



    public int getDoanhThuTheoKhoangThoiGian(String ngayBatDau, String ngayKetThuc) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy"); // Định dạng ngày đầu vào
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy"); // Định dạng ngày đầu ra
        try {
            // Chuyển đổi ngày bắt đầu và ngày kết thúc sang định dạng "yyyy-MM-dd"
            Date startDate = dateFormat.parse(ngayBatDau);
            Date endDate = dateFormat.parse(ngayKetThuc);
            ngayBatDau = outputFormat.format(startDate);
            ngayKetThuc = outputFormat.format(endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int tongTien = 0;
        Cursor cursor = db.rawQuery(
                "SELECT SUM(pm.tienthue) " +
                        "FROM PHIEUMUON pm " +
                        "WHERE pm.ngay BETWEEN ? AND ?",
                new String[]{ngayBatDau, ngayKetThuc}
        );

        if (cursor.moveToFirst()) {
            tongTien = cursor.getInt(0);
        }
        cursor.close();
        return tongTien;
    }
}
