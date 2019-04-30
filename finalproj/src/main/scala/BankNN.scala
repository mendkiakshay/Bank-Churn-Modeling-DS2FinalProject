import scalation.linalgebra._
import scalation.analytics.classifier._
import scalation.analytics._
import scalation.stat.StatVector._
import scalation.math._
import scala.math._
import scalation.random.Normal
import scalation.plot._
import ExampleAutoMPG._
import ActivationFun._
import Optimizer._
import scala.collection.mutable.Set
import data._
import Initializer._
import MatrixTransform._
import scala.collection.mutable.Set
import scalation.stat.Statistic
import PredictorMat.pullResponse

object BankNN extends App{


   hp("bSize") = 32
   hp("eta") = 0.1
   hp("maxEpochs") = 1000

   val prcp = NeuralNet_XL(BankTrain.xy++BankTest.xy, nz =Array(20,24), af = Array (f_lreLU, f_tanh, f_sigmoid))

   val valiX = rescaleX(BankValidate.xy,f_lreLU)
   val valiY = BankValidate.y

   prcp.train().eval()
   val yp1 = prcp.predict(valiX)

   val yp = yp1.col(0)

  // for confusion matrix
   for(i <- 0 until yp.dim) if(yp(i)< .5) yp(i) = 0 else yp(i) = 1
   val res = new VectorD(yp.dim)
   var one = 0
   var two = 0
   var three = 0                                      //Precision = tp/fp+tp
                                                      //recall = tp/fn+tp
   var four = 0
   for(i <- 0 until yp.dim){
        if(yp(i) == valiY(i) && yp(i) == 1){
            res(i) = 1      //true positive
            one += 1
        }
        else if(valiY(i) == 1 && yp(i) == 0){  //False Negative
            res(i) = 0
            two += 1
        }
        else if(valiY(i) == 0 && yp(i) == 1){ //False Positive
            res(i) = 0
            three += 1
        }
        else{
            res(i) = 1    //true negative
            four += 1
        }
   }
   println("accuracy")
   println(res.sum/res.dim)
   println(one) //true positive
   println(two) //false negative
   println(three) //false positive
   println(four) //true negative



    def rescaleX(xy: MatrixD, f1i: AFF): MatriD = {
        val f_ = f1i                                              // try different activation function
        val cy = xy.dim2 - 1
        var itran1: FunctionV_2V = null
        val xy_s =                                                  // scaled version of xy
        if (f1i.bounds != null) {                            // scale to bounds
            val extrem = extreme (xy)
            itran1 = unscaleV ((extrem._1(cy), extrem._2(cy)), f1i.bounds) _
            scale (xy, extrem, f1i.bounds)
        }else {                                             // normalize
            val (mu_xy, sig_xy) = (xy.mean, stddev (xy))
            itran1 = denormalizeV ((mu_xy(cy), sig_xy(cy))) _
            normalize (xy, (mu_xy, sig_xy))
        } // if

        val (x, y) = pullResponse (xy_s)
        setCol2One (x)
        x
    }

    def forwardSel(x: MatrixD, y: VectorD, eta: Double, bSize: Int, f1:AFF, datasetName:String){
    hp("maxEpochs") = 1000
    hp("eta") = eta
    hp("bSize") = bSize
    val trg = rescale(x :^+ y , f1)
    //trg.reset (eta_ = 0.02)
    //trg.train().eval()
    //println(trg.report)
    val n = x.dim2
    val rSq = new MatrixD(n-1,3)
    val fcols = Set(0)
    for(l <- 1 until n){
        var (x_j, b_j, fit_j) = trg.forwardSel(fcols) // add most predictive variable
        fcols += x_j
        val xcols = x.selectCols(fcols.toArray)
        var trg_j = rescale(xcols:^+y, f1)
        var result = trg_j.crossVal()
        var cv = result(0).mean
        rSq(l-1) = VectorD(fit_j(0),fit_j(7),cv)
    }

    println("FCOLS:")
    println( fcols)

    println("max r2 is:")
    println(rSq.col(0).max())
    println("max r2A is:")
    println(rSq.col(1).max())
    println("n* for adj r2: " + (rSq.col(1).argmax()+1))
    println("max cv R2 is:")
    println(rSq.col(2).max())
    println("n* for cv r2: " + (rSq.col(2).argmax()+1))
    println(rSq)
    //println(rSq.col(1))
    val t = VectorD.range(1, n)
    new PlotM(t,
        rSq.t*100,
        Array("R2","R2 Adj", "CV R2"),
        datasetName + " R square vs R bar square", true)

}

def rescale(xy: MatrixD, f1i: AFF): Perceptron ={
  val f_ = f1i                                              // try different activation function
  val cy = xy.dim2 - 1
  var itran1: FunctionV_2V = null
  val xy_s =                                                  // scaled version of xy
  if (f1i.bounds != null) {                            // scale to bounds
    val extrem = extreme (xy)
    itran1 = unscaleV ((extrem._1(cy), extrem._2(cy)), f1i.bounds) _
    scale (xy, extrem, f1i.bounds)
  }else {                                             // normalize
    val (mu_xy, sig_xy) = (xy.mean, stddev (xy))
    itran1 = denormalizeV ((mu_xy(cy), sig_xy(cy))) _
    normalize (xy, (mu_xy, sig_xy))
  } // if

  val (x, y) = pullResponse (xy_s)
  setCol2One (x)
  new Perceptron (x, y, f1 = f1i, itran=itran1)
}
}
