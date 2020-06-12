namespace Solution {
    open Microsoft.Quantum.Diagnostics;
    open Microsoft.Quantum.Arrays;
    open Microsoft.Quantum.Intrinsic;
    open Microsoft.Quantum.Canon;
    open Microsoft.Quantum.Convert;
    open Microsoft.Quantum.Math;

    operation Solve (unitary : (Qubit => Unit is Adj+Ctl)) : Int {
        mutable x = 1;
        using(q = Qubit[2]) {
            H(q[0]);
            Controlled X([q[0]], q[1]);
            Controlled unitary([q[0]], q[1]);
            Controlled X([q[0]], q[1]);
            H(q[0]);
            if(M(q[0]) == One) {
                set x = 0;
            }
            if(x == 0) {
                X(q[0]);
            }
        }
        if(x == 1) {
            return 1;
        }
        return 0;
    }
}