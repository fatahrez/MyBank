package com.lukmotech.bankbuddy.ui.transactions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.lukmotech.bankbuddy.R
import com.lukmotech.bankbuddy.presentation.factory.ViewModelFactory
import com.lukmotech.bankbuddy.presentation.model.Status
import com.lukmotech.bankbuddy.presentation.model.Transaction
import com.lukmotech.bankbuddy.presentation.viewmodels.TransactionVM
import com.lukmotech.bankbuddy.utils.ALPHA_HIDDEN
import com.lukmotech.bankbuddy.utils.ALPHA_VISILE
import com.lukmotech.bankbuddy.utils.setCustomChecked
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_transaction_list.*
import javax.inject.Inject

class TransactionListActivity : AppCompatActivity(),
    TransactionListAdapter.TransactionClickListener,
    CompoundButton.OnCheckedChangeListener {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var transactionVM: TransactionVM

    private val transactionListAdapter = TransactionListAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_transaction_list)

        supportActionBar?.let {
            it.setDisplayShowHomeEnabled(true)
            it.setDisplayHomeAsUpEnabled(true)
        }

        rvTransactionList.layoutManager = LinearLayoutManager(this)
        rvTransactionList.adapter = transactionListAdapter

        checkboxTransactionCredit.setOnCheckedChangeListener(this)
        checkboxTransactionDebit.setOnCheckedChangeListener(this)
        checkboxTransactionFlagged.setOnCheckedChangeListener(this)

        transactionVM = ViewModelProviders.of(this, viewModelFactory)
            .get(TransactionVM::class.java)
        transactionVM.transactionListSource.observe(this, Observer {
            when (it.status) {
                Status.LOADING -> {
                    showLoader()
                }
                Status.ERROR -> {
                    hideLoader()
                }
                Status.SUCCESS -> {
                    hideLoader()
                    it.data?.let { transitions ->
                        transactionListAdapter.populate(transitions)
                    }
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_transaction, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        item?.let {
            when(item.itemId) {
                android.R.id.home -> {
                    this@TransactionListActivity.finish()
                }
                R.id.action_reset_filter -> {
                    checkboxTransactionCredit.setCustomChecked(false, this)
                    checkboxTransactionDebit.setCustomChecked(false, this)
                    checkboxTransactionFlagged.setCustomChecked(false, this)
                    transactionVM.resetFilters()
                }
            }
        }
        return false
    }

    private fun hideLoader() {
        pbTransactionLoader.visibility = View.GONE
        rvTransactionList.alpha = ALPHA_VISILE
        llTransactionFilterHolder.alpha = ALPHA_VISILE
    }

    private fun showLoader() {
        pbTransactionLoader.visibility = View.VISIBLE
        rvTransactionList.alpha = ALPHA_HIDDEN
        llTransactionFilterHolder.alpha = ALPHA_HIDDEN
    }

    override fun onTransactionTapped(transaction: Transaction) {
        Toast.makeText(this, "${transaction.amountInCents} clicked", Toast.LENGTH_SHORT).show()
    }

    override fun onFlaggedToggled(transaction: Transaction) {
        TODO("Not yet implemented")
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        buttonView?.let {
            transactionVM.filterTransaction(
                credit = checkboxTransactionCredit.isChecked,
                debit = checkboxTransactionDebit.isChecked,
                flagged = checkboxTransactionFlagged.isChecked
            )
        }
    }
}