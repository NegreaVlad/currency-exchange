package co.zipperstudios.currencyexchange.ui.currency.exchange

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import co.zipperstudios.currencyexchange.R
import co.zipperstudios.currencyexchange.binding.FragmentDataBindingComponent
import co.zipperstudios.currencyexchange.data.model.CurrencyExchange
import co.zipperstudios.currencyexchange.databinding.FragmentCurrencyExchangeBinding
import co.zipperstudios.currencyexchange.di.ViewModelInjectionField
import co.zipperstudios.currencyexchange.di.qualifiers.ViewModelInjection
import co.zipperstudios.currencyexchange.ui.base.BaseFragment
import co.zipperstudios.currencyexchange.ui.currency.exchange.adapter.CurrencyExchangeAdapter
import timber.log.Timber
import javax.inject.Inject

class CurrencyExchangeFragment : BaseFragment<FragmentCurrencyExchangeBinding>() {

    override fun layoutRes() = R.layout.fragment_currency_exchange

    companion object {
        fun newInstance(): CurrencyExchangeFragment {
            return CurrencyExchangeFragment()
        }
    }

    private lateinit var adapter: CurrencyExchangeAdapter
    private val currencyClickedEvent: ((CurrencyExchange) -> Unit)? = object : (CurrencyExchange) -> Unit {
        override fun invoke(p1: CurrencyExchange) {

        }
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<CurrencyExchangeVM>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initBinding()
        initObservers()
    }

    private fun initAdapter() {
        adapter = CurrencyExchangeAdapter(FragmentDataBindingComponent(this), currencyClickedEvent)
    }

    private fun initBinding() {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.exchangesList.adapter = adapter
    }

    private fun initObservers() {
        viewModel.get().currencyExchanges.observe(this, Observer { exchangeRates ->
            Timber.d("Currency info $exchangeRates")

//            if (jobs.status == Status.SUCCESS || jobs.status == Status.ERROR) {
//                swipe_to_refresh.isRefreshing = false
//            }

            if (exchangeRates != null) {
                adapter.submitList(exchangeRates.data?.exchangeRates?.toList())
            } else {
                adapter.submitList(emptyList())
            }
        })
    }
}