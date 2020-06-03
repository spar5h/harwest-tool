namespace Solution {
    open Microsoft.Quantum.Primitive;
    open Microsoft.Quantum.Canon;
 
    operation Solve (qs : Qubit[]) : Int {
        body {
            Controlled Z([qs[0]], qs[1]);
            H(qs[0]);
            H(qs[1]);
            mutable val = 0;
            if(M(qs[0]) == Zero) {
                set val += 1;
            }
            if(M(qs[1]) == Zero) {
                set val += 2;
            }
            return val;
        }
    }
}