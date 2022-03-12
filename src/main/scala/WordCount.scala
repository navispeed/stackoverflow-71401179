import org.apache.spark.sql.SparkSession
import org.apache.spark.rdd._

import org.apache.log4j.Logger
import org.apache.log4j.Level

object WordCount {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.ERROR)
    Logger.getLogger("akka").setLevel(Level.ERROR)

    val spark = SparkSession.builder
      .appName("WordCount").getOrCreate()

    val unwanted =  Set("about", "also", "after", "been", "does", "that", "from", "gets", "goes", "have", "into", "there", "they", "them", "their", "then", "this", "what", "when", "where", "which", "while", "will", "with")

    val plots = loadData(spark)
    val words = plots.flatMap(r => r.toLowerCase.split("\\W+"))
      .filter(word => word.length() > 3 && !unwanted.contains(word))
      .map(word => (word,1)).cache()
    val total = words.count()
    val counts = words.reduceByKey(_+_).mapValues(_/total).sortBy(_._2, ascending = false)
    val res = counts.take(100)
    for ((key, value) <- res){
      println("Word : " + key + " Freq : " + value)
    }
    spark.stop()
  }

  def loadData(spark: SparkSession) : RDD[String]= {
    val movieLensHomeDir = "."
    val plots = spark.read.options(Map("header" -> "true", "delimiter" -> ","))
      .csv(movieLensHomeDir + "/input.csv").rdd
      .map(r => r.getString(0))
    plots
  }
}