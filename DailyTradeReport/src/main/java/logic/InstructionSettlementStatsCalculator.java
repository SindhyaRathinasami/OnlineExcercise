package main.java.logic;

import main.java.logic.rank.Rank;
import main.java.instruction.Instruction;
import main.java.instruction.TradeFlag;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

import static java.util.stream.Collectors.*;

/**
 * Define a mapping between dates and the trading amount on those dates, based on instructions
 */
public class InstructionSettlementStatsCalculator {

    // a predicate for outgoing amount
    private final static Predicate<Instruction> outgoingInstructionsPredicate =
            instruction -> instruction.getFlag().equals(TradeFlag.BUY);

    // a predicate for incoming amount
    private final static Predicate<Instruction> incomingInstructionsPredicate =
            instruction -> instruction.getFlag().equals(TradeFlag.SELL);

    /**
     * Finds the daily outgoing trading amount in USD
     * @param instructions to calculate the stats from
     * @return a mapping from date - to total amount
     */
    public static Map<LocalDate, BigDecimal> calculateDailyOutgoingAmount(Set<Instruction> instructions) {
        return calculateDailyAmount(instructions, outgoingInstructionsPredicate);
    }

    /**
     * Finds the daily incoming trading amount in USD
     * @param instructions to calculate the stats from
     * @return a mapping from date - total amount
     */
    public static Map<LocalDate, BigDecimal> calculateDailyIncomingAmount(Set<Instruction> instructions) {
        return calculateDailyAmount(instructions, incomingInstructionsPredicate);
    }

    /**
     * Ranks the daily outgoing by trading amount in USD
     * @param instructions to calculate the stats from
     * @return a map from date to - ranks
     */
    public static Map<String, LinkedList<Rank>> calculateDailyOutgoingRanking(Set<Instruction> instructions) {
        return calculateRanking(instructions, outgoingInstructionsPredicate);
    }

    /**
     * Ranks the daily incoming by trading amount in USD
     * @param instructions to calculate the stats from
     * @return map from date to - ranks 
     */
    public static Map<String, LinkedList<Rank>> calculateDailyIncomingRanking(Set<Instruction> instructions) {
        return calculateRanking(instructions, incomingInstructionsPredicate);
    }

    private static Map<LocalDate, BigDecimal> calculateDailyAmount(
            Set<Instruction> instructions, Predicate<Instruction> predicate)
    {
        return instructions.stream()
                .filter(predicate)
                .collect(groupingBy(Instruction::getSettlementDate,
                    mapping(Instruction::getTradeAmount,
                        reducing(BigDecimal.ZERO, BigDecimal::add))));
    }

    private static Map<String, LinkedList<Rank>> calculateRanking(
            Set<Instruction> instructions, Predicate<Instruction> predicate)
    {
        final Map<String, LinkedList<Rank>> ranking = new HashMap<>();

        instructions.stream()
            .filter(predicate)
            .collect(groupingBy(Instruction::getSettlementDate, toSet()))
            .forEach((date, dailyInstructionSet) -> {
                final AtomicInteger rank = new AtomicInteger(1);

                final LinkedList<Rank> ranks = dailyInstructionSet.stream()
                    .sorted((a, b) -> b.getTradeAmount().compareTo(a.getTradeAmount()))
                    .map(instruction -> new Rank(rank.getAndIncrement(), instruction.getEntity(), date))
                    .collect(toCollection(LinkedList::new));
                String dayofWeek = LocalDate.parse(date.toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.UK);

                ranking.put(dayofWeek, ranks);
            });

        return ranking;
    }
}
