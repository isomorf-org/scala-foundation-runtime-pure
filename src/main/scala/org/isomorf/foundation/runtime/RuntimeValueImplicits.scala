package org.isomorf.foundation.runtime

import scala.language.implicitConversions

object RuntimeValueImplicits {

  // TODO: this Function0 should also be constrained by RTFunction too
  implicit def function0ToAny[A](f: Function0[A]): A = {
    f()
  }

  implicit def stringToListChar(s: String): List[Char] = {
    s.toCharArray().toList
  }

  implicit def listChartoString(cs: List[Char]): String = {
    cs.mkString
  }

}
