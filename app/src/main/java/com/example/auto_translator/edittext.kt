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


/**
 * This class is what makes it so the fragment can send the info to the activity
 */
class ItemViewModel : ViewModel(){
    // This receives the data
    val selected = MutableLiveData<String>()
    // this is what gets the data
    val selectedItem: LiveData<String> get() = selected
    // this is what sets the data
    fun selectItem(item: String){
        selected.value = item
    }
}
class edittext : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    /**
     * this is what is used to setup the editText view and the entire fragment
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // this is the viewModel same as the one in mainActivity
        var viewModel : ItemViewModel = ViewModelProvider(requireActivity()).get(ItemViewModel::class.java)
        // This is the fragments view
        var view = inflater.inflate(R.layout.fragment_edittext,container,false)
        // This is the editText box
        var editText = view.findViewById<EditText>(R.id.textEditor)
        // This is how I know when the user changes the text
        editText.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // Whenever its changed it gets updated
                viewModel.selectItem(editText.text.toString())
            }

            override fun afterTextChanged(p0: Editable?) {
                // Or after the user gets done changing it
                viewModel.selectItem(editText.text.toString())
            }

        })
        return view
    }
    /**
     * This is so the fragment can actually get made
     */
    companion object {
        @JvmStatic
        fun newInstance() =
            edittext().apply {

            }
    }
}