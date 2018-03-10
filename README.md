# INFO233.Oblig2

Deloppgave 6.

Append: Append har kompleksitet O(N), da den itererer gjennom listen som kommer inn og kaller add() på hver av disse elementene.

Prepend: Også O(N). Den innkommende listen reverseres først, som da essensielt kaller put() for hvert element i listen, og sender den tilbake til parameteret slik at den kommer i korrekt rekkefølge når vi igjen bruke en forEach løkke for å iterere gjennom listen og kaller put() for hver av disse. Kompleksiteten er O(N).

Concat: 
