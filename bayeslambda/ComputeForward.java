package bayeslambda;

/** This is how most systems do computing, from a past state to a future state,
unlike the MathAxioms class which computes it all at once using bayes rule,
at least in theory converging toward that, though due to the impossibility of haltingOracles
its unlikely to converge in less than infinite time for all possible combos,
but I do expect it is infinitely precise math,
and the chance of all possibilities totals 1.0,
and unlike chaitinsConstant (haltingProbability)
is well defined in all parts (cuz thats an infinite sum that doesnt converge
as it sums (weighted averages) 2^0 things, sums 2^1 things in with that,
sums 2^2 things in with that, ... sums 2^n things in with that...
each of which is well defined but together does not converge,
though I havent looked deeply into it so am not completely sure about chaitins constant.
In this system, haltingProbability is 100% and every call pair halts in constant time
if you cache all possible <func,param,return> triples of all possible functions
and just check them (in an infinite number of threads) against the constraints,
if we use the semantic that not halting is the same as halting on the doesNotHalt symbol.
Every pair of nodes <func,param> instantly returns some node,
in the abstract math, BUT in the ComputeForward way, it does not always halt,
and you'd have to wait an infinite time to prove it "returns the doesNotHalt symbol".
The chance that <func,param> returns x, forall x, sums to 1.0,
therefore every false statement can be disproven in finite time,
such as if its claimed that <someFunc,someParam> returns xyz WITHIN t number of compute cycles,
then after t cycles if it has not returned something, that statement is false,
or if it returns something other than xyz, then that statement is false.
Or if its claimed that <someFunc,someParam> returns doesNotHalt,
then when it halts (returns anything) that statement is disproven.
But I'm not planning on (or maybe later? It would make it extremely more complex and slow)
including the math for "WITHIN t number of compute cycles",
and instead only including the relations between nodes in terms of bayes rule timelessly,
though timelessly it can still be computed by storing a BigInteger with each Node
for how many cycles it has cost so far, but I just dont want to have that many times more nodes.
This ComputeForward class is the time based way of sequentially computing the next step,
so (TODO) it probably needs callquads to do that, even though the bayes math may only need callpair math???
Upgraded it to 5 childs (up to 4 of them can be nonnull at once, and an isDone boolean) so can do callquad math.
*/
public class ComputeForward{
	private ComputeForward(){}
	
	/** some callquads have 2 possible next callquads (trydeterminismelse_and_tightenToDeterministic),
	and others have only 1 possible next callquad aka this returns the same Node regardless of whichMultiverseBranch.
	*/
	public Node next(Node callquad, boolean whichMultiverseBranch){
		
	}

}