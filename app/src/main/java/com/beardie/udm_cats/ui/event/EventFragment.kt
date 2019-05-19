package com.beardie.udm_cats.ui.event

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProviders
import com.beardie.udm_cats.DaggerBottomSheetDialogFragment
import com.beardie.udm_cats.R
import com.beardie.udm_cats.di.ViewModelFactory
import com.beardie.udm_cats.local.models.Event
import com.beardie.udm_cats.local.models.EventType
import com.beardie.udm_cats.view_models.EventViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.event_fragment.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class EventFragment : DaggerBottomSheetDialogFragment(), View.OnClickListener {

  val EVENT = "EventArg"
  val COORD_LONG = "CoordArgLong"
  val COORD_LAT = "CoordArgLat"


  companion object {
    fun newInstance(event: Event) = EventFragment().apply {
      arguments = bundleOf(EVENT to event)
    }

    fun newInstance(coord: LatLng) = EventFragment().apply {
      arguments = bundleOf(COORD_LONG to coord.longitude, COORD_LAT to coord.latitude)
    }
  }

  @Inject
  lateinit var viewModelFactory: ViewModelFactory

  private lateinit var viewModel: EventViewModel

  var event: Event? = null
  var coords: LatLng? = null
  private var isReadonly: Boolean = false
  private lateinit var nameEditText: TextInputEditText
  private lateinit var aboutEditText: TextInputEditText
  private lateinit var start_date: TextInputEditText
  private lateinit var end_date: TextInputEditText
  private lateinit var addButton: Button

  private var calendar = Calendar.getInstance()
  private var startDate: Date? = null
  private var finishDate: Date? = null

  var startDatePicker: DatePickerDialog.OnDateSetListener = object : DatePickerDialog.OnDateSetListener {

    override fun onDateSet(
      view: DatePicker, year: Int, monthOfYear: Int,
      dayOfMonth: Int
    ) {
      calendar.set(Calendar.YEAR, year)
      calendar.set(Calendar.MONTH, monthOfYear)
      calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
      startDate = Date(calendar.timeInMillis)
      updateLabel(start_date)
    }
  }

  var finishDatePicker: DatePickerDialog.OnDateSetListener = object : DatePickerDialog.OnDateSetListener {

    override fun onDateSet(
      view: DatePicker, year: Int, monthOfYear: Int,
      dayOfMonth: Int
    ) {
      calendar.set(Calendar.YEAR, year)
      calendar.set(Calendar.MONTH, monthOfYear)
      calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
      finishDate = Date(calendar.timeInMillis)
      updateLabel(end_date)
    }
  }

  private fun updateLabel(et: EditText) {
    val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
    et.setText(sdf.format(calendar.getTime()))
  }

  private fun validateStartDate(): Boolean {
    if (!TextUtils.isEmpty(start_date.text)) {
      til_start_date.setError(null)
    } else {
      til_start_date.setError("Date not set")
    }
    return !TextUtils.isEmpty(start_date.text)
  }

  private fun validateFinishDate(): Boolean {
    if (!TextUtils.isEmpty(end_date.text)) {
      til_end_date.setError(null)
    } else {
      til_end_date.setError("Date not set")
    }
    return !TextUtils.isEmpty(end_date.text)
  }

  private fun validateName(): Boolean {
    if (!TextUtils.isEmpty(nameEditText.text)) {
      til_event_name.setError(null)
    } else {
      til_event_name.setError("Name not set")
    }
    return !TextUtils.isEmpty(nameEditText.text)
  }

  private fun validateAbout(): Boolean {
    if (!TextUtils.isEmpty(aboutEditText.text)) {
      til_event_about.setError(null)
    } else {
      til_event_about.setError("About not set")
    }
    return !TextUtils.isEmpty(aboutEditText.text)
  }

  private fun initDateTimePicker() {
    start_date.setOnClickListener {
      DatePickerDialog(
        context,
        startDatePicker,
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
      ).show()
    }
    end_date.setOnClickListener {
      DatePickerDialog(
        context,
        finishDatePicker,
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
      ).show()
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    event = arguments?.get(EVENT) as Event?
    if (event == null) {
      isReadonly = false
      coords = LatLng(arguments?.getDouble(COORD_LAT) as Double, arguments?.getDouble(COORD_LONG) as Double)
    } else
      isReadonly = true
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val view = inflater.inflate(R.layout.event_fragment, container, false)
    nameEditText = view.findViewById(R.id.ed_event_name)
    aboutEditText = view.findViewById(R.id.ed_event_about)
    addButton = view.findViewById(R.id.btn_event_add)
    start_date = view.findViewById(R.id.ed_start_date)
    end_date = view.findViewById(R.id.ed_end_date)
    initDateTimePicker()
    return view
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    viewModel = ViewModelProviders.of(this, viewModelFactory).get(EventViewModel::class.java)
    addButton.visibility = if (isReadonly) View.GONE else View.VISIBLE
    addButton.setOnClickListener(this)
    if (event != null) {
      nameEditText.setText(event!!.name)
      nameEditText.isEnabled = false
      aboutEditText.setText(event!!.about)
      aboutEditText.isEnabled = false
      start_date.setText(Date(event!!.unixStartDate).toString())
      start_date.isEnabled = false
      end_date.setText(Date(event!!.unixEndDate).toString())
      end_date.isEnabled = false
    }
  }

  override fun onClick(v: View?) {
    if (validateName() && validateStartDate() && validateFinishDate() && validateAbout()) {
      val tmp = Event(
        12351325,
        nameEditText.text.toString(),
        aboutEditText.text.toString(),
        1,
        1,
        startDate!!.time,
        finishDate!!.time,
        coords!!.latitude,
        coords!!.longitude,
        null,
        null,
        EventType(1, "saljfalsjfl")
      )
      viewModel.addEvent(tmp)
    }
  }
}