package com.oops.project.product.domain;

import java.io.Serializable;
// import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
// import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
// import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.validation.annotation.Validated;
 
@Validated
@Entity
@Table(name = "product")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
public class Product implements Serializable {
 
    private static final long serialVersionUID = 4048798961366546485L;
 
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Size(max = 255)
    private String name;
    
    // @Pattern(regexp ="^\\+?[0-9. ()-]{7,25}$", message = "Phone number")
    @Size(max = 2048)
    private String image1;
    
    @Size(max = 2048)
    private String image2;
    
    @Size(max = 2048)
    private String image3;
    
    @Size(max = 2048)
    private String image4;

    private float price;

    private int stock;

    @Size(max=4000)
    private String reviews;

    @Size(max=1000)
    private String categories;

    private int discount;

    @Size(max=1000)
    private String tags;

    // @Email(message = "Email Address")
    // @Size(max = 100)
    // private String email;
    
    // @Size(max = 4000)
    // private String address1;
    
    // @Size(max = 50)
    // private String address2;
    
    // @Size(max = 50)
    // private String address3;
    
    // @Size(max = 20)
    // private String postalCode;
    
    // @Column(length = 4000)
    // private String note;
}