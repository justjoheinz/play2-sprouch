scalaVersion := "2.10.2"

name := "play2-sprouch"

version := "0.1.1"

organization := "justjoheinz"

scalaVersion := "2.10.2"

resolvers += Classpaths.sbtPluginReleases

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += "sprouch repo" at
      "http://kimstebel.github.com/sprouch/repository"

resolvers += "spray repo" at "http://repo.spray.io/"

scalacOptions += "-feature"

libraryDependencies ++= {
  val playVersion = "2.1.1"
  Seq(
  "play" %% "play" % playVersion,
  "play" %% "play-test" % playVersion % "test",
  ("sprouch" %% "sprouch" % "0.5.11").exclude("com.typesafe.akka" , "akka-actor"))
}

publishMavenStyle := true

publishTo <<= version { (v: String) =>
  val snapshots = Some(Resolver.file("Local github (snapshots)",  new File(Path.userHome.absolutePath+"/justjoheinz.github.io/repository/snapshots")))
  val releases =  Some(Resolver.file("Local github (releases)", new File(Path.userHome.absolutePath+"/justjoheinz.github.io/repository/releases")))
  if (v.trim.endsWith("SNAPSHOT"))
    snapshots
  else
    releases
}