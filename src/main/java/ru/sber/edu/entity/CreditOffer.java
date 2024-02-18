package ru.sber.edu.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import ru.sber.edu.entity.auth.User;
import ru.sber.edu.ui.table.TableColumn;
import ru.sber.edu.ui.table.UiColumn;
import ru.sber.edu.ui.table.UiFieldType;

import java.util.List;

@Entity
@Table(name = "credit_offer")
@Data
@NoArgsConstructor
@NamedEntityGraph(
        name = "credit_offer-entity-graph",
        attributeNodes = {
                @NamedAttributeNode(value = "credit", subgraph = "subgraph-bank"),
                @NamedAttributeNode("user"),
                @NamedAttributeNode("creditOfferStatus"),},
        subgraphs = {@NamedSubgraph(name = "subgraph-bank",
                attributeNodes = {@NamedAttributeNode(value = "bankId")})}
)
public class CreditOffer {

    @Id
    @GeneratedValue(generator = "credit-offer-generator")
    @GenericGenerator(
            name = "credit-offer-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "credit_offer_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "70"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            })
    @Column(name = "credit_offer_id")
    private Long creditOfferId;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "credit_id")
    private Credit credit;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "status_name")
    private CreditOfferStatus creditOfferStatus;

    public static List<UiColumn> getClientColumns() {
        return List.of(
                new TableColumn("creditId", "Id"),
                new TableColumn("creditOffer-NameLinkToCredit", "Name", UiFieldType.CUSTOM),
                new TableColumn("creditOffer-StatusName", "Status", UiFieldType.CUSTOM)
        );
    }
}
