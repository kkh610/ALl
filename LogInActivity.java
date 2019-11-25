package halla.icsw.kkhproject11_18;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LogInActivity extends AppCompatActivity {

    dbHelper helper;
    SQLiteDatabase db;
    EditText id, pw;
    Button b1;


    String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);


        Button member = findViewById(R.id.member);
        id = findViewById(R.id.l_id);
        pw = findViewById(R.id.l_pw);
        helper = new dbHelper(this);
        db = helper.getWritableDatabase();
        b1 = findViewById(R.id.login);


        member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

    }


    public void login(View v) {
        String idtext = id.getText().toString();
        String pwtext = pw.getText().toString();

        if (idtext.length() > 0 && pwtext.length() > 0) {

            Cursor cursor = db.rawQuery("SELECT _id,password,name FROM 회원 WHERE _id = '" + idtext + "' AND password = '" + pwtext + "';", null);


            if (cursor.getCount() > 0) {
                Toast.makeText(this, "로그인되었습니다.", Toast.LENGTH_LONG).show();
                while (cursor.moveToNext()) {
                    String _id = cursor.getString(0);
                    String password =cursor.getString(1);
                    name = cursor.getString(2);
                }
                // 마이페이지로 보내는거 수정중이었음
                Intent intent1 = new Intent(LogInActivity.this, MainActivity.class);
                intent1.putExtra("name", name);
                startActivity(intent1);

            } else if (cursor.getCount() == 0) {
                Toast.makeText(this, "회원이 아닙니다.", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "회원정보를 입력해주세요", Toast.LENGTH_LONG).show();
            }
        }
        else {
            Toast.makeText(this, "회원정보를 입력해주세요", Toast.LENGTH_LONG).show();
        }



    }
}
