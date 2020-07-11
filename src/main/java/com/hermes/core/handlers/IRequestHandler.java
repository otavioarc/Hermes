package com.hermes.core.handlers;

public interface IRequestHandler<M, T> {
    T handle(M m);
}
