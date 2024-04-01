/*
 * Copyright (C) 2020 The Guava Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.common.math;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.math.MathPreconditions.checkRoundingUnnecessary;

import com.google.common.annotations.GwtIncompatible;
import com.google.common.annotations.J2ktIncompatible;
import java.math.RoundingMode;

/**
 * Helper type to implement rounding {@code X} to a representable {@code double} value according to
 * a {@link RoundingMode}.
 */
@J2ktIncompatible
@GwtIncompatible
@ElementTypesAreNonnullByDefault
abstract class ToDoubleRounder<X extends Number & Comparable<X>> {
  /**
   * Returns x rounded to either the greatest double less than or equal to the precise value of x,
   * or the least double greater than or equal to the precise value of x.
   */
  abstract double roundToDoubleArbitrarily(X x);

  /** Returns the sign of x: either -1, 0, or 1. */
  abstract int sign(X x);

  /** Returns d's value as an X, rounded with the specified mode. */
  abstract X toX(double d, RoundingMode mode);

  /** Returns a - b, guaranteed that both arguments are nonnegative. */
  abstract X minus(X a, X b);

  /** Rounds {@code x} to a {@code double}. */
  final double roundToDouble(X x, RoundingMode mode) {
	  checkNotNull(x, "x");
	  checkNotNull(mode, "mode");
	  double roundArbitrarily = roundToDoubleArbitrarily(x);
	  if (Double.isInfinite(roundArbitrarily)) {
		  return handleInfiniteCase(roundArbitrarily, mode, x);
	  }
	  X roundArbitrarilyAsX = toX(roundArbitrarily, RoundingMode.UNNECESSARY);
	  int cmpXToRoundArbitrarily = x.compareTo(roundArbitrarilyAsX);
	  return handleFiniteCase(roundArbitrarily, cmpXToRoundArbitrarily, mode, x);
  }

  private double handleInfiniteCase(double roundArbitrarily, RoundingMode mode, X x) {
	  switch (mode) {
	  case DOWN:
	  case HALF_EVEN:
	  case HALF_DOWN:
	  case HALF_UP:
		  return handleInfiniteWithSign(roundArbitrarily, x);
	  case FLOOR:
		  return handleFloorCase(roundArbitrarily);
	  case CEILING:
		  return handleCeilingCase(roundArbitrarily);
	  case UP:
		  return roundArbitrarily;
	  case UNNECESSARY:
		  throw new ArithmeticException(x + " cannot be represented precisely as a double");
	  default:
		  throw new AssertionError("Unsupported rounding mode: " + mode);
	  }
  }

  private double handleFiniteCase(double roundArbitrarily, int cmpXToRoundArbitrarily, RoundingMode mode, X x) {
	  switch (mode) {
	  case UNNECESSARY:
		  return handleUnnecessaryCase(roundArbitrarily, cmpXToRoundArbitrarily);
	  case FLOOR:
		  return handleFloorCase(roundArbitrarily, cmpXToRoundArbitrarily);
	  case CEILING:
		  return handleCeilingCase(roundArbitrarily, cmpXToRoundArbitrarily);
	  case DOWN:
		  return handleDownCase(roundArbitrarily, cmpXToRoundArbitrarily, x);
	  case UP:
		  return handleUpCase(roundArbitrarily, cmpXToRoundArbitrarily, x);
	  case HALF_DOWN:
	  case HALF_UP:
	  case HALF_EVEN:
		  return handleHalfCase(roundArbitrarily, cmpXToRoundArbitrarily, mode, x);
	  default:
		  throw new AssertionError("Unsupported rounding mode: " + mode);
	  }
  }

  private double handleInfiniteWithSign(double roundArbitrarily, X x) {
	  switch (sign(x)) {
	  case 1:
		  return Double.MAX_VALUE;
	  case -1:
		  return -Double.MAX_VALUE;
	  case 0:
	  default:
		  throw new AssertionError("Invalid sign: " + sign(x));
	  }
  }
  private double handleFloorCase(double roundArbitrarily) {
      return (roundArbitrarily == Double.POSITIVE_INFINITY)
              ? Double.MAX_VALUE
              : Double.NEGATIVE_INFINITY;
  }

