name := "slick-with-akka-http"

version := "0.1"

scalaVersion := "2.12.9"

libraryDependencies +="com.typesafe.akka" %% "akka-actor" % "2.5.26"
libraryDependencies +="com.typesafe.akka" %% "akka-http" % "10.1.9"
libraryDependencies +="com.typesafe.akka" %% "akka-stream" % "2.5.26"
libraryDependencies +="com.typesafe.play" %% "play-json" % "2.7.4"
libraryDependencies +="de.heikoseeberger" %% "akka-http-play-json" % "1.27.0"
libraryDependencies +="com.h2database" % "h2" % "1.4.196"
libraryDependencies +="com.typesafe.slick" %% "slick" % "3.2.0"