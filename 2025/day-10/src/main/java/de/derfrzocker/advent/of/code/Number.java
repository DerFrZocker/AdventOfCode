package de.derfrzocker.advent.of.code;

public record Number(int denominator, int numerator) {

    private static final int[] PRIMES = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71};

    public static final Number ZERO = new Number(0, 1);
    public static final Number ONE = new Number(1, 1);

    public static Number of(int number) {
        return new Number(number, 1);
    }

    private static Number ofReduce(int denominator, int numerator) {
        if (denominator == 0) {
            return Number.ZERO;
        }

        for (int prime : PRIMES) {
            if (Math.abs(denominator) < prime || Math.abs(numerator) < prime) {
                break;
            }
            if (denominator % prime != 0) {
                continue;
            }
            if (numerator % prime != 0) {
                continue;
            }

            denominator = denominator / prime;
            numerator = numerator / prime;
        }

        return new Number(denominator, numerator);
    }

    public Number multiply(Number other) {
        return Number.ofReduce(Math.multiplyExact(denominator, other.denominator), Math.multiplyExact(numerator, other.numerator));
    }

    public Number divide(Number other) {
        if (other.isZero()) {
            throw new RuntimeException("Cannot be zero.");
        }
        if (other.denominator < 0) {
            return Number.ofReduce(-Math.multiplyExact(denominator, other.numerator), Math.multiplyExact(numerator, Math.abs(other.denominator)));
        } else {
            return Number.ofReduce(Math.multiplyExact(denominator, other.numerator), Math.multiplyExact(numerator, other.denominator));
        }
    }

    public Number add(Number other) {
        if (numerator == other.numerator) {
            return new Number(Math.addExact(denominator, other.denominator), numerator);
        }

        int firstDenominator = Math.multiplyExact(denominator, other.numerator);
        int secondDenominator = Math.multiplyExact(other.denominator, numerator);

        return Number.ofReduce(Math.addExact(firstDenominator, secondDenominator), Math.multiplyExact(numerator, other.numerator));
    }

    public Number subtract(Number other) {
        if (numerator == other.numerator) {
            return new Number(Math.subtractExact(denominator, other.denominator), numerator);
        }
        int firstDenominator = Math.multiplyExact(denominator, other.numerator);
        int secondDenominator = Math.multiplyExact(other.denominator, numerator);

        return Number.ofReduce(Math.subtractExact(firstDenominator, secondDenominator), Math.multiplyExact(numerator, other.numerator));
    }

    public boolean isZero() {
        return denominator == 0;
    }

    public boolean isOne() {
        return denominator == numerator;
    }

    public boolean isWholeNumber() {
        return denominator % numerator == 0;
    }

    public boolean isSmallerZero() {
        return !isZero() && denominator < 0;
    }

    public int wholeNumber() {
        if (!isWholeNumber()) {
            throw new RuntimeException();
        }

        return denominator / numerator;
    }
}
