package io.jay.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Table(name = "ACCOUNT")
@Entity(name = "ACCOUNT")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountView {

    @Id
    private UUID accountId;

    private String name;

    private Double balance;


}
