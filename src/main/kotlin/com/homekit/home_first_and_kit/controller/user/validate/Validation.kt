package com.homekit.home_first_and_kit.controller.user.validate

class ValidationError(val violations: List<Violation>)
class Violation(val field: String, val message: String)