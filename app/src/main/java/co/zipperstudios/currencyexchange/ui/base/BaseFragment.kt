package co.zipperstudios.currencyexchange.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import co.zipperstudios.currencyexchange.binding.FragmentDataBindingComponent
import dagger.android.support.DaggerFragment

abstract class BaseFragment<T : ViewDataBinding> : DaggerFragment() {

    lateinit var binding: T

    @LayoutRes
    abstract fun layoutRes(): Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val dataBinding = DataBindingUtil.inflate<T>(
            inflater,
            layoutRes(),
            container,
            false,
            FragmentDataBindingComponent(this)
        )
        binding = dataBinding
        return dataBinding.root
    }
}