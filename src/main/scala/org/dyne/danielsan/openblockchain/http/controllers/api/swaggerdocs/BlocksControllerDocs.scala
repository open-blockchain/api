package org.dyne.danielsan.openblockchain.http.controllers.api.swaggerdocs

import org.dyne.danielsan.openblockchain.data.entity.Block
import org.scalatra.swagger.{StringResponseMessage, SwaggerSupport}

/**
  * Created by dan_mi_sun on 22/05/2016.
  */
trait BlocksControllerDocs extends SwaggerSupport {

  val applicationDescription = "OpenBlockchain API"
  val getBlocks =
    (apiOperation[List[Block]]("getBlocks")
      summary "Shows all Blocks"
      notes "Currently shows all blocks, with no possibility of filtering them.")

  val getBlock =
    (apiOperation[Block]("getBlock")
      summary "Shows a block"
      notes "Retrieves a block by its hash."
      parameters
      pathParam[String]("id").description("ID of the block that needs to be fetched").required
      responseMessage StringResponseMessage(200, "Block retrieved"))

  val getBlockTransactionCount =
    (apiOperation[Int]("getBlockTransactionCount")
      summary "Shows the number of transactions within a block"
      notes "Retrieves transaction count from block by its hash."
      parameters
      pathParam[String]("id").description("ID of the block that needs to be fetched").required
      responseMessage StringResponseMessage(200, "Block Transaction-count retrieved"))

  val getBlockOpReturnTransactionCount =
    (apiOperation[Int]("getBlockOpReturnTransactionCount")
      summary "Shows the number of OP_RETURN transactions within a block"
      notes "Retrieves OP_RETURN transaction count from block by its hash."
      parameters
      pathParam[String]("id").description("ID of the block that needs to be fetched").required
      responseMessage StringResponseMessage(200, "Block OP_RETURN-Transaction-count retrieved"))

}
