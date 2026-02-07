package com.lastcup.api.domain.intake.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
        name = "intake_option",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_intake_option", columnNames = {"intake_id", "option_id"})
        }
)
public class IntakeOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "intake_id", nullable = false)
    private Intake intake;

    @Column(name = "option_id", nullable = false)
    private Long optionId;

    @Column(nullable = false)
    private int quantity;

    protected IntakeOption() {
    }

    private IntakeOption(Intake intake, Long optionId, int quantity) {
        this.intake = intake;
        this.optionId = optionId;
        this.quantity = quantity;
    }

    static IntakeOption create(Intake intake, Long optionId, int quantity) {
        validateIntake(intake);
        validateOptionId(optionId);
        validateQuantity(quantity);

        return new IntakeOption(intake, optionId, quantity);
    }

    private static void validateIntake(Intake intake) {
        if (intake == null) {
            throw new IllegalArgumentException("intake is null");
        }
    }

    private static void validateOptionId(Long optionId) {
        if (optionId == null || optionId <= 0) {
            throw new IllegalArgumentException("optionId is invalid");
        }
    }

    private static void validateQuantity(int quantity) {
        if (quantity < 1) {
            throw new IllegalArgumentException("option quantity must be at least 1");
        }
    }

    public Long getId() {
        return id;
    }

    public Intake getIntake() {
        return intake;
    }

    public Long getOptionId() {
        return optionId;
    }

    public int getQuantity() {
        return quantity;
    }
}
