package co.zipperstudios.currencyexchange.ui.currency.exchange

import javax.inject.Inject
import co.zipperstudios.currencyexchange.R
import co.zipperstudios.currencyexchange.di.qualifiers.ViewModelInjection
import co.zipperstudios.currencyexchange.di.ViewModelInjectionField
import co.zipperstudios.currencyexchange.ui.base.BaseFragment

class CurrencyExchangeFragment : BaseFragment() {

    override fun layoutRes() = R.layout.fragment_currency_exchange

    companion object {
        fun newInstance(): CurrencyExchangeFragment {
            return CurrencyExchangeFragment()
        }
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<CurrencyExchangeVM>

}