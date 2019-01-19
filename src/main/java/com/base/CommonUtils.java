package com.base;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class CommonUtils {

	static ArrayList<String> list1 = new ArrayList<String>();

	/*
	public static void main(String args[]) throws IOException {

		//list1.add("Vlad");
		//list1.add("Andrey");

		//writeListToFile(list1, "OKCUltraMatches.txt");
		list1 = readListFile("OKCUltraMatches.txt");
		System.out.println(list1);
		
		list1.add("neStr");
		System.out.println(list1);
	}*/

	public int getRandomNumberInRange(int min, int max) {

		if (min >= max) {
			throw new IllegalArgumentException("max must be greater than min");
		}
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	public static ArrayList<String> readListFile(String file) throws IOException {

		BufferedReader reader = new BufferedReader(new FileReader(file));
		String currentLine = reader.readLine();
		reader.close();

		// format
		if(!currentLine.isEmpty()) {
			currentLine = currentLine.replaceAll("\\[", "");
			//currentLine = currentLine.replaceAll(",", "\",");
			//currentLine = currentLine.replaceAll(" ", " \"");
			currentLine = currentLine.replaceAll("\\]", "");
		}
		
		List<String> myList = new ArrayList<String>(Arrays.asList(currentLine.split(", ")));
		return (ArrayList<String>) myList;
	}

	public static void writeListToFile(ArrayList<String> list, String file) throws IOException {

		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		writer.write(list.toString());
		writer.close();
	}
}
