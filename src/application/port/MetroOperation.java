package application.port;

import application.exception.MetroOperationException;

@FunctionalInterface
public interface MetroOperation {

    void execute() throws MetroOperationException;
}
