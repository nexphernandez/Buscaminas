package es.nexphernandez.buscaminas.model;

import java.util.Objects;

/**
 * @author nexphernandez
 * @version 1.0.0
 */
public class UsuarioEntity {

    private String name;
    private String password;
    private Integer answers;
    private Integer hits;

    /**
     * Constructor por defecto.
     */
    public UsuarioEntity() {
    }

    /**
     * Constructor solo con nombre y contrasenia.
     * 
     * @param name     nombre del usuario.
     * @param password contrasenia del usuario.
     */
    public UsuarioEntity(String name, String password) {
        this.name = name;
        this.password = password;
    }

    /**
     * Constructor general.
     * 
     * @param name     nombre del usuario.
     * @param password contrasenia del usuario.
     * @param answers  respuestas del usuario.
     * @param hits aciertos del usuario.
     */
    public UsuarioEntity(String name, String password, Integer answers, Integer hits) {
        this.name = name;
        this.password = password;
        this.answers = answers;
        this.hits = hits;
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

    public Integer getAnswers() {
        return this.answers;
    }

    public void setAnswers(Integer answers) {
        this.answers = answers;
    }

    public Integer getHits() {
        return this.hits;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
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
                ", answers='" + getAnswers() + "'" +
                ", accuracy='" + getHits() + "'" +
                "}";
    }

}