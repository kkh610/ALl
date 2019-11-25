package halla.icsw.kkhproject11_18;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class GenreSearchActivity extends AppCompatActivity {

    String DB_NAME="book.db";

    private void copyDatabase(File dbFile){
        try {
            String folderPath="/data/data/"+getPackageName()+"/databases";
            File folder=new File(folderPath);
            if(!folder.exists()) folder.mkdirs();
            InputStream is=getAssets().open(DB_NAME);
            OutputStream os=new FileOutputStream(dbFile);
            byte[] buffer=new byte[1024];
            while(is.read(buffer)>0) os.write(buffer);
            os.flush();
            is.close(); os.close();
        }catch (Exception e){}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre_search);

        TextView searchText = findViewById(R.id.searchText);

        File dbFile = getDatabasePath(DB_NAME);
        if (!dbFile.exists()) copyDatabase(dbFile);

        Intent intent = getIntent();
        String searchStr = intent.getStringExtra("search");

        SQLiteDatabase db=openOrCreateDatabase(DB_NAME, Context.MODE_PRIVATE, null);
        Cursor cursor=db.rawQuery(
                "SELECT * FROM 책 WHERE 책제목 like '%"+searchStr+ "%';", null
        );
        Cursor cursor2=db.rawQuery(
                "SELECT * FROM 책 WHERE 지은이 like '%"+searchStr+ "%';", null
        );
        if(cursor.getCount()>0 && cursor2.getCount()==0) {
            String result="";
            int i=0;
            while (cursor.moveToNext()){
                String strid=cursor.getString(0);
                String strname=cursor.getString(1);

                result+=("<"+strid+">"+"  "+"- "+strname+"\n" +
                        "-----------------------------");
            }
            searchText.setText(result);
        }

        else  {
            String result2="";

            while (cursor2.moveToNext()){
                String strid=cursor2.getString(0);
                String strname=cursor2.getString(1);

                result2+=("<"+strid+">"+"  "+"- "+strname+"\n" +
                        "-----------------------------");

            }
            searchText.setText(result2);
            if ( searchText.getText().toString()==""){
                searchText.setText("검색 결과가 없습니다");
            }
        }
    }
}
