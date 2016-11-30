package what.microservice.userinterface.model

import org.neo4j.ogm.annotation.{GraphId, NodeEntity}

//: ----------------------------------------------------------------------------------
//: Copyright © 2016 Philip Andrew https://github.com/PhilAndrew  All Rights Reserved.
//: ----------------------------------------------------------------------------------

@NodeEntity
class MicroConfig {
  @GraphId
  private var id: java.lang.Long = _
  private var nodeId: String = _

  def getNodeId = nodeId

  def this(s: String) = {
    this()
    nodeId = s
  }
}
