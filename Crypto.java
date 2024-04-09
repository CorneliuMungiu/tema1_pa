import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;

public class Crypto {
	private int n;
	private int k;
	private String key;
	private String substring;
	public static final int mod = (int) 1e9 + 7;

	//Citirea din fisier
	public void readFromFile(BufferedReader myReader) {
		try {
			String[] lineSplit = myReader.readLine().split(" ");
			this.n = Integer.parseInt(lineSplit[0]);
			this.k = Integer.parseInt(lineSplit[1]);

			this.key = myReader.readLine();
			this.substring = myReader.readLine();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	//Returneaza numarul de caractere distincte dintr-un String
	public static int cntDistinct(String str) {
		HashSet<Character> s = new HashSet<Character>();
		for (int i = 0; i < str.length(); i++) {
			s.add(str.charAt(i));
		}
		return s.size();
	}


	//Returneaza numarul de aparitii a substringului in toate cheile posibile
	public long getResult() {
		//Caractere Distincte(fara "?")
		int distinctCharacters = cntDistinct(this.getSubstring());
		long[] dp = new long[this.getSubstring().length() + 1];
		dp[0] = 1;
		//Parcurge Stringul key caracter cu caracter
		for (int i = 1; i <= this.getKey().length(); i++) {
			//Parcurge substringul caracter cu carcacter de la dreapta spre stanga
			for (int j = this.getSubstring().length() - 1; j >= 0; j--) {
				//Daca key[i] e litera din substring
				if (this.getKey().charAt(i - 1) == this.getSubstring().charAt(j)) {
					dp[j + 1] = (dp[j + 1] + dp[j]) % mod;
				}
			}
			//Daca key[i] e '?'
			if (this.getKey().charAt(i - 1) == '?') {
				//Parcurge substringul caracter cu carcacter de la dreapta spre stanga
				for (int j = this.getSubstring().length(); j > 0; j--) {
					dp[j] = (distinctCharacters * dp[j] % mod + dp[j - 1]) % mod;
				}
				dp[0] = distinctCharacters * dp[0] % mod;
			}
		}
		return dp[this.getSubstring().length()];
	}


	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("crypto.in"));
		BufferedWriter bw = new BufferedWriter(new FileWriter("crypto.out"));

		Crypto crypto = new Crypto();
		crypto.readFromFile(br);
		bw.write("" + crypto.getResult());

		br.close();
		bw.close();
	}

	public int getN() {
		return n;
	}

	public void setN(int n) {
		this.n = n;
	}

	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getSubstring() {
		return substring;
	}

	public void setSubstring(String substring) {
		this.substring = substring;
	}
}
