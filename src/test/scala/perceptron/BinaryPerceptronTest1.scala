package perceptron

import breeze.linalg.DenseVector
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.scalatest.{FlatSpec, FunSuite, Matchers}

class BinaryPerceptronTest1 extends FlatSpec with Matchers {

  Logger.getLogger("org").setLevel(Level.ERROR)
  "A perceptron" should "train the data with more accuracy as 50" in {
    val sparkSession = SparkSession.builder
      .master("local")
      .appName("Perceptron")
      //      .config("spark.some.config.option", "config-value")
      .getOrCreate()

    val data_csv = sparkSession.read
      .format("com.databricks.spark.csv")
      .option("header", "false")
      .load("./src/universal/binary/sonar-data.csv")

    val features = data_csv.drop("_c60").rdd.map(row => {
      val f_DV = DenseVector(row.toSeq.toArray.map(x=> x.asInstanceOf[String].toDouble))
      val v = DenseVector.ones[Double](f_DV.length+1 )
      v(1 to f_DV.length):=f_DV
      v
    })
    val labels = data_csv.select("_c60").rdd.map(r => r(0).asInstanceOf[String].toDouble)

    val Array(train_data,test_data) = features.zip(labels).randomSplit(Array(0.7, 0.3))
    val train_features = train_data.map(x=>x._1)
    val train_label = train_data.map(x=>x._2)
    val test_features = test_data.map(x=>x._1)
    val test_labels = test_data.map(x=>x._2)

    val act_f = new PerceptronActFunction
    val model = new BinaryPerceptron(0.002, 500, act_f.sigmoid)
    model.fit(train_features, train_label)

    val X_y = test_features.zip(test_labels)
    val accuracy = X_y.map(data => {
      if (Math.abs(model.prediction(data._1) - data._2) <0.01) 1.0 else 0.0
    }).reduce((x,y) => x + y) / test_features.count() * 100

    assert(accuracy > 50)

  }

}
