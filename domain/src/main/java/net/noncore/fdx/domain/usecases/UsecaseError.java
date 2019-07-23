package net.noncore.fdx.domain.usecases;

public class UsecaseError extends RuntimeException {
    private final Request request;

    public UsecaseError(Request request, Throwable cause) {
        super(cause);
        this.request = request;
    }
}
