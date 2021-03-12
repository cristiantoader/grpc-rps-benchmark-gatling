package org.ctoader.grpc

import java.io.File

import com.github.phisgr.gatling.grpc.Predef._
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.grpc.examples.helloworld.helloworld.{GreeterGrpc, HelloRequest}
import io.grpc.netty.{GrpcSslContexts, NettyChannelBuilder}
import io.netty.handler.ssl.SslContext
import io.netty.handler.ssl.util.InsecureTrustManagerFactory

import scala.concurrent.duration.DurationInt

class GrpcBenchmarkSimulation extends Simulation {

  val host = "localhost"
  val port = 8080
  val trustManager = new File("")

  val sslContext: SslContext = GrpcSslContexts.forClient()
    .trustManager(InsecureTrustManagerFactory.INSTANCE)
    .build()

  val grpcSecured = grpc(NettyChannelBuilder.forAddress(host, port)
    .sslContext(sslContext))
    .disableWarmUp

  private val scenarioBuilder: ScenarioBuilder =
    scenario("performance_benchmarking")
      .repeat(4000) {
        exec(grpc("hello_world")
          .rpc(GreeterGrpc.METHOD_SAY_HELLO)
          .payload(HelloRequest.of("funny_falcon")))
      }


  setUp(
    scenarioBuilder.inject(atOnceUsers(250))
      .protocols(grpcSecured))
}
