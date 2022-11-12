package com.edu.apigateway;

public class Abc {

	public static void main(String[] args) {

		Integer arr[] = { 10, 12, 10, 15, -1, 7, 6,5, 4, 2, 1, 1, 1 };

		int curr = 0;
		//int sum = 17;

		for (int i = 0; i < arr.length; i++) {

			for (int j = arr.length-1; j > i; j--) {

				curr = arr[i] + arr[j];
				
				System.out.println(arr[i] + " " + arr[j] + " = " + curr);
			}

		} 

		
	}

}
