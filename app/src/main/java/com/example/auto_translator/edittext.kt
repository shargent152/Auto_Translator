package com.example.auto_translator

import android.content.ClipData.Item
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.w3c.dom.Text

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [edittext.newInstance] factory method to
 * create an instance of this fragment.
 */

class ItemViewModel : ViewModel(){
    val selected = MutableLiveData<String>()
    val selectedItem: LiveData<String> get() = selected
    fun selectItem(item: String){
        selected.value = item
    }
}
class edittext : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var viewModel : ItemViewModel = ViewModelProvider(requireActivity()).get(ItemViewModel::class.java)
        var view = inflater.inflate(R.layout.fragment_edittext,container,false)

        var editText = view.findViewById<EditText>(R.id.textEditor)
        editText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.selectItem(editText.text.toString())
            }

            override fun afterTextChanged(p0: Editable?) {
                viewModel.selectItem(editText.text.toString())
            }

        })
        // Inflate the layout for this fragment
        return view
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment edittext.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            edittext().apply {
                arguments = Bundle().apply {

                }
            }
    }
}