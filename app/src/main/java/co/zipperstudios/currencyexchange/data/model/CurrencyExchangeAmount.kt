package co.zipperstudios.currencyexchange.data.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR

data class CurrencyExchangeAmount(var _amount: Float = 1f) :
    BaseObservable() {

    var amount: Float
        @Bindable get() = _amount
        set(value) {
            _amount = value
            notifyPropertyChanged(BR.amount)
        }
}