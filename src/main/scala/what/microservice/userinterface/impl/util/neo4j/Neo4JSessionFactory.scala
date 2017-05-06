package what.microservice.userinterface.impl.util.neo4j

import org.neo4j.driver.v1.{AuthTokens, GraphDatabase}
import org.neo4j.ogm.Neo4JOSGI
import org.neo4j.ogm.annotation._
import org.neo4j.ogm.config.{Configuration}
import org.neo4j.ogm.drivers.bolt.driver.BoltDriver

import scala.beans.BeanProperty
import org.neo4j.ogm.session.Session
import org.neo4j.ogm.session.SessionFactory
import org.neo4j.ogm.transaction.Transaction


