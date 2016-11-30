package what.microservice.userinterface.impl.util.neo4j

import org.neo4j.driver.v1.{AuthTokens, GraphDatabase}
import org.neo4j.ogm.Neo4JOSGI
import org.neo4j.ogm.annotation._
import org.neo4j.ogm.config.{Configuration, DriverConfiguration}
import org.neo4j.ogm.drivers.bolt.driver.BoltDriver

import scala.beans.BeanProperty
import org.neo4j.ogm.session.Session
import org.neo4j.ogm.session.SessionFactory
import org.neo4j.ogm.transaction.Transaction

//: ----------------------------------------------------------------------------------
//: Copyright © 2016 Philip Andrew https://github.com/PhilAndrew  All Rights Reserved.
//: ----------------------------------------------------------------------------------

object Neo4JSessionFactory {
  def modelPackages = Seq("what.microservice.userinterface.model")

  private lazy val sessionFactory = {

    Neo4JOSGI.modelPackagePath = "what.microservice.userinterface.model"

    val neo4Jip = "192.168.9.131"
    val neo4Juser = "neo4j"
    val neo4Jpassword = "password"

    val configuration = new Configuration()
    configuration.set("username", neo4Juser)
    configuration.set("password", neo4Jpassword)

    val b = new BoltDriver()
    configuration.set("driver", "org.neo4j.ogm.drivers.bolt.driver.BoltDriver")
    configuration.set("URI", "bolt://" + neo4Juser + ":" + neo4Jpassword + "@" + neo4Jip + ":7687") // bolt port is 7687, http port is 7474
    import collection.JavaConverters._
    new SessionFactory(configuration, modelPackages: _*)
  }

  def getNeo4jSession(): Session = {
    sessionFactory.openSession()
  }
}

