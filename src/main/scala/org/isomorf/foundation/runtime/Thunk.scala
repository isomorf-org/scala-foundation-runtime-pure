package org.isomorf.foundation.runtime

trait Thunk[A] extends scala.Function0[A] {
  override abstract lazy val apply = super.apply()
}
