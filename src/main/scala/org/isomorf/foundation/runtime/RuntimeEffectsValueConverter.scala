package org.isomorf.foundation.runtime

import scala.reflect.runtime.universe._

/**
 * Handles the internal translations of values to/from Effects
 */
trait RuntimeEffectsValueConverter {

  def fromRTValueToEffectsValue[V, X](value: V)(implicit vTag: WeakTypeTag[V], xTag: WeakTypeTag[X]): Option[X]

  def toRTValuefromEffectsValue[V, X](any: X)(implicit vTag: WeakTypeTag[V], xTag: WeakTypeTag[X]): Option[V]
}
