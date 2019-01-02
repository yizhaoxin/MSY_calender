package msy.msycalender;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {

    private CalendarView calendarView;
    private ListView listView;
    private MainBroadcastReceiver receiver;
    public static List<Map<String,Object>> list;
    private Button button1;
    private String selectDate;//传递的时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //初始化
        calendarView=findViewById(R.id.calender);
        listView=findViewById(R.id.main_lv);
        receiver=new MainBroadcastReceiver();
        button1=findViewById(R.id.btn_add_even);
        selectDate=new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        //日历日期
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String dd=String.valueOf(i2);
                String mm=String.valueOf(++i1);
                if (++i1<10)
                    mm="0"+String.valueOf(i1);
                if (i2<10)
                    dd="0"+String.valueOf(i2);
                selectDate=String.valueOf(i)+"-"+mm+"-"+dd;
            }
        });

        //注册广播监听
        IntentFilter filter=new IntentFilter("msy.calender.main_listview.show");
        registerReceiver(receiver,filter);

        //按钮点击事件
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AddEvenActivity.class);
                intent.putExtra("passDate",selectDate);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //启动服务
        final Intent i=new Intent(MainActivity.this,MainService.class);
        startService(i);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> m=list.get(position);
                Intent intent=new Intent(MainActivity.this,ShowTagActivity.class);
                intent.putExtra("date",(String) m.get("date"));
                intent.putExtra("time",(String) m.get("time"));
                intent.putExtra("title",(String) m.get("title"));
                intent.putExtra("data",(String) m.get("data"));
                intent.putExtra("grade",(float) m.get("grade"));
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    class MainBroadcastReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            MainAdapter adapter=new MainAdapter();
            listView.setAdapter(adapter);
        }
    }

    class MainAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Holder holder;
            if (convertView==null){
                //初次
                convertView= LayoutInflater.from(MainActivity.this).inflate(R.layout.main_lv_item,null);
                holder=new Holder();

                holder.date=convertView.findViewById(R.id.main_lv_item_date);
                holder.time=convertView.findViewById(R.id.main_lv_item_time);
                holder.title=convertView.findViewById(R.id.main_lv_item_title);
                holder.data=convertView.findViewById(R.id.main_lv_item_data);
                holder.ratingBar=convertView.findViewById(R.id.main_lv_item_rating);

                convertView.setTag(holder);
            }else {
                holder= (Holder) convertView.getTag();
            }

            holder.date.setText(list.get(position).get("date").toString());
            holder.time.setText(list.get(position).get("time").toString());
            holder.title.setText(list.get(position).get("title").toString());
            holder.data.setText(list.get(position).get("data").toString());
            holder.ratingBar.setRating((Float) list.get(position).get("grade"));
            return convertView;
        }
    }

    class Holder{
        TextView date;
        TextView time;
        TextView title;
        TextView data;
        RatingBar ratingBar;
    }
}
