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
import timber.log.Timber
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CurrencyExchangeFragment : BaseFragment<FragmentCurrencyExchangeBinding>() {

    override fun layoutRes() = R.layout.fragment_currency_exchange

    companion object {
        fun newInstance(): CurrencyExchangeFragment {
            return CurrencyExchangeFragment()
        }
    }

    private var exchangeList: MutableList<CurrencyExchange>? = null

    private val refreshHandler = Handler()
    private val refreshExchangeRunnable = object: Runnable {
        override fun run() {
            viewModel.get().refresh.value = true
            refreshHandler.postDelayed(this, refreshInterval)
        }
    }
    private val refreshInterval = TimeUnit.MILLISECONDS.convert(10, TimeUnit.SECONDS)

    private lateinit var adapter: CurrencyExchangeAdapter
    private val currencyClickedEvent: ((CurrencyExchange) -> Unit)? = object : (CurrencyExchange) -> Unit {
        override fun invoke(p1: CurrencyExchange) {
            Timber.d("Clicked item $p1")

            viewModel.get().currentBaseCurrency = p1.currencyCode

            val clickedItemIndex = exchangeList?.indexOfFirst { it.currencyCode == p1.currencyCode }
            clickedItemIndex?.let {
                val previousItem = exchangeList?.get(0)
                previousItem?.isHeader = false

                val currentItem = exchangeList?.get(clickedItemIndex)
                currentItem?.isHeader = true

                // Adjust exchange rates based on current header
                currentItem?.let {
                    val currentAmount = currentItem.exchangeRate * adapter.amount.amount
                    val currentExchangeRate = currentItem.exchangeRate
                    exchangeList?.map { it.exchangeRate /= currentExchangeRate }
                    currentItem.exchangeRate = 1f
                    adapter.amount.amount = currentAmount
                }

                exchangeList?.subList(0, clickedItemIndex + 1)?.let { Collections.rotate(it, -clickedItemIndex) }
                adapter.notifyItemRangeChanged(0, clickedItemIndex + 1)
            }
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
            Timber.d("Currency info $exchangeRates")

            val list = exchangeRates?.exchangeRates?.toMutableList()
            exchangeList = list
            adapter.submitList(list ?: emptyList())
        })
    }
}