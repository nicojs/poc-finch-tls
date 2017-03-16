import java.security.KeyStore
import javax.net.ssl.{KeyManagerFactory, SSLContext, TrustManagerFactory}

import com.twitter.finagle.Http
import com.twitter.util.Await
import io.circe.generic.auto._
import io.finch._
import io.finch.circe._


object WebserverApp extends App {

  def getSSLContext: SSLContext = {
    // Create and initialize the SSLContext with key material
    val passphrase = "sample".toCharArray()
    val trustPassphrase = "sample".toCharArray()
    // First initialize the key and trust material
    val ksKeys = KeyStore.getInstance("JKS")
    val keystoreResource = this.getClass.getClassLoader.getResourceAsStream("sample-keystore.jks")
    ksKeys.load(keystoreResource, passphrase)
    val ksTrust = KeyStore.getInstance("JKS")
    val trustStoreResource = this.getClass.getClassLoader.getResourceAsStream("sample-keystore.jks")
    ksTrust.load(trustStoreResource, trustPassphrase)
    // KeyManagers decide which key material to us
    val kmf = KeyManagerFactory.getInstance("SunX509")
    kmf.init(ksKeys, passphrase)
    // TrustManagers decide whether to allow connections
    val tmf = TrustManagerFactory.getInstance("SunX509")
    tmf.init(ksTrust)
    val sslContext = SSLContext.getInstance("TLS")
    sslContext.init(kmf.getKeyManagers, tmf.getTrustManagers, null)
    sslContext
  }


  def sample: Endpoint[String] =
    get("sample") {
      Ok("Was it a TLS connection?... probably not")
    }

  val routes = sample

  val server = Http.server
    .withTransport.tls(getSSLContext)
    .serve(s":38082", routes.handle {
      case e: Exception =>
        InternalServerError(e)
    }.toService)

  println("Server running on :38082")
  Await.result(server)

}