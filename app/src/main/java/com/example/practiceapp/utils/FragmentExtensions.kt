package com.example.practiceapp.utils

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog


fun <T : Fragment> showBottomSheet(context: Context, view: View) {
    val dialog = BottomSheetDialog(context)
    //val view = layoutInflater.
    dialog.setCancelable(true)
    dialog.setCanceledOnTouchOutside(true)
    dialog.setContentView(view)
    dialog.show()
}