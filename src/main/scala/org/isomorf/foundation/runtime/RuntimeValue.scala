package org.isomorf.foundation.runtime

import scala.concurrent.Future
import scala.util.Try

import monix.eval.Task

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
  def effect(): Task[A]
  def map[B](f: A => B): RTEffect[B]
  def flatMap[B](f: A => RTEffect[B]): RTEffect[B]
}

object RTEffect {
  private case class Impl[A](t: Task[A]) extends RTEffect[A] {
    def effect(): Task[A] = t
    def map[B](f: A => B): RTEffect[B] = RTEffect(t.map(f))
    def flatMap[B](f: A => RTEffect[B]): RTEffect[B] = RTEffect(t.flatMap(f(_).effect()))
  }

  def apply[A](t: Task[A]): RTEffect[A] = {
    Impl(t)
  }

  def apply[A](f: Future[A]): RTEffect[A] = {
    Impl(Task.fromFuture(f))
  }

  def apply[A](e: RTEffect[A]): RTEffect[A] = {
    e
  }

  def apply[A](t: Try[A]): RTEffect[A] = {
    Impl(Task.fromTry(t))
  }

  def apply[A](any: A): RTEffect[A] = {
    Impl(Task.eval(any))
  }
}
