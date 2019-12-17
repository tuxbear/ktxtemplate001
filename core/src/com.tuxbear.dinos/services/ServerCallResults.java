package com.tuxbear.dinos.services;

public class ServerCallResults {

    public ServerCallResults(ServerCallStatus status, String failureString) {
        this.status = status;
        this.failureString = failureString;
    }

    private ServerCallStatus status;

    public String getFailureString() {
        return failureString;
    }

    public void setFailureString(String failureString) {
        this.failureString = failureString;
    }

    public ServerCallStatus getStatus() {
        return status;
    }

    public void setStatus(ServerCallStatus status) {
        this.status = status;
    }

    private String failureString;

    public static ServerCallResults success() { return new ServerCallResults(ServerCallStatus.SUCCESS, null); }
    public static ServerCallResults failure(String message) { return new ServerCallResults(ServerCallStatus.FAILURE, message); }
    public static ServerCallResults loginRequired() { return new ServerCallResults(ServerCallStatus.LOGIN_REQUIRED, null); }

}
