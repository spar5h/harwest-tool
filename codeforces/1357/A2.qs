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
            X(q[0]);
            X(q[1]);
            unitary(q);
            let a = M(q[0]);
            let b = M(q[1]);
            if(a == Zero) {
                set x = 2;
                X(q[1]);
            }
            elif(b == Zero) {
                set x = 1;
                X(q[0]);
            }
            else {
                X(q[1]);
                unitary(q);
                if(M(q[0]) == Zero) {
                    set x = 3;
                    X(q[1]);
                }
                else {
                    set x = 0;
                    X(q[0]);
                }
            }
        }
        return x;
    }
}