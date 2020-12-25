package bayeslambda;

/** immutable binary forest node, representing either a call pair (which may be halted or not)
or [a question of do these 2 things equal or not, as a bayesvar].
<br><br>
(equality edge and bayesvar, OR...)
callpair like https://github.com/benrayfield/iotavm it has a worse bigO than callquad
but same bigO if you dont accumulate deep forest of nodes not yet halted.
Step function has bigO of height of forest or down to wherever its all halted,
such as the S lambda of 3 curried params x y z evals to ((x z)(y z))
which is nonhalted (x z) called on nonhalted (y z) and whatever those return
call one on the other which is a nonhalted made of 2 nonhalteds,
and x y andOr z may also be nonhalted, unless you eval them before calling S on them.
Since this is meant to be computed statistically,
it can in theory be the constant bigO instead of forestHeight bigO,
by using a bayesvar to represent the equality vs nonequality of all (unordered) pairs of callpair,
like doTheyEqual: (S I I (pair T)) vs (pair T (pair T)), yes they equal
cuz (S I I) calls its param on its param, and its param is (pair T),
and (pair T (pair T)) isDone. So the equalityVsNonequality bayesvar between those 2 things
should statistically be found to be 1.0 or converging toward 1.0,
compared to equality edge between (S I I (pair T)) vs T, should be 0.0.
This will be especially hard to figure out using the superposition op (see Callquad.superposition).
<br><br>
There will be no boolean in Callpair for is it the doesNotHalt vs universalFunc,
cuz will be 1.0 equality edge between (S I I (S I I)) and everything else that "does not halt",
since that is an infinite loop. Similarly, we dont need to know if it is the universalLambda or not
since that can in theory be known statistically by which things equal which other things or not.
*/
public final class Pair{
//public class Callpair{
	
	//public final Callpair func, param;
	public final Pair left, right;
	
	/** if false, then its a callpair. If true then its a bayesvar representing the question "do these 2 things equal?".
	An equalityEdge is the second lowest level of bayesAddress, a single var that can be true or false (or statistically between).
	The lowest level of bayesAddress has no vars and only 1 bayesWeight, and that weight is 1.0.
	*
	public final boolean isEqualityEdge;
	
	/** a bayesAddress is a mapping of all possible bayesvar to true (10), false (01), or unknown (00).
	[x=true y=false z=false] + [x=true y=true z=false] = [x=true z=false], which are 3 bayesAddresses.
	[] is the lowest bayesAddress and always has the weight 1.0, since all weights in a powerset sum to 1.
	[x=true] + [x=false] = [].
	If x==y and y==z then x==z, in equality edges, though it should only converge toward that statistically
	not instantly be true by constraint. Among other axioms.
	A bayesAddress has only 1 scalar value, not an array of values.
	There can be many equations of a+b=c and a+x=j and b+y=m etc.
	At each bayesAddress, the number of bayesvars changes by only 1 bayesvar at a time, to add one or remove one,
	immutably/statelessly as the space of bayes inference only needs to do inference on 1 thing at a time.
	*
	public final boolean isBayesAddress;
	*/
	
	/** see the comment above for isBayesAddress and isEqualityEdge, which are being merged into this int bayesLevel instead.
	bayesLevel 0 means lambda call pair.
	bayesLevel 1 means equality edge, the question of do 2 lambda call pairs equal eachother or not.
	bayesLevel 2 is conditionalProbability between 2 equality edges, like part of a double[1<<2].
	bayesLevel 3 is conditionalProbability between 3 equality edges, like part of a double[1<<3] and can represent NAND etc.
	bayesLevel N is conditionalProbability between N equality edges as the number of doubles needed increases exponentially
	but since its sparse (in theory unsure how well can converge in a large sparse network of these) it doesnt cost exponential storage
	but should still represent enough of the relevant relations between the bayesvars to compute
	what happens when you call a lambda on a lambda to find/create a lambda.
	<br><br>
	Normally bayesLevel will be maybe around 5 or 30, unsure how deep it needs to go.
	*/
	public final int bayesLevel;
	
	/*public Pair(boolean isEqualityEdge, Pair left, Pair right){
		this.isEqualityEdge = isEqualityEdge;
		this.left = left;
		this.right = right;
	}*/
	
	public Pair(int bayesLevel, Pair left, Pair right){
		this.bayesLevel = bayesLevel;
		this.left = left;
		this.right = right;
	}

}