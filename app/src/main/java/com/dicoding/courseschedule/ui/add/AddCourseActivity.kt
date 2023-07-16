package com.dicoding.courseschedule.ui.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Spinner
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.util.TimePickerFragment
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddCourseActivity : AppCompatActivity(), TimePickerFragment.DialogTimeListener {

    private lateinit var viewModel: AddCourseViewModel

    private lateinit var tvStartTime: TextView
    private lateinit var tvEndTime: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)

        tvStartTime = findViewById(R.id.tv_start_time)
        tvEndTime = findViewById(R.id.tv_end_time)

        val factory = AddViewModelFactory.createFactory(this)
        viewModel = ViewModelProvider(this, factory).get(AddCourseViewModel::class.java)
    }

    private fun setInput() {
        val edCourseName = findViewById<TextInputEditText>(R.id.ed_course_name)
        val spinnerDay = findViewById<Spinner>(R.id.spinner_day)
        val edLecturer = findViewById<TextInputEditText>(R.id.ed_lecturer)
        val edNote = findViewById<TextInputEditText>(R.id.ed_note)

        val dayNumber = spinnerDay.selectedItemPosition

        viewModel.insertCourse(
            edCourseName.text.toString(),
            dayNumber,
            tvStartTime.text.toString(),
            tvEndTime.text.toString(),
            edLecturer.text.toString(),
            edNote.text.toString()
        )
        finish()
    }

    fun showTimePicker(view: View) {
        val dialogFragment = TimePickerFragment()
        dialogFragment.show(supportFragmentManager, view.id.toString())
    }

    override fun onDialogTimeSet(tag: String?, hour: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        when (tag) {
            R.id.ib_start_time.toString() -> {
                tvStartTime.text = dateFormat.format(calendar.time)
            }

            R.id.ib_end_time.toString() -> {
                tvEndTime.text = dateFormat.format(calendar.time)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_add, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_insert -> {
                setInput()
            }
        }
        return true
    }
}