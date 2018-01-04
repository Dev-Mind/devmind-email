package com.devmind.email.service.impl;

/**
 * @author Dev-Mind <guillaume@dev-mind.fr>
 * @since 04/01/18.
 */
public class ElasticEmailResponseDTO {
    private Boolean success;
    private String error;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
