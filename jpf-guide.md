1. Search.search()
- notify SearchStarted
- while(!done):
    + backtrack() if need
        * notify StateBacktracked
    + forward() if ok - actually already executed transition
        + notify StateAdvanced
        + notify PropertyViolated
        + notify SearchConstraintHit

- notify SearchFinished

2. VM.forward()
- ss.initializeNextTransition() - actually not execute transition
    + curCG = nextCg
    + nextCg = null
    + notify ChoiceGeneratorSet
    + advanceCurCG(vm)
        * if hasMoreChoices :
            * advance(vm, cg)
                + cg.advance() - move to next choice
                + notify ChoiceGeneratorAdvanced
        * else :
            * notify ChoiceGeneratorProcessed
- backtracker.pushKernelState()
- lastTrailInfo = path.getLast()
- ss.executeNextTransition(vm) - really execute transition
    + setExecThread(vm)
    + trail = new Transition(curCg, execThread)
    + executeTransitsion(this)

- backtracker.pushSystemState()
- updatePath()


3. ThreadInfo.executeTransition
- while (pc != null) :
    - nextPC = executeInstruction
    - if (ss.breakTransition) return
    - pc = nextPC;

4. ThreadInfo.executeInstruction
- nextPC - null
- notify executeInstruction
- nextPC = insn.execute
- increment executedInstructions
- notify instructionExecuted

5. setNextChoiceGenerator(ChoiceGenerator cg)
- if use random -> cg.randomize()
- nextCG = cg
- notify ChoiceGeneratorRegistered
