package net.noncore.fdx.domain.usecases;

public interface Usecase<REQUEST extends Request, RESPONSE extends Response> {

    RESPONSE doIt(REQUEST request) throws UsecaseError;
}
