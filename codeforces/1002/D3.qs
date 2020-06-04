namespace Solution {
    open Microsoft.Quantum.Primitive;
    open Microsoft.Quantum.Canon;
    open Microsoft.Quantum.Convert;
    open Microsoft.Quantum.Math;
    open Microsoft.Quantum.Arrays;
 
    operation Solve (x : Qubit[], y : Qubit) : () {
        body {
            (ControlledOnInt(3, X))(x, y);
            (ControlledOnInt(5, X))(x, y);
            (ControlledOnInt(6, X))(x, y);
            (ControlledOnInt(7, X))(x, y);
        }
    }
}