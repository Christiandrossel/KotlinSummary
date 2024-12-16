A binary gap within a positive integer N is any maximal sequence of consecutive zeros that is surrounded by ones at both ends in the binary representation of N.

For example, number 9 has binary representation 1001 and contains a binary gap of length 2. The number 529 has binary representation 1000010001 and contains two binary gaps: one of length 4 and one of length 3. The number 20 has binary representation 10100 and contains one binary gap of length 1. The number 15 has binary representation 1111 and has no binary gaps. The number 32 has binary representation 100000 and has no binary gaps.

Write a function:

class Solution { public int solution(int N); }

that, given a positive integer N, returns the length of its longest binary gap. The function should return 0 if N doesn't contain a binary gap.

For example, given N = 1041 the function should return 5, because N has binary representation 10000010001 and so its longest binary gap is of length 5. Given N = 32 the function should return 0, because N has binary representation '100000' and thus no binary gaps.

Write an efficient algorithm for the following assumptions:

N is an integer within the range [1..2,147,483,647].


 Eine binäre Lücke innerhalb einer positiven Ganzzahl N ist jede maximale Folge von aufeinanderfolgenden Nullen,
 die an beiden Enden von Einsen umgeben ist, in der binären Darstellung von N.

 Zum Beispiel hat die Zahl 9 die binäre Darstellung 1001 und enthält eine binäre Lücke der Länge 2.
 Die Zahl 529 hat die binäre Darstellung 1000010001 und enthält zwei binäre Lücken: eine der Länge 4 und eine der Länge 3.
 Die Zahl 20 hat die binäre Darstellung 10100 und enthält eine binäre Lücke der Länge 1.
 Die Zahl 15 hat die binäre Darstellung 1111 und hat keine binären Lücken. Die Zahl 32 hat die binäre Darstellung 100000
 und hat keine binären Lücken.

 Schreiben Sie eine Funktion: class Solution { public int solution(int N); } die,
 gegeben eine positive Ganzzahl N, die Länge ihrer längsten binären Lücke zurückgibt.
 Die Funktion sollte 0 zurückgeben, wenn N keine binäre Lücke enthält. Zum Beispiel, gegeben N = 1041,
 sollte die Funktion 5 zurückgeben, weil N die binäre Darstellung 10000010001 hat und somit ihre längste binäre Lücke
 der Länge 5 ist. Gegeben N = 32 sollte die Funktion 0 zurückgeben, weil N die binäre Darstellung '100000' hat
 und somit keine binären Lücken.

 Schreiben Sie einen effizienten Algorithmus
 für die folgenden Annahmen: N ist eine Ganzzahl im Bereich [1 .. 2.147.483.647].
