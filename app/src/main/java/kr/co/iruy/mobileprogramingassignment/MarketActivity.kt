package kr.co.iruy.mobileprogramingassignment

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.widget.*
import androidx.core.content.ContextCompat

class MarketActivity : AppCompatActivity() {
    private val listView: GridView by lazy {
        findViewById<GridView>(R.id.itemLV)
    }
    private val getPhotoBT: Button by lazy {
        findViewById<Button>(R.id.getPhotoBT)
    }
    private val itemET: EditText by lazy {
        findViewById<EditText>(R.id.itemET)
    }
    private val deleteBT: Button by lazy{
        findViewById<Button>(R.id.deleteBT)
    }
    private val informationBT: Button by lazy{
        findViewById<Button>(R.id.informationBT)
    }
    private val addressET : TextView by lazy{
        findViewById<TextView>(R.id.addressET)
    }
    private val phoneNumberET : TextView by lazy{
        findViewById<TextView>(R.id.phoneNumberET)
    }
    private val nameET : TextView by lazy{
        findViewById<TextView>(R.id.nameET)
    }
    private val itemUploadBT : Button by lazy{
        findViewById<Button>(R.id.itemUploadBT)
    }
    val items = mutableListOf<ListViewItem>()
    var itemGet = false
    lateinit var uri : Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_market)

        //인텐트로 데이터 받기;
        informationBT.setOnClickListener{
            if (intent.hasExtra("login")){
                if (intent.getStringExtra("login")!!.equals("1")){
                    addressET.text = intent.getStringExtra("address").toString()
                    nameET.text = intent.getStringExtra("name").toString()
                    phoneNumberET.text = intent.getStringExtra("phoneNumber").toString()
                }
                else if (intent.getStringExtra("login")!!.equals("0")){
                    val Signintent = Intent(this, SignUpActivity::class.java)
                    val dialog = CustomDialog(this)
                    dialog.showDialog()
                    dialog.setOnClickListener(object : CustomDialog.OnDialogClickListener{
                        override fun onClicked() {
                            startActivity(Signintent)
                        }
                    })
                }
            }
        }

        items.add(ListViewItem("에이다", BitmapFactory.decodeResource(this.resources, R.drawable.ada)))
        items.add(ListViewItem("바이낸스 코인", BitmapFactory.decodeResource(this.resources, R.drawable.bnb)))
        items.add(ListViewItem("바트코인", BitmapFactory.decodeResource(this.resources, R.drawable.btc)))
        items.add(ListViewItem("이더리움", BitmapFactory.decodeResource(this.resources, R.drawable.eth)))
        items.add(ListViewItem("솔라나", BitmapFactory.decodeResource(this.resources, R.drawable.sol)))
        val adapter = ListViewAdapter(items)
        listView.adapter = adapter

        // 사진 가져오기 버튼
        getPhotoBT.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 105)
        }
        itemUploadBT.setOnClickListener{
            if(itemGet){
                items.add(
                    ListViewItem(
                        itemET.text.toString(),
                        MediaStore.Images.Media.getBitmap(contentResolver, uri)
                    )
                )
                val adapter = ListViewAdapter(items)
                listView.adapter = adapter
                itemGet = false
            }
            else{
                Toast.makeText(applicationContext, "사진을 먼저 업로드해주세요.", Toast.LENGTH_LONG).show()
            }
        }
        deleteBT.setOnClickListener{
            var text = itemET.text.toString()
            for(i in items.indices){
                if (items[i].title == text){
                    items.removeAt(i)
                    break
                }
            }
            val adapter = ListViewAdapter(items)
            listView.adapter = adapter
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                105 -> {
                    uri = data?.data!!
                    itemGet = true
                }
            }
        }
    }
}