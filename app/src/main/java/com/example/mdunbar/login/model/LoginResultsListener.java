package com.example.mdunbar.login.model;

/**
 * Results listener companion to {@link LoginUseCase}
 *
 * @author Mike Dunbar
 */
public interface LoginResultsListener {

    /**
     * Success callback
     */
    void onLoginSuccess();

    /**
     * Network error callback
     */
    void onNetworkError();

    /**
     * Validate error callback
     */
    void onValidationError();

    /**
     * Logging util method, just for debugging
     * @param desc description
     */
    void logThreadState(String desc);
}
