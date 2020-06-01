namespace Solution {
    open Microsoft.Quantum.Primitive;
    open Microsoft.Quantum.Canon;
    open Microsoft.Quantum.Extensions.Math;
    
    operation Solve (qs : Qubit[]) : Unit {
        Ry(2.0 * ArcCos(1.0 / Sqrt(3.0)), qs[0]);
        Controlled H([qs[0]], qs[1]);
        Controlled X([qs[1]], qs[0]);
    }
}