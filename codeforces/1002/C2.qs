namespace Solution {
    open Microsoft.Quantum.Primitive;
    open Microsoft.Quantum.Canon;
    open Microsoft.Quantum.Convert;
    open Microsoft.Quantum.Math;
    open Microsoft.Quantum.Arrays;
 
    operation Solve (q : Qubit) : Int {
        body {
            let inv = RandomInt(2);
            if(inv == 0) {
                if(M(q) == One) {
                    return 1;
                }
                else {
                    return -1;
                }
            }
            else {
                Z(q);
                H(q);
                if(M(q) == Zero) {
                    return 0;
                }
                else {
                    return -1;
                }
            }
        }
    }
}