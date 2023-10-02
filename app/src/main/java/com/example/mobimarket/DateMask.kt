package com.example.mobimarket

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

class DateMask(private val editText: EditText) : TextWatcher {
    private var isFormatting: Boolean = false

    companion object {
        private const val MASK_FORMAT = "##.##.####"
        private const val MASK_CHARACTER = '#'
    }

    init {
        editText.addTextChangedListener(this)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

    override fun afterTextChanged(s: Editable?) {
        if (isFormatting) return

        isFormatting = true

        val date = s.toString().replace("[^\\d]".toRegex(), "")
        val formattedDate = StringBuilder()

        var maskIndex = 0
        var dateIndex = 0

        while (maskIndex < MASK_FORMAT.length && dateIndex < date.length) {
            if (MASK_FORMAT[maskIndex] == MASK_CHARACTER) {
                formattedDate.append(date[dateIndex])
                dateIndex++
            } else {
                formattedDate.append(MASK_FORMAT[maskIndex])
            }
            maskIndex++
        }

        while (maskIndex < MASK_FORMAT.length) {
            if (MASK_FORMAT[maskIndex] != MASK_CHARACTER) {
                formattedDate.append(MASK_FORMAT[maskIndex])
            }
            maskIndex++
        }

        editText.setText(formattedDate.toString())
        editText.setSelection(formattedDate.length)
        isFormatting = false
    }
}