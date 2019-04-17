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

object MainTest extends App{
    /*
    val lrg = new LogisticRegression(ChurnTrain.ox,ChurnTrain.y.toInt)
    //val rg = new Regression(ChurnTrain.ox, ChurnTrain.y)
    lrg.train()
    val yp = lrg.classify(ChurnValidate.ox)
    println(yp)
    println(lrg.fitMap (ChurnValidate.y.toInt, yp))
    //println(logReg.report)
*/
   hp("bSize") = 64
   hp("eta") = 0.11875
   hp("maxEpochs") = 1000
   val prcp = NeuralNet_3L(ChurnTrain.oxy,null,10,hp,f_lreLU,f_sigmoid)
   prcp.train2().eval()
   //val yp = prcp.predict()
   println(prcp.report)


    
}