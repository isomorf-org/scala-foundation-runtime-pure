package org.isomorf.foundation.runtime

import scala.util.Try

sealed trait RTValue

////////
// pure:
////////

sealed trait RTPure extends RTValue

// parent class to all generated functions
trait RTFunction extends RTPure

// parent class to all generated data types
trait RTData extends RTPure

sealed trait RTNative extends RTData {
  def any: Any
}

object RTNative {

  def apply(any: Any): RTNative = {
    any match {
      case n: RTNative => {
        n
      }
      case _ => {
        case class Impl(val any: Any) extends RTNative
        Impl(any)
      }
    }
  }

  def unapply(native: RTNative): Option[Any] = {
    Some(native.any)
  }
}

object RTPure {

  def apply(any: Any): RTPure = {
    any match {
      case p: RTPure => p
      case _         => RTNative(any)
    }
  }
}

///////////
// effects:
///////////

sealed trait RTEffect[A] extends RTValue {
  def effect(): Try[A]
}

object RTEffect {
  final def apply[A](e: => Try[A]): RTEffect[A] = {
    new RTEffect[A] {
      final override def effect: Try[A] = {
        e
      }
    }
  }
}
