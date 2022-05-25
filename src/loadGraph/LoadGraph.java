package loadGraph;

import java.io.BufferedReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class LoadGraph {
	public static int[][] loadData(String filePath) {
		int[][] arr = null;
		Path path = Paths.get(filePath);
		Charset charset = Charset.forName("US-ASCII");
		try (BufferedReader reader = Files.newBufferedReader(path, charset)) {
			String line = null;
			int count = 0;
			while ((line = reader.readLine()) != null) {
				String k[] = line.split(" ");
				if (k.length == 1) {
					arr = new int[Integer.parseInt(k[0])][Integer.parseInt(k[0])];
				} else {
					for (int i = 0; i < k.length; i++) {
						arr[count][i] = Integer.parseInt(k[i]);
					}
					count++;
				}

			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());

		}

		// ----------------------------------------
//		for (int i = 0; i < arr.length; i++) {
//			for (int j = 0; j < arr.length; j++) {
//
//				System.out.print(arr[i][j] + " ");
//			}
//			System.out.println();
//		}
		// -----------------------------------------

		return arr;
	}


	public static void main(String[] args) {
		int[][] arr = LoadGraph.loadData("5.txt");

		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length; j++) {
				System.out.print(arr[i][j] + " ");
			}
			System.out.println();
		}
	}
}
