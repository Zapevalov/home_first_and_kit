package com.homekit.home_first_and_kit.controller.user

import com.fasterxml.jackson.module.kotlin.MissingKotlinParameterException
import com.homekit.home_first_and_kit.controller.user.validate.ValidationError
import com.homekit.home_first_and_kit.controller.user.validate.Violation
import org.springframework.http.HttpStatus
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class UserControllerAdvice {

    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleMethodArgumentNotValid(exception: MethodArgumentNotValidException): ValidationError {
        val violations = exception.bindingResult.allErrors
            .mapNotNull { error ->
                when (error) {
                    is FieldError -> Violation(error.field, error.defaultMessage ?: "")
                    is ObjectError -> Violation(error.objectName, error.defaultMessage ?: "")
                    else -> null
                }
            }
            .toList()
        return ValidationError(violations)
    }

    @ExceptionHandler(value = [MissingKotlinParameterException::class])
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleMissingKotlinParameter(exception: MissingKotlinParameterException): ValidationError {
        val fieldName = exception.path.joinToString(separator = ".") { it.fieldName }
        val violation = Violation(fieldName, "must not be null")
        return ValidationError(listOf(violation))
    }

//    @ExceptionHandler(value = [MissingKotlinParameterException::class])
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    fun handleEmptyKotlinParameter(exception: MissingKotlinParameterException): ValidationError {
//        val fieldName = exception.path.joinToString(separator = ".") { it.fieldName }
//        val violation = Violation(fieldName, "must not be empty")
//        return ValidationError(listOf(violation))
//    }
}