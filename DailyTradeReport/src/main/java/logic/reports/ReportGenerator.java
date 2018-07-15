package main.java.logic.reports;

import main.java.logic.SettlementDateFinder;
import main.java.logic.InstructionSettlementStatsCalculator;
import main.java.logic.rank.Rank;
import main.java.instruction.Instruction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class ReportGenerator implements IReportGenerator {
    private StringBuilder stringBuilder = new StringBuilder();

    @Override
    public String generateInstructionsReport(Set<Instruction> instructions) {
        // calculate the correct settlement dates
        SettlementDateFinder.calculateSettlementDates(instructions);

        // the report string building
        return generateDailyOutgoingRanking(instructions,
                generateDailyIncomingRanking(instructions,
                generateDailyIncomingAmount(instructions,
                generateDailyOutgoingAmount(instructions, stringBuilder))))
            .toString();
    }

    private static StringBuilder generateDailyOutgoingAmount(Set<Instruction> instructions, StringBuilder stringBuilder) {
        final Map<LocalDate, BigDecimal> dailyOutgoingAmount =
                InstructionSettlementStatsCalculator.calculateDailyOutgoingAmount(instructions);

        stringBuilder
                .append("\n----------------------------------------\n")
                .append("         Outgoing Amount Per Day         \n")
                .append("----------------------------------------\n")
                .append("      Day       |      Date       |    Trade Amount      \n")
                .append("----------------------------------------\n");

        for (LocalDate date : dailyOutgoingAmount.keySet()) {
            stringBuilder
                .append(LocalDate.parse(date.toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.UK)).append("       |      ").
                    append(date).append("       |      ").
                    append(dailyOutgoingAmount.get(date)).append("\n");
        }

        return stringBuilder;
    }

    private static StringBuilder generateDailyIncomingAmount(Set<Instruction> instructions, StringBuilder stringBuilder) {
        final Map<LocalDate, BigDecimal> dailyOutgoingAmount =
                InstructionSettlementStatsCalculator.calculateDailyIncomingAmount(instructions);

        stringBuilder
                .append("\n----------------------------------------\n")
                .append("         Incoming Amount Per Day         \n")
                .append("----------------------------------------\n")
                .append("      Day       |      Date           Trade Amount      \n")
                .append("----------------------------------------\n");

        for (LocalDate date : dailyOutgoingAmount.keySet()) {
            stringBuilder
                    .append(LocalDate.parse(date.toString(), DateTimeFormatter.ofPattern("yyyy-MM-dd")).
                            getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.UK)).append("       |      ").
                    append(date).append("       |      ").
                    append(dailyOutgoingAmount.get(date)).append("\n");
        }

        return stringBuilder;
    }

    private static StringBuilder generateDailyOutgoingRanking(Set<Instruction> instructions, StringBuilder stringBuilder) {
        final Map<String, LinkedList<Rank>> dailyOutgoingRanking =
                InstructionSettlementStatsCalculator.calculateDailyOutgoingRanking(instructions);

        stringBuilder
                .append("\n----------------------------------------\n")
                .append("         Outgoing Ranking          \n")
                .append("----------------------------------------\n")
                .append("     Day    |     Rank   |   Entity     \n")
                .append("----------------------------------------\n");

        for (String date : dailyOutgoingRanking.keySet()) {
            for (Rank rank : dailyOutgoingRanking.get(date)) {
                stringBuilder
                    .append(date).append("   |      ")
                    .append(rank.getRank()).append("     |    ")
                    .append(rank.getEntity()).append("\n");
            }
        }

        return stringBuilder;
    }

    private static StringBuilder generateDailyIncomingRanking(Set<Instruction> instructions, StringBuilder stringBuilder) {
        final Map<String, LinkedList<Rank>> dailyIncomingRanking =
                InstructionSettlementStatsCalculator.calculateDailyIncomingRanking(instructions);

        stringBuilder
                .append("\n----------------------------------------\n")
                .append("         Incoming Ranking          \n")
                .append("----------------------------------------\n")
                .append("     Day    |     Rank   |   Entity     \n")
                .append("----------------------------------------\n");

        for (String day : dailyIncomingRanking.keySet()) {
            for (Rank rank : dailyIncomingRanking.get(day)) {
                stringBuilder
                        .append(day).append("   |      ")
                        .append(rank.getRank()).append("     |    ")
                        .append(rank.getEntity()).append("\n");
            }
        }

        return stringBuilder;
    }
}
