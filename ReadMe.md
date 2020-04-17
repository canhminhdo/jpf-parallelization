# JPF-PARALLELIZATION
**jpf-parallelization** takes advantages of JPF to make it running in parallel with many independent instances.

Using Depth-First Search strategy to accquire all possible traces at a depth.
Each JPF instance obtains a trace and replay it. 

Once finished replaying the trace, the JPF instance starts generating all possible traces at a new depth, 
of course the new depth is deeper than the old depth