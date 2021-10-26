package kr.co.iruy.mobileprogramingassignment

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import java.util.regex.Pattern

class SignUpActivity : AppCompatActivity() {
    private val passwordET : TextInputEditText by lazy{
        findViewById<TextInputEditText>(R.id.passwordET)
    }
    private val idET : EditText by lazy{
        findViewById<EditText>(R.id.idET)
    }
    private val signUpBT : Button by lazy{
        findViewById<Button>(R.id.signUpBT)
    }
    private val radioGroup : RadioGroup by lazy{
        findViewById<RadioGroup>(R.id.RG)
    }
    private val checkEmail : Button by lazy{
        findViewById<Button>(R.id.emailCheck)
    }
    private val nameET : EditText by lazy{
        findViewById<EditText>(R.id.nameET)
    }
    private val addressET : EditText by lazy{
        findViewById<EditText>(R.id.addressET)
    }
    private val phoneNumberET : EditText by lazy{
        findViewById<EditText>(R.id.phoneNumberET)
    }
    var emailCheck = false
    var radioCheck = false
    var passwordCheck = false
    val passwordValidation = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        val sharedPreferences = getSharedPreferences("person information",Context.MODE_PRIVATE)
        val saveId = sharedPreferences.getString("id","")
        checkEmail.setOnClickListener{
            if(saveId == idET.text.toString()){
                Toast.makeText(applicationContext, "중복된 아이디 입니다", Toast.LENGTH_LONG).show()
                emailCheck = false
            }
            else{
                emailCheck = true
                Toast.makeText(applicationContext, "사용 가능한 아이디 입니다.", Toast.LENGTH_LONG).show()
            }
        }
        passwordET.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                // text가 변경 된 후
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // text가 변경되기 전 호출
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                checkPassword()
            }
        })

        // 라디오 그룹 체크 여부 확인
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.agreeBT ->{
                    radioCheck = true
                }
                R.id.disagreeBT ->{
                    radioCheck = false
                }
            }
        }
        // 로그인 버튼
        signUpBT.setOnClickListener{
            if (radioCheck && passwordCheck && emailCheck && nameET.length() != 0 && phoneNumberET.length() != 0 && addressET.length() != 0){
                val sharedPreferences = getSharedPreferences("person information", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                editor.putString("id",idET.text.toString())
                editor.putString("password",passwordET.text.toString())
                editor.putString("name",nameET.text.toString())
                editor.putString("address",addressET.text.toString())
                editor.putString("phoneNumber",phoneNumberET.text.toString())
                editor.apply()
                Toast.makeText(applicationContext, "회원가입 성공!.", Toast.LENGTH_LONG).show()
                val intent = Intent(this, MarketActivity::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(applicationContext, "개인정보 입력이 제대로 되었는지 확인해주세요.", Toast.LENGTH_LONG).show()
            }
        }
    }
    fun checkPassword():Boolean{
        var password = passwordET.text.toString().trim() // 공백제거
        val p = passwordValidation.matches(password) // 패턴이 맞으면 true 아니면 false
        if (p && password.length >= 8 && password.length <= 12){
            passwordET.error = null
            passwordCheck = true
            return true
        }
        else{
            passwordET.error = "비밀번호 형식에 맞지 않습니다."
            passwordCheck = false
            return false
        }
    }
}