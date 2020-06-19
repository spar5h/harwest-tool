namespace Solution {
    open Microsoft.Quantum.Diagnostics;
    open Microsoft.Quantum.Arrays;
    open Microsoft.Quantum.Intrinsic;
    open Microsoft.Quantum.Canon;
    open Microsoft.Quantum.Convert;
    open Microsoft.Quantum.Math;

   operation Solve (unitary : (Qubit => Unit is Adj+Ctl)) : Int {
        mutable x = 0;
        using(q = Qubit()) {
            unitary(q);
            Z(q);
            unitary(q);
            if(M(q) == Zero) {
                set x = 1;
            }
            else {
                X(q);
            }
        }
        return x;
    }
}