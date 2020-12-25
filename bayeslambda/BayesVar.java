package bayeslambda;

/** immutable. a superpositionable variable that can be true/1, false/0, or anywhere between,
based on the conditionalProbability of combos of BayesVars.
TODO This will either be a Node (as callquad representing a <funcX,paramY> returns retZ)
or a pair of callpairs meaning <<funcX,paramY>,retZ>, or something like that.
See the comments in MathAxioms.java and README.md about the green, blue, and red arrows between binary forest nodes.
When I wrote that, I was thinking about each possible red arrow as a BayesVar. 
*/
public class BayesVar<T>{
	
	public T target;

}
