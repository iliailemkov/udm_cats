package com.beardie.udm_cats.local.models

import android.os.Parcel
import android.os.Parcelable

data class Event(
  var id: Long,
  var name: String,
  var about: String,
  var eventTypeId: Long,
  var userId: Long,
  var unixStartDate: Long,
  var unixEndDate: Long,
  var altiitude: Double,
  var longitude: Double,
  var users: List<User>?,
  var author: User?,
  var eventType: EventType
) : Parcelable {
  constructor(parcel: Parcel) : this(
    parcel.readLong(),
    parcel.readString(),
    parcel.readString(),
    parcel.readLong(),
    parcel.readLong(),
    parcel.readLong(),
    parcel.readLong(),
    parcel.readDouble(),
    parcel.readDouble(),
    parcel.createTypedArrayList(User),
    parcel.readParcelable(User::class.java.classLoader),
    parcel.readParcelable(EventType::class.java.classLoader)
  ) {
  }

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeLong(id)
    parcel.writeString(name)
    parcel.writeString(about)
    parcel.writeLong(eventTypeId)
    parcel.writeLong(userId)
    parcel.writeLong(unixStartDate)
    parcel.writeLong(unixEndDate)
    parcel.writeDouble(altiitude)
    parcel.writeDouble(longitude)
    parcel.writeTypedList(users)
    parcel.writeParcelable(author, flags)
    parcel.writeParcelable(eventType, flags)
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Parcelable.Creator<Event> {
    override fun createFromParcel(parcel: Parcel): Event {
      return Event(parcel)
    }

    override fun newArray(size: Int): Array<Event?> {
      return arrayOfNulls(size)
    }
  }
}