package org.isomorf.foundation.runtime

object RuntimeAdapters {
  object StringPatternAdapter {
    def unapply(s: String): Option[String] = {
      Some(s)
    }
    def unapply(s: List[Char]): Option[String] = {
      Some(s.mkString)
    }
  }

  object UnrunnablePattern {
    def unapply(any: Any): Boolean = {
      HaltException.unrunnable()
    }
  }

  object TodoPattern {
    def unapply(any: Any): Boolean = {
      HaltException.todo()
    }
  }
}
