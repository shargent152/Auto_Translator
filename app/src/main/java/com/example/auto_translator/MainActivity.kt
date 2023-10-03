package com.example.auto_translator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

var sourceLang = 0
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null){
            var fragment = supportFragmentManager.beginTransaction().add(R.id.frag,edittext.newInstance(),"editText").commit()
                fragment
        }

    }

    fun english_translate(view: View) {
        if(sourceLang == 1){
            // does nothing same language

        }
        else if (sourceLang == 2){
            // goes to spanish
        }
        else if (sourceLang == 3){
            // goes to german
        }
    }
    fun spanish_translate(view: View) {}
    fun german_translate(view: View) {}
    fun english_sourceLanguage(view: View) {
        sourceLang = 1
    }
    fun spanish_sourceLanguage(view: View) {
        sourceLang = 2
    }
    fun german_sourceLanguage(view: View) {
        sourceLang = 3
    }
}


