package com.pirum.exercises.worker

import scala.concurrent.duration.FiniteDuration
import com.pirum.exercises.worker.Master.{DisplayResult, GetResult, StartJob}
import akka.actor.{Actor, PoisonPill, Timers}
import akka.routing.Broadcast
import com.pirum.exercises.worker.Worker.Work
import com.pirum.exercises.worker.Program._

object Master {
  case object StartJob
  case object GetResult
  case object DisplayResult
}

class Master(listOfTasks: List[Task], timeout: FiniteDuration) extends Actor with Timers {

  def receive = {
    case StartJob =>
      listOfTasks foreach { task =>
        router ! Work(task, timeout)
      }
    case GetResult =>
      timers.startSingleTimer(
        java.util.UUID.randomUUID.toString,
        DisplayResult,
        timeout
      )
    case DisplayResult =>
      println(successful.reverse)
      println(failure.reverse)
      println(timedOut.reverse)
      router ! Broadcast(PoisonPill)
      router ! PoisonPill
  }
}
