package cinema

fun printCinemaHall(cinemaHall: Array<Array<String>>) {
    println()
    print("Cinema:\n ")

    for (i in 1..cinemaHall[0].size) {
        print(" $i")
    }
    println()

    for (i in cinemaHall.indices) {
        print(i + 1)
        for (j in cinemaHall[i].indices) {
            print(" " + cinemaHall[i][j])
        }
        println()
    }
}

fun menu(): Int {
    println()
    println("1. Show the seats")
    println("2. Buy a ticket")
    println("3. Statistics")
    println("0. Exit")
    return readLine()!!.toInt()
}

fun buyTicket(cinemaHall: Array<Array<String>>) {
    do {
        var input = false
        print("\nEnter a row number: ")
        val ticketNumOfRow = readLine()!!.toInt()
        print("Enter a seat number in that row: ")
        val ticketNumOfSeatsInRow = readLine()!!.toInt()

        if (ticketNumOfRow > cinemaHall.size || ticketNumOfSeatsInRow > cinemaHall[0].size) {
            println("\nWrong input!")
        } else if (cinemaHall[ticketNumOfRow - 1][ticketNumOfSeatsInRow - 1] == "B") {
            println("\nThat ticket has already been purchased!")
        } else {
            cinemaHall[ticketNumOfRow - 1][ticketNumOfSeatsInRow - 1] = "B"
            val price = ticketPrice(cinemaHall, ticketNumOfRow)
            println("Ticket price: $$price")
            input = true
        }
    } while (!input)

}

fun ticketPrice(cinemaHall: Array<Array<String>>, ticketNumOfRow: Int): Int {
    return (if (cinemaHall.size * cinemaHall[0].size <= 60 || ticketNumOfRow <= cinemaHall.size / 2) 10 else 8)
}

fun statistic(cinemaHall: Array<Array<String>>) {
    val ticketCountAndIncome = ticketCountAndCurrentIncome(cinemaHall)
    val percentage = 100 * ticketCountAndIncome[0] / (cinemaHall.size * cinemaHall[0].size).toDouble()
    val currentIncome = ticketCountAndIncome[1]
    val totalIncome = if (cinemaHall.size * cinemaHall[0].size <= 60) {
        cinemaHall.size * cinemaHall[0].size *10
    } else {
        cinemaHall.size / 2 * cinemaHall[0].size * 10 + (cinemaHall.size - cinemaHall.size / 2) * cinemaHall[0].size * 8
    }
    println("\nNumber of purchased tickets: ${ticketCountAndIncome[0]}")
    println("Percentage: %.2f".format(percentage) + "%")
    println("Current income: $$currentIncome")
    println("Total income: $$totalIncome")
}

fun ticketCountAndCurrentIncome(cinemaHall: Array<Array<String>>): IntArray {
    val tickets = IntArray(2)
    for (i in cinemaHall.indices) {
        for (j in cinemaHall[0].indices) {
            if (cinemaHall[i][j] == "B") {
                tickets[0]++
                tickets[1] += ticketPrice(cinemaHall, i + 1)
            }
        }
    }
    return tickets
}

fun main() {
    print("Enter the number of rows: ")
    val numOfRow = readLine()!!.toInt()
    print("Enter the number of seats in each row: ")
    val numOfSeatsInRow = readLine()!!.toInt()

    val cinemaHall = Array(numOfRow, { Array(numOfSeatsInRow , { "S" }) })

    do {
        val n = menu()
        when (n) {
            1 -> printCinemaHall(cinemaHall)
            2 -> buyTicket(cinemaHall)
            3 -> statistic(cinemaHall)
            0 -> println("Exit")
            else -> println("\nTry again")
        }
    } while (n != 0)

}