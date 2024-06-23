package com.Tcs.EmployeeManagement.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serial;

@ResponseStatus(value = HttpStatus.NOT_FOUND)

public class EmployeeNOTFound extends RuntimeException
{

    @Serial
    private static final long serialVersionUID = 5_088_656_726_691_794_786L;

    public EmployeeNOTFound(String message)
    {
        super(message);
    }

}
