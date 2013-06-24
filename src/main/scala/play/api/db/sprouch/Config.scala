package play.api.db.sprouch

import play.api._
import sprouch.{Config => SprouchConfig}
import akka.actor.ActorSystem
import play.api.Play.current
import java.net.URL
import scala.{Some, Option}
import scala.Some

/**
 * Configuration for couchDb
 *
 */
object Config {

  private def splitUserInfo(userInfo: String): Option[(String, String)] = {
    userInfo.split(':') match {
      case Array(name, passwd) => Some((name -> passwd))
      case _ => None
    }
  }

  /**
   * Create a new configuration object.
   * This will create the database configuration from your application.conf file, with the structure
   * {{{
   *   couchdb.default="myname"
   *   couchdb.default.hostname="http://..."
   *   couchdb.default.port=5984
   *   couchdb.default.userpass="login:passwd"
   *   couchdb.default.https=false
   * }}}
   *
   * In case the application.conf file has an entry
   * {{{
   *   couchdb.default.connection
   * }}}
   * this will override all the other elements and has the format:
   * {{{
   *   couchdb.default.connection="https://login:passwd@my.host.com:5984/dbName"
   * }}}
   * @param dbName the configuration to use
   * @param app the play app
   * @param actorSystem an actorsystem
   * @return new Config object
   */
  def apply(dbName: String = "default", app: Application = play.api.Play.current, actorSystem: ActorSystem = play.api.libs.concurrent.Akka.system): SprouchConfig = {
    val conf = app.configuration
    val dbKey = s"couchdb.$dbName"
    val hostKey = s"$dbKey.hostname"
    val portKey = s"s$dbKey.port"
    val userPassKey = s"$dbKey.userPass"
    val httpsKey = s"$dbKey.https"
    val connection = s"$dbKey.connection"

    conf.getString(connection) match {
      case Some(url) =>
        val dbUrl = new URL(url)
        val userInfo = dbUrl.getUserInfo()
        val userPass = if (userInfo == null || "".equals(userInfo))
          None
        else splitUserInfo(userInfo)
        val port = if (dbUrl.getPort() == -1)
          dbUrl.getDefaultPort
        else dbUrl.getPort()

        val config = SprouchConfig(actorSystem, dbUrl.getHost(), port, userPass,
          if ("https".equals(dbUrl.getProtocol())) true else false)
        println(config)
        config

      case None =>
        val host = conf.getString(hostKey).getOrElse("localhost")
        val port = conf.getInt(portKey).getOrElse(5984)
        val userPass = conf.getString("userPassKey").getOrElse("")
        val https = conf.getBoolean(httpsKey).getOrElse(false)

        SprouchConfig(actorSystem, host, port, splitUserInfo(userPass), https)
    }
  }
}