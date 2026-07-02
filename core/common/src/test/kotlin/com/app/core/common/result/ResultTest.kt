package com.app.core.common.result

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNull
import kotlin.test.assertTrue

class ResultTest {

  @Test
  fun `Success should hold data correctly`() {
    val result: AppResult<String> = AppResult.Success("hello")
    assertIs<AppResult.Success<String>>(result)
    assertEquals("hello", result.data)
    assertTrue(result.isSuccess)
  }

  @Test
  fun `Error should hold error correctly`() {
    val result: AppResult<String> = AppResult.Error(AppError.NoConnection())
    assertIs<AppResult.Error>(result)
    assertTrue(result.isError)
  }

  @Test
  fun `getOrNull returns data on success`() {
    val result: AppResult<Int> = AppResult.Success(42)
    assertEquals(42, result.getOrNull())
  }

  @Test
  fun `getOrNull returns null on error`() {
    val result: AppResult<Int> = AppResult.Error(AppError.Unknown())
    assertNull(result.getOrNull())
  }

  @Test
  fun `map transforms success value`() {
    val result = AppResult.Success(5).map { it * 2 }
    assertIs<AppResult.Success<Int>>(result)
    assertEquals(10, result.data)
  }

  @Test
  fun `map preserves error`() {
    val error = AppError.Network()
    val result = AppResult.Error(error).map { "transformed" }
    assertIs<AppResult.Error>(result)
    assertEquals(error, result.error)
  }

  @Test
  fun `flatMap chains operations`() {
    val result = AppResult.Success(5)
      .flatMap { AppResult.Success(it.toString()) }
    assertIs<AppResult.Success<String>>(result)
    assertEquals("5", result.data)
  }

  @Test
  fun `getOrElse returns default on error`() {
    val result: AppResult<String> = AppResult.Error(AppError.Unknown())
    val value = result.getOrElse { "default" }
    assertEquals("default", value)
  }

  @Test
  fun `getOrElse returns data on success`() {
    val result: AppResult<String> = AppResult.Success("real")
    val value = result.getOrElse { "default" }
    assertEquals("real", value)
  }

  @Test
  fun `onSuccess executes action for success`() {
    var captured = ""
    AppResult.Success("test").onSuccess { captured = it }
    assertEquals("test", captured)
  }

  @Test
  fun `onError executes action for error`() {
    var captured: AppError? = null
    val error = AppError.Server(code = "404", httpCode = 404)
    AppResult.Error(error).onError { captured = it }
    assertEquals(error, captured)
  }
}
