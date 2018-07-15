package main.java.instruction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

/**
 *  Instruction class defines the instructions details
 */
public class Instruction {

    // An entity whose shares are to be bought or sold
    private final String entity;

    // Buy or Sell Instruction
    private final TradeFlag flag;

    // Date on which the instruction was sent
    private final LocalDate instructionDate;

    // Date on which the instruction to be settled
    private LocalDate settlementDate;

    private final InstructionDetails details;

    public Instruction(
            String entity,
            TradeFlag flag,
            LocalDate instructionDate,
            LocalDate settlementDate,
            InstructionDetails details)
    {
        this.entity = entity;
        this.flag = flag;
        this.instructionDate = instructionDate;
        this.settlementDate = settlementDate;
        this.details = details;
    }

    public String getEntity() {
        return entity;
    }

    public TradeFlag getFlag() {
        return flag;
    }

    public LocalDate getInstructionDate() {
        return instructionDate;
    }

    public void setSettlementDate(LocalDate newDate) {
        settlementDate = newDate;
    }

    public LocalDate getSettlementDate() {
        return settlementDate;
    }

    public InstructionDetails getDetails() {
        return details;
    }

    public Currency getCurrency() {
        return getDetails().getCurrency();
    }

    public BigDecimal getAgreedFx() {
        return getDetails().getAgreedFx();
    }

    public int getUnits() {
        return getDetails().getUnits();
    }

    public BigDecimal getPricePerUnit() {
        return getDetails().getPricePerUnit();
    }

    public BigDecimal getTradeAmount() {
        return getDetails().getTradeAmount()
                .setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    @Override
    public String toString() {
        return entity;
    }
}
