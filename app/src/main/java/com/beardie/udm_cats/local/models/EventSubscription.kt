package com.beardie.udm_cats.local.models

data class EventSubscription(
  val id: Long,
  val userId: Long,
  val eventId: Long
)