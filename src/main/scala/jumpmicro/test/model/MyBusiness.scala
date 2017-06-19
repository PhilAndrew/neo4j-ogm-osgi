package jumpmicro.test.model

import org.neo4j.ogm.annotation.Relationship
import org.neo4j.ogm.annotation.{GraphId, NodeEntity, Relationship}
import java.lang.{Long => JLong}
import java.util.{Set => JSet}
import java.util.{HashSet => JHashSet}
import java.lang.{String => JString}
import java.time.{Instant, LocalDateTime}
import java.lang.{Boolean => JBoolean}
import java.util.{List => JList}
import java.util.{LinkedList => JLinkedList}

import org.neo4j.ogm.annotation.typeconversion.DateLong

import scala.beans.BeanProperty
import scala.collection.JavaConverters._

//: -------------------------------------------------------------------------------------
//: Copyright © 2017 Philip Andrew https://github.com/PhilAndrew  All Rights Reserved.
//: Released under the MIT License, refer to the project website for licence information.
//: -------------------------------------------------------------------------------------


@NodeEntity
class MyBusiness {
  @GraphId
  @BeanProperty
  var id: JLong = _

  @BeanProperty
  var name: JString = ""

  @Relationship(`type` = "BANK_BUSINESS", direction = "INCOMING")
  @BeanProperty
  var bankBusiness: JSet[MyBank] = _

  @Relationship(`type` = "BANKBUSINESS", direction ="OUTGOING")
  @BeanProperty
  var child: JSet[BankBusiness] = _

  @Relationship(`type` = "BANKBUSINESS", direction = "INCOMING")
  @BeanProperty
  var parent: JSet[BankBusiness] = _
}
