package org.dyne.danielsan.openblockchain.http.controllers.api

import org.dyne.danielsan.openblockchain.OpenBlockchainStack
import org.dyne.danielsan.openblockchain.data.database.ChainDatabase
import org.dyne.danielsan.openblockchain.http.controllers.api.swaggerdocs.BlocksControllerDocs
import org.json4s.{DefaultFormats, Formats}
import org.scalatra.FutureSupport
import org.scalatra.json.JacksonJsonSupport
import org.scalatra.swagger.Swagger

import scala.concurrent.Await
import scala.concurrent.duration._

/**
  * Created by dan_mi_sun on 20/05/2016.
  */
class BlocksController(implicit val swagger: Swagger) extends OpenBlockchainStack with FutureSupport with JacksonJsonSupport with BlocksControllerDocs {

  protected implicit lazy val jsonFormats: Formats = DefaultFormats
  val executor = scala.concurrent.ExecutionContext.global

  before() {
    contentType = formats("json")
  }

  get("/", operation(getBlocks)) {
    ChainDatabase.listAllBlocks
  }

  get("/:id", operation(getBlock)) {
    val id = params("id")
    Await.result(ChainDatabase.getBlockByHash(id), 3.seconds) match {
      case Some(block) => block
      case None => halt(404, "")
    }
  }

  get("/transaction-count/:id", operation(getBlockTransactionCount)) {
    val id = params("id")
    Await.result(ChainDatabase.getBlockTransactionCountByHash(id), 3.seconds) match {
      case Some(btc) => btc
      case None => halt(404, "")
    }
  }

  get("/op-return-transaction-count/:id", operation(getBlockOpReturnTransactionCount)) {
    val id = params("id")
    Await.result(ChainDatabase.getBlockOpReturnTransactionCountByHash(id), 3.seconds) match {
      case Some(btc) => btc
      case None => halt(404, "")
    }
  }

}


