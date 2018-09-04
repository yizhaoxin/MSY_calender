package msy.msycalender;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by MSY on 2017/10/19.
 */

public class AddEvenActivity extends Activity {

    private TextView tv_date;//日期
    private Spinner sp_hour;
    private Spinner sp_min;
    private RatingBar ratingBar;
    private EditText edt_title;
    private EditText edt_data;
    private Button button;
    private String date;
    private float grade=1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_even);

        //初始化
        tv_date=findViewById(R.id.add_even_date);
        sp_hour=findViewById(R.id.add_even_sp_h);
        sp_min=findViewById(R.id.add_even_sp_m);
        ratingBar=findViewById(R.id.add_even_ratingBar);
        edt_title=findViewById(R.id.add_even_edt_title);
        edt_data=findViewById(R.id.add_even_edt_data);
        button=findViewById(R.id.add_even_btn);

        //设置默认等级
        ratingBar.setRating(1);


        //日期显示设置
        Intent intent=getIntent();
        date=intent.getStringExtra("passDate");
        tv_date.setText(date);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String time="";
                String title=edt_title.getText().toString();
                String data=edt_data.getText().toString();
                grade=ratingBar.getRating();

                int hh=sp_hour.getSelectedItemPosition();
                int min=sp_min.getSelectedItemPosition();
                if (hh<10)
                    time="0"+String.valueOf(hh);
                else
                    time=String.valueOf(hh);
                if (min<10)
                    time=time+":0"+String.valueOf(min);
                else
                    time=time+":"+String.valueOf(min);

                //添加数据
                CalenderDatabaseHelper calenderDatabaseHelper=new CalenderDatabaseHelper(AddEvenActivity.this,"calenderDatabase.db",null,1);
                SQLiteDatabase db=calenderDatabaseHelper.getWritableDatabase();
                ContentValues values=new ContentValues();
                values.put("date",date);
                values.put("time",time);
                values.put("title",title);
                values.put("data",data);
                values.put("grade",grade);
                db.insert("calenderDataTable",null,values);
                Toast.makeText(getApplication(),"数据添加完成",Toast.LENGTH_SHORT).show();

                //关闭该页面
                finish();
            }
        });
    }
}
