package com.lukmotech.bankbuddy.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.Observer
import com.lukmotech.bankbuddy.R
import com.lukmotech.bankbuddy.presentation.factory.ViewModelFactory
import com.lukmotech.bankbuddy.presentation.model.Status
import com.lukmotech.bankbuddy.presentation.model.UserInfo
import com.lukmotech.bankbuddy.presentation.viewmodels.HomeVM
import com.lukmotech.bankbuddy.ui.transactions.TransactionListActivity
import com.lukmotech.bankbuddy.utils.*
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private val dateFormat = SimpleDateFormat("MMM - dd", Locale.getDefault())

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var homeVM: HomeVM

    private var userInfo: UserInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        homeVM = ViewModelProviders.of(this, viewModelFactory).get(HomeVM::class.java)
        homeVM.getUserInfoResource.observe(this, Observer { resource ->
            when (resource.status) {
                Status.LOADING -> {
                    showLoader()
                }
                Status.ERROR -> {
                    userInfo = null
                    hideLoader()
                    tvHomeMessage.text = getText(R.string.msg_something_went_wrong)
                }
                Status.SUCCESS -> {
                    hideLoader()
                    resource.data?.let {
                        tvHomeUserName.text = it.displayName
                        tvHomeAccountNumber.text = 
                            String.format(
                                getString(R.string.display_account_num),
                                it.accountNumber
                            )
                        tvHomeBalance.text = 
                            String.format(
                                getString(R.string.display_account_balance),
                                it.accountBalance.toCurrency()
                            )

                        tvHomeAccountType.text = it.accountType.capitalize()

                        userInfo = it
                    }
                }
            }
        })

        displayBankOperationsClosedMessage()

        setClickListeners()
    }

    private fun setClickListeners() {
        llHomePremiumAccount.setOnClickListener {
            userInfo?.let {
                val title = when (it.premiumCustomer) {
                    true -> getText(R.string.title_premium_perks)
                    false -> getText(R.string.title_upgrade_to_premium)
                }
                showAlertDialog(
                    title,
                    R.string.msg_premium_account_benefits
                )
            }
        }

        llHomeImportantInfo.setOnClickListener {
            userInfo?.let {
                showAlertDialog(
                    getText(R.string.title_important_info),
                    R.string.msg_important_information
                )
            }
        }

        llHomeAccountType.setOnClickListener {
            userInfo?.let {
                showAlertDialog(
                    getText(R.string.title_important_info),
                    R.string.msg_account_type_info
                )
            }
        }

        llHomeTransactions.setOnClickListener {
            val transactionsIntent = Intent(this, TransactionListActivity::class.java)
            startActivity(transactionsIntent)
        }
    }

    private fun displayBankOperationsClosedMessage() {
        val operationShutDay = Calendar.getInstance().addDays(DAYS_AFTER_CURRENT_DATE)
        tvHomeMessage.text = String.format(
            getString(R.string.msg_operations_closed),
            dateFormat.format(operationShutDay.time)
        )
    }

    private fun hideLoader(){
        cvAccountInfoHolder.alpha = ALPHA_VISILE
        tvHomeMessage.alpha = ALPHA_VISILE
        llHomeActionHolder.alpha = ALPHA_VISILE
        pbHomeLoader.visibility = View.GONE
    }

    private fun showLoader() {
        cvAccountInfoHolder.alpha = ALPHA_HIDDEN
        tvHomeMessage.alpha = ALPHA_HIDDEN
        llHomeActionHolder.alpha = ALPHA_HIDDEN
        pbHomeLoader.visibility = View.VISIBLE
    }

    private fun showAlertDialog(title: CharSequence, msgPremiumAccountBenefits: Int) {
        AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(msgPremiumAccountBenefits)
            .setCancelable(true)
            .setPositiveButton(R.string.btn_txt_dismiss) { dialog, _ ->
                dialog.dismiss()
            }.create()
            .show()
    }

}
