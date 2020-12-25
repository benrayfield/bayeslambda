package bayeslambda;
import java.util.List;
import java.util.function.IntToDoubleFunction;

/** immutable. TODO copy some code from BayesianCortex (which turned out to barely work, not a smart system, just an experiment)
and that other code with the Math.exp based learning rule for bayesnodes
where the voxels slowly move outward from window center by bayesian inference (in my q18 dir somewhere?)
or the bayesnet in my codesimian software. I have various bayes code laying around.
Also, can use other kinds of statistics such as a RBM neuralnet can compute bayes rule at least approximately, in theory,
or a little close to it but maybe does not have the exact same limit of statistical behaviors.
Theres some equation relating chance to energy, where chance of a boltzmann neuralnet node
is sigmoid (1/(1+e^-x)) of a weightedSum of connected node states (which each range 0 to 1)
and the whole thing has an energy equation thats sum of the multiply of 3 things: 2 node states and weight between them,
and can represent node bias as weight between each node and a node whose state is always 1 such as it has a
very large magnitude weight between itself and itself so it would always be on.
But back to bayes math...
<br><br>
Bayesian inference is a transform of number[] that adjusts the ratio between 2 halfs of that array,
and there are log2(number[].length) ways to split it in half, each being the chance that a certain bayesvar is 1 or 0,
such as you can represent NAND as 3 bayesvars and 8 bayesweights and a specific 4 of those weights are nonzero,
and you can do inference on it by adjusting the chances of the 2 input nodes which automatically adjusts
the chance of the 1 output node, or inference from any set of nodes to any set of nodes.
The chance of each of those 3 bit vars is sum of a specific 4 numbers among those 8 numbers.
Various bayesnodes partially overlap, having some bayesvars in common and others not in common.
In this bayeslambda system, there is 1 correct set of bayesweights, with no ambiguity, and no state,
other than it will appear stateful to approximate the space of all possibilities.
The space of all possibilities is a single bayesnode of, somewhere around the 2 exponent
the number of integers] number of weights which all sum to 1 and each is a possibility
thats either allowed or not allowed
and is 3sat-like in that way but statistically is better to view it statistically.
*/
public class BayesNode implements IntToDoubleFunction{
	
	/** TODO immutable List */
	public final List<BayesVar> vars;
	
	/** size 1<<vars.size(). All of these sum to 1 */
	protected double[] weights;
	
	/** dont modify either of those after giving here, and they're both used as immutable. */
	public BayesNode(List<BayesVar> vars, double[] weights){
		this.vars = vars;
		this.weights = weights;
		if(1<<vars() != weights()) throw new RuntimeException("Sizes dont match");
		if(Math.abs(weightSum()-1) > 1e-12) throw new RuntimeException("Weights dont sum close enough to 1 (only allow roundoff)");
	}
			
	public int vars(){ return vars.size(); }
			
	public int weights(){ return weights.length; }
	
	/** must be 1.0 except roundoff */
	public double weightSum(){
		double sum = 0;
		for(double weight : weights) sum += weight;
		return sum;
	}

	public double applyAsDouble(int mapOfVarToBit){
		return weights[mapOfVarToBit];
	}

}
