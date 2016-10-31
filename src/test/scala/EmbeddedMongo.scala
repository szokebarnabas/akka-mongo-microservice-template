import java.util.logging.Logger

import scala.concurrent.ExecutionContext

import de.flapdoodle.embed.mongo.{Command, MongodStarter}
import de.flapdoodle.embed.mongo.config.{MongodConfigBuilder, Net, RuntimeConfigBuilder}
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.process.config.IRuntimeConfig
import de.flapdoodle.embed.process.config.io.ProcessOutput
import de.flapdoodle.embed.process.runtime.Network
import reactivemongo.api.MongoDriver

class EmbeddedMongo(port: Int = 28018) {
  implicit val executionContext = ExecutionContext.Implicits.global

  private val driver = new MongoDriver

  val nodes = List(s"localhost:$port")

  private lazy val connection = driver.connection(nodes)

  lazy val db = connection("test")

  lazy val mongodConfig = new MongodConfigBuilder()
    .version(Version.Main.V2_4)
    .net(new Net(port, Network.localhostIsIPv6))
    .build

  lazy val logger = Logger.getLogger(getClass().getName());

  lazy val runtimeConfig: IRuntimeConfig = new RuntimeConfigBuilder()
    .defaultsWithLogger(Command.MongoD, logger)
    .processOutput(ProcessOutput.getDefaultInstanceSilent)
    .build;

  lazy val runtime = MongodStarter.getInstance(runtimeConfig)
  lazy val mongodExecutable = runtime.prepare(mongodConfig)

  def start = mongodExecutable.start

  def stop = mongodExecutable.stop
}