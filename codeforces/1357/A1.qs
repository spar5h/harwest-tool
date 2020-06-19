namespace Solution {
    open Microsoft.Quantum.Diagnostics;
    open Microsoft.Quantum.Arrays;
    open Microsoft.Quantum.Intrinsic;
    open Microsoft.Quantum.Canon;
    open Microsoft.Quantum.Convert;
    open Microsoft.Quantum.Math;

    operation Solve (unitary : (Qubit[] => Unit is Adj+Ctl)) : Int {
        mutable x = 0;
        using(q = Qubit[2]) {
            X(q[1]);
            unitary(q);
            if(M(q[0]) == One) {
                set x = 1;
            }
            if(x == 1) {
                X(q[0]);
            }
            X(q[1]);
        }
        if(x == 1) {
            return 1;
        }
        return 0;
    }
}