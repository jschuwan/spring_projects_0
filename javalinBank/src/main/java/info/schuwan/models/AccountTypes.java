package info.schuwan.models;

import io.javalin.core.security.RouteRole;

public enum AccountTypes implements RouteRole {
    CHECKING, SAVINGS, LOAN, EMPLOYEE
}
