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

package co.zipperstudios.currencyexchange.ui.currency.exchange.adapter

import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import co.zipperstudios.currencyexchange.R
import co.zipperstudios.currencyexchange.data.model.CurrencyExchange
import co.zipperstudios.currencyexchange.data.model.CurrencyExchangeAmount
import co.zipperstudios.currencyexchange.databinding.CurrencyItemBinding
import co.zipperstudios.currencyexchange.ui.common.DataBoundListAdapter
import co.zipperstudios.currencyexchange.utils.DecimalDigitsInputFilter
import java.util.*
import android.view.inputmethod.InputMethodManager.SHOW_IMPLICIT
import android.content.Context.INPUT_METHOD_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import android.content.Context
import android.view.GestureDetector
import android.view.inputmethod.InputMethodManager
import co.zipperstudios.currencyexchange.utils.GestureInterface
import co.zipperstudios.currencyexchange.utils.GestureListener


open class CurrencyExchangeAdapter(
    private val dataBindingComponent: DataBindingComponent,
    private val callback: ((CurrencyExchange) -> Unit)?
) : DataBoundListAdapter<CurrencyExchange, CurrencyItemBinding>(
    diffCallback = object : DiffUtil.ItemCallback<CurrencyExchange>() {
        override fun areItemsTheSame(oldItem: CurrencyExchange, newItem: CurrencyExchange): Boolean {
            return oldItem.currencyCode == newItem.currencyCode
        }

        override fun areContentsTheSame(oldItem: CurrencyExchange, newItem: CurrencyExchange): Boolean {
            return oldItem == newItem
        }
    }
), GestureInterface {
    private var primaryItemUpdated: Boolean = false
    val amount = CurrencyExchangeAmount()
    var items: MutableList<CurrencyExchange>? = null

    private val textChangeListener: TextWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            amount.amount = s.toString().toFloatOrNull() ?: 0f
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

    }

    override fun createBinding(parent: ViewGroup): CurrencyItemBinding {
        val binding = DataBindingUtil
            .inflate<CurrencyItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.currency_item,
                parent,
                false,
                dataBindingComponent
            )

        val gestureDetector = GestureDetector(binding.touchInterceptor.context, GestureListener(object: GestureInterface {
            override fun onClick() {
                binding.exchange?.let {
                    callback?.invoke(it)
                }
            }
        }))

        binding.touchInterceptor.setOnTouchListener { v, event ->
            return@setOnTouchListener gestureDetector.onTouchEvent(event)
        }

        return binding
    }

    override fun bind(binding: CurrencyItemBinding, item: CurrencyExchange) {
        binding.currencyAmount.filters = arrayOf<InputFilter>(DecimalDigitsInputFilter(2))
        binding.exchange = item
        binding.amount = amount
        binding.textChangeListener = textChangeListener

        if (item.isHeader) {
            dataBindingComponent.fragmentBindingAdapters.bindExchangeRate(
                binding.currencyAmount,
                amount.amount,
                item.exchangeRate,
                false,
                textChangeListener
            )

            if (primaryItemUpdated) {
                requestEditTextFocus(binding)
                primaryItemUpdated = false
            }
        }
    }

    private fun requestEditTextFocus(binding: CurrencyItemBinding) {
        binding.root.post {
            binding.currencyAmount.requestFocus()
            val imm = binding.currencyAmount.context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
            imm!!.showSoftInput(binding.currencyAmount, SHOW_IMPLICIT)
        }
    }

    override fun submitList(list: MutableList<CurrencyExchange>?) {
        if (items != null) {
            items?.map { originalCurrency ->
                list?.find { it.currencyCode == originalCurrency.currencyCode }
                    ?.exchangeRate?.let { originalCurrency.exchangeRate = it }
            }
        } else {
            items = list
            super.submitList(list ?: emptyList())
        }
    }

    fun updatePrimaryCurrency(p1: CurrencyExchange) {
        val clickedItemIndex = items?.indexOfFirst { it.currencyCode == p1.currencyCode }
        clickedItemIndex?.let {
            val previousItem = items?.get(0)
            previousItem?.isHeader = false

            val currentItem = items?.get(clickedItemIndex)
            currentItem?.isHeader = true

            // Adjust exchange rates based on current header
            currentItem?.let {
                val currentAmount = currentItem.exchangeRate * amount.amount
                val currentExchangeRate = currentItem.exchangeRate
                items?.map { it.exchangeRate /= currentExchangeRate }
                currentItem.exchangeRate = 1f
                amount.amount = currentAmount
            }

            items?.subList(0, clickedItemIndex + 1)?.let { Collections.rotate(it, -clickedItemIndex) }
            notifyItemRangeChanged(0, clickedItemIndex + 1)

            primaryItemUpdated = true
        }
    }
}
