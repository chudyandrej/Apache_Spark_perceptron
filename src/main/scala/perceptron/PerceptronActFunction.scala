package perceptron

import breeze.numerics.{exp, pow}

class PerceptronActFunction extends java.io.Serializable {

  val linear: (Double, Boolean) => Double = (x, multiclass) => {
    if(multiclass){
      x
    } else {
      if (x > 0) 1.0f else 0.0f
    }

  }

  val sigmoid: (Double, Boolean) => Double = (x, multiclass) => {
    if(multiclass) {
      1 / (1 + exp(-x))
    } else {
      if ((1 / (1 + exp(-x))) > 0.5) 1.0f else 0.0f
    }
  }

  val gaussian: (Double, Boolean) => Double = (x, multiclass) => {
    if(multiclass) {
      exp(-pow(-x, 2) / (2 * 2))
    }else{
      if (exp(- pow(-x,2) / (2*2)) > 0.5) 1.0f else 0.0f
    }
  }
}
