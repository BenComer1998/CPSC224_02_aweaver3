/****************
Homework #1
January 29, 2019
Ben Comer and Alex Weaver
****************/

import java.lang.String;
import java.util.Scanner;
import java.lang.Math;

public class Yahtzee {
	public static final int DICE_IN_PLAY = 5;

	public static Scanner keyboard = new Scanner(System.in);

	private static int hand[];

	public static void main(String[] args) {
		hand = new int[DICE_IN_PLAY];
		char playAgain = 'y';

		while (playAgain == 'y') {
			String keep = "nnnnn"; // Default setting to roll all dice again
			int turn = 1;
			while (turn < 4 && keep != "yyyyy") {
				// Rolls unheld dice here
				for (int dieNumber = 0; dieNumber < DICE_IN_PLAY; dieNumber++) {
					if (keep.charAt(dieNumber) != 'y')
						hand[dieNumber] = rollDie();
				}

				// Output roll results
				System.out.print("Your roll was: ");
				for (int dieNumber = 0; dieNumber < DICE_IN_PLAY; dieNumber++) {
					System.out.print(hand[dieNumber] + " ");
				}
				System.out.println();

				// If it's not the last roll of the hand, prompt the user.
				if (turn < 3) {
					System.out.print("enter dice to keep (y or n) ");
					keep = keyboard.nextLine();
				}
				turn++;
			}

			// Scoring starts
			// First, we need to sort to check for straights

			sortArray(hand, DICE_IN_PLAY);
			System.out.print("Here is your sorted hand : ");
			for (int dieNumber = 0; dieNumber < DICE_IN_PLAY; dieNumber++) {
				System.out.print(hand[dieNumber] + " ");
			}
			System.out.println();

			// Upper scorecard
			for (int dieValue = 1; dieValue <= 6; dieValue++) {
				int currentCount = 0;
				for (int diePosition = 0; diePosition < 5; diePosition++) {
					if (hand[diePosition] == dieValue)
						currentCount++;
				}
				System.out.println("Score " + (dieValue * currentCount) + " on the " +
					dieValue + " line");
			}

			// Lower scorecard
			if (maxOfAKindFound(hand) >= 3) {
				System.out.println("Score " + totalAllDice(hand) +
					" on the 3 of a Kind line");
			}
			else System.out.println("Score 0 on the 3 of a Kind line");

			if (maxOfAKindFound(hand) >= 4) {
				System.out.println("Score " + totalAllDice(hand) +
					" on the 4 of a Kind line");
			}
			else System.out.println("Score 0 on the 4 of a Kind line");

			if (fullHouseFound(hand))
				System.out.println("Score 25 on the Full House line");
			else
				System.out.println("Score 0 on the Full House line");

			if (maxStraightFound(hand) >= 4)
				System.out.println("Score 30 on the Small Straight line");
			else
				System.out.println("Score 0 on the Small Straight line");

			if (maxStraightFound(hand) >= 5)
				System.out.println("Score 40 on the Large Straight line");
			else
				System.out.println("Score 0 on the Large Straight line");

			if (maxOfAKindFound(hand) >= 5)
				System.out.println("Score 50 on the Yahtzee line");
			else
				System.out.println("Score 0 on the Yahtzee line");

			System.out.println("Score " + totalAllDice(hand) +
				" on the Chance line");

			System.out.println();
			System.out.print("Enter 'y' to play again ");
			playAgain = keyboard.next().charAt(0);
		}

	}

	public static int rollDie() {
		// Math.random gives a random double between 0 and 1 inclusive.
		// This formula gives equal odds to 1 - 6 being rolled.
		int roll = (int) (Math.random() * 6 + 1);
		return roll;
	}

	public static int maxOfAKindFound(int hand[]) {
		int maxCount = 0;
		int currentCount;
		for (int dieValue = 1; dieValue <= 6; dieValue++) {
			currentCount = 0;
			for (int diePosition = 0; diePosition < 5; diePosition++) {
				if (hand[diePosition] == dieValue)
					currentCount++;
			}
			if (currentCount > maxCount)
				maxCount = currentCount;
		}
		return maxCount;
	}

	public static int totalAllDice(int hand[]) {
		int total = 0;
		for (int diePosition = 0; diePosition < 5; diePosition++) {
			total += hand[diePosition];
		}
		return total;
	}

	public static void sortArray(int array[], int size) {
		boolean swap;
		int temp;

		do {
			swap = false;
			for (int count = 0; count < (size - 1); count++) {
				if (array[count] > array[count + 1]) {
					temp = array[count];
					array[count] = array[count + 1];
					array[count + 1] = temp;
					swap = true;
				}
			}
		} while (swap);
	}

	public static int maxStraightFound(int hand[]) {
		int maxLength = 1;
    int curLength = 1;
    for(int counter = 0; counter < 4; counter++)
    {
        if (hand[counter] + 1 == hand[counter + 1] ) //jump of 1
            curLength++;
        else if (hand[counter] + 1 < hand[counter + 1]) //jump of >= 2
            curLength = 1;
        if (curLength > maxLength)
            maxLength = curLength;
    }
    return maxLength;
	}

	public static boolean fullHouseFound(int hand[]) {
		boolean foundFH = false;
    boolean found3K = false;
    boolean found2K = false;
		int currentCount ;
    for (int dieValue = 1; dieValue <=6; dieValue++)
    {
        currentCount = 0;
        for (int diePosition = 0; diePosition < 5; diePosition++)
        {
            if (hand[diePosition] == dieValue)
                currentCount++;
        }
        if (currentCount == 2)
            found2K = true;
        if (currentCount == 3)
            found3K = true;
    }
    if (found2K && found3K)
        foundFH = true;
    return foundFH;
	}

}
