package com.skow.lab33

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
//    private var sizeItemId = R.id.radioButtonSizeMedium
//    private var colorItemId = R.id.radioButtonColorBlack

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Lab33)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolBar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.menu)

        val checkBoxItalic = findViewById<CheckBox>(R.id.radioButtonType1)
        val checkBoxBold = findViewById<CheckBox>(R.id.radioButtonType2)
        val checkBoxSerif = findViewById<CheckBox>(R.id.radioButtonType3)
        val radioGroupFontColor = findViewById<RadioGroup>(R.id.radioGroupFontColor)
        val radioGroupFontSize = findViewById<RadioGroup>(R.id.radioGroupFontSize)
        val buttonDayMode = findViewById<Button>(R.id.button_day)
        val buttonNightMode = findViewById<Button>(R.id.button_night)

        // Obsługa zmiany czcionki po zaznaczeniu/odznaczeniu checkboxów
        checkBoxItalic.setOnClickListener { updateFontStyle() }
        checkBoxBold.setOnClickListener { updateFontStyle() }
        checkBoxSerif.setOnClickListener { updateFontStyle() }


        radioGroupFontColor.setOnCheckedChangeListener { _, checkedId -> updateFontColor(checkedId) }


        radioGroupFontSize.setOnCheckedChangeListener { _, checkedId -> updateFontSize(checkedId) }


        buttonDayMode.setOnClickListener {
            val data = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
            if (data.getInt("theme_num", 0) != 1) {
                val editor = data.edit()
                editor.putInt("theme_num", 1)
                editor.apply()
                applyTheme(data)
                recreate()
            }
        }

        buttonNightMode.setOnClickListener {
            val data = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
            if (data.getInt("theme_num", 0) != 2) {
                val editor = data.edit()
                editor.putInt("theme_num", 2)
                editor.apply()
                applyTheme(data)
                recreate()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.red_trail -> startActivity(getString(R.string.red_trail), R.drawable.czerwony, R.style.AppTheme1)
            R.id.green_trail -> startActivity(getString(R.string.green_trail), R.drawable.zielony, R.style.AppTheme2)
            R.id.blue_trail -> startActivity(getString(R.string.blue_trail), R.drawable.niebieski, R.style.AppTheme3)
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun startActivity(text: String, img: Int, theme: Int): Boolean {
        val intent = Intent(this, Activity2::class.java)
        intent.putExtra("imageResourceId", img)
        intent.putExtra("textToShow", text)
        intent.putExtra("themeResourceId", theme)
        startActivity(intent)
        return true
    }

    private fun applyTheme(data: SharedPreferences): Int {
        when (data.getInt("theme_num", 0)) {
            1 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            2 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        return data.getInt("theme_num", 0)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    private fun updateFontStyle(){
        val isItalic = findViewById<CheckBox>(R.id.radioButtonType1).isChecked
        val isBold = findViewById<CheckBox>(R.id.radioButtonType2).isChecked
        val isSerif = findViewById<CheckBox>(R.id.radioButtonType3).isChecked


        val bold: Int = if (isBold) 700 else 400

        val newTypeface: Typeface = if (isSerif) {
            Typeface.create(Typeface.SERIF, bold , isItalic)
        }else {
            Typeface.create(Typeface.DEFAULT, bold, isItalic)
        }

        findViewById<TextView>(R.id.text_author).typeface = newTypeface
    }

    private fun updateFontSize(checkedId: Int){
        val textView = findViewById<TextView>(R.id.text_author)
        when(checkedId){
            R.id.radioButtonSizeSmall -> {
                textView.textSize = 10F
            }
            R.id.radioButtonSizeMedium -> {
                textView.textSize = 20F
            }
            R.id.radioButtonSizeLarge -> {
                textView.textSize = 30F
            }
        }
        //todo mozliwe zeby przypisywac wartosc do itemSizeId czy cos takiego zzeby po powrocie z innej
        //todo aktywnosci dzialalo

    }

    private fun updateFontColor(checkedId: Int){
        val textView = findViewById<TextView>(R.id.text_author)
        when(checkedId){
            R.id.radioButtonColorRed -> {
                textView.setTextColor(ContextCompat.getColor(this, R.color.redColor))
            }
            R.id.radioButtonColorGreen-> {
                textView.setTextColor(ContextCompat.getColor(this, R.color.greenColor))
            }
            R.id.radioButtonColorBlack -> {
                val data = getSharedPreferences("my_prefs", Context.MODE_PRIVATE)
                val stockColor = if (data.getInt("theme_num", 0) == 1) R.color.fontBlackColor
                else R.color.fontWhiteColor
                textView.setTextColor(ContextCompat.getColor(this, stockColor))
            }
        }
        //todo mozliwe zeby przypisywac wartosc do itemSizeId czy cos takiego zzeby po powrocie z innej
        //todo aktywnosci dzialalo
    }
}