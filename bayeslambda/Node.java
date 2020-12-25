package bayeslambda;
import java.util.HashMap;
import java.util.Map;

public final class Node{

	public static final Node universalFunction = new Node(true);
	
	public static final Node doesNotHalt = new Node(false);
	
	/** convenient shorter name for universalFunction */
	public static final Node u = universalFunction;
	
	/** convenient shorter name for doesNotHalt */
	public static final Node o = doesNotHalt;
	
	public final Node func, param, stackFunc, stackParam, cacheKey;
	
	public final boolean isUniversalFunction;
	
	public final boolean isDoesNotHalt;
	
	/** is either of the 2 leafs or [a call pair thats halted] */
	public final boolean isDone;
	
	/** is TIGHTENED from false to true in (superposition x y universalFunction)
	which returns (x universalFunction) and requires all calls of superposition inside that recursively
	return from their first of 3 params (like (x universalFunction)) instead of second (like (y universalFunction)),
	if it halts at all (else returns doesNotHalt, in abstract math, but that may cost an infinite number of compute cycles
	and memory to know if it "returns doesNotHalt" or not).
	*/
	public final boolean isForceDeterminismRecursively;
	
	private final int hash;
	
	private Node(boolean isUniversalFunction){
		func = param = stackFunc = stackParam = cacheKey = null;
		this.isDone = true;
		
		//FIXME is a isForceDeterminismRecursively and !isForceDeterminismRecursively version of each of the 2 leafs
		//(so 4 leafs instead of 2? I hope not, but it seems likely) needed to make the math consistent?
		this.isForceDeterminismRecursively = false;

		this.isUniversalFunction = isUniversalFunction;
		this.isDoesNotHalt = !isUniversalFunction;
		hash = isUniversalFunction ? 187661 : 177409; //or 4 of these, if that "fixme ... isForceDeterminismRecursively"
	}
	
	private Node(boolean isDone, boolean isForceDeterminismRecursively, Node func, Node param, Node stackFunc, Node stackParam, Node cacheKey){
		this.isDone = isDone;
		this.isForceDeterminismRecursively = isForceDeterminismRecursively;
		this.func = func;
		this.param = param;
		this.stackFunc = stackFunc;
		this.stackParam = stackParam;
		this.cacheKey = cacheKey;
		this.isDoesNotHalt = this.isUniversalFunction = false;
		//Its important this system does not depend on any specific language its coded in, as its a statement about math.
		//if using C++ etc: replace System.identityHashCode(x) with &x in C++ for similar behavior.
		hash = (isDone?324556345:553284)
			+(isForceDeterminismRecursively?884356732:35264743)
			+hashPart(func,49999)
			+hashPart(param,192307)
			+hashPart(stackFunc,119298)
			+hashPart(stackParam,130087)
			+hashPart(cacheKey,165212);
	}
	
	/** func/L. universalFunction is (identityFunction universalFunction). doesNotHalt is (doesNotHalt doesNotHalt). */
	public Node func(){
		return func!=null ? func : (isUniversalFunction ? identityFunction() : doesNotHalt);
	}
	
	/** param/R. universalFunction is (identityFunction universalFunction). doesNotHalt is (identityFunction doesNotHalt). */
	public Node param() {
		return param!=null ? param : this;
	}
	
	protected static int hashPart(Object o, int x){
		return o==null ? x : (System.identityHashCode(o)*x);
	}
	
	public boolean isCallPair(){
		return !isUniversalFunction & !isDoesNotHalt;
	}
	
	public int hashCode(){ return hash; }
	
	public boolean equals(Object o){
		if(o == this) return true; //optimization
		if(!(o instanceof Node)) return false;
		Node n = (Node)o;
		return hash==n.hashCode() //optimization
			&& isDone==n.isDone
			&& isForceDeterminismRecursively==n.isForceDeterminismRecursively
			&& func==n.func
			&& param==n.param
			&& stackFunc==n.stackFunc
			&& stackParam==n.stackParam
			&& cacheKey==n.cacheKey
			&& isUniversalFunction==n.isUniversalFunction
			&& isDoesNotHalt==n.isDoesNotHalt; 
	}
	
	private static final Map<Node,Node> dedup = new HashMap();
	
	/** deduped forest node */
	private static final Node node(boolean isHalted, boolean isForceDeterminismRecursively,
			Node func, Node param, Node stackFunc, Node stackParam, Node cacheKey){
		Node n = new Node(isHalted, isForceDeterminismRecursively, func, param, stackFunc, stackParam, cacheKey);
		Node d = dedup.get(n);
		if(d == null){
			d = n;
			dedup.put(d, d);
		}
		return d;
	}
	
	/** deduped call pair of this and param, which has not halted yet and is !isForceDeterminismRecursively */
	public Node cp(Node param){
		return node(false,false,this,param,null,null,null);
	}
	
	/** halted call pair thats !isForceDeterminismRecursively */
	public Node hcp(Node param){
		return node(true,false,this,param,null,null,null);
	}
	
