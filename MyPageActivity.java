package halla.icsw.kkhproject11_18;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MyPageActivity extends AppCompatActivity {

    dbHelper helper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);

        TextView nameText = findViewById(R.id.nameText);

        helper = new dbHelper(MyPageActivity.this);
        db = helper.getReadableDatabase();

        Intent intent = getIntent();
        String nameIntent = intent.getStringExtra("name1");

        nameText.setText(nameIntent+"님");

        if(nameText.getText().toString().equals("null님")) {
            nameText.setText("로그인이 필요합니다.");
        }
    }

    public void bookMarkOnClick(View view){
        Intent intent = new Intent(MyPageActivity.this, BookMarkActivity.class);
        startActivity(intent);
    }
}
