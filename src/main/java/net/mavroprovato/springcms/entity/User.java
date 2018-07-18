package net.mavroprovato.springcms.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.OffsetDateTime;

/**
 * Application users
 */
@Entity(name = "users")
public class User {

    /** The unique identifier of the user */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /** The user name */
    @Column(nullable = false, unique = true)
    private String userName;

    /** The user password */
    @Column(nullable = false)
    @JsonIgnore
    private String password;

    /** The user email */
    @Column(nullable = false, unique = true)
    private String email;

    /** The user first name */
    @Column
    private String firstName;

    /** The user last name */
    @Column
    private String lastName;

    /** The user web site */
    @Column
    private String webSite;

    /** The user creation date */
    @Column(nullable = false)
    @CreationTimestamp
    private OffsetDateTime createdAt;

    /** The user update date */
    @Column(nullable = false)
    @UpdateTimestamp
    private OffsetDateTime updatedAt;

    /** The content status */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    /**
     * Return the user unique identifier.
     *
     * @return The user unique identifier.
     */
    public int getId() {
        return id;
    }

    /**
     * Return the user name.
     *
     * @return The user name.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Set the user name.
     *
     * @param userName The user name.
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Get the user password.
     *
     * @return The user password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set the user password.
     *
     * @param password The user password.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Get the user email.
     *
     * @return The user email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the user email.
     *
     * @param email The user email.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get the user first name.
     *
     * @return The user first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Set the user first name.
     *
     * @param firstName The user first name.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Get the user last name.
     *
     * @return The user last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Set the user last name.
     *
     * @param lastName The user last name.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Get the user web site.
     *
     * @return The user web site.
     */
    public String getWebSite() {
        return webSite;
    }

    /**
     * Set the user web site.
     *
     * @param webSite The user web site.
     */
    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }

    /**
     * Return the content item creation date.
     *
     * @return The content item creating date.
     */
    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Return the content item update date.
     *
     * @return The content item creating date.
     */
    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    /**
     * Get the user role.
     *
     * @return The user role.
     */
    public Role getRole() {
        return role;
    }

    /**
     * Set the user role.
     *
     * @param role The user role.
     */
    public void setRole(Role role) {
        this.role = role;
    }
}
