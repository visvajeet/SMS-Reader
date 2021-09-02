package com.app.sms.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.sms.databinding.FragmentMessageDetailsBinding
import com.app.sms.models.MessageItem
import com.app.sms.utils.Constants.BUNDLE_KEY_MESSAGE_DETAILS
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MessageDetailsFragment : BottomSheetDialogFragment() {

    lateinit var binding : FragmentMessageDetailsBinding

    private val message by lazy {
        arguments?.getParcelable<MessageItem>(BUNDLE_KEY_MESSAGE_DETAILS)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentMessageDetailsBinding.inflate(inflater,container,false)
        binding.message = message
        binding.executePendingBindings()

        return binding.root

    }

}