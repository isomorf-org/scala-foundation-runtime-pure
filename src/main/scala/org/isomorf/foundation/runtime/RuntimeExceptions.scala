package org.isomorf.foundation.runtime

sealed trait HaltReason

object HaltReason {
  final case object Todo extends HaltReason
  final case object Unrecoverable extends HaltReason
  final case object BrokenAssumption extends HaltReason
  final case object Unrunnable extends HaltReason
  final case object Unmatched extends HaltReason
}

final class HaltException(val reason: HaltReason) extends RuntimeException

object HaltException {

  def todo() = throw new HaltException(HaltReason.Todo)

  def unrecoverable() = throw new HaltException(HaltReason.Unrecoverable)

  def brokenAssumption() = throw new HaltException(HaltReason.BrokenAssumption)

  def unrunnable() = throw new HaltException(HaltReason.Unrunnable)

  def unmatched() = throw new HaltException(HaltReason.Unmatched)
}
