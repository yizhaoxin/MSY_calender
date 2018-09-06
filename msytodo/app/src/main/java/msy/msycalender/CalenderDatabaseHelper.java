package msy.msycalender;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by MSY on 2017/10/19.
 */

public class CalenderDatabaseHelper extends SQLiteOpenHelper {

    private String DATA="create table calenderDataTable(" +
            "date text," +
            "time text," +
            "title text," +
            "data text," +
            "finish text," +
            "grade float)";

    private Context context;

    public CalenderDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(DATA);

        //设置默认数据
        ContentValues values=new ContentValues();
        values.put("date","2017-10-19");
        values.put("time","09:28");
        values.put("title","默认标题");
        values.put("data","默认内容");
        values.put("finish","0");
        values.put("grade",1);
        sqLiteDatabase.insert("calenderDataTable",null,values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
