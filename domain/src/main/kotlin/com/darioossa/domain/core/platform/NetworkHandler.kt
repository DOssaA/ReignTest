package com.darioossa.domain.core.platform

/**
 * interface for allowing controlling network status,
 * to be implemented by the respective presentation layer
 */

interface NetworkHandler{
    fun isConnected(): Boolean
}