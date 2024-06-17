// Implementación de la tarea PoisonPill
class PoisonPill implements Runnable {

    public void run() {
        throw new PoisonException("Worker detenido por PoisonPill");
    }

}

// Definición de la excepción PoisonException
class PoisonException extends RuntimeException {
    public PoisonException(String message) {
        super(message);
    }
}
