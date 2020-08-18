package com.webronin_26.shopping.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.webronin_26.shopping.R
import com.webronin_26.shopping.ShoppingApplication
import com.webronin_26.shopping.databinding.CategoryFragmentBinding

class CategoryFragment : Fragment() {

    private lateinit var viewModel: CategoryViewModel

    private lateinit var viewDataBinding: CategoryFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.category_fragment, container, false)

        viewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        viewModel.repository = (requireContext().applicationContext as ShoppingApplication).repository
        lifecycle.addObserver(viewModel)

        viewDataBinding = CategoryFragmentBinding.bind(view).apply { viewmodel = viewModel }
        viewDataBinding.lifecycleOwner = this

        return view
    }
}
