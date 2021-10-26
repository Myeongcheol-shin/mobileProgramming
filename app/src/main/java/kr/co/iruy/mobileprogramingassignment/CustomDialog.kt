package kr.co.iruy.mobileprogramingassignment

import android.app.Dialog
import android.content.Context
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText

class CustomDialog(context: Context)
{
    private val dialog = Dialog(context)
    private lateinit var onClickListener: OnDialogClickListener

    fun setOnClickListener(listener: OnDialogClickListener)
    {
        onClickListener = listener
    }

    fun showDialog()
    {
        dialog.setContentView(R.layout.custom_dialog)
        dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.show()


        dialog.findViewById<Button>(R.id.successButton).setOnClickListener {
            dialog.dismiss()
            onClickListener.onClicked()
        }

        dialog.findViewById<Button>(R.id.closeButton).setOnClickListener {
            dialog.dismiss()
        }

    }

    interface OnDialogClickListener
    {
        fun onClicked()
    }

}