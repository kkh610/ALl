package halla.icsw.kkhproject11_18;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

class dbHelper2 extends SQLiteOpenHelper {
    public dbHelper2(Context context) { super (context, "BookMark.db", null, 1); }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE 북마크 ( 이름 TEXT);");
    }
    public void onUpgrade(SQLiteDatabase db, int oldver, int newVer) {
        db.execSQL("DROP TABLE IF EXISTS 북마크");
        onCreate(db);
    }
}


public class BookMarkActivity extends AppCompatActivity {

    SQLiteDatabase db;
    EditText textView;
    TextView text;

    String value = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_mark);

        dbHelper2 helper = new dbHelper2(this);
        db = helper.getWritableDatabase();

        textView = findViewById(R.id.textView);
        text = (TextView) findViewById(R.id.text);

        textView.setText("");

        Intent intent = getIntent();
        value = intent.getStringExtra("bookName");
        textView.setText(value);


    }


    public void onAdd(View view) {
        String n = textView.getText().toString();
        db.execSQL("INSERT INTO 북마크 VALUES  ('" + n + "');");

    }

    public void onlist(View view) {

        Cursor cursor = db.rawQuery("SELECT 이름 FROM 북마크;", null);
        String bookMark = "북마크 리스트 : \n";
        while (cursor.moveToNext()) {
            String n = cursor.getString(0);
            bookMark += n + "\n";
        }
        text.setText(bookMark);
        textView.setText(value);
    }

    public void onDelete (View view) {
        String n = textView.getText().toString();

        db.execSQL("DELETE FROM 북마크 WHERE 이름 = '" + n + "';");
        onlist(view);
    }
}
