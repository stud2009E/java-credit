package ru.sber.edu.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sber.edu.entity.auth.User;
import ru.sber.edu.repository.ID.CreditOfferID;

@Entity
@Table(name = "credit_offer")
@Data
@NoArgsConstructor
@IdClass(CreditOfferID.class)
@NamedEntityGraph(
        name = "credit_offer-entity-graph",
        attributeNodes = {
                @NamedAttributeNode(value = "credit", subgraph = "subgraph-bank"),
                @NamedAttributeNode("user"),
                @NamedAttributeNode("creditOfferStatus"), },
        subgraphs = { @NamedSubgraph(name = "subgraph-bank",
                                     attributeNodes = {@NamedAttributeNode(value = "bank")}) }
)
public class CreditOffer {

    @Id
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "credit_id")
    private Credit credit;

    @Id
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "status_name")
    private CreditOfferStatus creditOfferStatus;
}
