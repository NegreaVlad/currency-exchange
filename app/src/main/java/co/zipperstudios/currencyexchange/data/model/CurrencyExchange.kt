package co.zipperstudios.currencyexchange.data.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR

data class CurrencyExchange(
    val currencyCode: String,
    var _exchangeRate: Float,
    var isHeader: Boolean
) : BaseObservable() {
    var exchangeRate: Float
        @Bindable get() = _exchangeRate
        set(value) {
            _exchangeRate = value
            notifyPropertyChanged(BR.exchangeRate)
        }
}