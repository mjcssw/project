package kr.ac.mjc.parkproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login2);

        Button joinBtn = findViewById(R.id.btn_et);

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailEt = findViewById(R.id.email_et);
                String email = emailEt.getText().toString();
                if (email.equals("")) {
                    Toast.makeText(LoginActivity.this, "아이디를 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                EditText passwordEt = findViewById(R.id.password_et);
                String password = passwordEt.getText().toString();

                if (email.length() < 8) {
                    Toast.makeText(LoginActivity.this, "아이디를 정확히 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.length() < 7) {
                    Toast.makeText(LoginActivity.this, "비밀번호는 7자리 이상 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(LoginActivity.this, "회원가입이 완료되었습니다", Toast.LENGTH_SHORT).show();

                // ✅ 여기만 추가
                Intent intent = new Intent(LoginActivity.this, MapActivity.class);
                startActivity(intent);
                finish();  // LoginActivity 종료
            }
        });
    }
}
