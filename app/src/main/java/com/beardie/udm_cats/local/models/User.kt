package com.beardie.udm_cats.local.models

import android.os.Parcel
import android.os.Parcelable

data class User(
  val id: Long?,
  val login: String,
  val userName: String,
  val normalizedUserName: String,
  val email: String,
  val normalizedEmail: String,
  val emailConfirmed: Boolean,
  val password: String,
  val avatar: String,
  val gender: String,
  val dateBirthday: Long,
  val about: String
) : Parcelable {
  constructor(parcel: Parcel) : this(
    parcel.readValue(Long::class.java.classLoader) as? Long,
    parcel.readString(),
    parcel.readString(),
    parcel.readString(),
    parcel.readString(),
    parcel.readString(),
    parcel.readByte() != 0.toByte(),
    parcel.readString(),
    parcel.readString(),
    parcel.readString(),
    parcel.readLong(),
    parcel.readString()
  ) {
  }

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeValue(id)
    parcel.writeString(login)
    parcel.writeString(userName)
    parcel.writeString(normalizedUserName)
    parcel.writeString(email)
    parcel.writeString(normalizedEmail)
    parcel.writeByte(if (emailConfirmed) 1 else 0)
    parcel.writeString(password)
    parcel.writeString(avatar)
    parcel.writeString(gender)
    parcel.writeLong(dateBirthday)
    parcel.writeString(about)
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Parcelable.Creator<User> {
    override fun createFromParcel(parcel: Parcel): User {
      return User(parcel)
    }

    override fun newArray(size: Int): Array<User?> {
      return arrayOfNulls(size)
    }
  }
}