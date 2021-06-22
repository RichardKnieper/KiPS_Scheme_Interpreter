# Scheme Compiler - implementiert in Kotlin

### Umgesetzte Sprachbestandteile

* Addition, Subtraktion, Multiplikation, Division mit positiven und negativen double Werten
* Runden mit _round_
* Vergleichen von Zahlen (<, >, <=, >=, ==)
* Wahrheitswerte mit _#t_ und _#f_
* Verzweigungen mit _if_ und _cond_
* Die Auswertung von Lambdaausdrücken
* Das Definieren von Variablen und Methoden mit _define_
* Lokale Variablen mit _let_
* _cons_ und _list_ als Datenstrukturen
* Das Erstellen von Quotes mit _quote_ oder _'_
* Der Zugriff auf Listen-/Quoteelemente mit _car_ und _cdr_
* Ausgabe der Listenlänge mit _length_
* Ausgabe, ob eine Liste leer ist mit _null?_
* Strings
* _set!_ um Variablen zu überschreiben

### Funktionen der REPL

Neben dem Ausführen von Scheme Code bietet die REPL folgende Befehle:
* _:q_ oder _:quit_ um die REPL zu schließen
* _:c_ oder _:clear_ um alle gespeicherten Variablen und Methoden zu löschen
* _:t XY_ um einen Test mit dem Namen XY auszuführen

#### Tests

Name | Funktion
---- | --------
fac | Rekursive Implementierung der Fakultät
konto | Das Kontobeispiel aus der Vorlesung um Closures zu testen
lambda | Ein Lambdaaufruf, der eine globale Variable neu setzt, aber keinen Rückgabewert hat
let | Verschiedene Tests von _let_
merge | Implementierung und Aufruf von Mergesort
quote1 | Tests für _quote_
quote2 | s.o.
quote3 | s.o.
square | Tests das Übergeben von Methoden als Parametern
string | Tests der Strings
