package com.nagp.usermanager.exceptions;

public class Error {
    /**
     * The errorMessage.
     */
    private String errorMessage;

    public Error() {
    }
    public Error(String errorMessage) {
        super();
        this.errorMessage = errorMessage;
    }



    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}