  private double handleCeilingCase(double roundArbitrarily) {
      return (roundArbitrarily == Double.POSITIVE_INFINITY)
              ? Double.POSITIVE_INFINITY
              : -Double.MAX_VALUE;
  }

  private double handleUnnecessaryCase(double roundArbitrarily, int cmpXToRoundArbitrarily) {
      checkRoundingUnnecessary(cmpXToRoundArbitrarily == 0);
      return roundArbitrarily;
  }

  private double handleFloorCase(double roundArbitrarily, int cmpXToRoundArbitrarily) {
      return (cmpXToRoundArbitrarily >= 0)
              ? roundArbitrarily
              : DoubleUtils.nextDown(roundArbitrarily);
  }

  private double handleCeilingCase(double roundArbitrarily, int cmpXToRoundArbitrarily) {
      return (cmpXToRoundArbitrarily <= 0) ? roundArbitrarily : Math.nextUp(roundArbitrarily);
  }

  private double handleDownCase(double roundArbitrarily, int cmpXToRoundArbitrarily, X x) {
      if (sign(x) >= 0) {
          return (cmpXToRoundArbitrarily >= 0)
                  ? roundArbitrarily
                  : DoubleUtils.nextDown(roundArbitrarily);
      } else {
          return (cmpXToRoundArbitrarily <= 0) ? roundArbitrarily : Math.nextUp(roundArbitrarily);
      }
  }

  private double handleUpCase(double roundArbitrarily, int cmpXToRoundArbitrarily, X x) {
      if (sign(x) >= 0) {
          return (cmpXToRoundArbitrarily <= 0) ? roundArbitrarily : Math.nextUp(roundArbitrarily);
      } else {
          return (cmpXToRoundArbitrarily >= 0)
                  ? roundArbitrarily
                  : DoubleUtils.nextDown(roundArbitrarily);
      }
  }

  private double handleHalfCase(double roundArbitrarily, int cmpXToRoundArbitrarily, RoundingMode mode, X x) {
      double roundFloorAsDouble;
      double roundCeilingAsDouble;

      if (cmpXToRoundArbitrarily >= 0) {
          roundFloorAsDouble = roundArbitrarily;
          roundCeilingAsDouble = Math.nextUp(roundArbitrarily);
          if (roundCeilingAsDouble == Double.POSITIVE_INFINITY) {
              return roundFloorAsDouble;
          }
      } else {
          roundCeilingAsDouble = roundArbitrarily;
          roundFloorAsDouble = DoubleUtils.nextDown(roundArbitrarily);
          if (roundFloorAsDouble == Double.NEGATIVE_INFINITY) {
              return roundCeilingAsDouble;
          }
      }

      X roundFloor = toX(roundFloorAsDouble, RoundingMode.FLOOR);
      X roundCeiling = toX(roundCeilingAsDouble, RoundingMode.CEILING);

      X deltaToFloor = minus(x, roundFloor);
      X deltaToCeiling = minus(roundCeiling, x);
      int diff = deltaToFloor.compareTo(deltaToCeiling);
      if (diff < 0) { // closer to floor
          return roundFloorAsDouble;
      } else if (diff > 0) { // closer to ceiling
          return roundCeilingAsDouble;
      }
      // halfway between the representable values; do the half-whatever logic
      switch (mode) {
          case HALF_EVEN:
        	  // roundFloorAsDouble and roundCeilingAsDouble are neighbors, so precisely
              // one of them should have an even long representation
              return ((Double.doubleToRawLongBits(roundFloorAsDouble) & 1L) == 0)
                      ? roundFloorAsDouble
                      : roundCeilingAsDouble;
          case HALF_DOWN:
              return (sign(x) >= 0) ? roundFloorAsDouble : roundCeilingAsDouble;
          case HALF_UP:
              return (sign(x) >= 0) ? roundCeilingAsDouble : roundFloorAsDouble;
          default:
              throw new AssertionError("impossible");
      }
  }
}