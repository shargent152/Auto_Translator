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
// The string the user inputs
var translator = ""
// decides which translator to use
var translateLang = 0
// decides what the source language is
var sourceLang = 0

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // checks that its the first run through so the fragment can run
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.frag, edittext.newInstance(), "editText").commit()
        }
        // creates the viewModel so data can be sent between the fragment and the activity
        var viewModel: ItemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        // connects to the viewModel
        viewModel.selectedItem.observe(this) {
            // sets the string to whatever was sent to the viewModel
            translator = it
            // this sees if the same language is picked and if so it just repeats the word
            if (translateLang == sourceLang){
                var translated = findViewById<TextView>(R.id.translatedword)
                translated.setText(translator)
            }
            // ALL OF THESE ARE THE SAME
            // 1 is english 2 is spanish 3 is german and 4 is auto detect
            // First it checks to see which pair the user picked
            // Then it makes a translation builder
            // then it runs the translatorFunction
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
            // This one is special
            // It first finds what language the string is
            // then it figures out which translator the user wants to use
            // then it translates
            // basically the same just with one more step
            else if (sourceLang == 4){
                val languageId = LanguageIdentification.getClient()
                languageId.identifyLanguage(translator).addOnSuccessListener{
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
        }

    }

    /**
     * Runs when the english translate radio button is pushed
     * it also resets the viewModel just in case that the user has everything else picked
     * That way they don't have to push the button twice to change the language
     */
    fun english_translate(view: View) {
        translateLang = 1
        var viewModel : ItemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        viewModel.selectItem(translator)
    }
    /**
     * Runs when the spanish translate radio button is pushed
     * it also resets the viewModel just in case that the user has everything else picked
     * That way they don't have to push the button twice to change the language
     */
    fun spanish_translate(view: View) {
        translateLang = 2
        var viewModel : ItemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        viewModel.selectItem(translator)
    }
    /**
     * Runs when the german translate radio button is pushed
     * it also resets the viewModel just in case that the user has everything else picked
     * That way they don't have to push the button twice to change the language
     */
    fun german_translate(view: View) {
        translateLang = 3
        var viewModel : ItemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        viewModel.selectItem(translator)
    }
    /**
     * Runs when the english source radio button is pushed
     * it also resets the viewModel just in case that the user has everything else picked
     * That way they don't have to push the button twice to change the language
     */
    fun english_sourceLanguage(view: View) {
        sourceLang = 1
        var viewModel : ItemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        viewModel.selectItem(translator)
    }
    /**
     * Runs when the spanish source radio button is pushed
     * it also resets the viewModel just in case that the user has everything else picked
     * That way they don't have to push the button twice to change the language
     */
    fun spanish_sourceLanguage(view: View) {
        sourceLang = 2
        var viewModel : ItemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        viewModel.selectItem(translator)
    }
    /**
     * Runs when the german source radio button is pushed
     * it also resets the viewModel just in case that the user has everything else picked
     * That way they don't have to push the button twice to change the language
     */
    fun german_sourceLanguage(view: View) {
        sourceLang = 3
        var viewModel : ItemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        viewModel.selectItem(translator)
    }
    /**
     * Runs when the auto detect radio button is pushed
     * it also resets the viewModel just in case that the user has everything else picked
     * That way they don't have to push the button twice to change the language
     */
    fun auto_Detect(view: View) {
        sourceLang = 4
        var viewModel : ItemViewModel = ViewModelProvider(this).get(ItemViewModel::class.java)
        viewModel.selectItem(translator)
    }

    /**
     * This function takes two variables
     * it takes a TranslatorOptions variable so that it can translate
     * it also takes the string it will be translating
     * it does not return anything because it edits the textview itself
     */
    fun translatorFunc(options: TranslatorOptions,translator: String){
        // gets the final building blocks of the translator made
        val Translation = Translation.getClient(options)
        // checks to make sure the user has the translator downloaded
        var conditions = DownloadConditions.Builder().requireWifi().build()
        // this downloads it if needed
        Translation.downloadModelIfNeeded(conditions).addOnSuccessListener {

        }
            .addOnFailureListener{

            }
        // this does the actual translating
        Translation.translate(translator).addOnSuccessListener {
            // This pulls the textView
            var translated = findViewById<TextView>(R.id.translatedword)
            translated.setText(it)
        }.addOnFailureListener{
            Log.i("error","I BROKE")
        }
    }


}


