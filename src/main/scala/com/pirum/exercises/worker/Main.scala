package com.pirum.exercises.worker

import akka.actor.ActorSystem
import com.pirum.exercises.worker.Master.{GetResult, StartJob}
import com.pirum.exercises.worker.Program._

object WorkerPool extends App {

  implicit val system = ActorSystem("worker-pool")

  master ! StartJob
  master ! GetResult

}
