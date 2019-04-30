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
import scalation.stat.Statistic
import scalation.stat.StatVector.corr
import scalation.random.CDF.studentTCDF
import scalation.util.{banner, Error}
import scalation.util.Unicode.sub

object LogitBankTest extends App{

    val x = rescaleX(BankTrain.ox)
    val lrg = new LogisticRegression(x,BankTrain.y.toInt)
    lrg.train()
    val yp = lrg.classify(rescaleX(BankTest.ox))
    //println(yp)

    println(lrg.fitMap (BankTest.y.toInt, yp))





    // val fcols = Set(0)


  //  forwardSelT(x, BankTrain.y,"Bank")


    val res = new VectorD(yp.dim)
    var one = 0
    var two = 0
    var three = 0
    var four = 0
    for(i <- 0 until yp.dim){
            if(yp(i) == BankValidate.y(i) && yp(i) == 1){
                res(i) = 1
                one += 1
            }
            else if(BankValidate.y(i) == 1 && yp(i) == 0){
                res(i) = 0
                two += 1
            }
            else if(BankValidate.y(i) == 0 && yp(i) == 1){
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

    def forwardSel (cols: Set [Int], x: MatriD, y: VectorI): (Int, Double) = {
        val ir    =  0
        var j_max = -1                                               // index of variable to add
        var ft_max = 0.0      // optimize on quality of fit

        for (j <- x.range2 if ! (cols contains j)) {
            val cols_j = cols + j                                    // try adding variable/column x_j
            val x_cols = x.selectCols (cols_j.toArray)               // x projected onto cols_j columns
            val rg_j   = new LogisticRegression (x_cols, y.toInt)   // regress with x_j added
            rg_j.train ()
            val yp = rg_j.classify(x_cols)                                   // train model, evaluate QoF
            val ft  = rg_j.fitMap(y,yp)
            //println(ft("acc")>".5")
            val qof = ft("acc").toDouble                                         // rSqBar/rSq
            println (s"forwardSel: cols_$j = $cols_j, qof_$j = $qof")
            if (qof > ft_max) { j_max = j; ft_max = ft("acc").toDouble }
        } // for
        val k = cols.size + 1
        println (s"forwardSel: add (#$k) variable $j_max, qof = ${ft_max}")
        (j_max, ft_max)
    }


    def rescaleX(xy: MatrixD): MatriD = {
        val (mu_xy, sig_xy) = (xy.mean, stddev (xy))
        val xy_s = normalize (xy, (mu_xy, sig_xy))
        val (x, y) = pullResponse (xy_s)
        setCol2One (x)
        x
    }
    def forwardSelT(x: MatriD, y: VectorD, datasetName:String){
    val n = x.dim2
    val rSq = new MatrixD(n-1,3)
    val fcols = Set(0)
    for(l <- 1 until n){
        var (x_j, fit_j) = forwardSel(fcols,x,y.toInt) // add most predictive variable
        //if(x_j < 0) x_j = l
        fcols += x_j
        val xcols = x.selectCols(fcols.toArray)
        rSq(l-1) = VectorD(fit_j,x_j,0)
    }

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


}
