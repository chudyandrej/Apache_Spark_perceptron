package perceptron

import breeze.linalg.DenseVector
import org.apache.spark.rdd.RDD

class MultiClassPerceptron(var learning_rate: Double, var n_epoch: Int, var act_function:(Double, Boolean) => Double)
  extends java.io.Serializable  {

  var perceptrons:Array[BinaryPerceptron] = new Array[BinaryPerceptron](1)
  var classes = new Array[Double](1)

  def fit(X: RDD[DenseVector[Double]], y: RDD[Double]): Unit = {
    this.classes = y.distinct().collect().array
    val classes_count = this.classes.length
    this.perceptrons = new Array[BinaryPerceptron](classes_count)

    (0 until classes_count).map(i=> {
      this.perceptrons(i) = new BinaryPerceptron(this.learning_rate, this.n_epoch, this.act_function, multiclass=true)
      // Relabeled for class i
      val label_class = y.map(x => {if (x == this.classes(i)) 1.0 else 0.0})
      this.perceptrons(i).fit(X, label_class, logging=false)
      i
    })
  }

  def prediction(features: DenseVector[Double]): Double = {
    // Multi-Class Decision Rule:
    val scores = this.perceptrons.map(x=>x.prediction(features))
    val max_index = scores.zipWithIndex.maxBy(_._1)._2
    this.classes(max_index)
  }
}
