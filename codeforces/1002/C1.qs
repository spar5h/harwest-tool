namespace Solution {
    open Microsoft.Quantum.Primitive;
    open Microsoft.Quantum.Canon;
    open Microsoft.Quantum.Convert;
    open Microsoft.Quantum.Math;
    open Microsoft.Quantum.Arrays;
 
    operation Solve (q : Qubit) : Int {
        body {
            Ry(ArcCos(4.0 / 5.0), q);
            if(M(q) == Zero) {
                return 0;
            }
            return 1;
        }
    }
}
