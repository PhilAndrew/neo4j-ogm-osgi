package jumpmicro.test.model

import org.neo4j.ogm.Neo4JOSGI
import org.neo4j.ogm.config.Configuration
import org.neo4j.ogm.drivers.bolt.driver.BoltDriver
import org.neo4j.ogm.exception.ConnectionException
import org.neo4j.ogm.session.{Session, SessionFactory}

//: ----------------------------------------------------------------------------------
//: Copyright © 2017 Philip Andrew https://github.com/PhilAndrew  All Rights Reserved.
//: Released under the MIT License, refer to the project website for licence information.
//: ----------------------------------------------------------------------------------

object TestNeo4j// extends App
{

  println("Connect neo4j")

  val session = getNeo4jSession()

/*
  val bank = new MyBank()
  val business = new MyBusiness()
  bank.bankBusiness = business

  session.save(business)

  session.save(bank)

  session.clear()
*/



  val all = session.loadAll(classOf[MyBusiness], 2)
  val x = all.toArray.toSeq
  for (a <- x) {
    println(a)
    println(a.getClass.getName)
    val bankBusiness = a.asInstanceOf[MyBusiness].bankBusiness
    println("Trying to see if relationships are preserved....")
    println("Trying to see if relationships are preserved....")
    println("Trying to see if relationships are preserved....")
    println("Trying to see if relationships are preserved....")
    println("Trying to see if relationships are preserved....")
    println("Trying to see if relationships are preserved....")
    println(bankBusiness)
    println(bankBusiness.size().toString)
    println(bankBusiness.toArray().toSeq.head.asInstanceOf[MyBank])
  }

  println("Finished")



  def modelPackages = Seq("jumpmicro.test.model")

  private lazy val sessionFactory: SessionFactory = {

    val neo4Jip = "localhost"
    val neo4Juser = "neo4j"
    val neo4Jpassword = "sybaris"

    Neo4JOSGI.modelPackagePath = "jumpmicro.test.model"

    //val configuration = new Configuration()
    import org.neo4j.ogm.config.Configuration
    val builder = new Configuration.Builder

    builder.autoIndex("assert")
    builder.uri("bolt://" + neo4Juser + ":" + neo4Jpassword + "@" + neo4Jip + ":7687")
    builder.connectionPoolSize(150)
    builder.credentials(neo4Juser, neo4Jpassword)

    val configuration = builder.build

    //builder.setDriverClassName("org.neo4j.ogm.drivers.bolt.driver.BoltDriver").setURI("bolt://neo4j:password@localhost").setConnectionPoolSize(150)

    // Indexes http://neo4j.com/docs/ogm-manual/current/reference/#reference:indexing
    //configuration.setAutoIndex("assert")
    //configuration.set("username", neo4Juser)
    //configuration.set("password", neo4Jpassword)

    //val b = new BoltDriver()
    //configuration.set("driver", "org.neo4j.ogm.drivers.bolt.driver.BoltDriver")
    //configuration.set("URI", ) // bolt port is 7687, http port is 7474


    import collection.JavaConverters._
    val result = try {
      new SessionFactory(configuration, modelPackages: _*)
    } catch {
      case ex: ConnectionException => {
        println(ex.getStackTraceString)
        println("Failed to connect to the Neo4J database, however this MicroService will still continue to run without a connection.")
        null
      }
      case ex: Exception => {
        println("Why")
        ex.printStackTrace()
        null
      }
    }
    result
  }

  def getNeo4jSession(): Session = {
    if (sessionFactory!=null)
      sessionFactory.openSession()
    else null
  }
}
