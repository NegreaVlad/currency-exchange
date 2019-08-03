/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package co.zipperstudios.currencyexchange.binding

import android.graphics.drawable.Drawable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestListener
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

/**
 * Binding adapters that work with a fragment instance.
 */
class FragmentBindingAdapters @Inject constructor(val fragment: Fragment) {
    val dateFormat: SimpleDateFormat = SimpleDateFormat("dd MMM ", Locale.getDefault())

    @BindingAdapter(value = ["imageUrl", "imageRequestListener"], requireAll = false)
    fun bindImage(imageView: ImageView, url: String?, listener: RequestListener<Drawable?>?) {
        Glide.with(fragment).load(url).listener(listener).into(imageView)
    }

    @BindingAdapter(value = ["swipeRefreshListener"])
    fun bindSwipeRefreshListener(
        swipeRefreshLayout: SwipeRefreshLayout,
        swipeRefreshListener: SwipeRefreshLayout.OnRefreshListener
    ) {
        swipeRefreshLayout.setOnRefreshListener(swipeRefreshListener)
    }

    @BindingAdapter(value = ["amount", "exchangeRate", "isHeader", "textChangeListener"], requireAll = false)
    fun bindExchangeRate(editText: EditText, amount: Float, exchangeRate: Float, isHeader: Boolean, textChangeListener: TextWatcher) {
        editText.removeTextChangedListener(textChangeListener)
        if (isHeader) {
            editText.addTextChangedListener(textChangeListener)
            return
        }

        editText.setText((amount * exchangeRate).toString())
    }

    @BindingAdapter(value = ["displayCurrencyName"], requireAll = true)
    fun bindExchangeRate(textView: TextView, currencyCountryCode: String) {
        try {
            val currency = Currency.getInstance(currencyCountryCode)

            textView.text = currency.displayName
        } catch (e: IllegalArgumentException) {
            Timber.e(e)
            textView.text = ""
        }
    }
}

