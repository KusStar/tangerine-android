package com.kuss.tangerine.views

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kuss.tangerine.R
import com.kuss.tangerine.db.Task
import com.kuss.tangerine.model.TaskViewModel
import com.kuss.tangerine.util.constants.TaskType
import kotlinx.android.synthetic.main.modal.*
import java.util.*
import kotlin.random.Random

class Modal(val viewModel: TaskViewModel) : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.modal, container, false)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        setStyle(DialogFragment.STYLE_NORMAL, R.style.ModalTheme)
        return super.onCreateDialog(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        save.setOnClickListener {
            val text = editText.text.toString()
            val type = TaskType().getType(text)

            if(text.isNotEmpty()) {
                val task = Task(
                    text,
                    type,
                    checked = false,
                    date = Date().time
                )
                viewModel.insert(task)
                dismiss()
            }

        }
        super.onActivityCreated(savedInstanceState)
    }
    companion object {
        const val TAG = "ModalBottomSheet"
    }
}