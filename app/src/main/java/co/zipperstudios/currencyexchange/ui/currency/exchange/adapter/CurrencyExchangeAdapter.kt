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
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import co.zipperstudios.currencyexchange.R
import co.zipperstudios.currencyexchange.data.model.CurrencyExchange
import co.zipperstudios.currencyexchange.data.model.CurrencyExchangeAmount
import co.zipperstudios.currencyexchange.databinding.CurrencyItemBinding
import co.zipperstudios.currencyexchange.ui.common.DataBoundListAdapter


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
) {
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
        binding.root.setOnClickListener {
            binding.exchange?.let {
                callback?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: CurrencyItemBinding, item: CurrencyExchange) {
        binding.exchange = item
        binding.amount = amount
        binding.textChangeListener = textChangeListener

        if (item.isHeader) {
            dataBindingComponent.fragmentBindingAdapters.bindExchangeRate(binding.currencyAmount, amount.amount, item.exchangeRate, false, textChangeListener)
        }
    }

    override fun submitList(list: MutableList<CurrencyExchange>?) {
        if (items != null) {
            items?.map {originalCurrency ->  list?.find { it.currencyCode == originalCurrency.currencyCode }?.exchangeRate?.let { originalCurrency.exchangeRate = it } }
        } else {
            items = list
            super.submitList(list ?: emptyList())
        }
    }
}
