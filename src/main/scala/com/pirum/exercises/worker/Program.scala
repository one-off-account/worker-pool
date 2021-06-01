package com.pirum.exercises.worker

import akka.actor.Props
import akka.routing.RoundRobinPool
import com.pirum.exercises.worker.WorkerPool.system
import scala.concurrent.duration._
import scala.collection.mutable.ListBuffer
import scala.language.postfixOps

object Program {

  var successful = ListBuffer[Task]()
  var failure = ListBuffer[Task]()
  var timedOut = ListBuffer[Task]()

  val taskList = List(
    Task(Throws, 4 seconds),
    Task(Completed, 3 seconds),
    Task(Throws, 50 seconds),
    Task(Completed, 5 seconds),
    Task(Throws, 1 seconds)
  )

  val numberOfWorkers = 4
  val timeout = 8 seconds

  val master = system.actorOf(Props(new Master(taskList, timeout)), "master")
  val router = system.actorOf(RoundRobinPool(numberOfWorkers).props(Props[Worker]()), "router")
  val worker = system.actorOf(Props[Worker], "worker")
}
