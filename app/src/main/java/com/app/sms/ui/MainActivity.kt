package com.app.sms.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import com.app.sms.R
import com.app.sms.adapters.MessageAdapter
import com.app.sms.databinding.ActivityMainBinding
import com.app.sms.models.MessageItem
import com.app.sms.utils.CheckPermission
import com.app.sms.utils.Constants.BUNDLE_KEY_MESSAGE_DETAILS
import com.app.sms.viewmodels.MessagesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MessagesViewModel by viewModels()

    private lateinit var adapter: MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setAdapter()
        observeData()

    }

    private fun observeData() {
        if (!CheckPermission.hasPermissions(this)) {
            return
        }

        lifecycleScope.launchWhenResumed {
            viewModel.messagesFLow.collectLatest {
                adapter.submitData(it)
            }
        }
    }

    private fun setAdapter() {
        adapter = MessageAdapter(clickListener)
        binding.rv.adapter = adapter
    }

    private val clickListener = MessageAdapter.ClickListener { _: View, messageItem: MessageItem ->

        val frg = MessageDetailsFragment()
        val bundle = Bundle()
        bundle.putParcelable(BUNDLE_KEY_MESSAGE_DETAILS, messageItem)
        frg.arguments = bundle
        frg.show(supportFragmentManager,"MessageDetailsFragment")

    }

    override fun onStart() {
        super.onStart()
        if (!CheckPermission.hasPermissions(this)) {
            openPermissionActivity()
        }
    }

    private fun openPermissionActivity() {
        startActivity(Intent(this, PermissionActivity::class.java))
        finish()
    }

}