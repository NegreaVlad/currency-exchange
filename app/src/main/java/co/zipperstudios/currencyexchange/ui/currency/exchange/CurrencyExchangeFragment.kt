package co.zipperstudios.currencyexchange.ui.currency.exchange

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import co.zipperstudios.currencyexchange.R
import co.zipperstudios.currencyexchange.databinding.FragmentCurrencyExchangeBinding
import co.zipperstudios.currencyexchange.di.ViewModelInjectionField
import co.zipperstudios.currencyexchange.di.qualifiers.ViewModelInjection
import co.zipperstudios.currencyexchange.ui.base.BaseFragment
import javax.inject.Inject

class CurrencyExchangeFragment : BaseFragment<FragmentCurrencyExchangeBinding>() {

    override fun layoutRes() = R.layout.fragment_currency_exchange

    companion object {
        fun newInstance(): CurrencyExchangeFragment {
            return CurrencyExchangeFragment()
        }
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<CurrencyExchangeVM>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initBinding()
        initObservers()
    }

    private fun initBinding() {
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun initObservers() {
        viewModel.get().currencyExchanges.observe(this, Observer {

        })
    }
}