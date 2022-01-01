package com.manic.galaxy.domain.shared

/**
 * Is thrown if any business rule has been violated.
 */
class BusinessException(val error: String) : RuntimeException(error)
