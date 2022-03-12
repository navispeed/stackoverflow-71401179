name := "stackoverflow-71401179"

version := "0.1"

scalaVersion := "2.12.3"

val sparkVersion = "3.1.2"
val lombokVersion = "1.16.16"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion % "provided",
  "org.apache.spark" %% "spark-sql" % sparkVersion % "provided",
  "org.projectlombok" % "lombok" % lombokVersion
)
