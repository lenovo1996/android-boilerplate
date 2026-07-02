package com.app.core.common.extensions

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ExtensionsTest {

  // -- safeValueOf --

  private enum class Vehicles { CAR, MOTOR_BIKE }

  @Test
  fun `safeValueOf returns enum for valid string`() {
    assertEquals(Vehicles.CAR, safeValueOf<Vehicles>("CAR"))
  }

  @Test
  fun `safeValueOf replaces dashes with underscores`() {
    assertEquals(Vehicles.MOTOR_BIKE, safeValueOf<Vehicles>("MOTOR-BIKE"))
  }

  @Test
  fun `safeValueOf returns null for invalid string`() {
    assertNull(safeValueOf<Vehicles>("BICYCLE"))
  }

  @Test
  fun `safeValueOf returns null for null input`() {
    assertNull(safeValueOf<Vehicles>(null))
  }

  // -- toInt --

  @Test
  fun `true toInt returns 1`() {
    assertEquals(1, true.toInt())
  }

  @Test
  fun `false toInt returns 0`() {
    assertEquals(0, false.toInt())
  }

  // -- isGreaterThanOrEqualTo --

  @Test
  fun `greater value returns true`() {
    assertTrue(5.0.isGreaterThanOrEqualTo(3.0))
  }

  @Test
  fun `equal value returns true`() {
    assertTrue(5.0.isGreaterThanOrEqualTo(5.0))
  }

  @Test
  fun `lesser value returns false`() {
    assertFalse(2.0.isGreaterThanOrEqualTo(3.0))
  }

  @Test
  fun `null value returns false`() {
    val value: Double? = null
    assertFalse(value.isGreaterThanOrEqualTo(1.0))
  }

  // -- multiLet --

  @Test
  fun `multiLet executes closure when all non-null`() {
    var executed = false
    multiLet("a", 1, true) { executed = true }
    assertTrue(executed)
  }

  @Test
  fun `multiLet returns null when any element is null`() {
    val result = multiLet("a", null, true) { }
    assertNull(result)
  }
}
