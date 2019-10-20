import Dependencies._

ThisBuild / version := "0.1"

ThisBuild /scalaVersion := "2.11.12"

lazy val commonSettings = Seq(
  libraryDependencies := dependencies
)

lazy val assemblySettings = Seq(
  assemblyMergeStrategy in assembly := {
    case PathList("mozilla", _ @ _*)        => MergeStrategy.discard
    case PathList("com", "esotericsoftware", "minlog", _ @ _*) => MergeStrategy.discard
    case x => {
      val oldStrategy = (assemblyMergeStrategy in assembly).value
      oldStrategy(x)
    }
  }
)

lazy val root = (project in file("."))
  .disablePlugins(sbtassembly.AssemblyPlugin)
  .aggregate(sentimentModel)

// modules
val modelScoring = "model-scoring"

lazy val sentimentModel = (project in file(modelScoring))
  .settings(
    name := modelScoring,
    commonSettings,
    assemblySettings
  )