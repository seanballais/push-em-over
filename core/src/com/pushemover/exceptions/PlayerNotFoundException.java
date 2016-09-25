package com.pushemover.exceptions;

public class PlayerNotFoundException extends Exception
{
    public PlayerNotFoundException () {};

    public PlayerNotFoundException ( String message )
    {
        super ( message );
    }

    public PlayerNotFoundException ( Throwable cause )
    {
        super ( cause );
    }

    public PlayerNotFoundException ( String message, Throwable cause )
    {
        super ( message, cause );
    }

    public PlayerNotFoundException ( String message,
                                     Throwable cause,
                                     boolean enableSuppression,
                                     boolean writeableStackTrace
                                   )
    {
        super ( message, cause, enableSuppression, writeableStackTrace );
    }
}
