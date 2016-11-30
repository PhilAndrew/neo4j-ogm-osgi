package what.microservice.userinterface.model

import org.neo4j.ogm.cypher.Filter
import org.neo4j.ogm.model.Result
import what.microservice.userinterface.impl.util.neo4j.Neo4JSessionFactory


object TestNeo4JOgm extends App {

  val session = Neo4JSessionFactory.getNeo4jSession()

  def test1() = {
    val query = new java.lang.String("MATCH (n:MicroConfig) RETURN n")
    import collection.JavaConverters._
    val r: Result = session.query(query, new java.util.HashMap[String, Object]())
    var found = false
    val it = r.queryResults().iterator()
    while (it.hasNext) {
      val next = it.next()
      found = true
    }
  }

  def test2() = {
    val nodeId = "1"
    val microConfig = session.loadAll(classOf[MicroConfig], new Filter("nodeId", nodeId))
    println(microConfig)
  }

  test2()

}
