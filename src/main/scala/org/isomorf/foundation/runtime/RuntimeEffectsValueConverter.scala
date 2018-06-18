package org.isomorf.foundation.runtime

import scala.reflect.ClassTag

/**
 * Handles the internal translations of values to/from Effects
 */
trait RuntimeEffectsValueConverter {

  def fromRTValueToEffectsValue[V, X](value: V)(implicit vTag: ClassTag[V], xTag: ClassTag[X]): Option[X]

  def toRTValuefromEffectsValue[V, X](any: X)(implicit vTag: ClassTag[V], xTag: ClassTag[X]): Option[V]
}