	public static final Node uu = u.hcp(u);
	
	/** returns the first 4 curries, where first 3 are u or uu, and the next is default comment which is u,
	leaving 3 more curries before it evals.
	*/
	public static final Node op(int zeroToSeven){
		return u.hcp((zeroToSeven&4)==0 ? u : uu)
			.hcp((zeroToSeven&2)==0 ? u : uu)
			.hcp((zeroToSeven&1)==0 ? u : uu)
			.hcp(u); //default comment
	}
	
	/** λx.λy.λz.xz(yz) */
	public static final Node S = op(0);
	
	/** λx.λy.x */
	public static final Node T = op(1).hcp(u);
	
	/** λx.λy.y */
	public static final Node F = op(2).hcp(u);
	
	/** λx.(pair (VM_L x) (VM_R x)) */
	public static final Node pairOfLROf = op(3).hcp(u).hcp(u);
	
	/** λx.{VM_isLeaf x, returns T or F} */
	public static final Node isLeaf = op(4).hcp(u).hcp(u);
	
	/** λx.λy.λz.zxy */
	public static final Node pair = op(5);
	
	/** (comment Curry) = λcomment.λignore.λx.λy.x(pair (...λcomment.λignore.λx...) y) //TODO write this more precisely
	<br><br>
	//Curry = λx.λy.x(pair x y), where x is often designed to call (Curry x) in some cases and (L x) and (R x) etc. //FIXME dont want to lose comment param... so todo use the full call (leaf a b c comment x) aka (a_kind_of_curry x) instead of x in that pair
	<br><br>
	//Curry = λx.λy.x(pair (Curry x) y) //I might use λx.λy.x(pair x y) instead?
	*/
	public static final Node curry = op(6).hcp(u);
	
	/** callquad has 2 possible next callquads here, which keeps doubling
	(except where multiple possible paths lead to the same thing, but on average it doubles) 2 4 8 16 32...
	This is where the bayes math is most interesting.
	<br><br>
	trydeterminismelse_and_tightenToDeterministic =
	Lx.Ly.Lz.{nondeterministicly: (x leaf) or (y leaf), or tightenToDeterminismRecursively if (isLeaf z),
	and counts as deterministic whenever it returns (x leaf) even if nondeterminism would be allowed that
	nondeterminism was not used such as if it does not run out of memory or compute cycles etc then it is
	repeatable by anyone on the internet you give 2 lambdas to call one on the other which led to this,
	and especially this can be defined as the 2 possible branches in fntape (see occamsfuncer readme) to
	literally explore the gametree/gameweb of all possible functions so can nondeterministicly return any
	function at all from that since its known that is within the gametree/gameweb, or it can be called with
	other params than how to build a fntape}
	*/
	public static final Node superposition = op(7);
	
	
	
	/** λy.y, aka identityFunction */
	public static final Node I = F.hcp(u);
	
	/** a semantic, like in occamsfuncer. Since F only uses the last 2 of 3 params,
	the semantic that if the first of 3 params is uu then it means typeval,
	and of course it still computes the F lambda regardless of that param but can be viewed by func/L and param/R.
	*/
	public static final Node typeval = F.func().hcp(uu);
	
	public static final Node identityFunction(){
		throw new RuntimeException("TODO");
	}
	
	public static void main(String[] args){
		//FIXME since I upgraded it to callquad math (instead of its subset which is callpair math),
		//these tests are incomplete.
		
		Node leafLeaf = universalFunction.cp(universalFunction);
		Node leafLeaf_leaf = leafLeaf.cp(universalFunction);
		Node leaf_leafLeaf = universalFunction.cp(leafLeaf);
		Node leafLeaf_leafLeaf = leafLeaf.cp(leafLeaf);
		Node leaf_leafLeaf_again = universalFunction.cp(universalFunction.cp(universalFunction));
		if(leaf_leafLeaf != leaf_leafLeaf_again) throw new Error("Didnt dedup");
		Node leafLeaf_leafLeaf_again = leafLeaf.cp(leafLeaf);
		if(leafLeaf_leafLeaf != leafLeaf_leafLeaf_again) throw new Error("Didnt dedup");
		if(leaf_leafLeaf == leafLeaf_leaf) throw new Error("Shouldnt equal");
		if(universalFunction == leafLeaf) throw new Error("Shouldnt equal");
		
		Node leafNonhalt = universalFunction.cp(doesNotHalt);
		Node leafNonhalt_leaf = leafNonhalt.cp(universalFunction);
		Node leafNonhalt_again = universalFunction.cp(doesNotHalt);
		Node leafNonhalt_leaf_again = leafNonhalt_again.cp(universalFunction);
		if(leafNonhalt_leaf != leafNonhalt_leaf_again) throw new Error("Didnt dedup");
		
		if(universalFunction == doesNotHalt) throw new Error("The 2 leafs must not equal");
		
		System.out.println("Tests passed");
	}

}