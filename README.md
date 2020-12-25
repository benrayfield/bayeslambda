# bayeslambda
(TODO organize what is already strongly implied by my various other code) A set of math axioms which relate Bayes Rule to a certain universal function and the often-infinitely-expensive-to-compute facts of which lambda calls do and do not halt, including a doesNotHalt symbol. The universal function always curries 7 parameters and in practice in p2p networks does not "fork blockchains" but smoothly negotiates using bayes rule in terms of function called on function creates/finds what function. Functions will support unicode, voxels, and low lag musical instruments.

This is the universal function:

8 opcodes, which all curry exactly 7 params, and which of 8 is chosen by the first 3 of 7 curried params each being leaf or anything except leaf, and param_4_of_7 is comment/ignoredExceptByLAndR:

S = Lx.Ly.Lz.xz(yz)

T =  Lx.Ly.x

FI = Lx.Ly.y

pairOfLROf = Lx.(pair (VM_L x) (VM_R x))

IsLeaf = Lx.{VM_isLeaf x, returns T or F}

Pair = Lx.Ly.Lz.zxy

Curry = Lx.Ly.x(pair (Curry x) y)

trydeterminismelse_and_tightenToDeterministic = Lx.Ly.Lz.{nondeterministicly: (x leaf) or (y leaf), or tightenToDeterminismRecursively if (isLeaf z), and counts as deterministic whenever it returns (x leaf) even if nondeterminism would be allowed that nondeterminism was not used such as if it does not run out of memory or compute cycles etc then it is repeatable by anyone on the internet you give 2 lambdas to call one on the other which led to this, and especially this can be defined as the 2 possible branches in fntape (see occamsfuncer readme) to literally explore the gametree/gameweb of all possible functions so can nondeterministicly return any function at all from that since its known that is within the gametree/gameweb, or it can be called with other params than how to build a fntape}

Relevant links:

https://en.wikipedia.org/wiki/Bayes%27_theorem

https://en.wikipedia.org/wiki/SKI_combinator_calculus


---things which can be derived from it---

L = (S (T pairOfLROf) (T T))

R = (S (T pairOfLROf) (T F))

Will check specificly for those as an optimization, similar to how other things will be compiled.

Possibly may lead to the simplest and most intuitive proof that P != NP, which proofs may already exist for but is just so lost in deep abstraction that people either dont care or dont believe it... https://en.wikipedia.org/wiki/P_versus_NP_problem But there are bigger things to think about than abstract math proofs, such as the efficiency of sharing pure math/lambda functions across the internet at gaming low lag and what fun tools and serious number crunching tools etc could be built with it maybe.


---disorganized text below---

few minimalist opcodes...

instead of gas and spend, just use this...
(trydeterminismelse lazigA lazigB salt) -> either (lazigA leaf) or (lazigB leaf) or infloop.
Salt can be anything, such as might be interpreted as a request for some gas limit, request to read the gas (lazigB adds 1 to the gas, then multiply by 2, which can be abstracted away, etc).
If it returns (lazigA leaf) or infloops, it counts as deterministic, but if it returns (lazigB leaf) (even if that equals "(lazigA leaf) or infloop" which were given up on/smited) then its nondeterministic.
Also need a tightenToDeterministic op which causes (trydeterminismelse lazigA lazigB salt) to always do only (lazigA leaf).

(SolveExists x)->leaf (else infloop if not solvable) can be derived (using fn tape and 5 way gametree, like in occamsfuncer readme) therefore does not need to be an op, and neither does (Solve x) since can use (trydeterminismelse lazigA lazigB salt) to explore the possible fn tape, and abstract that away as an optimization.

Typeval can be put in Pair if use 1 of the 3 opcode bits (the case when its NOT LEAF) to put some constant thats not leaf there, BUT i'd prefer those 3 things normally be leaf or (leaf leaf), so could also put it in T or F depending on their first of 3 params which they dont even use, preferably F since it returns its third of 3 params aka the value compared to T would return the type. Yes, put typeval in F. Can derive an isTypeval fn.

S T FI L R IsLeaf Pair Curry
trydeterminismelse 
tightenToDeterministic

8 opcodes: S T FI pairOfLROf IsLeaf Pair Curry trydeterminismelse_and_tightenToDeterministic

but how does trydeterminismelse_and_tightenToDeterministic work? If salt is leaf, it tightens to always return the (lazigA leaf) call and recursively is deterministic?

