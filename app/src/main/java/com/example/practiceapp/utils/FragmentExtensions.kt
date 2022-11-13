package com.example.practiceapp.utils

import android.content.Context
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialog

//Todo 1) Adapt from OneBottomSheet
// 2) Steal the callback logic too addOnStateChangedCallBack()
// 3) This should be bottom sheet extension and could handle swiping too
fun <T : Fragment> showBottomSheet(context: Context, view: View) {
    val dialog = BottomSheetDialog(context)
    //val view = layoutInflater.
    dialog.setCancelable(true)
    dialog.setCanceledOnTouchOutside(true)
    dialog.setContentView(view)
    dialog.show()
}