package com.qifan.powerpermission.data

/**
 * configuration file to set global environment
 */
interface Configuration {
    /**
     * enable log mode or not
     */
    val enableLog: Boolean
}

/**
 * Default Implementation of configuration for setting global environment
 */
internal data class DefaultConfiguration(
    override val enableLog: Boolean = false
) : Configuration
