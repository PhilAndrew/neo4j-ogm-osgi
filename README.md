
This is Neo4j OGM working in OSGi.

I copied the Neo4j OGM code and placed it all into one project here, the Neo4j OGM code is not in one project, it is split into a few different ones such as API, Bolt driver, HTTP driver etc. By placing all the code into one project it simplifies the OSGi resolution of packages as this produces just one artifact rather than multiple.

Here it is assumed you are using [SBT Osgi Felix](https://github.com/doolse/sbt-osgi-felix) in your OSGi project to include this in a normal OSGi project. This project does not produce an OSGi bundle, it produces a local artifact which can be used by SBT Osgi Felix. 

This could easily be adapted to produce an OSGi bundle but I wish to use this code within my SBT Osgi Felix project. 

Git clone, to use it in your OSGi project. Run this in this project

    sbt publishLocal

Now it is in the local repository on this computer.

In the program which uses this, you can specify in build.sbt dependencies.

    "universe" % "neo4j-ogm-osgi_2.11" % "1.4"

Your code must call the following in your bundle startup to set the bundle context within Neo4JOGM to allow it to use the OSGi classloader to find your model classes. Must be the first thing your code does.

    org.neo4j.ogm.Neo4JOGM.setBundleContext(bundleContext);
    
Your code must export your model publically, assuming your annotated class models are in jumpmicro.impl.util.neo4j.model
    
    exportPackage := Seq("jumpmicro", "jumpmicro.impl.util.neo4j.model")
    
Your code must rewrite neo4j-ogm-osgi OSGi artifact to give it access via reflection to the model.
    
    osgiRepositoryRules := Seq(
      rewriteCustom("neo4j-ogm-osgi_2.11", ManifestInstructions(extraProperties = Map("DynamicImport-Package" -> "*")))
    )    