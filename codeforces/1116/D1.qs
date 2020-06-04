namespace Solution {
    open Microsoft.Quantum.Primitive;
    open Microsoft.Quantum.Canon;
 
    operation Solve (qs : Qubit[]) : Unit {
        let n = Length(qs);
        mutable mask = 1;
        for(i in 0 .. n - 2) {
            set mask *= 2;
        }
        for(i in 0 .. mask - 1) {
            (ControlledOnInt(i, H))(qs[1 .. n - 1], qs[0]);
        }
    }
}