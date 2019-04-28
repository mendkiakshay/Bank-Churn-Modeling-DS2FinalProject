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

object LogitTest extends App{
   
   
   /* val x = rescaleX(ChurnTrain.ox)
    val lrg = new LogisticRegression(x,ChurnTrain.y.toInt)
    //val rg = new Regression(ChurnTrain.ox, ChurnTrain.y)
    lrg.train()
    val yp = lrg.classify(rescaleX(ChurnValidate.ox))
    //println(yp)
    println(lrg.fitMap (ChurnValidate.y.toInt, yp))
    val res = new VectorD(yp.dim)
    var one = 0
    var two = 0
    var three = 0
    var four = 0
    for(i <- 0 until yp.dim){
            if(yp(i) == ChurnValidate.y(i) && yp(i) == 1){ 
                res(i) = 1 
                one += 1
            }
            else if(ChurnValidate.y(i) == 1 && yp(i) == 0){
                res(i) = 0
                two += 1
            }
            else if(ChurnValidate.y(i) == 0 && yp(i) == 1){
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
        //println(logReg.report)

    def forwardSel (cols: Set [Int]): (Int, VectoD, VectoD) = {


    }


    def rescaleX(xy: MatrixD): MatriD = {
        val (mu_xy, sig_xy) = (xy.mean, stddev (xy))
        val xy_s = normalize (xy, (mu_xy, sig_xy))
        val (x, y) = pullResponse (xy_s)
        setCol2One (x) 
        x
    }*/

}