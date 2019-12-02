package com.kuss.tangerine.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.kuss.tangerine.R
import com.kuss.tangerine.util.constants.EmojiMapping
import kotlinx.android.synthetic.main.card.view.*

class SelectEmojiAdapter internal constructor(
    context: Context,
    private val list: Array<Int>
) : BaseAdapter() {

    private val inflater = LayoutInflater.from(context)
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Any {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val id = list[position]
        val emojiView = inflater.inflate(R.layout.select_emoji_item, null)
        emojiView.imageView.setBackgroundResource(id)
        emojiView.textView.text = EmojiMapping.names[position]
        return emojiView
    }
}
