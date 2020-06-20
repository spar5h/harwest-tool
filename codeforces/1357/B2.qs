namespace Solution {
    open Microsoft.Quantum.Diagnostics;
    open Microsoft.Quantum.Arrays;
    open Microsoft.Quantum.Intrinsic;
    open Microsoft.Quantum.Canon;
    open Microsoft.Quantum.Convert;
    open Microsoft.Quantum.Math;
    open Microsoft.Quantum.Bitwise;
    
    operation operU (inputs : Qubit[], q : Qubit[]) :  Unit is Adj+Ctl {
        let n = Length(inputs);
        for(i in 0 .. n - 1) {
            if(i % 2 == 0) {
                (ControlledOnBitString([true, false], X))([inputs[i], q[1]], q[0]);
                (ControlledOnBitString([true, false], X))([inputs[i], q[0]], q[1]);
            }
            else {
                (ControlledOnBitString([true, false], X))([inputs[i], q[0]], q[1]);
                (ControlledOnBitString([true, false], X))([inputs[i], q[1]], q[0]);
            }
        }
    }
    
    operation Solve (inputs : Qubit[], output : Qubit) : Unit is Adj+Ctl {
        let n = Length(inputs);
        using(q = Qubit[2]) {
            ApplyWithCA(operU(inputs, _), (ControlledOnInt(0, X))(_, output), q);
        }
    }

}