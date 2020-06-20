namespace Solution {
    open Microsoft.Quantum.Diagnostics;
    open Microsoft.Quantum.Arrays;
    open Microsoft.Quantum.Arithmetic;
    open Microsoft.Quantum.Intrinsic;
    open Microsoft.Quantum.Canon;
    open Microsoft.Quantum.Convert;
    open Microsoft.Quantum.Math;
    open Microsoft.Quantum.Bitwise;

    operation Solve (unitary : (Qubit => Unit is Adj+Ctl)) : Int {
        mutable x = 0;
        using(q = Qubit[2]) {
            H(q[0]);
            Controlled unitary([q[0]], q[1]);
            Controlled unitary([q[0]], q[1]);
            H(q[0]);
            //Y
            if(M(q[0]) == Zero) {
                H(q[0]);
                Controlled unitary([q[0]], q[1]);
                S(q[0]);
                Controlled X([q[0]], q[1]);
                H(q[0]);
                //-Y
                if(M(q[0]) == Zero) {
                    set x = 2;
                }
                //Y
                else {
                    set x = 0;
                    X(q[0]);
                }
            }
            //XZ
            else {
                X(q[0]);
                H(q[0]);
                Controlled unitary([q[0]], q[1]);
                Controlled X([q[0]], q[1]);
                H(q[0]);
                //XZ
                if(M(q[0]) == Zero) {
                    set x = 3;
                }
                //-XZ
                else {
                    set x = 1;
                    X(q[0]);
                }
            }
        }
        return x;
    }
}