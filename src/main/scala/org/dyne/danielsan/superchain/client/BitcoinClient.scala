package org.dyne.danielsan.superchain.client

import org.dyne.danielsan.superchain.data.models.Block
import org.json4s.jackson.JsonMethods._
import org.json4s.jackson.Serialization.write
import org.json4s.{DefaultFormats, _}

import scala.language.postfixOps
import scalaj.http.{Base64, Http}

/**
  * Created by dan_mi_sun on 10/03/2016.
  */
class BitcoinClient {

  implicit val formats = DefaultFormats

  val baseUrl = "http://127.0.0.1:8332"

  def getHashForId(id: Int): String = {
    val request = BtcRequest("getblockhash", List(id))
    val json = write(request)
    val resp = Http(baseUrl).postData(json)
      .header("content-type", "application/json")
      .header("Authorization", auth)
      .asString
      .body
    (parse(resp) \ "result").extract[String]
  }


  def getBlockForHash(hash: String): String = {
    val request = BtcRequest("getblock", List(hash))
    val json = write(request)
    Http(baseUrl).postData(json)
      .header("content-type", "application/json")
      .header("Authorization", auth)
      .asString
      .body
  }

  def getBlockForId(id: Int): Block = {
    val hash: String = getHashForId(id)
    val blockString = getBlockForHash(hash)
    val json = parse(blockString) \ "result"
    json.extract[Block]
  }

  def getTransactionIdsFromWithinBlock(id: Int): List[String] = {
    val block = getBlockForId(id)
    block.tx
  }

  //I am trying to extract the Transaction Ids to create a list of all Transactions
  //not sure yet if we need to insert these into Cassandra as we actually only need
  //them to access the transaction JSON object

  def extractTransactionIds(id: Int): String = {
    val transactionList = getTransactionIdsFromWithinBlock(id)
    transactionList.map(t => t.head)
  }

  private

  def auth = {
    "Basic " + Base64.encodeString("dave:suckme")
  }
}

case class BtcRequest(method: String, params: List[Any])

