namespace Solution {
    open Microsoft.Quantum.Intrinsic;

    operation Solve (unitary : (Qubit[] => Unit is Adj+Ctl)) : Int {
        mutable x = 0;
        using(q = Qubit[2]) {
            X(q[1]);
            unitary(q);
            if(M(q[1]) == One) {
                set x = 1;
            }
            if(x == 1) {
                X(q[1]);
            }
        }
        if(x == 1) {
            return 1;
        }
        return 0;
    }
}