namespace Solution {
    open Microsoft.Quantum.Diagnostics;
    open Microsoft.Quantum.Arrays;
    open Microsoft.Quantum.Intrinsic;
    open Microsoft.Quantum.Canon;
    open Microsoft.Quantum.Convert;
    open Microsoft.Quantum.Math;
    open Microsoft.Quantum.Bitwise;
    
    operation Solve (theta : Double, unitary : (Qubit => Unit is Adj+Ctl)) : Int {
        mutable x = 0;
        let c = Floor(PI() / theta);
        mutable count = 0;
        using(q = Qubit()) {
            repeat {
                for(iter in 0 .. c - 1) {
                    unitary(q);
                }
                if(M(q) == One) {
                    set x = 1;
                    X(q);
                }
                set count += 1;
            }
            until (x == 1 or count == 10);
        }
        return x;
    }
}