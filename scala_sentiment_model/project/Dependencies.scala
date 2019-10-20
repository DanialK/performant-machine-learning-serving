import sbt._

object Dependencies {
  // https://mvnrepository.com/artifact/ml.dmlc/xgboost4j
  lazy val xgboost = "ml.dmlc" % "xgboost4j" % "0.90"

  lazy val akkaHttp = "com.typesafe.akka" %% "akka-http" % "10.1.7"
  lazy val akkaHttpStream = "com.typesafe.akka" %% "akka-stream" % "2.5.20"
  lazy val akkaHttpJson = "de.heikoseeberger" %% "akka-http-circe" % "1.25.2"

  lazy val circe = Seq(
    "io.circe" %% "circe-core",
    "io.circe" %% "circe-parser",
    "io.circe" %% "circe-generic").map(_ % "0.10.0")

  lazy val dependencies =
    Seq(akkaHttp, xgboost, akkaHttpStream, akkaHttpJson) ++ circe
}
