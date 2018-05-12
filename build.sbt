name := "Apache_Spark_Perceptron"


version := "0.1"

scalaVersion := "2.11.5"

libraryDependencies += "org.scalanlp" %% "breeze" % "0.12"
libraryDependencies += "org.scalanlp" %% "breeze-viz" % "0.12"
libraryDependencies += "org.apache.spark" % "spark-core_2.11" % "2.0.1"
libraryDependencies += "org.apache.spark" % "spark-sql_2.11" % "2.0.1"
dependencyOverrides += "org.scalatest" %% "scalatest" % "3.0.1"


resolvers += "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/"

