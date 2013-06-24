# play2-sprouch #


play2-sprouch is a simple library to connect Play2 and the nice asynchronous library sprouch for CouchDb.
It does not do much, apart from providing a Config object, which uses settings from `application.conf`
to provide a seamless Play2 integration experience.

## Installation ##

Simply add a dependency to your `project/Build.scala` file  like:

    val appDependencies = Seq(
    ...,
    "justjoheinz" %% "play2-sprouch" % "0.1-SNAPSHOT")

and add the according resolver to the project settings:

      resolvers += "justjoheinz" at "http://justjoheinz.github.io/repository/snapshots"

## Usage ##

In order to use play and sprouch all you need to do is to provide a connection config to your application.conf file.
The easiest way to do this, is to add a line like:

     couchdb.local.connection="http://127.0.0.1:5984"

In your play application you can pick up this configuration by using:

     val config = play.api.db.sprouch.Config("local") // default is "default"
     val couch = sprouch.Couch(config)

## Warning ##

In the current snapshot release play2-sproch contains a slightly modified version of the original sprouch library.
This dependency will vanish in the release version and adding a dependency to sprouch will be required later on.

## References ##

For more information on sprouch have a look at <https://github.com/KimStebel/sprouch>


