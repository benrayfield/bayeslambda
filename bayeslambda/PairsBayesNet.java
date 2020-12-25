package bayeslambda;
import java.util.Map;

public class PairsBayesNet{
	
	/** weights between equalityVsNonequalityEdges between pairs of binary forest of lambda calls.
	Keys in this map must have Pair.bayesLevel>0 since bayesLevel==0 is a lambda call
	(which may equal other lambda calls or not, which is the subject to do bayesian statistics about).
	If 2 lambda calls equal eachother, then one can be the past (evaling) and one the future (return value), for example.
	<br><br>
	A pair represents all the bayesvars that it can reach recursively,
	such as a dense double[512] of bayes weights has some certain 32 weights inside it,
	thats 1/16 of the 512, so its a conditional probability of 4 bayesvars since 2^4==16,
	and its the sum of 32 doubles, and it can reach all 32 of those recursively
	as its first Pair.left and Pair.right branch each can reach 16 things
	(in any order, todo use lambdacomparator my other github project for norming order?)
	and each of those can reach 8 things and each of those can reach 4 things
	and each of those can reach 2 things
	and each of those can reach 1 thing which is an equalityEdge (do 2 lambda calls equal or not?)
	which can reach 2 pairs that are each a lambda call,
	and for any lambda call x, (L x (R x)) equals x, and (L x) is a lambda call,
	and (R x) is a lambda call, and (L x (R x)) is a lambda call, and x is a lambda call,
	and equality edge between (L x (R x)) vs x should converge toward 1.0, for all possible x,
	and other axioms as the timeless/allAtOnceStatistical form of what ComputeForward.java
	will compute the lambdas the direct sequential way as the Pairs/callpairs are Callquad.func and Callquad.param
	but without the other fields such as stack and cacheKey that are used in the "direct sequential way" of computing it.
	For any Pair p whose bayesLevel>0, sparseWeights.get(p)==sparseWeights.get(p.left)+sparseWeights.get(p.right) IN THEORY
	but IN PRACTICE theres roundoff and multiple such equations pulling on each weight so its not expected
	to be exactly that and instead view it as leastSquares of the difference between those 2 sides of the equation
	or something like that, find an energy function to minimize etc.
	*/
	protected Map<Pair,Double> sparseWeights;
	
	TODO Pair implements Comparable<Pair> by comparing first by height, and to break ties compare by the 2 left childs,
	and to break ties by that compare by the 2 right childs, and since equality is cached in a dedup map,
	you only ever recurse into the 2 left childs OR the 2 right childs so it has bigO of forest height
	to compare 2 lambdas (secureHash ids would be even faster of bigO, but this does not require they have ids at all
	and is a more objective truth of how to sort binary forest nodes, and guarantees no collisions).
	So sort the bayesaddresses (Pair keys in the sparseWeights map) by that, so dont have to look for
	different orders of the same set of bayesvars. It guarantees a normed form of each bayesaddress,
	as long as the equality edge puts the lowestOrEqual sorted lambda call as its left.
	Its a little similar to godel numbering except doesnt get into paradoxes of statements about statements
	except statements about lambda called on lambda returns lambda,
	including that I have a small simple lambda (fntape, see occamsfuncer readme) that loops over
	the set of all possible lambdas in approx order of increasing kolmogorov complexity
	aka in that loop the zip file or 7z file etc (compression in general) will occur before its expanded form
	unless the compressed form is slightly bigger than the expanded form (which happens when you compress randomness),
	and the first million digits of pi will occur in that loop before 1000 random digits.
	Its just an impractically slow loop if not navigated sparsely.
	
	I think I've got something backward here, that the bayesLevel might be upsidedown.
	
	

}
