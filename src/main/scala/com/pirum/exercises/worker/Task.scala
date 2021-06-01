package com.pirum.exercises.worker

import scala.concurrent.duration.FiniteDuration

sealed trait Action
case object Throws extends Action
case object Completed extends Action

case class Task(action: Action, duration: FiniteDuration)
