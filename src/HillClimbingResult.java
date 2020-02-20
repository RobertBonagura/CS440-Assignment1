public class HillClimbingResult {

   private Graph graph;
   private Solution[] solutions;
   private int solutionIndex;

   public HillClimbingResult(Graph graph, Solution[] solutions, int solutionIndex){
      this.graph = graph;
      this.solutions = solutions;
      this.solutionIndex = solutionIndex;
   }

   public Graph getGraph() {
      return this.graph;
   }

   public Solution[] getSolutions() {
      return solutions;
   }

   public int getSolutionIndex() {
      return solutionIndex;
   }
}
