
### What does it do?

This is Neo4J OGM Object to Graph Mapper working in OSGi as a library. It is not tested, but it happens to work, good luck if it works for you.

Why is this one a good idea? This one uses Java reflection to inspect your model classes to find out how to map them to the graph. The other official Neo4J OGM reads class files to find out the mapping. In an OSGi environment, accessing class files on disk is not an attractive option, its better to use Java reflection. 

### How to use in SBT

You will need to do the following to publish to your local repository, sorry!

    sbt publishLocal
    
Then the sbt dependency is

    "universe" % "neo4j-ogm-osgi_2.11" % "1.4.39"

### Copyright

All copyright of the code written by Neo4J belongs to Neo4J and you should refer to them for any copyright issue. Refer to Neo4J OGM [Neo4J OGM](https://github.com/neo4j/neo4j-ogm).

I havn't investigated the copyright side of this yet so this is my lazy disclaimer.

### Neo4j OGM OSGi

This is Neo4j OGM working in OSGi. It is used by my Scala project [Jump Micro](https://github.com/PhilAndrew/JumpMicro).

I copied the Neo4j OGM code and placed it all into one project here, the Neo4j OGM code is not in one project, it is split into a few different ones such as API, Bolt driver, HTTP driver etc. By placing all the code into one project it simplifies the OSGi resolution of packages as this produces just one artifact rather than multiple.

Here it is assumed you are using [SBT Osgi Felix](https://github.com/doolse/sbt-osgi-felix) in your OSGi project to include this in a normal OSGi project. This project does not produce an OSGi bundle, it produces a local artifact which can be used by SBT Osgi Felix. 

This could easily be adapted to produce an OSGi bundle but I wish to use this code within my SBT Osgi Felix project. Create an issue and ask me if you want to know how to make an OSGi bundle.
 
Its not tied to Scala, I just want to use SBT to build the project as it is simple to use built.sbt. You can use this from Java as well.
 
### If you want to develop locally, publishLocal

Git clone, to use it in your OSGi project. Run this in this project

    sbt publishLocal

Now it is in the local repository on this computer.

In the program which uses this, you can specify in build.sbt dependencies.

    "universe" % "neo4j-ogm-osgi_2.11" % "1.4.39"

Your code must call the following in your bundle startup to set the bundle context within Neo4JOGM to allow it to use the OSGi classloader to find your model classes. Must be the first thing your code does.

    org.neo4j.ogm.Neo4JOGM.setBundleContext(bundleContext);
    
Your code must export your model publically, assuming your annotated class models are in jumpmicro.impl.util.neo4j.model
    
    exportPackage := Seq("jumpmicro", "jumpmicro.impl.util.neo4j.model")
    
Your code must rewrite neo4j-ogm-osgi OSGi artifact to give it access via reflection to the model.
    
    osgiRepositoryRules := Seq(
      rewriteCustom("neo4j-ogm-osgi_2.11", ManifestInstructions(extraProperties = Map("DynamicImport-Package" -> "*")))
    )    