package msy.msycalender;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by MSY on 2017/10/27.
 */

public class ShowTagActivity extends Activity {

    private TextView tvDate;
    private TextView tvTime;
    private RatingBar ratingBar;
    private TextView tvTitle;
    private TextView tvData;
    private Button button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_tag);

        //初始化
        tvDate=findViewById(R.id.show_tag_date);
        tvTime=findViewById(R.id.show_tag_time);
        ratingBar=findViewById(R.id.show_tag_ratingbar);
        tvTitle=findViewById(R.id.show_tag_title);
        tvData=findViewById(R.id.show_tag_data);
        button=findViewById(R.id.btn_change_finish);

        //获取数据
        Intent intent=getIntent();
        String date=intent.getStringExtra("date");
        String time=intent.getStringExtra("time");
        float grade=intent.getFloatExtra("grade",0);
        final String title=intent.getStringExtra("title");
        String data=intent.getStringExtra("data");

        //设置页面数据
        tvDate.setText(date);
        tvTime.setText(time);
        ratingBar.setRating(grade);
        tvTitle.setText(title);
        tvData.setText(data);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CalenderDatabaseHelper calenderDatabaseHelper=new CalenderDatabaseHelper(ShowTagActivity.this,"calenderDatabase.db",null,1);
                SQLiteDatabase db=calenderDatabaseHelper.getWritableDatabase();
                db.execSQL("delete from calenderDataTable where title=?",new String[]{title});
                Toast.makeText(ShowTagActivity.this,"事件已完成",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
