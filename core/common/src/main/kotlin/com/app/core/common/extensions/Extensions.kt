package com.app.core.common.extensions

/**
 * Safely converts a string to an enum value, returning null if not found.
 */
inline fun <reified T : Enum<T>> safeValueOf(name: String?): T? {
  return name?.let {
    try {
      enumValueOf<T>(it.replace("-", "_"))
    } catch (_: IllegalArgumentException) {
      null
    }
  }
}

/**
 * Executes [closure] only if all elements are non-null.
 */
inline fun <T : Any> multiLet(vararg elements: T?, closure: (List<T>) -> Unit): Unit? {
  return if (elements.all { it != null }) {
    closure(elements.filterNotNull())
  } else {
    null
  }
}

/**
 * Converts a Boolean to Int (true = 1, false = 0).
 */
fun Boolean.toInt(): Int = if (this) 1 else 0

/**
 * Returns true if this Double is greater than or equal to [value].
 */
fun Double?.isGreaterThanOrEqualTo(value: Double): Boolean = this != null && this >= value
