
Run

sbt publishLocal

Now it is in the local repository on this computer.

In the program which uses this, you can specify in build.sbt

    "universe" % "neo4j-ogm-osgi_2.11" % "1.2"

