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
        using(q = Qubit[3]) {
            H(q[0]);
            H(q[1]);

            (ControlledOnInt(2, Z))([q[2], q[1]], q[0]);
            
            //I -> 0, X -> 2, Y -> 3, Z -> 1
            (ControlledOnInt(0, unitary))([q[2]], q[0]);
           
            (ControlledOnInt(2, Z))([q[2], q[1]], q[0]);

            (ControlledOnInt(0, H))([q[2]], q[0]);

            (ControlledOnInt(0, H))([q[2]], q[1]);

            let r0 = M(q[0]);
            let r1 = M(q[1]);

            if(r1 == Zero) {
                if(r0 == Zero) {
                    set x = 0;
                }
                else {
                    X(q[0]);
                    set x = 3;
                }
            }
            else {
                X(q[1]);
                if(r0 == Zero) {
                    set x = 1;
                }
                else {
                    X(q[0]);
                    set x = 2;
                }
            }

        }
        return x;
    }
}