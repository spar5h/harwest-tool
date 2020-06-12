namespace Solution {
    open Microsoft.Quantum.Intrinsic;

    operation Solve (unitary : (Qubit => Unit is Adj+Ctl)) : Int {
        mutable x = 0;
        using(q = Qubit()) {
            H(q);
            unitary(q);
            H(q);
            if(M(q) == One) {
                set x = 1;
            }
            if(x == 1) {
                X(q);
            }
        }
        if(x == 1) {
            return 1;
        }
        return 0;
    }
}