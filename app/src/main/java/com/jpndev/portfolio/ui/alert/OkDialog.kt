package com.jpndev.portfolio.ui.alert

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.jpndev.portfolio.R
import com.jpndev.portfolio.databinding.AlertOkBinding
import com.jpndev.portfolio.databinding.FragmentMoreBinding
import com.jpndev.portfolio.ui.interfaces.OnDismissListner



class OkDialog : DialogFragment() {


    var mBaseDialog: MBaseDialog?=null
 
    var from: String? = "Coming Soon"

    companion object {

        var closed = true
       
        fun newInstance(content: String): OkDialog {
            val f = OkDialog()

            val args = Bundle()

            f.arguments = args

            return f
        }
        fun newInstance(): OkDialog {
            val f = OkDialog()
            val args = Bundle()
            f.arguments = args

            return f
        }
        fun newInstance(mBaseDialog: MBaseDialog): OkDialog {
            val f = OkDialog()

            val args = Bundle()
            args.putParcelable("ok_dialog_bundle",mBaseDialog)

            f.arguments = args

            return f
        }
        fun newInstance(mBaseDialog: MBaseDialog, from:String): OkDialog {
            val f = OkDialog()

            val args = Bundle()
            args.putParcelable("ok_dialog_bundle",mBaseDialog)
            args.putString("from", from)

            f.arguments = args

            return f
        }
        fun showDialog(context: Activity) :OkDialog{

            val newFragment = OkDialog.newInstance()
            try {
                if (!context.isFinishing && !context.isDestroyed) {
                    val fragmentManager = (context as FragmentActivity).supportFragmentManager

                    val transaction = fragmentManager.beginTransaction()
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    transaction.add(android.R.id.content, newFragment)
                        .addToBackStack(null).commit()
                }
            } catch (e: Exception) {
                e.message
            }
            return newFragment
        }
        fun showDialog(context: Activity, mBaseDialog: MBaseDialog) :OkDialog{
            val newFragment = OkDialog.newInstance(mBaseDialog)
            try {

                if (!context.isFinishing && !context.isDestroyed) {
                    val fragmentManager = (context as FragmentActivity).supportFragmentManager

                    val transaction = fragmentManager.beginTransaction()
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    transaction.add(android.R.id.content, newFragment)
                        .addToBackStack(null).commit()


                }
            } catch (e: Exception) {
                e.message
            }

            return newFragment

        }
        fun showDialog(context: Activity, mBaseDialog: MBaseDialog, from:String) :OkDialog{
            val newFragment = OkDialog.newInstance(mBaseDialog,from)

            try {
                if (!context.isFinishing && !context.isDestroyed) {
                    val fragmentManager = (context as FragmentActivity).supportFragmentManager

                    val transaction = fragmentManager.beginTransaction()
                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    transaction.add(android.R.id.content, newFragment)
                        .addToBackStack(null).commit()
                }
            } catch (e: Exception) {
                e.message
            }
            return newFragment
        }




    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBaseDialog = getArguments()?.getParcelable("ok_dialog_bundle")


        from= mBaseDialog?.from

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

     //   _binding = AlertOkBinding.inflate(inflater, container, false)
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return dialog
    }

    private var _binding: AlertOkBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        _binding = AlertOkBinding.inflate(inflater, container, false)
        val root: View = binding.root

        /*val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })*/
        return root
      //  return inflater.inflate(R.layout.alert_ok, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        closed = false
        binding.  text2Txv.visibility=View.GONE

        mBaseDialog?.let {
           it. desc?.let {
                binding.  text2Txv.setText(it)
                if( it?.isNotEmpty()?:false)
                    binding.  text2Txv.visibility=View.VISIBLE

            }
            if(mBaseDialog!!.close_icon!!)
                binding.  closeRlay.visibility= View.VISIBLE
            else
                binding.  closeRlay.visibility= View.GONE
            it. title?.let {
                binding.  text1Txv.setText(it)
                if( it?.isNotEmpty()?:false)
                    binding.  text1Txv.visibility=View.VISIBLE

            }




        }?:let {


        }
        setErrorText("")
        val listener = activity as OnDismissListner?
        binding. topRlay.setOnClickListener(View.OnClickListener { })
        binding. topRlay.setOnTouchListener(View.OnTouchListener { v, event -> false })
        binding.closeRlay.setOnClickListener(View .OnClickListener {
            listener?.onDismiss(false)
            onBackFinish()

        })
        binding. okCview.setOnClickListener(View.OnClickListener {
            listener?.onDismiss(true)
            onBackFinish()

        })

     //   showKeyboard(otp_pinv)
    }

    fun setOTPText(text: String) {
    }

    fun setErrorText(text: String) {
        Log.d("_sms", "setErrorText text:" + text)
      //  Log.d("_sms", "setErrorText error_txv :" + error_txv?.text)
      //  error_txv?.setText(text)
    }

    fun popupDismiss() {
        val listener = activity as OnDismissListner?
        listener?.onDismiss(false)
        onBackFinish()
    }

    fun redirectLogin(activity: Activity?) {
        activity?.let {
         /*   ToastHandler.newInstance(activity).mustShowToastSessionExpired()
            HomeSignTabActivity.tab_position = 4
            CCRBXActivity.class_name = activity?.javaClass
            val intent = Intent(activity, SignktActivity::class.java)
            startActivity(intent)*/
        }
    }


    fun onBackFinish() {

        closed = true
        dismiss()
    }




}
