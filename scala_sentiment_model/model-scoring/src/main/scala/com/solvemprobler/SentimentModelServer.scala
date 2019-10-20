package com.solvemprobler

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives
import akka.stream.ActorMaterializer
import ml.dmlc.xgboost4j.scala.{DMatrix, XGBoost}

trait AppService {
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = system.dispatcher

  final case class Features(data: Array[Array[Float]])
  final case class Result(result: Array[Array[Float]], duration: Double)

  import Directives._
  import de.heikoseeberger.akkahttpcirce.FailFastCirceSupport._
  import io.circe.generic.auto._

  val model = XGBoost.loadModel("data/classifier.model")

  val route = {
    path("score") {
      post {
        val start = System.currentTimeMillis()
        decodeRequest {
          entity(as[Features]) { features =>
            complete {
              val nRow = features.data.length
              val nCol = features.data.head.length
              val dMatrix = new DMatrix(features.data.flatten, nRow, nCol, Float.NaN)
              val result = model.predict(dMatrix)
              dMatrix.delete()
              val end = System.currentTimeMillis()
              val duration = end - start
              Result(result = result, duration = duration)
            }
          }
        }
      }
    }
  }

  Http().bindAndHandle(route, "0.0.0.0", 5000)

  println(s"Server online at http://localhost:5000/\n")
}

object SentimentModelServer extends App with AppService {}