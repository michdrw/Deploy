package ar.com.ada.api.sueldos.excepciones;

import ar.com.ada.api.sueldos.services.EmpleadoService.EmpleadoValidationType;

/**
 * EmpleadoException
 */
public class EmpleadoException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private EmpleadoValidationType errorType;

    public EmpleadoException(EmpleadoValidationType errorType)
    {
        this.errorType = errorType;
    }

    /**
     * @return the errorType
     */
    public EmpleadoValidationType getErrorType() {
        return errorType;
    }
}