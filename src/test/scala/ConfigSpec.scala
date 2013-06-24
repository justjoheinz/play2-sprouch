/**
 * Created with IntelliJ IDEA.
 * User: markusklink
 * Date: 22.06.13
 * Time: 10:48
 * To change this template use File | Settings | File Templates.
 */


import org.specs2.mutable._

import play.api.db.sprouch.Config
import play.api.test._
import play.api.test.Helpers._
import sprouch.Couch
import play.api.libs.concurrent.Execution._

class ConfigSpec extends Specification {

  implicit val ec = Implicits.defaultContext

  "Config" should {
    "be able to create" in new WithApplication() {

      val config = Config()
      val couch = Couch(config)

    }
    "be able to create db" in new WithApplication {
      val config = Config()
      val couch = Couch(config)
    }
    "be able to create db from connection string" in new WithApplication(new FakeApplication(additionalConfiguration =
      Map("couchdb.heroku.connection" -> "https://blah:foo@some.heroku.cloudant.com"))) {
      val config = Config("heroku")
      val couch = Couch(config)

    }

    "be able to create db from localhost connection" in new WithApplication(new FakeApplication(additionalConfiguration =
      Map("couchdb.local.connection" -> "http://mk:mk@localhost:5984"))) {
      val config = Config("local")
      val couch = Couch(config)
    }
  }

}




