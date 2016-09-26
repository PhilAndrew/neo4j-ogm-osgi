
name := "neo4j-ogm-osgi"

version := "1.2"

scalaVersion := "2.11.8"

organization        := "universe"

// Bolt Driver

//libraryDependencies += "org.neo4j" % "neo4j" % "3.0.6"

libraryDependencies += "org.neo4j.driver" % "neo4j-java-driver" % "1.0.5"

libraryDependencies += "commons-io" % "commons-io" % "2.4"

// API

libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % "2.8.3"

libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.21"

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.1.7"

libraryDependencies += "ch.qos.logback" % "logback-core" % "1.1.7"

libraryDependencies += "commons-codec" % "commons-codec" % "1.10"

// Core

libraryDependencies += "org.apache.commons" % "commons-lang3" % "3.4"

libraryDependencies += "org.apache.commons" % "commons-collections4" % "4.1"
