package com.pirum.exercises.worker

import scala.concurrent.duration.FiniteDuration
import akka.actor.{Actor, Timers}
import com.pirum.exercises.worker.Worker.{HandleTask, Work}
import com.pirum.exercises.worker.Utils._

object Worker {
  case class Work(task: Task, timeout: FiniteDuration)
  case class HandleTask(task: Task)
}

class Worker extends Actor with Timers {

  def receive = {
    case Work(task, timeout) =>
      if(timeout > task.duration) {
        timers.startSingleTimer(
          java.util.UUID.randomUUID.toString,
          HandleTask(task),
          task.duration
        )
      } else timedOut = timedOut :+ task
    case HandleTask(task) => task.action match {
      case Throws =>
        failure = failure :+ task
      case Completed => successful = successful :+ task
    }
  }
}
