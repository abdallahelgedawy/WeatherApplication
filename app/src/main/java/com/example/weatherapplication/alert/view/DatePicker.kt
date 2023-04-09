package com.example.weatherapplication.alert.view

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.weatherapplication.R
import com.example.weatherapplication.alert.TimeDate
import com.example.weatherapplication.alert.viewmodel.AlertViewModel
import com.example.weatherapplication.alert.viewmodel.AlertViewModelFactory
import com.example.weatherapplication.dB.ConcreteLocalSource
import com.example.weatherapplication.model.Repository
import com.example.weatherapplication.network.WeatherClient
import java.text.SimpleDateFormat
import java.util.*
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.hours
import kotlin.time.toDuration


class DatePicker : Fragment() {
  lateinit var calendar: Calendar
  lateinit var dataPickerDialog: DatePickerDialog
  lateinit var selectDate : Button
  lateinit var selectTime : Button
  lateinit var date : TextView
  lateinit var time_tv : TextView
  lateinit var timeto : String
  lateinit var time_for_tv : TextView
  lateinit var date_for_tv : TextView
  lateinit var btn_time_for : Button
  lateinit var btn_date_for : Button
  lateinit var btn_save : Button
  lateinit var time : String
  lateinit var datefrom : String
  lateinit var dateto : String
  lateinit var radioGroup: RadioGroup


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_date_picker, container, false)
    }

    companion object {


        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DatePicker().apply {
                arguments = Bundle().apply {

                }
            }
    }

    private fun updateTimeLabel(totime: Calendar ) {
       val format = SimpleDateFormat("hh:mm aa")
          time = format.format(totime.time)
        time_tv.text = time

    }
    private fun updateTimeLabelto(totime: Calendar ) {
        val format = SimpleDateFormat("hh:mm aa")
        timeto = format.format(totime.time)
        time_for_tv.text = timeto
    }


    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        selectDate = view.findViewById(R.id.select_date)
        selectTime = view.findViewById(R.id.select_time)
        radioGroup = view.findViewById(R.id.notification_alarm)
        date = view.findViewById(R.id.date)
        time_tv = view.findViewById(R.id.time)
        time_for_tv = view.findViewById(R.id.tv_time_For)
        date_for_tv = view.findViewById(R.id.tv_date_for)
        btn_time_for = view.findViewById(R.id.select_time_for)
        btn_date_for = view.findViewById(R.id.select_date_for)
        btn_save = view.findViewById(R.id.btn_Save)
        val shared  = requireActivity().getSharedPreferences("notification_alarm" , Context.MODE_PRIVATE)
        super.onViewCreated(view, savedInstanceState)
        calendar = Calendar.getInstance()
        val data =  DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            calendar.set(Calendar.YEAR , year)
            calendar.set(Calendar.MONTH , month)
            calendar.set(Calendar.DAY_OF_MONTH , dayOfMonth)
            calendar.timeInMillis
          //  updateLabelDate(calendar)
        }
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = view.findViewById<RadioButton>(checkedId)
            val selectedText = radioButton.text.toString()
            if (selectedText == "Notification"){
                shared.edit().apply(){
                    putString("notification" , selectedText)
                    apply()
                }
            }
            else if (selectedText == "Alarm"){
                shared.edit().apply(){
                    putString("notification" , selectedText)
                    apply()
                }
            }
        }
        val calendar_to = Calendar.getInstance()
        val data_to=  DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            calendar_to.set(Calendar.YEAR , year)
            calendar_to.set(Calendar.MONTH , month)
            calendar_to.set(Calendar.DAY_OF_MONTH , dayOfMonth)
            calendar_to.timeInMillis
          //  updateLabelDateTo(calendar_to)

        }


        val toTime =  Calendar.getInstance()
        val fromTime = Calendar.getInstance()
        val timepickerto = TimePickerDialog.OnTimeSetListener { timePicker, hourOfDay, minute ->
            toTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
            toTime.set(Calendar.MINUTE, minute)
            toTime.timeInMillis
          //  toTime.timeZone = TimeZone.getDefault()
          //  updateTimeLabel(toTime)

        }
            val timepickerfrom = TimePickerDialog.OnTimeSetListener { timePickerfrom, hourOfDay, minute ->
                fromTime.set(Calendar.HOUR_OF_DAY , hourOfDay)
                fromTime.set(Calendar.MINUTE , minute)
                fromTime.timeInMillis
                //fromTime.timeZone = TimeZone.getDefault()
               // updateTimeLabelto(fromTime)
            }


        selectDate.setOnClickListener {
            DatePickerDialog(requireContext() ,
            data ,
            calendar.get(Calendar.YEAR) , calendar.get(Calendar.MONTH) , calendar.get(Calendar.DAY_OF_MONTH))
                .show()
        }
        selectTime.setOnClickListener {
            TimePickerDialog(requireContext() , timepickerto , toTime.get(Calendar.HOUR_OF_DAY) , toTime.get(Calendar.MINUTE)
            , false).show()

        }
        btn_time_for.setOnClickListener {
            TimePickerDialog(requireContext() , timepickerfrom , fromTime.get(Calendar.HOUR_OF_DAY) , fromTime.get(Calendar.MINUTE)
                , false).show()
        }
        btn_date_for.setOnClickListener {
            DatePickerDialog(requireContext() ,
                data_to ,
                calendar_to.get(Calendar.YEAR) , calendar_to.get(Calendar.MONTH) , calendar_to.get(Calendar.DAY_OF_MONTH))
                .show()

        }
        btn_save.setOnClickListener {
            var alertViewModelFactory = AlertViewModelFactory(Repository.getInstance(WeatherClient.getInstance() , ConcreteLocalSource.getInstance(requireContext())))
            var alertViewModel = ViewModelProvider(this , alertViewModelFactory).get(
                AlertViewModel::class.java)
            var data = TimeDate(toTime.timeInMillis.toString() , fromTime.timeInMillis.toString() , calendar.timeInMillis.toString() , calendar_to.timeInMillis.toString())
            alertViewModel.addToAlarm(TimeDate(toTime.time.toString() ,fromTime.time.toString() , calendar.time.toString() , calendar_to.time.toString()))
            var action = DatePickerDirections.actionDatePickerToAlert2()
            action.timedate =data
            Navigation.findNavController(requireView()).navigate(action)
            Log.i("TAG", "onViewCreated: " + data.timeto)
        }
    }

    private fun updateLabelDate(calendar: Calendar) {
     val day = SimpleDateFormat("dd").format(calendar.time)
        val month = SimpleDateFormat("MM").format(calendar.time)
        val year = SimpleDateFormat("yyyy").format(calendar.time)
        date.text = "${day}/${month}/${year}"
        datefrom = date.text.toString()
    }

    private fun updateLabelDateTo(calendar: Calendar) {
        val day = SimpleDateFormat("dd").format(calendar.time)
        val month = SimpleDateFormat("MM").format(calendar.time)
        val year = SimpleDateFormat("yyyy").format(calendar.time)
        date_for_tv.text = "${day}/${month}/${year}"
        dateto = date_for_tv.text.toString()
    }
}