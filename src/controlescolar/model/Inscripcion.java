package controlescolar.model;

public class Inscripcion {

    private int idInscripcion;
    private String status;
    private float importePago;

    public Inscripcion() {
    }

    public Inscripcion(int idInscripcion, String status, float importePago) {
        this.idInscripcion = idInscripcion;
        this.status = status;
        this.importePago = importePago;
    }

    public int getIdInscripcion() {
        return idInscripcion;
    }

    public void setIdInscripcion(int idInscripcion) {
        this.idInscripcion = idInscripcion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getImportePago() {
        return importePago;
    }

    public void setImportePago(float importePago) {
        this.importePago = importePago;
    }
}
