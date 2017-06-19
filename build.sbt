
name := "neo4j-ogm-osgi"

version := "1.4.93"

scalaVersion := "2.11.8"

organization        := "universe"


javacOptions ++= Seq("-encoding", "UTF-8")


scalacOptions ++= Seq("-encoding", "UTF-8")

// -Dfile.encoding=UTF-8

javaOptions += "-Dfile.encoding=UTF-8"


javacOptions ++= Seq("-source", "1.8", "-target", "1.8", "-Xlint")

initialize := {
  val _ = initialize.value
  if (sys.props("java.specification.version") != "1.8")
    sys.error("Java 8 is required for this project.")
}


// -encoding UTF-8
// -Dfile.encoding=UTF8

// Why


// https://mvnrepository.com/artifact/org.apache.lucene/lucene-queries
libraryDependencies += "org.apache.lucene" % "lucene-queries" % "6.4.1"

libraryDependencies += "org.apache.lucene" % "lucene-sandbox" % "6.4.1"

//libraryDependencies += "commons-httpclient" % "commons-httpclient" % "3.1"

libraryDependencies += "org.apache.httpcomponents" % "httpclient" % "4.5.2"

//libraryDependencies += "commons-logging" % "commons-logging" % "1.2"

// ???

libraryDependencies += "org.osgi" % "osgi.core" % "6.0.0"

// Bolt Driver

//libraryDependencies += "org.neo4j" % "neo4j" % "3.0.6"

libraryDependencies += "org.neo4j.driver" % "neo4j-java-driver" % "1.4.0"

libraryDependencies += "commons-io" % "commons-io" % "2.5"

// API

libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % "2.8.7"

libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.23"

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.7"

libraryDependencies += "ch.qos.logback" % "logback-core" % "1.2.1"

libraryDependencies += "commons-codec" % "commons-codec" % "1.10"

// Core

libraryDependencies += "org.apache.commons" % "commons-lang3" % "3.5"

libraryDependencies += "org.apache.commons" % "commons-collections4" % "4.1"




bintrayRepository := "org.philandrew"

bintrayPackage := "neo4j-ogm-osgi"

bintrayOrganization := Some("philandrew") // The organization is not the same as the username I logged into bintray with

//publishMavenStyle := true // true or false, it still published

publishMavenStyle := true

licenses += ("MIT", url("http://opensource.org/licenses/MIT"))

bintrayReleaseOnPublish in ThisBuild := true // true or false, it still published

