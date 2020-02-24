public class TaskResult {

   private Solution solutions[][];
   private double times_ms[][];
   private int maxK;
   private Graph maxGraph;

   public TaskResult(Solution solutions[][], double times_ms[][], int maxK,
                     Graph maxGraph){
      this.solutions = solutions;
      this.times_ms = times_ms;
      this.maxK = maxK;
      this.maxGraph = maxGraph;
   }


   public Solution[][] getSolutions() {
      return solutions;
   }

   public double[][] getTimes_ms() {
      return times_ms;
   }

   public int getMaxK() {
      return maxK;
   }

   public Graph getMaxGraph() {
      return maxGraph;
   }


}
