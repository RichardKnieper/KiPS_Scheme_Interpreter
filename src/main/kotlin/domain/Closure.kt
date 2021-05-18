package domain

class Closure(
    val method: List<Any>,
    val env: MutableMap<String, Any>,
    val parent: Closure?
)

/**
 * (define (erzeuge-konto saldo)
 *   (lambda (betrag)
 *     (set! saldo (+ saldo betrag))
 *     saldo))
 */

/**
 * (define konto (lambda (saldo)
 *                  (lambda (betrag)
 *                     (set! saldo (+ saldo betrag))
 *                     saldo)))
 */

// (define square (lambda (x) (* x x)))

// (define test (lambda (x) (lambda (y) x)))
