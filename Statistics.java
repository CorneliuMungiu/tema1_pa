import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

public class Statistics {
	private int n;
	private String[] words;

	//Read from file
	public void readFromFile(BufferedReader br) throws IOException {
		this.n = Integer.parseInt(br.readLine());
		this.words = new String[this.getN()];
		for (int i = 0; i < this.getN(); i++) {
			words[i] = br.readLine();
		}
	}

	//Intoarce un vector de Integer care reprezinta frecventa unei litere
	//in fiecare subsir. Acesta va fi pozitiv doar in cazul in care exista o
	//litera dominanta
	public static Integer[] frequency(int character, String[] words) {
		Integer[] wordsVec = new Integer[words.length];
		//Pentru fiecare cuvant din lista
		for (int i = 0; i < words.length; i++) {
			int counter = 0;
			//Parcurge fiecare caracter a cuvantului i
			//Incrementeaza daca a gasit litera si decrementeaza in caz ca nu a agsit-o
			for (char x : words[i].toCharArray()) {
				if (x == character) {
					counter++;
					continue;
				}
				counter--;
			}
			wordsVec[i] = counter;
		}
		return wordsVec;
	}

	//Calculeaza numarul maxim de combinatii de subsiruri care au o litera dominanta
	public int longestConcatenatedStr(String[] arr) {
		int max = 0;
		//Parcurge fiecare litera din alfabet
		for (char ch = 'a'; ch <= 'z'; ch++) {
			//Intoarce un vector care contine frecventa literii ch. Pozitia i a vectorului este
			//corespunzatoare subsirului i
			Integer[] v = frequency(ch, arr);
			//Sorteaza descrescator
			Arrays.sort(v,Collections.reverseOrder());
			int current = 0;
			int sum = 0;
			//incrementeaza current atat timp cat suma > 0
			for (int i = 0; i < v.length; i++) {
				sum += v[i];
				if (sum > 0) {
					current++;
				}
			}
			max = Math.max(max, current);
		}
		return max;
	}

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("statistics.in"));
		BufferedWriter bw = new BufferedWriter(new FileWriter("statistics.out"));

		Statistics statistics = new Statistics();
		statistics.readFromFile(br);

		bw.write("" + statistics.longestConcatenatedStr(statistics.getWords()) + "\n");

		bw.close();
		br.close();
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public String[] getWords() {
		return words;
	}

	public void setWords(String[] words) {
		this.words = words;
	}

	public Statistics(int n, String[] words) {
		this.n = n;
		this.words = words;
	}

	public Statistics() {
	}
}
