package com.suzu.test.ui.library

import android.content.Context
import android.view.LayoutInflater
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.suzu.test.databinding.BottomSheetLibraryFilterBinding

class LibraryFilterBottomSheet(
    context: Context,
    private val initialState: FilterState,
    private val onFilterChanged: (FilterState) -> Unit
) : BottomSheetDialog(context) {

    private val binding: BottomSheetLibraryFilterBinding =
        BottomSheetLibraryFilterBinding.inflate(LayoutInflater.from(context))

    init {
        setContentView(binding.root)
        setupViews()
    }

    private fun setupViews() {
        binding.cbNoKeywords.isChecked = initialState.noKeywords
        binding.cbUncategorized.isChecked = initialState.uncategorized
        binding.cbIsGif.isChecked = initialState.isGif
        binding.cbIsNonGif.isChecked = initialState.isNonGif

        val checkChangeListener = {
            val newState = FilterState(
                noKeywords = binding.cbNoKeywords.isChecked,
                uncategorized = binding.cbUncategorized.isChecked,
                isGif = binding.cbIsGif.isChecked,
                isNonGif = binding.cbIsNonGif.isChecked
            )
            onFilterChanged(newState)
        }

        binding.cbNoKeywords.setOnCheckedChangeListener { _, _ -> checkChangeListener() }
        binding.cbUncategorized.setOnCheckedChangeListener { _, _ -> checkChangeListener() }
        binding.cbIsGif.setOnCheckedChangeListener { _, _ -> checkChangeListener() }
        binding.cbIsNonGif.setOnCheckedChangeListener { _, _ -> checkChangeListener() }

        binding.btnClearFilter.setOnClickListener {
            binding.cbNoKeywords.isChecked = false
            binding.cbUncategorized.isChecked = false
            binding.cbIsGif.isChecked = false
            binding.cbIsNonGif.isChecked = false
            val emptyState = FilterState()
            onFilterChanged(emptyState)
        }
    }
}
