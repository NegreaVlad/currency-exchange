package co.zipperstudios.currencyexchange.ui.currency.exchange

import android.os.Bundle
import android.os.Handler
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
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class CurrencyExchangeFragment : BaseFragment<FragmentCurrencyExchangeBinding>() {

    override fun layoutRes() = R.layout.fragment_currency_exchange

    companion object {
        fun newInstance(): CurrencyExchangeFragment {
            return CurrencyExchangeFragment()
        }
    }

    private val refreshHandler = Handler()
    private val refreshExchangeRunnable: Runnable
    private val currencyClickedEvent: ((CurrencyExchange) -> Unit)?
    private val refreshInterval = TimeUnit.MILLISECONDS.convert(1, TimeUnit.SECONDS)

    private lateinit var adapter: CurrencyExchangeAdapter

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<CurrencyExchangeVM>

    init {
        currencyClickedEvent = object : (CurrencyExchange) -> Unit {
            override fun invoke(currencyExchange: CurrencyExchange) {
                viewModel.get().currentBaseCurrency = currencyExchange.currencyCode

                adapter.updatePrimaryCurrency(currencyExchange)
            }
        }

        refreshExchangeRunnable = object : Runnable {
            override fun run() {
                viewModel.get().refresh.value = true
                refreshHandler.postDelayed(this, refreshInterval)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()
        initBinding()
        initObservers()

        refreshHandler.post(refreshExchangeRunnable)
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
            binding.placeholder.visibility =
                if (exchangeRates?.exchangeRates?.isNotEmpty() == true) View.GONE else View.VISIBLE

            val list = exchangeRates?.exchangeRates?.toMutableList()
            adapter.submitList(list)
        })
    }
}