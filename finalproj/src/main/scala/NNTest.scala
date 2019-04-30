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

object NNTest extends App{
    hp("bSize") = 64
    hp("eta") = 0.22358
    hp("maxEpochs") = 1000
    var param = new MatrixD(30,4,0)
    var count = 0
    val names = Vector(f_sigmoid, f_tanh, f_lreLU)
    val nodes = Vector(12,18,24)
    var res = new VectorD(1000)
    var f0S = -1
    var f1S = -1
    /*for(nz <- nodes){
        for(f0 <- names){
            f0S += 1
            f0S = f0S % 3
            for(f1 <- names){*/
                f1S += 1
                f1S = f1S % 3
                val nn3L = NeuralNet_XL(ChurnTrain.xy ++ ChurnValidate.xy, nz = Array(16,24), af = Array(f_lreLU,f_sigmoid,f_tanh))
                val valiX = rescaleX(ChurnTest.xy,f_lreLU)
                nn3L.train().eval()
                val yp = nn3L.predict(valiX).col(0)
               // println(yp)
               res = new VectorD(yp.dim)
                for(i <- 0 until yp.dim){ 
                    if(yp(i)< .5) yp(i) = 0 
                    else yp(i) = 1
                    //res = res.zero()
                    //println(yp(i))
                    //println("vs.")
                    //println(ChurnValidate.y(i))
                    //if(yp(i) == ChurnValidate.y(i)) res(i) = 1
                    //else res(i) = 0
                }
                /*param(count) = VectorD(nz.toDouble,f0S.toDouble,f1S.toDouble,res.sum/res.dim)
                count += 1
                println(param)
            }
        }
    }*/
    //println(param)
    //println(param.col(3).max())
    //println(param.col(3).argmax())
   /*val nn3L = NeuralNet_3L(ChurnTrain.oxy,)
   //val tpr = Perceptron(ChurnValidate.oxy)
   //val prcp = NeuralNet_3L(ChurnTrain.oxy,null,10,hp,f_lreLU,f_sigmoid)
   val valiX = rescaleX(ChurnTest.oxy,f_lreLU)
   val valiY = ChurnTest.y
   prcp.train().eval()
   val yp = prcp.predict(valiX)
   for(i <- 0 until yp.dim) if(yp(i)< .5) yp(i) = 0 else yp(i) = 1
   val res = new VectorD(yp.dim)
   */var one = 0
   var two = 0
   var three = 0
   var four = 0
   for(i <- 0 until yp.dim){
        if(yp(i) == ChurnTest.y(i) && yp(i) == 1){ 
            res(i) = 1 
            one += 1
        }
        else if(ChurnTest.y(i) == 1 && yp(i) == 0){
            res(i) = 0
            two += 1
        }
        else if(ChurnTest.y(i) == 0 && yp(i) == 1){
            res(i) = 0
            three += 1
        }
        else{
            res(i) = 1
            four += 1
        }
   }
   println(res.sum/res.dim)
   println(one)
   println(two)
   println(three)
   println(four)
/*
    println("Optical")
  forwardSel(ChurnTrain.ox, ChurnTrain.y, 0.03, 64, f_lreLU, "Optical") //runtime solved
  println("_____________________________________________________")
  println("_____________________________________________________")

   //println(prcp.report)

    //println(BankFull.x(0))
*/
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


}