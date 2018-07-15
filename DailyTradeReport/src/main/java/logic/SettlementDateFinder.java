package main.java.logic;

import main.java.logic.workingdays.ArabicWorkingDays;
import main.java.logic.workingdays.UsualWorkingDays;
import main.java.logic.workingdays.IWorkingDays;
import main.java.instruction.Instruction;

import java.time.LocalDate;
import java.util.Currency;
import java.util.Set;

/**
 * settlement date calculator
 */
public class SettlementDateFinder {

    /**
     * function to calculate settlement date for every given instruction
     * @param instructions of which the settlement date will be calculated
     */
    public static void calculateSettlementDates(Set<Instruction> instructions) {
        instructions.forEach(SettlementDateFinder::calculateSettlementDate);
    }

    /**
     * Calculate the settlementDate Based on some logic
     * @param instruction of which the settlement date will be calculated
     */
    public static void calculateSettlementDate(Instruction instruction) {
        // Select proper strategy based on the Currency
        final IWorkingDays workingDaysMechanism = getWorkingDaysStrategy(instruction.getCurrency());

        // find the correct settlement date
        final LocalDate newSettlementDate =
                workingDaysMechanism.findFirstWorkingDate(instruction.getSettlementDate());

        if (newSettlementDate != null) {
            // set the correct settlement date
            instruction.setSettlementDate(newSettlementDate);
        }
    }

    /**
     * @param currency to choose
     * @return the correct working days 
     */
    private static IWorkingDays getWorkingDaysStrategy(Currency currency) {
        if ((currency.getCurrencyCode().equals("AED")) ||
            (currency.getCurrencyCode().equals("SAR")))
        {
            return ArabicWorkingDays.getInstance();
        }
        return UsualWorkingDays.getInstance();
    }
}
