namespace Solution {
    open Microsoft.Quantum.Diagnostics;
    open Microsoft.Quantum.Arrays;
    open Microsoft.Quantum.Intrinsic;
    open Microsoft.Quantum.Canon;
    open Microsoft.Quantum.Convert;
    open Microsoft.Quantum.Math;
    open Microsoft.Quantum.Arithmetic;

    operation Solve (register : LittleEndian) : Unit is Adj+Ctl {
        let n = Length(register!);
        X(register![0]);
        for(i in 1 .. n - 1) {
            (ControlledOnInt(0, X))(register![0 .. i - 1], register![i]);
        }
    }
}
