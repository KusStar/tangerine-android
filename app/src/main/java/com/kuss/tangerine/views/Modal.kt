package com.kuss.tangerine.views

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.kuss.tangerine.R
import com.kuss.tangerine.adapters.TaskListAdapter
import com.kuss.tangerine.db.Task
import com.kuss.tangerine.util.constants.EmojiMapping
import com.kuss.tangerine.util.helper.EmojiHelper
import kotlinx.android.synthetic.main.modal.*
import java.util.*
import com.kuss.tangerine.adapters.SelectEmojiAdapter
import android.view.MotionEvent


class Modal(
    private val adapter: TaskListAdapter
) : BottomSheetDialogFragment() {

    private var emojiId = EmojiMapping.ids[0]
    private var changeEmojiByText = true

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
        editText.requestFocus()

        editText.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(p0: Editable?) { }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(str: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if(str.toString().isEmpty()) {
                    changeEmojiByText = true
                }
                if(changeEmojiByText) {
                    emojiId = EmojiHelper.getType(str.toString())
                    emojiImageView.setImageResource(emojiId)
                }
            }
        })
        changeEmojiBtn.setOnClickListener {
            context?.let {
                selectEmojiListView.visibility = View.VISIBLE
                selectEmojiListView.setOnTouchListener(object : View.OnTouchListener {
                    override fun onTouch(v: View, event: MotionEvent): Boolean {
                        when (event.action) {
                            MotionEvent.ACTION_DOWN ->
                                // Disallow NestedScrollView to intercept touch events.
                                v.parent.requestDisallowInterceptTouchEvent(true)

                            MotionEvent.ACTION_UP ->
                                // Allow NestedScrollView to intercept touch events.
                                v.parent.requestDisallowInterceptTouchEvent(false)
                        }
                        v.onTouchEvent(event)
                        return true
                    }
                })
                val adapter = SelectEmojiAdapter(it, EmojiMapping.ids)

                selectEmojiListView.setOnItemClickListener { _, _, i, _ ->
                    val id = EmojiMapping.ids[i]
                    emojiImageView.setImageResource(id)
                    selectEmojiListView.visibility = View.GONE
                    emojiId = id
                    changeEmojiByText = false
                }
                selectEmojiListView.adapter = adapter

            }

        }
        save.setOnClickListener {
            val text = editText.text.toString()
            if(text.isNotEmpty()) {
                val task = Task(
                    text,
                    emojiId,
                    checked = false,
                    date = Date().time
                )
                adapter.insert(task)
                dismiss()
            }
        }
        super.onActivityCreated(savedInstanceState)
    }
    companion object {
        const val TAG = "ModalBottomSheet"

    }
}