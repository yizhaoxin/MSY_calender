package msy.msycalender;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MSY on 2017/10/19.
 */

public class MainService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("TAG","MainService开始运行");
                CalenderDatabaseHelper calenderDatabaseHelper=new CalenderDatabaseHelper(MainService.this,"calenderDatabase.db",null,1);
                SQLiteDatabase db=calenderDatabaseHelper.getWritableDatabase();
                Cursor cursor = db.rawQuery("select * from calenderDataTable order by date,time asc",null);
                List list=new ArrayList();
                if (cursor.moveToFirst()){
                    do {
                        Map map=new HashMap();
                        map.put("date",cursor.getString(cursor.getColumnIndex("date")));
                        map.put("time",cursor.getString(cursor.getColumnIndex("time")));
                        map.put("title",cursor.getString(cursor.getColumnIndex("title")));
                        map.put("data",cursor.getString(cursor.getColumnIndex("data")));
                        map.put("grade",cursor.getFloat(cursor.getColumnIndex("grade")));
                        list.add(map);
                    }while (cursor.moveToNext());
                }
                cursor.close();

                //数据复制
                MainActivity.list=list;

                //发送广播
                Log.i("TAG","MainService发送广播");
                Intent intent=new Intent("msy.calender.main_listview.show");
                sendBroadcast(intent);

                stopSelf();
            }
        }).start();
    }
}
