package com.vpaliy.melophile.di.scope;

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;

import javax.inject.Scope;

/**
 * A component that has this scope will exist as long as a view does.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewScope {
}