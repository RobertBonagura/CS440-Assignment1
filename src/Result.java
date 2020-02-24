public class Result {

   private Graph graph;
   private Solution solution;
   private double computationTime_ms;


   public Result(Graph graph, Solution solution, double computationTime_ms){
      this.graph = graph;
      this.solution = solution;
      this.computationTime_ms = computationTime_ms;
   }

   public Graph getGraph() {
      return this.graph;
   }

   public Solution getSolution() {
      return this.solution;
   }

   public double getComputationTime_ms() {
      return computationTime_ms;
   }
}
