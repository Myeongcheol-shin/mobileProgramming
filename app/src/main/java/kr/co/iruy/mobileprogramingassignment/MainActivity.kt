package kr.co.iruy.mobileprogramingassignment

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private val signUpBT : Button by lazy {
        findViewById<Button>(R.id.signUpBT)
    }
    private val loginBT: Button by lazy{
        findViewById<Button>(R.id.loginBT)
    }
    private val mainBT : Button by lazy{
        findViewById<Button>(R.id.mainBT)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val id = findViewById<EditText>(R.id.idET)
        val password = findViewById<EditText>(R.id.passwordET)

        signUpBT.setOnClickListener{
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        loginBT.setOnClickListener{
            val id_text = id.text.toString()
            val pw_text = password.text.toString()

            val sharedPreferences = getSharedPreferences("person information",Context.MODE_PRIVATE)
            val saveId = sharedPreferences.getString("id","")
            val savePw = sharedPreferences.getString("password","")

            // 유저 입력과 비교
            if(id_text == saveId && pw_text == savePw && id_text.length != 0 && password.text.length != 0){
                Toast.makeText(applicationContext, "로그인 성공.", Toast.LENGTH_LONG).show()
                val intent = Intent(this, MarketActivity::class.java)
                intent.putExtra("login","1")
                intent.putExtra("아이디",saveId)
                intent.putExtra("비밀번호",savePw)
                intent.putExtra("name",sharedPreferences.getString("name",""))
                intent.putExtra("phoneNumber",sharedPreferences.getString("phoneNumber",""))
                intent.putExtra("address",sharedPreferences.getString("address",""))
                startActivity(intent)
            }
            else if(id_text != saveId){
                Toast.makeText(applicationContext, "없는 아이디입니다.", Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(applicationContext, "잘못된 비밀번호입니다.", Toast.LENGTH_LONG).show()
            }
        }
        mainBT.setOnClickListener{
            val intent = Intent(this, MarketActivity::class.java)
            intent.putExtra("login","0")
            startActivity(intent)
        }
    }
}