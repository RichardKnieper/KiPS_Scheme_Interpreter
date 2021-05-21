package domain

enum class Token {

    // Math operations
    ADD,
    SUBTRACT,
    MULTIPLY,
    DIVIDE,

    // Comparison operators
    LESS,           // <
    MORE,           // >
    EQUAL,          // =
    MORE_EQUAL,     // >=
    LESS_EQUAL,     // <=

    LAMBDA,
    DEFINE,
    SET,

    IF,
    COND,

    CONS,
    LIST,
    CAR,
    CDR,
    LIST_END // '()
}
