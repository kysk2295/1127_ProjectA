package com.example.projecta;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class WidgetProvider extends AppWidgetProvider {
    static String s = "";
    public WidgetProvider() {
        super();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
        appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(context, getClass()));
        for (int i = 0; i < appWidgetIds.length; i++) {
            updateAppWidget(context, appWidgetManager, appWidgetIds[i]);
        }


    }

    private static void updateAppWidget(final Context context, AppWidgetManager appWidgetManager, int appWidgetId) {

        RemoteViews updateViews= new RemoteViews(context.getPackageName(),R.layout.app_widget);


        Thread thread= new Thread() {
            @Override
            public void run() {

                DBHelper dbHelper= new DBHelper(context);
                SQLiteDatabase sqLiteDatabase=dbHelper.getReadableDatabase();
                String sql="select * from tb_project";
                ArrayList<String> nameData= new ArrayList<>();
                ArrayList<String> dateData = new ArrayList<>();
                Data mdata= new Data();
                Cursor cursor=sqLiteDatabase.rawQuery(sql,null);

                while (cursor.moveToNext()) {
                    mdata.name = cursor.getString(1);
                    mdata.date = cursor.getString(2);
                    nameData.add(mdata.name);
                    dateData.add(mdata.date);
                }

                for (int x=0;x<nameData.size();x++){
                    s+=dateData.get(x)+"\n"+nameData.get(x)+"\n";
                }
                Log.d("asdfgh", s);

            }

        };
        thread.start();
        updateViews.setTextViewText(R.id.text, s);



        Intent i = new Intent(context, MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context,100,i,PendingIntent.FLAG_UPDATE_CURRENT);

        appWidgetManager.updateAppWidget(appWidgetId,updateViews);


    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
    }

    @Override
    public void onEnabled(Context context) {
        super.onEnabled(context);
    }

    @Override
    public void onDisabled(Context context) {
        super.onDisabled(context);
    }
}
