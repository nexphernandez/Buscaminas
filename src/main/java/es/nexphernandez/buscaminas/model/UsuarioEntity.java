package es.nexphernandez.buscaminas.model;

import java.util.Objects;

/**
 * @author nexphernandez
 * @version 1.0.0
 */
public class UsuarioEntity {

    private String name;
    private String password;

    /**
     * Constructor por defecto.
     */
    public UsuarioEntity() {
    }

    /**
     * Constructor solo con nombre y contrasenia.
     * @param name     nombre del usuario.
     * @param password contrasenia del usuario.
     */
    public UsuarioEntity(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof UsuarioEntity)) {
            return false;
        }
        UsuarioEntity user = (UsuarioEntity) o;
        return Objects.equals(name, user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "{" +
                " name='" + getName() + "'" +
                ", password='" + getPassword() + "'" +
                "}";
    }

}