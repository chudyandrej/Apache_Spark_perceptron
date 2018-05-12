package perceptron

import org.scalatest.FunSuite

class PerceptronActFunctionTest extends FunSuite {

  test("testLinear") {
    val act_f = new PerceptronActFunction
    assert(act_f.linear(0.6,false) == 1)
    assert(act_f.linear(-0.5,false) == 0)
    assert(act_f.linear(0.8,true) == 0.8)
    assert(act_f.linear(0.1,true) == 0.1)

  }

  test("testSigmoid") {
    val act_f = new PerceptronActFunction
    assert(act_f.sigmoid(1,false) == 1)
    assert(act_f.sigmoid(-1,false) == 0)
    assert(act_f.sigmoid(0,true) == 0.5)

  }

  test("testGaussian") {
    val act_f = new PerceptronActFunction
    assert(act_f.gaussian(1,false) == 1)
    assert(act_f.gaussian(5,false) == 0)
    assert(act_f.gaussian(-5,false) == 0)
    assert(act_f.gaussian(0,true) == 1)

  }

}
