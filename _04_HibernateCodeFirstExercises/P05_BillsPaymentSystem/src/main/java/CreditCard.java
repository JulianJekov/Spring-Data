import javax.persistence.*;

@Entity
@Table(name = "credit_cards")
public class CreditCard extends BillingDetails {

    @Column(name = "card_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CardType cardType;

    @Column(name = "expiration_month", nullable = false)
    private short expirationMonth;

    @Column(name = "expiration_year", nullable = false)
    private short expirationYear;

    public CreditCard() {
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public short getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(short expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public short getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(short expirationYear) {
        this.expirationYear = expirationYear;
    }
}
