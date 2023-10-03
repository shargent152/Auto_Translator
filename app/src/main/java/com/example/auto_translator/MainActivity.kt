package com.example.auto_translator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.languageid.LanguageIdentification
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import org.intellij.lang.annotations.Language
var translator = ""
var translateLang = 0
var sourceLang = 0

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.frag, edittext.newInstance(), "editText").commit()
        }
        var viewModel: ItemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        viewModel.selectedItem.observe(this, {
            translator = it
            if (translateLang == sourceLang){
                var translated = findViewById<TextView>(R.id.translatedword)
                translated.setText(translator)
            }
            else if (translateLang == 1 && sourceLang == 2){
                val options = TranslatorOptions.Builder().setSourceLanguage(TranslateLanguage.SPANISH).setTargetLanguage(TranslateLanguage.ENGLISH).build()
                translatorFunc(options,translator)
            }
            else if (translateLang == 1 && sourceLang == 3){
                val options = TranslatorOptions.Builder().setSourceLanguage(TranslateLanguage.GERMAN).setTargetLanguage(TranslateLanguage.ENGLISH).build()
                translatorFunc(options,translator)
            }
            else if (translateLang == 2 && sourceLang == 1){
                val options = TranslatorOptions.Builder().setSourceLanguage(TranslateLanguage.ENGLISH).setTargetLanguage(TranslateLanguage.SPANISH).build()
                translatorFunc(options,translator)
            }
            else if (translateLang == 2 && sourceLang == 3){
                val options = TranslatorOptions.Builder().setSourceLanguage(TranslateLanguage.GERMAN).setTargetLanguage(TranslateLanguage.SPANISH).build()
                translatorFunc(options,translator)
            }
            else if (translateLang == 3 && sourceLang == 1){
                val options = TranslatorOptions.Builder().setSourceLanguage(TranslateLanguage.ENGLISH).setTargetLanguage(TranslateLanguage.GERMAN).build()
                translatorFunc(options,translator)
            }
            else if (translateLang == 3 && sourceLang == 2) {
                val options =
                    TranslatorOptions.Builder().setSourceLanguage(TranslateLanguage.SPANISH)
                        .setTargetLanguage(TranslateLanguage.GERMAN).build()
                translatorFunc(options,translator)
            }
            else if (sourceLang == 4){
                var checker = false
                val languageId = LanguageIdentification.getClient()
                var lang = languageId.identifyLanguage(translator).addOnSuccessListener{
                    if (it == "und"){
                        Log.i("auto","No language")
                    }else{
                        Log.i("auto",it)
                        val language = TranslateLanguage.fromLanguageTag(it)
                        if (translateLang == 1 ){
                            val options =
                                TranslatorOptions.Builder().setSourceLanguage(language.toString())
                                    .setTargetLanguage(TranslateLanguage.ENGLISH).build()
                            translatorFunc(options,translator)
                        }
                        else if (translateLang == 2){
                            val options = TranslatorOptions.Builder().setSourceLanguage(language.toString())
                                .setTargetLanguage(TranslateLanguage.SPANISH).build()
                            translatorFunc(options,translator)
                        }
                        else if (translateLang == 3){
                            val options = TranslatorOptions.Builder().setSourceLanguage(language.toString())
                                .setTargetLanguage(TranslateLanguage.GERMAN).build()
                            translatorFunc(options,translator)
                        }
                    }
                }.addOnFailureListener {

                }




            }
        })

    }

    fun english_translate(view: View) {
        translateLang = 1
        var viewModel : ItemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        viewModel.selectItem(translator)
    }
    fun spanish_translate(view: View) {
        translateLang = 2
        var viewModel : ItemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        viewModel.selectItem(translator)
    }
    fun german_translate(view: View) {
        translateLang = 3
        var viewModel : ItemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        viewModel.selectItem(translator)
    }
    fun english_sourceLanguage(view: View) {
        sourceLang = 1
        var viewModel : ItemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        viewModel.selectItem(translator)
    }
    fun spanish_sourceLanguage(view: View) {
        sourceLang = 2
        var viewModel : ItemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        viewModel.selectItem(translator)
    }
    fun german_sourceLanguage(view: View) {
        sourceLang = 3
        var viewModel : ItemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        viewModel.selectItem(translator)
    }
    fun auto_Detect(view: View) {
        sourceLang = 4
        var viewModel : ItemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        viewModel.selectItem(translator)
    }
    fun translatorFunc(options: TranslatorOptions,translator: String){
        val Translation = Translation.getClient(options)
        var conditions = DownloadConditions.Builder().requireWifi().build()

        Translation.downloadModelIfNeeded(conditions).addOnSuccessListener {

        }
            .addOnFailureListener{

            }
        Translation.translate(translator).addOnSuccessListener {
            var translated = findViewById<TextView>(R.id.translatedword)
            translated.setText(it)
        }.addOnFailureListener{
            Log.i("plz","I BROKE")
        }
    }


}


