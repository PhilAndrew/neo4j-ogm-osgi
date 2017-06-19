package jumpmicro.test.model

//: ----------------------------------------------------------------------------------
//: Copyright © 2017 Philip Andrew https://github.com/PhilAndrew  All Rights Reserved.
//: Released under the MIT License, refer to the project website for licence information.
//: ----------------------------------------------------------------------------------

import org.neo4j.ogm.annotation._
import java.lang.{Long => JLong}
import java.util.{Set => JSet}
import java.util.{HashSet => JHashSet}
import java.lang.{String => JString}
import java.time.{Instant, LocalDateTime}
import java.lang.{Boolean => JBoolean}

import scala.beans.BeanProperty
import scala.collection.JavaConverters._
import org.neo4j.ogm.annotation.typeconversion.DateLong
import java.util.{List => JList}
import java.util.{LinkedList => JLinkedList}


@RelationshipEntity(`type` = "BANKBUSINESS")
class BankBusiness {
  @GraphId
  @BeanProperty
  var id: JLong = _

  @BeanProperty
  var version: JLong = 1L

  @BeanProperty
  var name: JString = ""

  @StartNode
  @BeanProperty
  var parentBusiness: MyBusiness = null

  @EndNode
  @BeanProperty
  var childBusiness: MyBusiness = null

}
