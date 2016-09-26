
Run

    sbt publishLocal

Now it is in the local repository on this computer.

In the program which uses this, you can specify in build.sbt

    "universe" % "neo4j-ogm-osgi_2.11" % "1.4"

Your code must call the following in your bundle startup to set the bundle context within Neo4JOGM to allow it to use the OSGi classloader to find your model classes.

    org.neo4j.ogm.Neo4JOGM.setBundleContext(bundleContext);