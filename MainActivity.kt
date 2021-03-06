package fastcampus.aop.part2.app_part2_chapter_02

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    private val clearButton: Button by lazy {
        findViewById<Button>(R.id.clearButton)
    }

    private val addButton: Button by lazy {
        findViewById<Button>(R.id.addButton)
    }

    private val runButton: Button by lazy {
        findViewById<Button>(R.id.runButton)
    }

    private val numberPicker: NumberPicker by lazy {
        findViewById<NumberPicker>(R.id.numberPicker)
    }

    private val numberTextViewList: List<TextView> by lazy {
        listOf<TextView>(
            findViewById<TextView>(R.id.textView1),
            findViewById<TextView>(R.id.textView2),
            findViewById<TextView>(R.id.textView3),
            findViewById<TextView>(R.id.textView4),
            findViewById<TextView>(R.id.textView5),
            findViewById<TextView>(R.id.textView6)
            )
    }

    private var didRun = false

    private val pickNumberSet = hashSetOf<Int>();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberPicker.minValue = 1
        numberPicker.maxValue = 45

        initRunButton()
        initAddButton()
        initClearButton()
    }

    private fun initRunButton(){
        runButton.setOnClickListener {
            val list = getRandomNumber()

            didRun = true

            list.forEachIndexed { index, number ->
                val textView = numberTextViewList[index]

                textView.text = number.toString()
                textView.isVisible = true
            }
            Log.d("MainActivity", list.toString())
        }
    }

    private fun initAddButton(){
        addButton.setOnClickListener {
            if(didRun){
                Toast.makeText(this, "????????? ?????? ??????????????????", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(pickNumberSet.size >= 5){
                Toast.makeText(this, "????????? 5???????????? ????????? ??? ????????????", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(pickNumberSet.contains(numberPicker.value)){
                Toast.makeText(this, "?????? ????????? ???????????????", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val textView = numberTextViewList[pickNumberSet.size]
            textView.isVisible = true /// ????????? ???????????? ??????
            textView.text = numberPicker.value.toString()

            pickNumberSet.add(numberPicker.value)
        }
    }

    private fun initClearButton() {
        clearButton.setOnClickListener {
            pickNumberSet.clear()
            numberTextViewList.forEach{
                it.isVisible = false
            }
            didRun = false
        }
    }
    private fun getRandomNumber(): List<Int> {
        val numberList = mutableListOf<Int>()
            .apply {
                for (i in 1..45) {
                    if (pickNumberSet.contains(i)) {
                        continue
                    }
                    this.add(i)
                }
            }
        numberList.shuffle()

        val newList = pickNumberSet.toList() + numberList.subList(0, 6 - pickNumberSet.size)
        return newList.sorted()
    }
}