package main.java.utils;

import main.java.instruction.Instruction;
import main.java.instruction.InstructionDetails;
import main.java.instruction.TradeFlag;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Currency;
import java.util.HashSet;
import java.util.Set;

public class InstructionsGenerator {
    public static Set<Instruction> getInstructions() {
        return new HashSet<>(Arrays.asList(

            new Instruction(
                "foo",
                TradeFlag.BUY,
                LocalDate.of(2016, 1, 01),
                LocalDate.of(2016, 1, 05),
                new InstructionDetails(
                        Currency.getInstance("SGD"),
                        BigDecimal.valueOf(0.50),
                        200,
                        BigDecimal.valueOf(100.25))),

            new Instruction(
                "bar",
                TradeFlag.BUY,
                LocalDate.of(2017, 7, 05),
                LocalDate.of(2017, 8, 25),
                new InstructionDetails(
                        Currency.getInstance("AED"),
                        BigDecimal.valueOf(0.22),
                        450,
                        BigDecimal.valueOf(150.5))),

            new Instruction(
                "foobar",
                TradeFlag.SELL,
                LocalDate.of(2018, 5, 11),
                LocalDate.of(2018, 5, 29),
                new InstructionDetails(
                        Currency.getInstance("SAR"),
                        BigDecimal.valueOf(0.27),
                        150,
                        BigDecimal.valueOf(300.6)))

        ));
    }
}
