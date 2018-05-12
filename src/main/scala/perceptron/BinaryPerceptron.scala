package perceptron
import breeze.linalg.DenseVector
import org.apache.spark.rdd.RDD

class BinaryPerceptron(var learning_rate: Double, var n_epoch: Int, var act_function:(Double, Boolean) => Double,
                       val multiclass: Boolean = false) extends java.io.Serializable  {

  var w: DenseVector[Double] = DenseVector.ones[Double](1)


  def fit(X: RDD[DenseVector[Double]], y: RDD[Double], logging:Boolean = true): Unit = {

    this.w = DenseVector.zeros[Double](X.first().length)


    val X_y = X.zip(y)
    val Array(train_data,valid_data) = X_y.randomSplit(Array(0.7, 0.3))


    for(e <- 1 to n_epoch) {

      val delta_w = train_data.map(data => {
        val pred = prediction(data._1)
        learning_rate * (data._2 - pred) * data._1
      })

      this.w += delta_w.reduce((x, y) => x + y)

      if (e % 10 == 0 || e == n_epoch){
        val accuracy = valid_data.map(data => {
          if (Math.abs(prediction(data._1) - data._2) < 0.01) 1.0 else 0.0
        }).reduce((x,y) => x + y) / valid_data.count()
        if(logging) {
          //          println(f"[Epoch $e%d] Accuracy  -----> $accuracy%2.2f")
          println(f"$accuracy")
        }


      }
    }
  }

  def prediction(features: DenseVector[Double]): Double = {
    this.act_function(features dot this.w, this.multiclass)
  }

}