So its down to a 6 param universalLambdaFuncAkaCombinator/patterncalcfunc.
Have space now to add a comment param, if want it, to make that 7, which would be the fourth of 7 params, between the 3 that are opcode chooser and the 3 params of each opcode. Put icon, comment, or whatever you want in that.

(leaf b b b comment x y z)

Add an optimization for trydeterminismelse_and_tightenToDeterministic to compute fntape and select from literally every possible function, so calling that on the saltOrWhateverItIs param can literally return anything without having to eval the fntape. And if saltOrWhateverItIs is leaf then it does tightenToDeterministic, and if its certain other patterns of things it does spend, getgas, solve, etc.

Yes, do it. The universal function is 7 params and only branches at most 1 nondetermistic bit at a time.

I'm concerned about its ability to optimize things by prefix in the 1-7 curries of occamsfuncer, but if it can do that, it can optimize like lambdasmiter too. For example, can it optimize lazig.

(curry aFuncBody (pair linCountsDown linkedListOfParams) nextParam)
-> (curry aFuncBody (pair (linCountsDown leaf) (pair nextParam linkedListOfParams)), IF linCountsDown != leaf,
ELSE -> (aFuncBody linkedListOfParams),
BUT I'm undecided if aFuncBody should be controlling that accumulation of params into the linkedlist and countdown VS if the curry opcode should do that,
given that (curry x y z) -> (x (pair (curry x y) z)) is the way for aFuncBody to do it and is most flexible such as could be a variable number of params
that depends on params that havent come in yet. Based on that, I do need to choose the most flexible way.
But that means need to more strategicly choose aFuncBody and it maybe doesnt get to be as simple as "ELSE -> (aFuncBody linkedListOfParams)"
cuz aFuncBody needs to know the difference between the curries being all done vs to curry 1 more.
Maybe just needs a bit in there, like use the second last value of linCountsDown (aka (T leaf)) to mean something and the last value (aka leaf) to mean something else,
as interpreted by aFuncBody.
..
Or very simple, like i was first thinking... If linCountsDown is leaf then eval, else accumulate param,
and aFuncBody choose which of those to do, or is designed not to do that at all as its a general way to build a turing complete currying system.

(curry aFuncBody (pair linCountsDown linkedListOfParams) nextParam)
-> (aFuncBody (pair (curry aFuncBody (pair linCountsDown linkedListOfParams)) nextParam ))
It seems overly complex.


If opcodes had 4 params instead of 3, would it look alot simpler...
(curry aFuncBody linCountsDown linkedListOfParams nextParam)
-> (aFuncBody (pair (curry aFuncBody linCountsDown linkedListOfParams) nextParam))





How about curry uses just 2 params, ignoring the first of 3 params...

(curry aFuncIncludingData nextParam) -> (aFuncIncludingData (pair aFuncIncludingData nextParam))
???
This at least helps it do recursion.
But couldnt that be derive? Of course it could.
Look into the few most common kinds of combinators...


Lx.Ly.x(pair (<Lx.Ly.a(pair a b)> x) y)
Thats all you really need for recursion in this system.
But how well it can it be optimized?

Lx.Ly.x(pair (<Lx.Ly.a(pair a b)> x) y)




(curry aFuncIncludingData nextParam)
-> (aFuncIncludingData (pair (curry aFuncIncludingData) nextParam))
Thats all you really need for recursion in this system.
But how well it can it be optimized?
...
YES, thats what I want the Curry opcode to be.

Curry = Lx.Ly.x(pair (Curry x) y)


8 opcodes, which all curry exactly 7 params, and which of 8 is chosen by the first 3 of 7 curried params each being leaf or anything except leaf, and param_4_of_7 is comment/ignoredExceptByLAndR:
S = Lx.Ly.Lz.xz(yz)
T =  Lx.Ly.x
FI = Lx.Ly.y
pairOfLROf = Lx.(pair (VM_L x) (VM_R x))
IsLeaf = Lx.{VM_isLeaf x, returns T or F}
Pair = Lx.Ly.Lz.zxy
Curry = Lx.Ly.x(pair (Curry x) y)
trydeterminismelse_and_tightenToDeterministic = Lx.Ly.Lz.{nondeterministicly: (x leaf) or (y leaf), or tightenToDeterminismRecursively if (isLeaf z), and counts as deterministic whenever it returns (x leaf) even if nondeterminism would be allowed that nondeterminism was not used such as if it does not run out of memory or compute cycles etc then it is repeatable by anyone on the internet you give 2 lambdas to call one on the other which led to this, and especially this can be defined as the 2 possible branches in fntape (see occamsfuncer readme) to literally explore the gametree/gameweb of all possible functions so can nondeterministicly return any function at all from that since its known that is within the gametree/gameweb, or it can be called with other params than how to build a fntape}

L = (S (T pairOfLROf) (T T))
R = (S (T pairOfLROf) (T F))
Will check specificly for those as an optimization, similar to how other things will be compiled.


Make a simple prototype in javascript, using base58 (or hex?) strings as function ids, and all statements are of the form: x(y)==z, or x.y==z, which is 2 ways to write the same thing
but in js they mean different things at a low level. Each function id is approx a 44 char string representing a 256 bit id thats 224 bits of hashOrLiteral and 32 bits of header.
All of these start with the 'λ' char. Also we might want a timeLastUsed on each such cache entry. All possible statements are deterministicly derivable,
other than the nondeterministic/dirty ones, so are cache. 1 of the bits will be isDirty, and 1 of the bits will be allowDirty (becomes !allowDirty if tightenToDeterminismRecursively
or something like that, but ive had problems putting that in ids for a long time and in general I tend to put allowDirty as part of stack instead of in the ids,
though it can certainly go in the ids if you're willing to rederive the ids other form (with that bit different) recursively).

TODO choose 256 chars among those that fit in 1 utf16 char and use base256, and make sure they're all valid first chars in a js java python etc var name,
so that way they're strings of 32 chars each. Find some in emojipedia. The world is ready as measured by most text editors correctly display unicode emojis etc.
Also it would be good if they are 256 consecutive unicode numbers so can just subtract that to get the byte and use Uint8ClampedArray in js and html canvas etc,
though this is not going to be binary efficient due to the very very many hashtable lookups.

the 2 binary forest childs of node x will be represented as <func param return> statements like b(c)==x, where (L x)==b and (R x)==c,
since forall x, (L x (R x)) equals x. This will allow other calls than just getting its forest shape, such as ((+ 2) 3)==5 aka <(+ 2), 3, 5>,
and (L (+ 2))==+ and (R (+ 2))==2.

Ids will contain up to 128 bits of literal data (such as a <double,double> complexnum or string) only when its a cbt,
or in some cases up to 256 bits of literal data using the idOfIdDepth optimization, and id is 256 bits.


Not so fast... forget js and what language for now...
Do callquads, and trydeterminismelse_and_tightenToDeterministic has 2 possible next callquads instead of the usual 1. Thats the gametree/gameweb.

Also redesign callquads to make <func,param,return> caching very clear, have all those be direct childs in some possible states of callquad,
instead of having to get func and param out of cacheKey (its 2 func param childs) and return being the func and param (without the other stuff) in this callquad.
..
Put 2 bits of bloomfilter in each callquad, meaning it branches to (x leaf) which is a callquad, the other meaning it branches to (y leaf) which is a callquad,
and if both bits are 0 then its unknown (accumulate ORs in the global sparse bloomfilter), and if both are 1 then it means ERROR back out fork the blockchainblockwebetc
(not a blockchain, is a web with many paths from and to everywhere).
Nondeterminism is solved by that, for example that the same call of trydeterminismelse_and_tightenToDeterministic including its third param which may contain SALT,
that same call must DETERMINISTICLY do the same thing every time its called everywhere on the internet OR do neither as UNKNOWN
as a lower trydeterminismelse_and_tightenToDeterministic on stack(s) can nondeterministicly choose not to look deeper into it and backOut/giveUp/smite,
and that itself is deterministic as soon as its imprinted onto the shared bloomfilter across the internet.
Imprinting such things is expensive of risk, but has no cryptocurrency connected to it necessarily, cuz it increases the chance others will FORK away from you.

Each callquad is a specific string that includes its own 256 bit hash and the 4 256 bit hashes of its childs and a few other bits of header etc
(such as isParentsFunc bit, see occamsfuncer callquad).

The set of all possible callquads are therefore an NP clique math problem, simply a set of excluded pairs that you can have neither, 1, or the other, but not both.
..
Maybe should allow proofOfWork on individual callquads and every computer in the network try to accumulate the most callquads that none of exclude any of eachother (a clique)???


Every callquad will be written in either hex or base256ThatFitsInUtf16AndIsPrintableChars, with _ between them, so 5 things with 4 _ between them then a last _ followed by a small header,
or something like that.

Given a small set of callquads, it implies the existence of other callquads (with perfect dedup).

A callquad will also have 2 bits that mean self exists, self not exists, unknown if self exists, or error if self both exists and not exists.

Example of a callquad (but would be 32-45 chars instead of the fake asdfskdfjsdkfjsdf example, so would be about 210 chars long as text, or ~164 bytes):
#me_asdfskdfjsdkfjsdf_L_sdkfjskdfjsdf_R_sdfkjsdfkjsdfkj_stack_kdlsfjslkdfjslkdfj_cacheKey_skdjfsakldfjskdjfsdf_header_wrsf

135 bytes fits in 1 cycle of SHA3_256. The 4 childs of a callquad fit in 128 bytes. So if the rest fits in 7 or less bytes, its twice as fast to hash as if the whole thing fits in 136 to 271 bytes. I have tested that speed and sizes. Lets go for 132 bytes, just an int header, or if can fit header into less, thats even better. But still can display it in any human readable form such as #me_asdfskdfjsdkfjsdf_L_sdkfjskdfjsdf_R_sdfkjsdfkjsdfkj_stack_kdlsfjslkdfjslkdfj_cacheKey_skdjfsakldfjskdjfsdf_header_wrsf .

FIXME callquads already have 2 states, in the old way, one which is a debugStepInto and one which is a debugStepOver, depending if a given cacheKey (the other param of VM_call, such as in occamsfuncer util classes the one with fn call(fn,fn)->fn though that may be commentedout), and those 2 paths always deterministicly lead to the same return value (same exact id, if its a deterministic call), so that would be forked again to 4 possible next states of a callquad... BUT what if its not just 4 possible states, as there can be many "other param of VM_call", but the only param that really matters is, is it the exact <func param return> cache you're looking for, vs is it something else, but that could be faked and it take a huge calculation to disprove it, or it might never halt so can never be disproven... so, can at least do this new way with the debugStepInto, but I'm uncertain how it fits with debugStepOver and thats a big problem cuz debugStepOver is needed to use the calculations done earlier or by others to avoid repeating those calculations like a turingMachine might have exponential cost vs linear cost depending if it reuses earlier <func,param,return> cache).

Generalize whole bloomfilter state to a bitstring of size 2^256 bits, in blocks of 2 bits,
and each block is 00 for UNKNOWN or 10 or 01 for it is a specific bit, and is never 11 cuz that means ERROR and if error is observed it requires smite/blockchainblockwebfork/backouttrysomethingelse/etc. This would be generally useful for a variety of systems that could share the same bloomfilter space, but it might help to have it be size 2^512 bits so could put 2 248 bit (31 byte) nodes (a pair of nodes) in it, and have a tiny amount of bloomFilter etc related data in those extra 2 bytes, so a bitstring of size 2^512 bits, stored SPARSELY and inexactly, converging, trying various combos together with other people and computers using the tool together.
???

Callquad or near anything else could be represented, as more nodes with a 2-way branching factor instead of ~4.1 way branching factor.

Ethereum seems to be using a 128 bit address space, and has to do huge calculation (4096 calls of sha512, or something like that) cuz 128 is too small. 512 would be much faster, needing only 1 or a few hash calculations each.

But more directly, 3 nodes are the fastest, as <func param return> cache, as 2^(3*256) size sparse address space.

Technically all you really need is to have 1 of 3 states (00 or 10 or 01) per pair of 31 byte ids, stored sparsely, and allowing anyone to just throw away (uncache / garbcol) any parts of the shared sparse bloom filter they dont care about at the time. I do still want it to be able to blit screens, throw simulated gaming-low-lag musical instruments across the internet at 10% lightspeed, etc. And for that you only generate ids for like 1 out of every 10000 nodes.

In the old way, I imagined the binary forest of lambda call pairs as each node being a small circle somewhere on screen (anywhere, doesnt matter), and 3 colored arrows outward from each circle to a circle. The green arrow points to its left/func child. The blue arrow points to its right/param child. The red arrow points to what the call returns, which is a circular arrow pointing at itself if its halted else points at some circle which has a red circular arrow pointing at itself.
...
In this new way, there are 4 colors of arrow, as there are 2 nondeterministic choices for some calls, and exponentially expanding there are 2^someHugeNumber number of arrows of the powerset of that number of arrows depending on which nondeterministic choice its "internal calculations higher on stack" do. This is a CONSTANT directed graph, never changes, just represents the space of all possible turing completeness, and you do things by navigating this extremely sparse space of all possibilities to a place where that thing is already done. Somewhere in it is the world around us. Somewhere in it is every software that exists on the internet currently. Somewhere in it is any finite size piece of info, and things derivable from it.
This can be modelled by logic or bayesRule etc, between the 1 bit of info at each callquad nondeterministicly branching either of 2 ways, or 4 or 8 or 16 ways... conditionally depending on what other callquads do, did, or will do (timelessly). If a node/circle is tightenToDeterministic (a bit in its id) then it has only 1 red outgoing arrow. To fully and to infinite precision complete this model of math and universal functions... There is a single node representing all possible nonhalting lambda calls, which is equal to the "return value of (S I I (S I I))" (which never halts), which the red arrow points at to mean "does not halt". In this model of computing, that often takes infinity compute cycles and memory to prove but is still certainly true or certainly false, even if we cant practically know.
...
This space of possibilities can be modelled as the conditional logic/probability/etc of the set of all red, green, and blue arrows between all pairs of binary forests, where each binary forest represents call pairs of the universal function (the one that always curries 7 params and has 8 opcodes, described above) such as for example theres a specific binary forest shape that represents (S I I (S I I)) whose 2 childs are both (S I I), and they all lead to the same leaf (the universal lambda function (combinator) which is also a pattern calculus function) and its 2 childs are I and itself, and since (I leaf)->leaf (a red arrow from (I leaf) to leaf, and a red arrow from leaf to leaf, and those are actually both the same node), it wraps around, and there is a red arrow from (L x (R x)) to x, from every halted function x to itself.

Simpler, it can be represented as the conditional probability of all <func param return> triples, such as a green arrow from x to y means <L x y> and a blue arrow from x to y means <R x y> and a red arrow from x to y means x eventually returns y (or if y is the constant that means "does not halt" then it means x does not halt, especially that <L doesNotHalt doesNotHalt> and <doesNotHalt anything doesNotHalt> and basically anything that has doesNotHalt as either its left or right child must return doesNotHalt aka must never return but as a math abstraction we say anything that does not halt halts on doesNotHalt, and there is no halted lambda which can detect if its param is doesNotHalt or not since just calling (haltingOracle doesNotHalt)->doesNotHalt cuz of course forall x, x does not equal haltingOracle and the whole concept of a haltingOracle is mathematically inconsistent at the level of any finite number of calculations even though if you have an infinite number of calculations it is certainly true or certainly false that a certain call of 2 finite size lambdas halts or does not halt. But we've got to live in the world of finite number of calculations if we are using digital computers instead of timecrystals and even then its very speculative what physics can and cant do, so lets stick to digital logic for now in this kind of math at least.

NO to... Actually, it probably needs callquads to do such conditionalProbably/conditionalLogic/etc, where the arrows are between pair of callquad, and conditionalProbability is between all combos of such arrowbetweenpairofcallquad being true or false.
...
NO, it doesnt need callquads. It just needs callpairs, cuz debugStepOver vs debugStepInto always lead to the same place, of all exponential number of paths (of step into vs step over at each point, then repeat branching over and over again until certainly arrive at the same node, that x called on y returns z regardless of the path through debugging it you take).

Imagine the green and blue arrows are constant, describing all possible binary forest of call pairs of any universal function.
Conditional probability only applies to the red arrows, which powerset-branch (like multiverse but hard logic like 3sat more than scalars, but scalars can be made of hard math of their bits... but back to the point...) You only need callquad 1 level deep, to represent calling a halted function (or the doesNotHalt symbol) on another halted (or doesNotHalt symbol) function, which is itself not necessarily halted but its red arrow(s) point only to halted functions (and point at itself if its left child called on its right child are the function called on the param that returns it, which is true of every halted function, including the doesNotHalt symbol and leaf).

Each depth-1-callquad is a single bayes node. All conditional branching of other bayesnodes is in terms of the powerset of such bayesnodes.

If we define a bayesnode as a binary forest node, whose left child is a callpair (at most 1 callquad deep) and whose right child is what it returns (another binary forest node, itself, or the doesNotHalt symbol) then conditional probability is entirely within the powerset of binary forest nodes each as a bayesnode as it represents only the nondeterminism of trydeterminismelse_and_tightenToDeterministic  which for example can select from every possible function using fntape as described in occamsfuncer readme.
