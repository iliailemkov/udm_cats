package com.beardie.udm_cats.local.models

import android.os.Parcel
import android.os.Parcelable


data class EventType(
  var id: Long,
  var name: String
) : Parcelable {
  constructor(parcel: Parcel) : this(
    parcel.readLong(),
    parcel.readString()
  ) {
  }

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeLong(id)
    parcel.writeString(name)
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Parcelable.Creator<EventType> {
    override fun createFromParcel(parcel: Parcel): EventType {
      return EventType(parcel)
    }

    override fun newArray(size: Int): Array<EventType?> {
      return arrayOfNulls(size)
    }
  }
}