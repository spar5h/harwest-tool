namespace Solution {
    open Microsoft.Quantum.Diagnostics;
    open Microsoft.Quantum.Arrays;
    open Microsoft.Quantum.Intrinsic;
    open Microsoft.Quantum.Canon;
    open Microsoft.Quantum.Convert;
    open Microsoft.Quantum.Math;

    operation Solve (qs : Qubit[]) : Unit {
        using (q = Qubit()) {
            repeat {
                H(qs[0]);
                H(qs[1]);
                (ControlledOnInt(0, X))(qs, q);
                let res = M(q);
                if(res == One) {
                    X(q);
                }
            }
            until (res == Zero);
        }
    }
}