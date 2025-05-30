package lotto

import lotto.Application.Companion.Prize

class Lotto(private val numbers: List<Int>) {
    init {
        require(numbers.size == 6) { "[ERROR] Lotto must contain exactly 6 numbers." }
        require(numbers.distinct().size == numbers.size) { "[ERROR] Lotto numbers must be unique." }
    }

    /**
     * Calculates the win for the given tickets and bonus number.
     *
     * @param tickets The list of tickets to evaluate.
     * @param bonusNumber The bonus number to consider for the second prize.
     * @return A map containing the prize rank and the count of tickets for each rank.
     */
    fun calculateWin(tickets: List<List<Int>>, bonusNumber: Int): Map<Prize, Int> {
        val results = mutableMapOf<Prize, Int>().apply { Prize.values().forEach { this[it] = 0 } }
        tickets.forEach { ticket ->
            val matchCount = ticket.count { it in numbers }
            val hasBonus = bonusNumber in ticket
            val prize = findPrize(matchCount, hasBonus)
            if (prize != null) {
                results[prize] = results[prize]!! + 1
            }
        }
        return results
    }

    /**
     * Finds the prize based on the match count and whether the bonus number is matched.
     *
     * @param matchCount The number of matches.
     * @param hasBonus Whether the bonus number is matched.
     * @return The corresponding prize, or null if no prize is found.
     */
    protected fun findPrize(
        matchCount: Int,
        hasBonus: Boolean
    ): Prize? {
        val prizeWithMatchCount = Prize.values().count { p -> p.matches == matchCount }

        return Prize.values().find {
            it.matches == matchCount && (it.bonusMatch == hasBonus || prizeWithMatchCount == 1)
        }
    }
}
