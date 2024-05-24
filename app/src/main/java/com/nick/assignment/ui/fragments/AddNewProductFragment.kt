package com.nick.assignment.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ajit.swipeandroidassignment.R
import com.nick.assignment.domain.Product
import com.ajit.swipeandroidassignment.databinding.FragmentAddNewProductBinding
import com.nick.assignment.ui.viewmodel.ProductViewModel
import com.nick.assignment.utils.ResponseState
import com.nick.assignment.utils.toast
import org.koin.android.ext.android.inject


class AddNewProductFragment : Fragment() {

    private lateinit var binding: FragmentAddNewProductBinding
    private val viewModel by inject<ProductViewModel>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddNewProductBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()

        val productTypes = resources.getStringArray(R.array.product_types)
        val spinnerAdapter = ArrayAdapter(requireContext(), androidx.transition.R.layout.support_simple_spinner_dropdown_item, productTypes)
        binding.spinnerProductType.adapter = spinnerAdapter

        binding.btnAddProduct.setOnClickListener {
            val productName = binding.editTextProductName.text.toString()
            val productType = binding.spinnerProductType.selectedItem.toString()
            val price = binding.editTextSellingPrice.text.toString().toDoubleOrNull()
            val tax = binding.editTextTaxRate.text.toString().toDoubleOrNull()

            if (validateInputs(productName, productType, price, tax)) {
                val product = Product(null, price!!, productName, productType, tax!!)

                viewModel.addProduct(product)
            }
        }

        binding.addProductBack.setOnClickListener {
            findNavController().navigateUp()
        }


    }

    private fun observeViewModel() {

        viewModel.addProductResult.observe(viewLifecycleOwner) { result ->
            when (result) {
                is ResponseState.Loading -> {
                    binding.addProductProgress.visibility = View.VISIBLE
                }
                is ResponseState.Success -> {
                    binding.addProductProgress.visibility = View.GONE
                    if (result.data) {
                        toast("Product added successfully!")
                        findNavController().navigateUp()
                    } else {
                            toast("Failed to add product.")
                    }
                }

                is ResponseState.Failure -> {
                    binding.addProductProgress.visibility = View.GONE
                    toast("Failed to add product.")
                    Log.e("add", "${result.error}")
                }
            }


        }
    }

    private fun validateInputs(
        productName: String,
        productType: String,
        price: Double?,
        tax: Double?
    ): Boolean {
        if (productName.isEmpty()) {
            binding.editTextProductName.error = "Product name cannot be empty"
            return false
        }

        if (productType.isEmpty()) {
        toast("Please select a product type")
            return false
        }

        if (price == null) {
            binding.editTextSellingPrice.error = "Invalid price"
            return false
        }

        if (tax == null) {
            binding.editTextTaxRate.error = "Invalid tax rate"
            return false
        }

        return true
    }



}