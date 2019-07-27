package co.zipperstudios.currencyexchange.ui.base

import android.app.Application
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

abstract class BaseViewModel() : ViewModel() {
    val compositeDisposable = CompositeDisposable()
}