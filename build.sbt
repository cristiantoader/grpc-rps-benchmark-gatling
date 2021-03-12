enablePlugins(GatlingPlugin)

organization := "org.ctoader.grpc"
name := "grpc-rps-benchmark-gatling"
version := "0.1"

scalaVersion := "2.12.12"

val gatlingVersion = "3.4.1"

javaOptions in Gatling := overrideDefaultJavaOptions("-Xms4096m", "-Xmx4096m")

libraryDependencies ++= Seq(
  "io.gatling" % "gatling-core" % gatlingVersion,
  "io.gatling.highcharts" % "gatling-charts-highcharts" % gatlingVersion % "test,it",
  "io.gatling" % "gatling-test-framework" % gatlingVersion % "test,it",
  "com.github.phisgr" %% "gatling-grpc" % "0.10.1"

)

PB.targets in Compile := Seq(
  scalapb.gen() -> (sourceManaged in Compile).value
)

libraryDependencies ++= Seq(
  "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf",
  "com.thesamet.scalapb" %% "scalapb-runtime-grpc" % scalapb.compiler.Version.scalapbVersion,
  "io.grpc" % "grpc-netty" % scalapb.compiler.Version.grpcJavaVersion,
)
